package allbegray.slack.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

import pl.hellsoft.slack.wrapper.model.MessageEvent;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryEvents {

	private String latest;
	private List<MessageEvent> messages;
	private Boolean has_more;

	public String getLatest() {
		return latest;
	}

	public void setLatest(String latest) {
		this.latest = latest;
	}

	public List<MessageEvent> getMessages() {
		if (messages == null) {
			messages = new ArrayList<MessageEvent>();
		}
		return messages;
	}

	public void setMessages(List<MessageEvent> messages) {
		this.messages = messages;
	}

	public Boolean getHas_more() {
		return has_more;
	}

	public void setHas_more(Boolean has_more) {
		this.has_more = has_more;
	}

	@Override
	public String toString() {
		return "HistoryEvents [latest=" + latest + ", messages=" + messages + ", has_more=" + has_more + "]";
	}

}
