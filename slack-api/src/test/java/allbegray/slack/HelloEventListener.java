package allbegray.slack;

import allbegray.slack.rtm.EventListener;
import com.fasterxml.jackson.databind.JsonNode;

public class HelloEventListener implements EventListener {

	@Override
	public void onMessage(String message) {
		System.out.println(message);
	}

}
