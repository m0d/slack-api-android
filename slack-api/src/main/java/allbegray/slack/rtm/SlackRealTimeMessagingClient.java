package allbegray.slack.rtm;

import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import allbegray.slack.BuildConfig;
import allbegray.slack.exception.SlackException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.logging.HttpLoggingInterceptor;

public class SlackRealTimeMessagingClient {

	private static final String TAG = "SlackRtmClient";

	private static OkHttpClient client;
	private static WebSocket ws;
	private static final int NORMAL_CLOSURE_STATUS = 1000;
	private String webSocketUrl;
	private ProxyServerInfo proxyServerInfo;
	private Map<String, List<EventListener>> listeners = new HashMap<String, List<EventListener>>();
	private List<CloseListener> closeListeners = new ArrayList<CloseListener>();
	private List<FailureListener> failureListeners = new ArrayList<FailureListener>();
	private boolean stop;
	private ObjectMapper mapper;
	private Integer pingMillis;

	public SlackRealTimeMessagingClient(String webSocketUrl) {
		this(webSocketUrl, null, null, null);
	}

	public SlackRealTimeMessagingClient(String webSocketUrl, ObjectMapper mapper) {
		this(webSocketUrl, null, mapper, null);
	}

	public SlackRealTimeMessagingClient(String webSocketUrl, Integer pingMillis) {
		this(webSocketUrl, null, null, pingMillis);
	}

	public SlackRealTimeMessagingClient(String webSocketUrl, ProxyServerInfo proxyServerInfo, ObjectMapper mapper) {
		this(webSocketUrl, proxyServerInfo, mapper, null);
	}

	public SlackRealTimeMessagingClient(String webSocketUrl, ProxyServerInfo proxyServerInfo, ObjectMapper mapper, Integer pingMillis) {
		if (mapper == null) {
			mapper = new ObjectMapper();
		}
		if (pingMillis == null) {
			pingMillis = 3 * 1000;
		}
		this.webSocketUrl = webSocketUrl;
		this.proxyServerInfo = proxyServerInfo;
		this.mapper = mapper;
		this.pingMillis = pingMillis;
	}

	private WebSocketListener webSocketListener = new WebSocketListener() {
		@Override
		public void onOpen(WebSocket webSocket, Response response) {
			Log.i(TAG, "Connected to Slack RTM (Real Time Messaging) server : " + webSocketUrl);
		}

		@Override
		public void onMessage(WebSocket webSocket, String text) {
			if (text == null)
				return;

			String type = null;
			JsonNode node = null;
			try {
				node = mapper.readTree(text);
				type = node.findPath("type").asText();
			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
			}

			if (!"pong".equals(type)) {
				Log.i(TAG, "Slack RTM message : " + text);
			}

			if (type != null) {
				List<EventListener> eventListeners = listeners.get(type);
				if (eventListeners != null && !eventListeners.isEmpty()) {
					for (EventListener listener : eventListeners) {
						listener.onMessage(node);
					}
				}
			}
		}

		@Override
		public void onClosed(WebSocket webSocket, int code, String reason) {
			stop = true;
			if (closeListeners != null && !closeListeners.isEmpty()) {
				for (CloseListener listener : closeListeners) {
					listener.onClose();
				}
			}
		}

		@Override
		public void onFailure(WebSocket webSocket, Throwable t, Response response) {
			if (!(t instanceof Exception))
				return;

			stop = true;
			Exception e = (Exception) t;
			if (failureListeners != null && !failureListeners.isEmpty()) {
				for (FailureListener listener : failureListeners) {
					listener.onFailure(new SlackException(e));
				}
			}
		}
	};

	public void addListener(Event event, EventListener listener) {
		addListener(event.name().toLowerCase(), listener);
	}

	public void addListener(String event, EventListener listener) {
		List<EventListener> eventListeners = listeners.get(event);
		if (eventListeners == null) {
			eventListeners = new ArrayList<EventListener>();
			listeners.put(event, eventListeners);
		}
		eventListeners.add(listener);
	}

	public void addCloseListener(CloseListener listener) {
		closeListeners.add(listener);
	}

	public void addFailureListener(FailureListener listener) {
		failureListeners.add(listener);
	}

	public void close() {
		Log.i(TAG, "Slack RTM closing...");
		stop = true;
		if (ws != null) {
			try {
                client.dispatcher().executorService().shutdown();
			    client.dispatcher().cancelAll();
				ws.close(NORMAL_CLOSURE_STATUS, null);
				//ws.cancel();
			} catch (Exception e) {
				// websocket already closed
			}
		}
		Log.i(TAG, "Slack RTM closed.");
	}

	public boolean connect() {
		try {
			OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
					.connectTimeout(0, TimeUnit.MILLISECONDS)
					.readTimeout(0, TimeUnit.MILLISECONDS)
					.writeTimeout(0, TimeUnit.MILLISECONDS);

			if (proxyServerInfo != null) {
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyServerInfo.getHost(), proxyServerInfo.getPort()));
				clientBuilder.proxy(proxy);
			}

			Request request = new Request.Builder().url(webSocketUrl).build();
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel( HttpLoggingInterceptor.Level.BODY );
                clientBuilder.addInterceptor(interceptor);
            }
			client = clientBuilder.build();

			ws = client.newWebSocket(request, webSocketListener);
			client.dispatcher().executorService().shutdown();

			await();

		} catch (Exception e) {
			close();
			throw new SlackException(e);
		}
		return true;
	}

	private long socketId = 0;

	private void ping() {
		ObjectNode pingMessage = mapper.createObjectNode();
		pingMessage.put("id", ++socketId);
		pingMessage.put("type", "ping");
		String pingJson = pingMessage.toString();
		if (ws != null) {
			try {
				ws.send(pingJson);
				Log.d(TAG, "ping : " + pingJson);
			} catch (IllegalStateException e) {
				Log.d(TAG, "websocket closed before we could write");
				close();
			}
		}
	}

	private void await() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(pingMillis);
					while (!stop) {
						ping();
						Thread.sleep(pingMillis);
					}
				} catch (Exception e) {
					throw new SlackException(e);
				}
			}
		});
		thread.start();
	}

}
