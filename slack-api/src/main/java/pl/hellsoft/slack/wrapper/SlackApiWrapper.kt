package pl.hellsoft.slack.wrapper

import allbegray.slack.BuildConfig
import allbegray.slack.rtm.Event
import allbegray.slack.rtm.SlackRealTimeMessagingClient
import allbegray.slack.webapi.SlackWebApiConstants
import allbegray.slack.webapi.retrofit.*
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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.hellsoft.slack.wrapper.model.AuthEvent
import pl.hellsoft.slack.wrapper.model.ConnectionEvent
import pl.hellsoft.slack.wrapper.model.MessageEvent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Grzegorz Pawełczuk
 * @email grzegorz.pawelczuk@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 22.12.2017
 */

open class SlackApiWrapper {
    private var compositeDisposable = CompositeDisposable()
    private lateinit var mToken : String
    private val timeoutInSeconds = 6L
    private var service: SlackService

    private var mRtmClient: SlackRealTimeMessagingClient? = null
    private var mConnected: Boolean = false
    private val mGson by lazy { GsonBuilder().create() }

    private lateinit var apiInterface: WrapperApiInterface
    private lateinit var rtmInterface: WrapperRtmInterface

    init {
        val okHttpClient = prepareOkHttpBuilder().build()

        service = Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .baseUrl(SlackWebApiConstants.SLACK_WEB_API_URL + "/")
                .build()
                .create(SlackService::class.java)
    }

    private fun prepareOkHttpBuilder() : OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
                .connectTimeout(timeoutInSeconds, TimeUnit.SECONDS)
                .writeTimeout(timeoutInSeconds, TimeUnit.SECONDS)
                .readTimeout(timeoutInSeconds, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }
        return builder
    }

    open fun init(token : String): Observable<SlackApiEvent> = Observable.create { emitter ->
        mToken = token
        apiInterface = WebApiImpl(service, mToken)
        rtmInterface = RtmApiImpl(service, mToken)
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
        val disposable = rtmInterface.rtmStart()
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    response ->
                    response?.run {
                    mRtmClient = SlackRealTimeMessagingClient(response.url)
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
        compositeDisposable.add(disposable)
    }

    private fun connect(): Boolean = try {
        mRtmClient?.connect() ?: false
    } catch (e: Exception) {
        false
    }

    private fun addListeners(emitter: ObservableEmitter<SlackApiEvent>) {
        mRtmClient?.addListener(Event.HELLO) {
            mConnected = true
            mToken?.run {
                val disposable = service.auth(this)
                        .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            response -> response?.run {
                            emitter.onNext(ConnectionEvent(true))
                            emitter.onNext(AuthEvent(response))
                        }
                        }, {
                            error -> emitter.onNext(ConnectionEvent(false, error.message))
                            mConnected = false
                        })
                compositeDisposable.add(disposable)
            }
        }

        mRtmClient?.addCloseListener {
            mConnected = false
            emitter.onNext(ConnectionEvent(false, "Conection closed"))
        }

        mRtmClient?.addFailureListener { throwable ->
            mConnected = false
            emitter.onNext(ConnectionEvent(false, throwable.message))
        }

        mRtmClient?.addListener(Event.MESSAGE) { messageNode ->
            w { "Event.MESSAGE: $messageNode" }
            try {
                val messageEvent : MessageEvent? = mGson.fromJson(messageNode.toString(), MessageEvent::class.java)
                messageEvent?.run {
                    emitter.onNext(messageEvent)
                }
            }catch (err: Exception){
                e{err.localizedMessage}
            }
        }
    }

    @SuppressLint("CheckResult")
    fun disconnect(callback: (() -> Unit)? = null) {
        mRtmClient?.run {
            close()
        }
        compositeDisposable.dispose()
        callback?.invoke()
    }

    fun getWebApiInterface() : WrapperApiInterface {
        return apiInterface
    }

    fun getRtminterface() : WrapperRtmInterface {
        return rtmInterface
    }
}