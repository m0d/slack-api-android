package pl.hellsoft.slack.wrapper.rtm

interface FailureListener {
    fun onFailure(t: Throwable)
}
