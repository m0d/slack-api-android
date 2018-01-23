package allbegray.slack.webapi

import allbegray.slack.type.HistoryEvents
import allbegray.slack.webapi.model.AuthTestResponse
import allbegray.slack.webapi.model.BotInfoResponse
import allbegray.slack.webapi.model.MeMessageResponse
import allbegray.slack.webapi.model.PostMessageResponse
import io.reactivex.Observable
import pl.hellsoft.slack.wrapper.model.MySlack
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


/**
 * @author Maciej Madetk
 * @email maciej.madetko@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 19/01/2018.
 */
interface SlackService {
    companion object {
        const val apiPath: String = SlackWebApiConstants.SLACK_WEB_API_URL
    }

    @GET("${apiPath}/${SlackWebApiConstants.RTM_START}")
    fun rtmStart(@Query("token") token: String) : Observable<MySlack>

    @POST("${apiPath}/${SlackWebApiConstants.AUTH_TEST}")
    fun auth(@Query("token") token: String) : Observable<AuthTestResponse>

    @GET("${apiPath}/${SlackWebApiConstants.BOTS_INFO}")
    fun getBotInfo(
            @Query("token") token: String,
            @Query("bot") bot: String) : Observable<BotInfoResponse>

    @POST("${apiPath}/${SlackWebApiConstants.CHAT_POST_MESSAGE}")
    fun postMessage(
            @Query("token") token: String,
            @Query("channel") channel: String,
            @Query("text") message: String,
            @Query("as_user") asUser: Boolean = true) : Observable<PostMessageResponse>

    @POST("${apiPath}/${SlackWebApiConstants.CHAT_ME_MESSAGE}")
    fun meMessage(
            @Query("token") token: String,
            @Query("channel") channel: String,
            @Query("text") message: String) : Observable<MeMessageResponse>

    @GET("${apiPath}/${SlackWebApiConstants.CHANNELS_HISTORY}")
    fun getChannelHistoryEvents(
            @Query("token") token: String,
            @Query("channel") channel: String,
            @Query("latest") latest: String?,
            @Query("oldest") oldest: String?,
            @Query("inclusive") inclusive: Boolean?,
            @Query("count") count: Int,
            @Query("unreads") unreads: Boolean?) : Observable<HistoryEvents>

}