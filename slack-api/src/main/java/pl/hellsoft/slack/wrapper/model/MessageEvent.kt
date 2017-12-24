package pl.hellsoft.slack.wrapper.model

import pl.hellsoft.slack.wrapper.SlackApiEvent
import pl.hellsoft.slack.wrapper.model.message.FileBody
import pl.hellsoft.slack.wrapper.model.message.MessageBody
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * @author Grzegorz Pawełczuk
 * @email grzegorz.pawelczuk@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 22.12.2017
 */

@JsonIgnoreProperties(ignoreUnknown = true)
data class MessageEvent(
        val type : String,
        val channel : String,
        val user : String?,
        val text : String?,
        val ts : String,
        val source_team : String?,
        val bot_id : String?,
        val team : String?,
        val is_ephemeral : Boolean? = false,
        val message : MessageBody? = null,
        val subtype : String? = null,
        val file : FileBody? = null,
        val reply_to: Int? = null,
        val previous_message: MessageBody? = null
) : SlackApiEvent {
    fun getTimestamp() : Long =  Math.floor(ts.toDouble()).toLong() * 1000
}



