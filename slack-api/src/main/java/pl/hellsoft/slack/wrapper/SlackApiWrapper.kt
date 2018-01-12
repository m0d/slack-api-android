package pl.hellsoft.slack.wrapper

import allbegray.slack.SlackClientFactory
import allbegray.slack.rtm.Event
import allbegray.slack.rtm.SlackRealTimeMessagingClient
import allbegray.slack.webapi.SlackWebApiClient
import android.annotation.SuppressLint
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.w
import com.google.gson.GsonBuilder
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.schedulers.Schedulers
import pl.hellsoft.slack.wrapper.model.AuthEvent
import pl.hellsoft.slack.wrapper.model.ConnectionEvent
import pl.hellsoft.slack.wrapper.model.MessageEvent
import pl.hellsoft.slack.wrapper.model.MySlack

/**
 * @author Grzegorz Pawełczuk
 * @email grzegorz.pawelczuk@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 22.12.2017
 */

open class SlackApiWrapper {
    private var mToken : String? = null
    private var mWebApiClient: SlackWebApiClient? = null
    private var mRtmClient: SlackRealTimeMessagingClient? = null
    private var mConnected: Boolean = false
    private val mGson by lazy { GsonBuilder().create() }

    fun init(token : String): Observable<SlackApiEvent> = Observable.create { emitter ->
        mToken = token
        if(mConnected){
            disconnect{
                init(emitter)
            }
        }else {
            init(emitter)
        }
    }

    fun getWebApiClient() = mWebApiClient

    private fun init(emitter: ObservableEmitter<SlackApiEvent>){
        var shouldConnect = true
        try {
            mWebApiClient = SlackClientFactory.createWebApiClient(mToken)
            val startRealTimeMessagingApi = mWebApiClient?.startRealTimeMessagingApi()
            startRealTimeMessagingApi?.run {
                val me : MySlack? = mGson.fromJson(startRealTimeMessagingApi.toString(), MySlack::class.java)
                me?.run {
                    mRtmClient = SlackRealTimeMessagingClient(me.url)
                }
            }
        }catch (e: Exception){
            emitter.onNext(ConnectionEvent(false, e.message))
            shouldConnect = false
        } finally {
            if (shouldConnect && !connect()) {
                emitter.onNext(ConnectionEvent(false))
            } else {
                addListeners(emitter)
            }
        }
    }

    private fun connect(): Boolean = try {
        mRtmClient?.connect() ?: false
    } catch (e: Exception) {
        false
    }

    private fun addListeners(emitter: ObservableEmitter<SlackApiEvent>) {
        mRtmClient?.addListener(Event.HELLO) {
            mConnected = true
            mWebApiClient?.run {
                emitter.onNext(ConnectionEvent(true))
                emitter.onNext(AuthEvent(auth()))
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
        mWebApiClient?.run {
            Flowable.fromCallable {
                shutdown()
            }.subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe({
                        callback?.invoke()
                    }, { error ->
                        e { "error ${error.message}" }
                        callback?.invoke()
                    })
        }
    }
}