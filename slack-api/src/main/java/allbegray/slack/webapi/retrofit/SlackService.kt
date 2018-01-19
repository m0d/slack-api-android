package allbegray.slack.webapi.retrofit

import allbegray.slack.webapi.SlackWebApiConstants
import allbegray.slack.webapi.retrofit.model.PostMessageResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query


/**
 * @author Maciej Madetko
 * @email maciej.madetko@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 19/01/2018.
 */
interface SlackService {
    companion object {
        const val apiPath: String = SlackWebApiConstants.SLACK_WEB_API_URL
    }

    @POST("$apiPath/chat.postMessage")
    fun postMessage(@Query("token") token: String, @Query("channel") channel: String, @Query("text") message: String, @Query("as_user") asUser: Boolean = true) : Observable<PostMessageResponse>
}