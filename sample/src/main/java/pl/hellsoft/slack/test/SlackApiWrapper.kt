package pl.hellsoft.slack.test

import allbegray.slack.SlackClientFactory
import allbegray.slack.rtm.Event
import allbegray.slack.rtm.SlackRealTimeMessagingClient
import allbegray.slack.webapi.SlackWebApiClient
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.w
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author Grzegorz Pawełczuk
 * @email gpawelczuk@hellsoft.pl
 * @since 19.12.2017
 */

class SlackApiWrapper(val token: String){
    private var mWebApiClient : SlackWebApiClient? = null
    private var mWebSocketUrl: String? = null
    private var mRtmClient : SlackRealTimeMessagingClient? = null
    private var mConnected: Boolean = false

    fun init() = Flowable.fromCallable{
        mWebApiClient = SlackClientFactory.createWebApiClient(token)
        mWebSocketUrl = mWebApiClient?.startRealTimeMessagingApi()?.findPath("url")?.asText()
        mRtmClient = SlackRealTimeMessagingClient(mWebSocketUrl)
    }

    fun connect() : Observable<Boolean> = Observable.create{ emitter ->
        emitter.onNext(mRtmClient?.connect() ?: false)
        emitter.onComplete()
    }

    fun connectionListener() : Observable<Boolean> = Observable.create{ emitter ->
        mRtmClient?.addListener(Event.HELLO) {
            w{"Event.HELLO received"}
            mConnected = true
            emitter.onNext(true)
        }

        mRtmClient?.addCloseListener {
            w{"Connection closed"}
            mConnected = false
            emitter.onNext(false)
        }

        mRtmClient?.addFailureListener { throwable ->
            e{"Failure message: " + throwable.message}
            mConnected = false
            emitter.onNext(false)
        }
    }

    fun authListener() : Observable<Boolean> = Observable.create{ emitter ->
        mRtmClient?.addListener(Event.HELLO) {
            mConnected = true
            emitter.onNext(true)
        }
    }

    fun onMessageReceived() : Observable<Boolean> = Observable.create { emitter ->
        mRtmClient?.addListener(Event.MESSAGE) { message ->
            d{"Event.MESSAGE: $message"}
            //emitter.onNext(message.)
        }
    }

    fun disconnect(){
        mRtmClient?.run {
            close()
        }
        mWebApiClient?.run {
            Flowable.fromCallable {
                shutdown()
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({},{ error -> e{"error ${error.message}"}})
        }
    }

}