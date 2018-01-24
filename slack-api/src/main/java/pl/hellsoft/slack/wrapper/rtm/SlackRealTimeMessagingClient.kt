package pl.hellsoft.slack.wrapper.rtm

import allbegray.slack.BuildConfig
import allbegray.slack.rtm.model.Ping
import allbegray.slack.rtm.model.RtmConnect
import android.util.Log
import com.github.ajalt.timberkt.Timber.d
import com.github.ajalt.timberkt.Timber.e
import com.github.ajalt.timberkt.Timber.i
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import pl.hellsoft.slack.wrapper.rtm.listener.CloseListener
import pl.hellsoft.slack.wrapper.rtm.listener.EventListener
import pl.hellsoft.slack.wrapper.rtm.listener.FailureListener
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author Maciej Madetko
 * @email maciej.madetko@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 23/01/2018.
 */
class SlackRealTimeMessagingClient(val webSocketUrl: String?, val proxyServerInfo: ProxyServerInfo? = null, val pingMillis: Int = 3000) {

    init {
        d { "SlackRealTimeMessagingClient init" }
    }
    companion object {
        const val NORMAL_CLOSURE_STATUS = 1000
    }

    private lateinit var disposables: CompositeDisposable

    private var client: OkHttpClient? = null
    private var ws: WebSocket? = null
    private val gson by lazy { GsonBuilder().create() }


    private val listeners = HashMap<String, MutableList<EventListener>>()
    private val closeListeners = ArrayList<CloseListener>()
    private val failureListeners = ArrayList<FailureListener>()

    private var socketId: Long = 0

    fun addListener(event: Event, listener: EventListener) {
        addListener(event.name.toLowerCase(), listener)
    }

    private fun addListener(event: String, listener: EventListener) {
        var eventListeners: MutableList<EventListener>? = listeners[event]
        if (eventListeners == null) {
            eventListeners = ArrayList()
            listeners[event] = eventListeners
        }
        eventListeners.add(listener)
    }

    fun addCloseListener(listener: CloseListener) {
        closeListeners.add(listener)
    }

    fun addFailureListener(listener: FailureListener) {
        failureListeners.add(listener)
    }

    private val webSocketListener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            i { "Connected to Slack RTM (Real Time Messaging) server : " + webSocketUrl }
        }

        override fun onMessage(webSocket: WebSocket, text: String?) {
            if (text == null)
                return

            var type: String? = null
            try {
                val connect = Gson().fromJson(text, RtmConnect::class.java)
                type = connect.type
            } catch (e: Exception) {
                e { "$e.message" }
            }

            if ("pong" != type) {
                i { "Slack RTM message : $text" }
            }

            if (type != null) {
                val eventListeners = listeners[type]
                if (eventListeners != null && !eventListeners.isEmpty()) {
                    for (listener in eventListeners) {
                        listener.onMessage(text)
                    }
                }
            }
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            if (!closeListeners.isEmpty()) {
                for (listener in closeListeners) {
                    listener.onClose()
                }
            }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            if (t !is Exception)
                return

            if (!failureListeners.isEmpty()) {
                for (listener in failureListeners) {
                    listener.onFailure(RuntimeException(t))
                }
            }
        }
    }

    fun close() {
        i { "Slack RTM closing..." }
        try {
            client?.dispatcher()?.executorService()?.shutdown()
            client?.dispatcher()?.cancelAll()
            ws?.close(NORMAL_CLOSURE_STATUS, null)
            //ws.cancel();
        } catch (e: Exception) {
            e { "web socket already closed" }
        } finally {
            disposables.dispose()
        }
        i { "Slack RTM closed." }
    }

    fun connect(): Boolean {
        try {
            disposables = CompositeDisposable()

            val clientBuilder = OkHttpClient.Builder()
                    .connectTimeout(0, TimeUnit.MILLISECONDS)
                    .readTimeout(0, TimeUnit.MILLISECONDS)
                    .writeTimeout(0, TimeUnit.MILLISECONDS)

            if (proxyServerInfo != null) {
                val proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress(proxyServerInfo.host, proxyServerInfo.port))
                clientBuilder.proxy(proxy)
            }

            val request = Request.Builder().url(webSocketUrl).build()
            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                clientBuilder.addInterceptor(interceptor)
            }
            client = clientBuilder.build()

            ws = client?.newWebSocket(request, webSocketListener)
            client?.dispatcher()?.executorService()?.shutdown()

            await()

        } catch (e: Exception) {
            close()
            throw RuntimeException(e)
        }

        return true
    }

    private fun ping() {
        val pingJson = gson.toJson(Ping(++socketId, "ping"))
        try {
            ws?.send(pingJson)
            d { "ping : $pingJson" }
        } catch (e: IllegalStateException) {
            d { "web socket closed before we could write" }
            close()
        }
    }

    private fun await() {
        disposables.add(Observable.interval(pingMillis.toLong(), TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { ping() },
                        { err -> e { "await ping error $err" } }
                ))
    }
}