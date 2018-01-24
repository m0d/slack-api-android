package allbegray.slack.rtm


interface EventListener {
    fun onMessage(message: String)
}
