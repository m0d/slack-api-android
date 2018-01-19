package allbegray.slack.type

import pl.hellsoft.slack.wrapper.model.MessageEvent

data class HistoryEvents(
        var ok : Boolean,
        var latest: String? = null,
        var messages: List<MessageEvent>,
        var has_more: Boolean = false)