package pl.hellsoft.slack.wrapper.rtm.model

import pl.hellsoft.slack.wrapper.rtm.model.RtmError

/**
 * @author Maciej Madetko
 * @email maciej.madetko@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 22/01/2018.
 */
class RtmConnect(val type: String, val error: RtmError)