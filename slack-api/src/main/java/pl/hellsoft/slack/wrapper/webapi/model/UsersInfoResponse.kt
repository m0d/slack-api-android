package pl.hellsoft.slack.wrapper.webapi.model

import pl.hellsoft.slack.wrapper.model.User

/**
 * @author Grzegorz Pawełczuk
 * @email grzegorz.pawelczuk@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 22/02/2018
 */

data class UsersInfoResponse(val ok: Boolean, val user: User? = null, val error: String? = null)