package pl.hellsoft.slack.wrapper.model.message

/**
 * @author Maciej Madetko
 * @email maciej.madetko@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 21/02/2018.
 */
data class Action(
        val name: String,
        val text: String,
        val type: String,
        val value: String? = null
)