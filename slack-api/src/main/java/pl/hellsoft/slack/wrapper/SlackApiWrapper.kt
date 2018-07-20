package pl.hellsoft.slack.wrapper

import android.annotation.SuppressLint
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.w
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.hellsoft.slack.wrapper.model.AuthEvent
import pl.hellsoft.slack.wrapper.model.ConnectionEvent
import pl.hellsoft.slack.wrapper.model.MessageEvent
import pl.hellsoft.slack.wrapper.rtm.*
import pl.hellsoft.slack.wrapper.rtm.listener.CloseListener
import pl.hellsoft.slack.wrapper.rtm.listener.EventListener
import pl.hellsoft.slack.wrapper.rtm.listener.FailureListener
import pl.hellsoft.slack.wrapper.webapi.WebApiImpl
import pl.hellsoft.slack.wrapper.webapi.WrapperApiInterface
import pl.hellsoft.slack.wrapper.webapi.model.AuthTestResponse
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.TimeUnit

/**
 * @author Grzegorz Pawełczuk
 * @email grzegorz.pawelczuk@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 22.12.2017
 */

open class SlackApiWrapper {
    private var mCompositeDisposable = CompositeDisposable()
    private lateinit var mToken : String
    private val mTimeoutInSeconds = 6L
    private var service: SlackService

    private var mRtmClient: SlackRealTimeMessagingClient? = null
    private var mConnected: Boolean = false
    private val mGson by lazy { GsonBuilder().create() }

    private var mApiInterface: WrapperApiInterface? = null
    private var mRtmInterface: WrapperRtmInterface? = null

    init {
        val okHttpClient = prepareOkHttpBuilder().build()

        service = Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .baseUrl(SlackApiConstants.SLACK_WEB_API_URL + "/")
                .build()
                .create(SlackService::class.java)
    }

    private fun prepareOkHttpBuilder() : OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
                .connectTimeout(mTimeoutInSeconds, TimeUnit.SECONDS)
                .writeTimeout(mTimeoutInSeconds, TimeUnit.SECONDS)
                .readTimeout(mTimeoutInSeconds, TimeUnit.SECONDS)

        val certificatePinner = CertificatePinner.Builder()
        try {
            certificatePinner
                    .add(
                        "slack.com",
                        "sha256/iI/JZwKo1G3TTBdHG9cbCyrqMXHb8xHIlrOw7fRvBuY="
                    )
        }catch (e: MalformedURLException){
            w{"certificate pinning exception"}
        }
        builder.certificatePinner(certificatePinner.build())
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)

        return builder
    }

    open fun isInitialized(): Boolean =  ::mToken.isInitialized

    open fun setDependencies(token : String){
        mApiInterface = WebApiImpl(service, token)
        mRtmInterface = RtmApiImpl(service, token)
    }

    open fun init(token : String): Observable<SlackApiEvent> = Observable.create { emitter ->
        mCompositeDisposable = CompositeDisposable()

        if(!isInitialized() || mToken != token) {
            setDependencies(token)
        }

        mToken = token

        if(mConnected){
            disconnect{
                prepare(emitter)
            }
        } else {
            prepare(emitter)
        }
    }

    private fun prepare(emitter: ObservableEmitter<SlackApiEvent>){
        var shouldConnect = true
        mRtmInterface?.run {
            val disposable = rtmStart()
                    .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        response ->
                        response?.run {
                            mRtmClient = SlackRealTimeMessagingClient(url ?: "", proxyServerInfo = null)
                        }
                    }, {
                        error ->
                        emitter.onNext(ConnectionEvent(false, error.message))
                        shouldConnect = false
                    }, {
                        if (shouldConnect && !connect()) {
                            emitter.onNext(ConnectionEvent(false))
                        } else {
                            addListeners(emitter)
                        }
                    })
            mCompositeDisposable.add(disposable)
        }
    }

    private fun connect(): Boolean = try {
        mRtmClient?.connect() ?: false
    } catch (e: Exception) {
        false
    }

    private fun addListeners(emitter: ObservableEmitter<SlackApiEvent>) {

        mRtmClient?.addListener(Event.HELLO, object : EventListener {
            override fun onMessage(message: String) {

                mConnected = true
                mToken.run {
                    val disposable = service.auth(this)
                            .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ response -> response?.run {
                                emitter.onNext(ConnectionEvent(true))
                                emitter.onNext(AuthEvent(response))
                            }
                            }, { error -> emitter.onNext(ConnectionEvent(false, error.message))
                                mConnected = false
                            })
                    mCompositeDisposable.add(disposable)
                }
            }

        })

        mRtmClient?.addCloseListener(object : CloseListener {
            override fun onClose() {
                mConnected = false
                emitter.onNext(ConnectionEvent(false, "Connection closed"))
            }
        })

        mRtmClient?.addFailureListener(object : FailureListener {
            override fun onFailure(t: Throwable) {
                mConnected = false
                emitter.onNext(ConnectionEvent(false, t.message))
                disconnect()
            }

        })

        mRtmClient?.addListener(Event.MESSAGE, object : EventListener {
            override fun onMessage(message: String) {
                w { "Event.MESSAGE: $message" }
                try {
                    val messageEvent : MessageEvent? = mGson.fromJson(message, MessageEvent::class.java)
                    messageEvent?.run {
                        emitter.onNext(messageEvent)
                    }
                }catch (err: Exception){
                    e{err.localizedMessage}
                }
            }

        })

    }

    @SuppressLint("CheckResult")
    open fun disconnect(callback: (() -> Unit)? = null) {
        mRtmClient?.run {
            close()
        }
        if(!mCompositeDisposable.isDisposed) {
            mCompositeDisposable.dispose()
        }
        callback?.invoke()
    }

    open fun getWebApiInterface() : WrapperApiInterface? {
        return mApiInterface
    }

    open fun getRtminterface() : WrapperRtmInterface? {
        return mRtmInterface
    }

    open fun authTest(token: String): Observable<AuthTestResponse> {
        return service.auth(token)
    }

}