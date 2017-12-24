package pl.hellsoft.slack.wrapper.model.message

/**
 * @author Grzegorz Pawełczuk
 * @email grzegorz.pawelczuk@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 24.12.2017
 */

data class AttachmentBody(
        val fallback : String? = null,
        val image_url : String? = null,
        val image_width : Int? = null,
        val image_height : Int? = null,
        val image_bytes : Int? = null,
        val from_url : String? = null,
        val id : Int? = null
)