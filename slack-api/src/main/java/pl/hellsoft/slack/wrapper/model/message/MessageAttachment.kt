package pl.hellsoft.slack.wrapper.model.message

/**
 * @author Grzegorz Pawełczuk
 * @email grzegorz.pawelczuk@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 03.01.2018
 */

data class MessageAttachment(
        val title: String? = null,
        val text: String? = null,
        val id: Long? = 0,
        val title_link: String? = null,
        val color: String? = null,
        val mrkdwn_in: List<String>? = null,
        val fallback: String? = null,
        val pretext: String? = null,
        val image_url: String? = null,
        val callback_id: String? = null,
        val actions : List<Action>? = null
)