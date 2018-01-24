package pl.hellsoft.slack.wrapper.webapi

import pl.hellsoft.slack.wrapper.model.HistoryEvents
import allbegray.slack.webapi.model.BotInfoResponse
import allbegray.slack.webapi.model.MeMessageResponse
import allbegray.slack.webapi.model.PostMessageResponse
import io.reactivex.Observable

/**
 * @author Maciej Madetko
 * @email maciej.madetko@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 19/01/2018.
 */
interface WrapperApiInterface {
    fun postMessage(channel: String, message: String) : Observable<PostMessageResponse>
    fun meMessage(channel: String, message: String) : Observable<MeMessageResponse>
    fun getChannelHistoryEvents(channel: String, latest: String?, oldest: String?, inclusive: Boolean?, count: Int, unreads: Boolean?) : Observable<HistoryEvents>
    fun getBotInfo(bot: String) : Observable<BotInfoResponse>
}