package pl.hellsoft.slack.wrapper.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import pl.hellsoft.slack.wrapper.SlackApiEvent
import pl.hellsoft.slack.wrapper.model.message.FileBody
import pl.hellsoft.slack.wrapper.model.message.MessageAttachment
import pl.hellsoft.slack.wrapper.model.message.MessageBody

/**
 * @author Grzegorz Pawełczuk
 * @email grzegorz.pawelczuk@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 22.12.2017
 */

@JsonIgnoreProperties(ignoreUnknown = true)
data class MessageEvent(
        var type : String,
        var channel : String,
        var user : String? = null,
        var text : String? = null,
        var ts : String,
        var source_team : String? = null,
        var bot_id : String? = null,
        var team : String? = null,
        var is_ephemeral : Boolean? = false,
        var message : MessageBody? = null,
        var subtype : String? = null,
        var file : FileBody? = null,
        var reply_to: Int? = null,
        var previous_message: MessageBody? = null,
        var mrkdwn: Boolean? = false,
        var attachments: List<MessageAttachment>? = emptyList()
) : SlackApiEvent {
    fun getTimestamp() : Long =  Math.floor(ts.toDouble()).toLong() * 1000
}



