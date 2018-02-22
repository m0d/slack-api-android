package pl.hellsoft.slack.wrapper

/**
 * @author Maciej Madetko
 * @email maciej.madetko@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 22/01/2018.
 *
 * @author Grzegorz Pawełczuk
 * @email grzegorz.pawelczuk@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 22/02/2018
 */


class SlackApiConstants {
    companion object {
        const val SLACK_WEB_API_URL = "https://slack.com/api"

        // test
        const val AUTH_TEST = "auth.test"

        // bots
        const val BOTS_INFO = "bots.info"

        // channels
        const val CHANNELS_HISTORY = "channels.history"

        //IM
        const val IM_HISTORY = "im.history"

        //Users
        const val USERS_INFO = "users.info"

        // chat
        const val CHAT_ME_MESSAGE = "chat.meMessage"
        const val CHAT_POST_MESSAGE = "chat.postMessage"

        // rtm
        const val RTM_START = "rtm.start"
    }
}
