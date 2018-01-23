package allbegray.slack;

import allbegray.slack.bot.SlackbotClient;
import allbegray.slack.rtm.SlackRealTimeMessagingClient;
import allbegray.slack.webapi.SlackWebApiClient;
import allbegray.slack.webapi.SlackWebApiClientImpl;

public abstract class SlackClientFactory {

	// web api

	public static SlackWebApiClient createWebApiClient(String token) {
		return new SlackWebApiClientImpl(token);
	}

	// slackbot

	public static SlackbotClient createSlackbotClient(String slackbotUrl) {
		return new SlackbotClient(slackbotUrl);
	}

	public static SlackbotClient createSlackbotClient(String slackbotUrl, int timeout) {
		return new SlackbotClient(slackbotUrl, timeout);
	}

	// rtm

	public static SlackRealTimeMessagingClient createSlackRealTimeMessagingClient(String token) {
		SlackWebApiClient webApiClient = createWebApiClient(token);
		String webSocketUrl = webApiClient.startRealTimeMessagingApi().findPath("url").asText();
		return new SlackRealTimeMessagingClient(webSocketUrl);
	}
}
