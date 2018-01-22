package allbegray.slack.webapi.retrofit

import allbegray.slack.type.Bot
import allbegray.slack.type.HistoryEvents
import allbegray.slack.webapi.SlackWebApiConstants
import allbegray.slack.webapi.retrofit.model.AuthTestResponse
import allbegray.slack.webapi.retrofit.model.BotInfoResponse
import allbegray.slack.webapi.retrofit.model.PostMessageResponse
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

    @GET("$apiPath/${SlackWebApiConstants.RTM_START}")
    fun rtmStart(@Query("token") token: String) : Observable<MySlack>

    @POST("$apiPath/${SlackWebApiConstants.AUTH_TEST}")
    fun auth(@Query("token") token: String) : Observable<AuthTestResponse>

    @GET("$apiPath/${SlackWebApiConstants.BOTS_INFO}")
    fun getBotInfo(
            @Query("token") token: String,
            @Query("bot") bot: String) : Observable<BotInfoResponse>

    @POST("$apiPath/${SlackWebApiConstants.CHAT_POST_MESSAGE}")
    fun postMessage(
            @Query("token") token: String,
            @Query("channel") channel: String,
            @Query("text") message: String,
            @Query("as_user") asUser: Boolean = true) : Observable<PostMessageResponse>

    @GET("$apiPath/${SlackWebApiConstants.CHANNELS_HISTORY}")
    fun getChannelHistoryEvents(
            @Query("token") token: String,
            @Query("channel") channel: String,
            @Query("latest") latest: String?,
            @Query("oldest") oldest: String?,
            @Query("inclusive") inclusive: Boolean?,
            @Query("count") count: Int,
            @Query("unreads") unreads: Boolean?) : Observable<HistoryEvents>

}