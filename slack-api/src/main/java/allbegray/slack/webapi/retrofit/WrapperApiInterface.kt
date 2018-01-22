package allbegray.slack.webapi.retrofit

import allbegray.slack.type.HistoryEvents
import allbegray.slack.webapi.retrofit.model.PostMessageResponse
import io.reactivex.Observable

/**
 * @author Maciej Madetko
 * @email maciej.madetko@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 19/01/2018.
 */
interface WrapperApiInterface {
    fun postMessage(channel: String, message: String) : Observable<PostMessageResponse>
    fun getChannelHistoryEvents(channel: String, latest: String?, oldest: String?, inclusive: Boolean?, count: Int, unreads: Boolean?) : Observable<HistoryEvents>
}