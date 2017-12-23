package pl.hellsoft.slack.wrapper.model.message

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * @author Grzegorz Pawełczuk
 * @email grzegorz.pawelczuk@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 22.12.2017
 */

@JsonIgnoreProperties(ignoreUnknown = true)
data class MessageBody( val text: String )