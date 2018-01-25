package pl.hellsoft.slack.wrapper.rtm.listener


interface EventListener {
    fun onMessage(message: String)
}
