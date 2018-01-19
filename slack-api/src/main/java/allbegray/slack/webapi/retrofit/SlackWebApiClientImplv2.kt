package allbegray.slack.webapi.retrofit

import allbegray.slack.webapi.SlackWebApiClientImpl
import allbegray.slack.webapi.SlackWebApiConstants
import retrofit2.Retrofit

/**
 * @author Maciej Madetko
 * @email maciej.madetko@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 19/01/2018.
 */
class SlackWebApiClientImplv2(private val slackToken: String) : SlackWebApiClientImpl(slackToken) {

    private var service: SlackService = Retrofit.Builder()
            .baseUrl(SlackWebApiConstants.SLACK_WEB_API_URL)
            .build()
            .create(SlackService::class.java)


    override fun postMessage(channel: String, text: String): String {
        service.postMessage(slackToken, channel, text)
        return ""
    }
}