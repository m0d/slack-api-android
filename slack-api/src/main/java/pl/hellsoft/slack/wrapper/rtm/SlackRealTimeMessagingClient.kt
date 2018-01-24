package pl.hellsoft.slack.wrapper.rtm

import allbegray.slack.BuildConfig
import allbegray.slack.rtm.model.Ping
import allbegray.slack.rtm.model.RtmConnect
import android.util.Log
import com.google.gson.Gson
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
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

    private val TAG = "SlackRtmClient"

    private var client: OkHttpClient? = null
    private var ws: WebSocket? = null
    private val NORMAL_CLOSURE_STATUS = 1000

    private val listeners = HashMap<String, MutableList<EventListener>>()
    private val closeListeners = ArrayList<CloseListener>()
    private val failureListeners = ArrayList<FailureListener>()

    private var stop: Boolean = false

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
            Log.i(TAG, "Connected to Slack RTM (Real Time Messaging) server : " + webSocketUrl)
        }

        override fun onMessage(webSocket: WebSocket, text: String?) {
            if (text == null)
                return

            var type: String? = null
            try {
                val connect = Gson().fromJson(text, RtmConnect::class.java)
                type = connect.type
            } catch (e: Exception) {
                Log.e(TAG, e.message, e)
            }

            if ("pong" != type) {
                Log.i(TAG, "Slack RTM message : " + text)
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
            stop = true
            if (!closeListeners.isEmpty()) {
                for (listener in closeListeners) {
                    listener.onClose()
                }
            }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            if (t !is Exception)
                return

            stop = true
            if (!failureListeners.isEmpty()) {
                for (listener in failureListeners) {
                    listener.onFailure(RuntimeException(t))
                }
            }
        }
    }

    fun close() {
        Log.i(TAG, "Slack RTM closing...")
        stop = true
        try {
            client?.dispatcher()?.executorService()?.shutdown()
            client?.dispatcher()?.cancelAll()
            ws?.close(NORMAL_CLOSURE_STATUS, null)
            //ws.cancel();
        } catch (e: Exception) {
            // websocket already closed
        }
        Log.i(TAG, "Slack RTM closed.")
    }

    fun connect(): Boolean {
        try {
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

    private var socketId: Long = 0

    private fun ping() {
        val pingJson = Gson().toJson(Ping(++socketId, "ping"))
        try {
            ws?.send(pingJson)
            Log.d(TAG, "ping : " + pingJson)
        } catch (e: IllegalStateException) {
            Log.d(TAG, "websocket closed before we could write")
            close()
        }
    }

    private fun await() {
        val thread = Thread(Runnable {
            try {
                Thread.sleep(pingMillis.toLong())
                while (!stop) {
                    ping()
                    Thread.sleep(pingMillis.toLong())
                }
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        })
        thread.start()
    }
}