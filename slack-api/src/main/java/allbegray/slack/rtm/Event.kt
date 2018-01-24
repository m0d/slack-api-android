package allbegray.slack.rtm

enum class Event {

    HELLO, MESSAGE, USER_TYPING, CHANNEL_MARKED, CHANNEL_CREATED, CHANNEL_JOINED, CHANNEL_LEFT, CHANNEL_DELETED, CHANNEL_RENAME, CHANNEL_ARCHIVE, CHANNEL_UNARCHIVE, CHANNEL_HISTORY_CHANGED, IM_CREATED, IM_OPEN, IM_CLOSE, IM_MARKED, IM_HISTORY_CHANGED, GROUP_JOINED, GROUP_LEFT, GROUP_OPEN, GROUP_CLOSE, GROUP_ARCHIVE, GROUP_UNARCHIVE, GROUP_RENAME, GROUP_MARKED, GROUP_HISTORY_CHANGED, FILE_CREATED, FILE_SHARED, FILE_UNSHARED, FILE_PUBLIC, FILE_PRIVATE, FILE_CHANGE, FILE_DELETED, FILE_COMMENT_ADDED, FILE_COMMENT_EDITED, FILE_COMMENT_DELETED, PIN_ADDED, PIN_REMOVED, PRESENCE_CHANGE, MANUAL_PRESENCE_CHANGE, PREF_CHANGE, USER_CHANGE, TEAM_JOIN, STAR_ADDED, STAR_REMOVED, REACTION_ADDED, REACTION_REMOVED, EMOJI_CHANGED, COMMANDS_CHANGED, TEAM_PLAN_CHANGE, TEAM_PREF_CHANGE, TEAM_RENAME, TEAM_DOMAIN_CHANGE, EMAIL_DOMAIN_CHANGED, BOT_ADDED, BOT_CHANGED, ACCOUNTS_CHANGED, TEAM_MIGRATION_STARTED

}
