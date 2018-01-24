package allbegray.slack.rtm

interface FailureListener {
    fun onFailure(t: Throwable)
}
