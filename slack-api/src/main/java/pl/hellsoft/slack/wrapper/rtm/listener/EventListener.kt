package pl.hellsoft.slack.wrapper.rtm


interface EventListener {
    fun onMessage(message: String)
}
