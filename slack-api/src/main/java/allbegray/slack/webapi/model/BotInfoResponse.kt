package allbegray.slack.webapi.model

import allbegray.slack.type.Bot

/**
 * @author Maciej Madetko
 * @email maciej.madetko@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 22/01/2018.
 */
data class BotInfoResponse(val ok: Boolean, val bot: Bot, val error: String)