package pl.hellsoft.slack.wrapper

import pl.hellsoft.slack.wrapper.model.HistoryResponse
import io.reactivex.Observable
import pl.hellsoft.slack.wrapper.model.MySlack
import pl.hellsoft.slack.wrapper.webapi.model.*
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


/**
 * @author Maciej Madetk
 * @email maciej.madetko@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 19/01/2018.
 *
 * @author Grzegorz Pawełczuk
 * @email grzegorz.pawelczuk@ftlearning.com
 * @desc optimization, getImHistory & getUserInfo impl.
 * @since 22/02/2018
 */

interface SlackService {
    companion object {
        const val apiPath: String = SlackApiConstants.SLACK_WEB_API_URL
    }

    @GET("$apiPath/${SlackApiConstants.RTM_START}")
    fun rtmStart(@Query("token") token: String) : Observable<MySlack>

    @POST("$apiPath/${SlackApiConstants.AUTH_TEST}")
    fun auth(@Query("token") token: String) : Observable<AuthTestResponse>

    @GET("$apiPath/${SlackApiConstants.BOTS_INFO}")
    fun getBotInfo(
            @Query("token") token: String,
            @Query("bot") bot: String) : Observable<BotInfoResponse>

    @POST("$apiPath/${SlackApiConstants.CHAT_POST_MESSAGE}")
    fun postMessage(
            @Query("token") token: String,
            @Query("channel") channel: String,
            @Query("text") message: String,
            @Query("as_user") asUser: Boolean = true) : Observable<PostMessageResponse>

    @POST("$apiPath/${SlackApiConstants.CHAT_ME_MESSAGE}")
    fun meMessage(
            @Query("token") token: String,
            @Query("channel") channel: String,
            @Query("text") message: String) : Observable<MeMessageResponse>

    @GET("$apiPath/${SlackApiConstants.CHANNELS_HISTORY}")
    fun getChannelHistory(
            @Query("token") token: String,
            @Query("channel") channel: String,
            @Query("latest") latest: String?,
            @Query("oldest") oldest: String?,
            @Query("inclusive") inclusive: Boolean?,
            @Query("count") count: Int,
            @Query("unreads") unreads: Boolean?) : Observable<HistoryResponse>

    @GET("$apiPath/${SlackApiConstants.IM_HISTORY}")
    fun getImHistory(
            @Query("token") token: String,
            @Query("channel") channel: String,
            @Query("latest") latest: String?,
            @Query("oldest") oldest: String?,
            @Query("inclusive") inclusive: Boolean?,
            @Query("count") count: Int,
            @Query("unreads") unreads: Boolean?) : Observable<HistoryResponse>

    @GET("$apiPath/${SlackApiConstants.USERS_INFO}")
    fun getUsersInfo(
            @Query("token") token: String,
            @Query("user") user: String) : Observable<UsersInfoResponse>

}