package pl.hellsoft.slack.wrapper

import allbegray.slack.SlackClientFactory
import allbegray.slack.rtm.Event
import allbegray.slack.rtm.SlackRealTimeMessagingClient
import allbegray.slack.webapi.SlackWebApiClient
import pl.hellsoft.slack.wrapper.model.AuthEvent
import pl.hellsoft.slack.wrapper.model.ConnectionEvent
import pl.hellsoft.slack.wrapper.model.MessageEvent
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.w
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.schedulers.Schedulers

/**
 * @author Grzegorz Pawełczuk
 * @email grzegorz.pawelczuk@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 22.12.2017
 */

class SlackApiWrapper(val token: String) {
    private var mWebApiClient: SlackWebApiClient? = null
    private var mWebSocketUrl: String? = null
    private var mRtmClient: SlackRealTimeMessagingClient? = null
    private var mConnected: Boolean = false

    fun init(): Observable<SlackApiEvent> = Observable.create { emitter ->
        if(mConnected){
            disconnect{
                init(emitter)
            }
        }else {
            init(emitter)
        }
    }

    private fun init(emitter: ObservableEmitter<SlackApiEvent>){
        var shouldConnect = true
        try {
            mWebApiClient = SlackClientFactory.createWebApiClient(token)
            mWebSocketUrl = mWebApiClient?.startRealTimeMessagingApi()?.findPath("url")?.asText()
            mRtmClient = SlackRealTimeMessagingClient(mWebSocketUrl)
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

    private val mObjectMapper: ObjectMapper by lazy {
        ObjectMapper().registerKotlinModule()
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
            val messageEvent = mObjectMapper.treeToValue(messageNode, MessageEvent::class.java)
            messageEvent?.run {
                emitter.onNext(messageEvent)
            }
        }
    }

    fun disconnect( callback: (() -> Unit)? = null) {
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