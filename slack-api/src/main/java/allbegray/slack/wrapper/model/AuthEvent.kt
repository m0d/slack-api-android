package allbegray.slack.wrapper.model

import allbegray.slack.type.Authentication
import allbegray.slack.wrapper.SlackApiEvent

/**
 * @author Grzegorz Pawełczuk
 * @email grzegorz.pawelczuk@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 22.12.2017
 */

data class AuthEvent( val auth : Authentication) : SlackApiEvent