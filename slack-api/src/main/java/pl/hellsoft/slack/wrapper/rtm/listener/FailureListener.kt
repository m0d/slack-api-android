package pl.hellsoft.slack.wrapper.rtm.listener

interface FailureListener {
    fun onFailure(t: Throwable)
}
