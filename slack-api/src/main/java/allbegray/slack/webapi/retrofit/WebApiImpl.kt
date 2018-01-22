package allbegray.slack.webapi.retrofit

import allbegray.slack.type.HistoryEvents
import allbegray.slack.webapi.retrofit.model.PostMessageResponse
import io.reactivex.Observable

/**
 * @author Maciej Madetko
 * @email maciej.madetko@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 22/01/2018.
 */
class WebApiImpl(private val service: SlackService, private val token: String) : WrapperApiInterface {
    override fun postMessage(channel: String, message: String) : Observable<PostMessageResponse> {
        return service.postMessage(token, channel, message)
    }

    override fun getChannelHistoryEvents(channel: String, latest: String?, oldest: String?, inclusive: Boolean?, count: Int, unreads: Boolean?): Observable<HistoryEvents> {
        return service.getChannelHistoryEvents(token, channel, latest, oldest, inclusive, count, unreads)
    }
}