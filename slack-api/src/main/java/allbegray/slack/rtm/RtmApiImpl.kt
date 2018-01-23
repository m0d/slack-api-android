package allbegray.slack.rtm

import allbegray.slack.webapi.SlackService
import io.reactivex.Observable
import pl.hellsoft.slack.wrapper.model.MySlack

/**
 * @author Maciej Madetko
 * @email maciej.madetko@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 22/01/2018.
 */
class RtmApiImpl(private val service: SlackService, private val token: String) : WrapperRtmInterface {
    override fun rtmStart(): Observable<MySlack> {
        return return service.rtmStart(token)
    }
}