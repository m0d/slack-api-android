package pl.hellsoft.slack.wrapper.model

data class HistoryResponse(
        var ok : Boolean,
        var latest: String? = null,
        var messages: List<MessageEvent>,
        var has_more: Boolean = false,
        var error: String? = null)