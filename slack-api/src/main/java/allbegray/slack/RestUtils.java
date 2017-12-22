package allbegray.slack;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import allbegray.slack.exception.SlackException;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.config.RequestConfig;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.impl.conn.PoolingHttpClientConnectionManager;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public abstract class RestUtils {

	private static final String TAG = "SlackClient";

	public static HttpEntity createUrlEncodedFormEntity(Map<String, String> parameters) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>(parameters.size());
		for (Entry<String, String> entry : parameters.entrySet()) {
			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8"));
	}

	public static HttpEntity createMultipartFormEntity(Map<String, String> parameters, InputStream is) {
		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
		multipartEntityBuilder.setCharset(Charset.forName("UTF-8"));

		multipartEntityBuilder.addBinaryBody("file", is, ContentType.create("application/octet-stream"), "file");
		for (Entry<String, String> entry : parameters.entrySet()) {
			multipartEntityBuilder.addTextBody(entry.getKey(), entry.getValue());
		}
		return multipartEntityBuilder.build();
	}

	public static CloseableHttpClient createHttpClient(int timeout) {
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		CloseableHttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig).build();
		return httpClient;
	}

	public static String execute(CloseableHttpClient httpClient, String url, HttpEntity httpEntity) {
		Log.i(TAG, "url : " + url);

		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(httpEntity);
			String retStr = httpClient.execute(httpPost, new StringResponseHandler());
			
			Log.i(TAG, "return : " + retStr);
			
			return retStr;
		} catch (IOException e) {
			throw new SlackException(e);
		}
	}

}
