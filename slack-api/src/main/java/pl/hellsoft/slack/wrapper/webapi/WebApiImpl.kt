package pl.hellsoft.slack.wrapper.webapi

import pl.hellsoft.slack.wrapper.model.HistoryResponse
import pl.hellsoft.slack.wrapper.webapi.model.BotInfoResponse
import pl.hellsoft.slack.wrapper.webapi.model.MeMessageResponse
import pl.hellsoft.slack.wrapper.webapi.model.PostMessageResponse
import io.reactivex.Observable
import pl.hellsoft.slack.wrapper.SlackService
import pl.hellsoft.slack.wrapper.webapi.model.UsersInfoResponse

/**
 * @author Maciej Madetko
 * @email maciej.madetko@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 22/01/2018.
 *
 * @author Grzegorz Pawełczuk
 * @email grzegorz.pawelczuk@ftlearning.com
 * @desc history methods impl.
 * @since 22/02/2018
 */

class WebApiImpl(private val service: SlackService, private val token: String) : WrapperApiInterface {
    override fun postMessage(channel: String, message: String): Observable<PostMessageResponse> {
        return service.postMessage(token, channel, message)
    }

    override fun meMessage(channel: String, message: String): Observable<MeMessageResponse> {
        return service.meMessage(token, channel, message)
    }

    override fun getChannelHistoryEvents(channel: String, latest: String?, oldest: String?, inclusive: Boolean?, count: Int, unreads: Boolean?): Observable<HistoryResponse> {
        return service.getChannelHistory(token, channel, latest, oldest, inclusive, count, unreads)
    }

    override fun getBotInfo(bot: String): Observable<BotInfoResponse> {
        return service.getBotInfo(token, bot)
    }

    override fun getImHistoryEvents(channel: String, latest: String?, oldest: String?, inclusive: Boolean?, count: Int, unreads: Boolean?): Observable<HistoryResponse> {
        return service.getImHistory(token, channel, latest, oldest, inclusive, count, unreads)
    }

    override fun getGroupsHistoryEvents(channel: String, latest: String?, oldest: String?, inclusive: Boolean?, count: Int, unreads: Boolean?): Observable<HistoryResponse> {
        return service.getGroupsHistory(token, channel, latest, oldest, inclusive, count, unreads)
    }

    override fun getHistoryEvents(channel: String, latest: String?, oldest: String?, inclusive: Boolean?, count: Int, unreads: Boolean?): Observable<HistoryResponse> {
        return when {
            channel.startsWith("G", ignoreCase = true) -> getGroupsHistoryEvents(channel, latest, oldest, inclusive, count, unreads)
            channel.startsWith("D", ignoreCase = true) -> getImHistoryEvents(channel, latest, oldest, inclusive, count, unreads)
            else -> getChannelHistoryEvents(channel, latest, oldest, inclusive, count, unreads)
        }
    }

    override fun getUserInfo(userId: String): Observable<UsersInfoResponse> {
        return service.getUsersInfo(token, userId)
    }
}