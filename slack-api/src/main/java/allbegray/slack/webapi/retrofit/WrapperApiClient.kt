package allbegray.slack.webapi.retrofit

import allbegray.slack.webapi.retrofit.model.PostMessageResponse
import io.reactivex.Observable

/**
 * @author Maciej Madetko
 * @email maciej.madetko@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 19/01/2018.
 */
interface WrapperApiClient {
    fun postMessage(channel: String, message: String) : Observable<PostMessageResponse>
}