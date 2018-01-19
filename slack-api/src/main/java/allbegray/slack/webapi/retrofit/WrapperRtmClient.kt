package allbegray.slack.webapi.retrofit

import io.reactivex.Observable
import pl.hellsoft.slack.wrapper.model.MySlack

/**
 * @author Maciej Madetko
 * @email maciej.madetko@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 19/01/2018.
 */
interface WrapperRtmClient {
    fun rtmStart() : Observable<MySlack>
}