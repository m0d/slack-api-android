package allbegray.slack.wrapper.model

import allbegray.slack.wrapper.SlackApiEvent

/**
 * @author Grzegorz Pawełczuk
 * @email grzegorz.pawelczuk@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 22.12.2017
 */

data class ConnectionEvent( val connected : Boolean, val info: String? = null ) : SlackApiEvent