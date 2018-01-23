package pl.hellsoft.slack.wrapper.model

import allbegray.slack.webapi.model.AuthTestResponse
import pl.hellsoft.slack.wrapper.SlackApiEvent

/**
 * @author Grzegorz Pawełczuk
 * @email grzegorz.pawelczuk@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 22.12.2017
 */

data class AuthEvent( val auth : AuthTestResponse) : SlackApiEvent