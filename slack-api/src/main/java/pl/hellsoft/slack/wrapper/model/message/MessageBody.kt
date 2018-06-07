package pl.hellsoft.slack.wrapper.model.message

/**
 * @author Grzegorz Pawełczuk
 * @email grzegorz.pawelczuk@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 22.12.2017
 */

data class MessageBody(
        var text: String?,
        val user: String?,
        val bot_id: String?,
        val type: String?,
        val subtype: String?,
        val ts: String,
        val attachments: List<AttachmentBody>? = emptyList()
)