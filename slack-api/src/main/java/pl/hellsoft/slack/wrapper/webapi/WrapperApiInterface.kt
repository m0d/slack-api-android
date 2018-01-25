package pl.hellsoft.slack.wrapper.webapi

import pl.hellsoft.slack.wrapper.model.HistoryResponse
import pl.hellsoft.slack.wrapper.webapi.model.BotInfoResponse
import pl.hellsoft.slack.wrapper.webapi.model.MeMessageResponse
import pl.hellsoft.slack.wrapper.webapi.model.PostMessageResponse
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
    fun getChannelHistoryEvents(channel: String, latest: String?, oldest: String?, inclusive: Boolean?, count: Int, unreads: Boolean?) : Observable<HistoryResponse>
    fun getBotInfo(bot: String) : Observable<BotInfoResponse>
}