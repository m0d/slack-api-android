package allbegray.slack.webapi.model

/**
 * @author Maciej Madetko
 * @email maciej.madetko@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 19/01/2018.
 */
data class MeMessageResponse(val ok: Boolean, val channel: String, val ts: String, val error: String)