package pl.hellsoft.slack.wrapper.rtm

import io.reactivex.Observable
import pl.hellsoft.slack.wrapper.model.MySlack
import pl.hellsoft.slack.wrapper.webapi.model.AuthTestResponse

/**
 * @author Maciej Madetko
 * @email maciej.madetko@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 19/01/2018.
 */
interface WrapperRtmInterface {
    fun rtmStart() : Observable<MySlack>
    fun authTest() : Observable<AuthTestResponse>
}