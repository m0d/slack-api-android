package pl.hellsoft.slack.wrapper.model
import com.google.gson.annotations.SerializedName


/**
 * @author Grzegorz Pawełczuk
 * @email grzegorz.pawelczuk@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 03.01.2018
 */

data class MySlack(
		@SerializedName("ok") val ok: Boolean?,
		@SerializedName("self") val self: Self?,
		@SerializedName("team") val team: Team?,
		@SerializedName("latest_event_ts") val latestEventTs: String?,
		@SerializedName("channels") val channels: List<Channel?>?,
		@SerializedName("groups") val groups: List<Group?>?,
		@SerializedName("ims") val ims: List<Im?>?,
		@SerializedName("cache_ts") val cacheTs: Int?,
		@SerializedName("read_only_channels") val readOnlyChannels: List<Any?>?,
		@SerializedName("can_manage_shared_channels") val canManageSharedChannels: Boolean?,
		@SerializedName("subteams") val subteams: Subteams?,
		@SerializedName("dnd") val dnd: Dnd?,
		@SerializedName("users") val users: List<User?>?,
		@SerializedName("cache_version") val cacheVersion: String?,
		@SerializedName("cache_ts_version") val cacheTsVersion: String?,
		@SerializedName("bots") val bots: List<Bot?>?,
		@SerializedName("url") val url: String?,
		@SerializedName("dead_pig") val deadPig: Boolean?,
		@SerializedName("warning") val warning: String?,
		@SerializedName("response_metadata") val responseMetadata: ResponseMetadata?,
		@SerializedName("error") val error: String?
)

data class User(
		@SerializedName("id") val id: String?,
		@SerializedName("team_id") val teamId: String?,
		@SerializedName("name") val name: String?,
		@SerializedName("deleted") val deleted: Boolean?,
		@SerializedName("color") val color: String?,
		@SerializedName("real_name") val realName: String?,
		@SerializedName("tz") val tz: String?,
		@SerializedName("tz_label") val tzLabel: String?,
		@SerializedName("tz_offset") val tzOffset: Int?,
		@SerializedName("profile") val profile: Profile?,
		@SerializedName("is_admin") val isAdmin: Boolean?,
		@SerializedName("is_owner") val isOwner: Boolean?,
		@SerializedName("is_primary_owner") val isPrimaryOwner: Boolean?,
		@SerializedName("is_restricted") val isRestricted: Boolean?,
		@SerializedName("is_ultra_restricted") val isUltraRestricted: Boolean?,
		@SerializedName("is_bot") val isBot: Boolean?,
		@SerializedName("updated") val updated: Int?,
		@SerializedName("is_app_user") val isAppUser: Boolean?,
		@SerializedName("presence") val presence: String?
)

data class Profile(
		@SerializedName("bot_id") val botId: String?,
		@SerializedName("api_app_id") val apiAppId: String?,
		@SerializedName("always_active") val alwaysActive: Boolean?,
		@SerializedName("real_name") val realName: String?,
		@SerializedName("display_name") val displayName: String?,
		@SerializedName("avatar_hash") val avatarHash: String?,
		@SerializedName("image_24") val image24: String?,
		@SerializedName("image_32") val image32: String?,
		@SerializedName("image_48") val image48: String?,
		@SerializedName("image_72") val image72: String?,
		@SerializedName("image_192") val image192: String?,
		@SerializedName("image_512") val image512: String?,
		@SerializedName("image_1024") val image1024: String?,
		@SerializedName("image_original") val imageOriginal: String?,
		@SerializedName("real_name_normalized") val realNameNormalized: String?,
		@SerializedName("display_name_normalized") val displayNameNormalized: String?,
		@SerializedName("fields") val fields: Any?,
		@SerializedName("team") val team: String?
)

data class Dnd(
		@SerializedName("dnd_enabled") val dndEnabled: Boolean?,
		@SerializedName("next_dnd_start_ts") val nextDndStartTs: Int?,
		@SerializedName("next_dnd_end_ts") val nextDndEndTs: Int?,
		@SerializedName("snooze_enabled") val snoozeEnabled: Boolean?
)

data class ResponseMetadata(
		@SerializedName("warnings") val warnings: List<String?>?
)

data class Bot(
		@SerializedName("id") val id: String?,
		@SerializedName("deleted") val deleted: Boolean?,
		@SerializedName("name") val name: String?,
		@SerializedName("updated") val updated: Int?,
		@SerializedName("app_id") val appId: String?,
		@SerializedName("icons") val icons: Icons?
)

data class Icons(
		@SerializedName("image_36") val image36: String?,
		@SerializedName("image_48") val image48: String?,
		@SerializedName("image_72") val image72: String?
)

data class Self(
		@SerializedName("id") val id: String?,
		@SerializedName("name") val name: String?,
		@SerializedName("prefs") val prefs: Prefs?,
		@SerializedName("created") val created: Int?,
		@SerializedName("manual_presence") val manualPresence: String?
)

data class Prefs(
		@SerializedName("user_colors") val userColors: String?,
		@SerializedName("color_names_in_list") val colorNamesInList: Boolean?,
		@SerializedName("tz") val tz: String?,
		@SerializedName("keyboard") val keyboard: Any?,
		@SerializedName("email_alerts") val emailAlerts: String?,
		@SerializedName("email_alerts_sleep_until") val emailAlertsSleepUntil: Int?,
		@SerializedName("email_misc") val emailMisc: Boolean?,
		@SerializedName("email_tips") val emailTips: Boolean?,
		@SerializedName("email_weekly") val emailWeekly: Boolean?,
		@SerializedName("email_offers") val emailOffers: Boolean?,
		@SerializedName("email_research") val emailResearch: Boolean?,
		@SerializedName("welcome_message_hidden") val welcomeMessageHidden: Boolean?,
		@SerializedName("search_sort") val searchSort: String?,
		@SerializedName("expand_inline_imgs") val expandInlineImgs: Boolean?,
		@SerializedName("expand_internal_inline_imgs") val expandInternalInlineImgs: Boolean?,
		@SerializedName("expand_snippets") val expandSnippets: Boolean?,
		@SerializedName("posts_formatting_guide") val postsFormattingGuide: Boolean?,
		@SerializedName("seen_welcome_2") val seenWelcome2: Boolean?,
		@SerializedName("seen_ssb_prompt") val seenSsbPrompt: Boolean?,
		@SerializedName("spaces_new_xp_banner_dismissed") val spacesNewXpBannerDismissed: Boolean?,
		@SerializedName("search_only_my_channels") val searchOnlyMyChannels: Boolean?,
		@SerializedName("search_only_current_team") val searchOnlyCurrentTeam: Boolean?,
		@SerializedName("emoji_mode") val emojiMode: String?,
		@SerializedName("emoji_use") val emojiUse: String?,
		@SerializedName("has_invited") val hasInvited: Boolean?,
		@SerializedName("has_uploaded") val hasUploaded: Boolean?,
		@SerializedName("has_created_channel") val hasCreatedChannel: Boolean?,
		@SerializedName("has_searched") val hasSearched: Boolean?,
		@SerializedName("search_exclude_channels") val searchExcludeChannels: String?,
		@SerializedName("messages_theme") val messagesTheme: String?,
		@SerializedName("webapp_spellcheck") val webappSpellcheck: Boolean?,
		@SerializedName("no_joined_overlays") val noJoinedOverlays: Boolean?,
		@SerializedName("no_created_overlays") val noCreatedOverlays: Boolean?,
		@SerializedName("dropbox_enabled") val dropboxEnabled: Boolean?,
		@SerializedName("seen_domain_invite_reminder") val seenDomainInviteReminder: Boolean?,
		@SerializedName("seen_member_invite_reminder") val seenMemberInviteReminder: Boolean?,
		@SerializedName("mute_sounds") val muteSounds: Boolean?,
		@SerializedName("arrow_history") val arrowHistory: Boolean?,
		@SerializedName("tab_ui_return_selects") val tabUiReturnSelects: Boolean?,
		@SerializedName("obey_inline_img_limit") val obeyInlineImgLimit: Boolean?,
		@SerializedName("require_at") val requireAt: Boolean?,
		@SerializedName("ssb_space_window") val ssbSpaceWindow: String?,
		@SerializedName("mac_ssb_bounce") val macSsbBounce: String?,
		@SerializedName("mac_ssb_bullet") val macSsbBullet: Boolean?,
		@SerializedName("expand_non_media_attachments") val expandNonMediaAttachments: Boolean?,
		@SerializedName("show_typing") val showTyping: Boolean?,
		@SerializedName("pagekeys_handled") val pagekeysHandled: Boolean?,
		@SerializedName("last_snippet_type") val lastSnippetType: String?,
		@SerializedName("display_real_names_override") val displayRealNamesOverride: Int?,
		@SerializedName("display_display_names") val displayDisplayNames: Boolean?,
		@SerializedName("time24") val time24: Boolean?,
		@SerializedName("enter_is_special_in_tbt") val enterIsSpecialInTbt: Boolean?,
		@SerializedName("msg_input_send_btn") val msgInputSendBtn: Boolean?,
		@SerializedName("msg_input_send_btn_auto_set") val msgInputSendBtnAutoSet: Boolean?,
		@SerializedName("graphic_emoticons") val graphicEmoticons: Boolean?,
		@SerializedName("convert_emoticons") val convertEmoticons: Boolean?,
		@SerializedName("ss_emojis") val ssEmojis: Boolean?,
		@SerializedName("seen_onboarding_start") val seenOnboardingStart: Boolean?,
		@SerializedName("onboarding_cancelled") val onboardingCancelled: Boolean?,
		@SerializedName("seen_onboarding_slackbot_conversation") val seenOnboardingSlackbotConversation: Boolean?,
		@SerializedName("seen_onboarding_channels") val seenOnboardingChannels: Boolean?,
		@SerializedName("seen_onboarding_direct_messages") val seenOnboardingDirectMessages: Boolean?,
		@SerializedName("seen_onboarding_invites") val seenOnboardingInvites: Boolean?,
		@SerializedName("seen_onboarding_search") val seenOnboardingSearch: Boolean?,
		@SerializedName("seen_onboarding_recent_mentions") val seenOnboardingRecentMentions: Boolean?,
		@SerializedName("seen_onboarding_starred_items") val seenOnboardingStarredItems: Boolean?,
		@SerializedName("seen_onboarding_private_groups") val seenOnboardingPrivateGroups: Boolean?,
		@SerializedName("seen_onboarding_banner") val seenOnboardingBanner: Boolean?,
		@SerializedName("onboarding_slackbot_conversation_step") val onboardingSlackbotConversationStep: Int?,
		@SerializedName("dnd_enabled") val dndEnabled: Boolean?,
		@SerializedName("dnd_start_hour") val dndStartHour: String?,
		@SerializedName("dnd_end_hour") val dndEndHour: String?,
		@SerializedName("sidebar_behavior") val sidebarBehavior: String?,
		@SerializedName("channel_sort") val channelSort: String?,
		@SerializedName("separate_private_channels") val separatePrivateChannels: Boolean?,
		@SerializedName("separate_shared_channels") val separateSharedChannels: Boolean?,
		@SerializedName("sidebar_theme") val sidebarTheme: String?,
		@SerializedName("sidebar_theme_custom_values") val sidebarThemeCustomValues: String?,
		@SerializedName("no_invites_widget_in_sidebar") val noInvitesWidgetInSidebar: Boolean?,
		@SerializedName("no_omnibox_in_channels") val noOmniboxInChannels: Boolean?,
		@SerializedName("k_key_omnibox_auto_hide_count") val kKeyOmniboxAutoHideCount: Int?,
		@SerializedName("show_sidebar_quickswitcher_button") val showSidebarQuickswitcherButton: Boolean?,
		@SerializedName("opt_out_react_messages") val optOutReactMessages: Boolean?,
		@SerializedName("ent_org_wide_channels_sidebar") val entOrgWideChannelsSidebar: Boolean?,
		@SerializedName("mark_msgs_read_immediately") val markMsgsReadImmediately: Boolean?,
		@SerializedName("start_scroll_at_oldest") val startScrollAtOldest: Boolean?,
		@SerializedName("snippet_editor_wrap_long_lines") val snippetEditorWrapLongLines: Boolean?,
		@SerializedName("ls_disabled") val lsDisabled: Boolean?,
		@SerializedName("f_key_search") val fKeySearch: Boolean?,
		@SerializedName("k_key_omnibox") val kKeyOmnibox: Boolean?,
		@SerializedName("prompted_for_email_disabling") val promptedForEmailDisabling: Boolean?,
		@SerializedName("full_text_extracts") val fullTextExtracts: Boolean?,
		@SerializedName("no_macelectron_banner") val noMacelectronBanner: Boolean?,
		@SerializedName("no_macssb1_banner") val noMacssb1Banner: Boolean?,
		@SerializedName("no_macssb2_banner") val noMacssb2Banner: Boolean?,
		@SerializedName("no_winssb1_banner") val noWinssb1Banner: Boolean?,
		@SerializedName("hide_user_group_info_pane") val hideUserGroupInfoPane: Boolean?,
		@SerializedName("mentions_exclude_at_user_groups") val mentionsExcludeAtUserGroups: Boolean?,
		@SerializedName("privacy_policy_seen") val privacyPolicySeen: Boolean?,
		@SerializedName("enterprise_migration_seen") val enterpriseMigrationSeen: Boolean?,
		@SerializedName("last_tos_acknowledged") val lastTosAcknowledged: String?,
		@SerializedName("search_exclude_bots") val searchExcludeBots: Boolean?,
		@SerializedName("load_lato_2") val loadLato2: Boolean?,
		@SerializedName("fuller_timestamps") val fullerTimestamps: Boolean?,
		@SerializedName("last_seen_at_channel_warning") val lastSeenAtChannelWarning: Int?,
		@SerializedName("msg_preview") val msgPreview: Boolean?,
		@SerializedName("msg_preview_persistent") val msgPreviewPersistent: Boolean?,
		@SerializedName("emoji_autocomplete_big") val emojiAutocompleteBig: Boolean?,
		@SerializedName("winssb_run_from_tray") val winssbRunFromTray: Boolean?,
		@SerializedName("winssb_window_flash_behavior") val winssbWindowFlashBehavior: String?,
		@SerializedName("two_factor_auth_enabled") val twoFactorAuthEnabled: Boolean?,
		@SerializedName("two_factor_type") val twoFactorType: Any?,
		@SerializedName("two_factor_backup_type") val twoFactorBackupType: Any?,
		@SerializedName("hide_hex_swatch") val hideHexSwatch: Boolean?,
		@SerializedName("show_jumper_scores") val showJumperScores: Boolean?,
		@SerializedName("enterprise_mdm_custom_msg") val enterpriseMdmCustomMsg: String?,
		@SerializedName("enterprise_excluded_app_teams") val enterpriseExcludedAppTeams: Any?,
		@SerializedName("client_logs_pri") val clientLogsPri: String?,
		@SerializedName("enhanced_debugging") val enhancedDebugging: Boolean?,
		@SerializedName("flannel_server_pool") val flannelServerPool: String?,
		@SerializedName("mentions_exclude_at_channels") val mentionsExcludeAtChannels: Boolean?,
		@SerializedName("confirm_clear_all_unreads") val confirmClearAllUnreads: Boolean?,
		@SerializedName("confirm_user_marked_away") val confirmUserMarkedAway: Boolean?,
		@SerializedName("box_enabled") val boxEnabled: Boolean?,
		@SerializedName("seen_single_emoji_msg") val seenSingleEmojiMsg: Boolean?,
		@SerializedName("confirm_sh_call_start") val confirmShCallStart: Boolean?,
		@SerializedName("preferred_skin_tone") val preferredSkinTone: String?,
		@SerializedName("show_all_skin_tones") val showAllSkinTones: Boolean?,
		@SerializedName("whats_new_read") val whatsNewRead: Int?,
		@SerializedName("frecency_jumper") val frecencyJumper: String?,
		@SerializedName("frecency_ent_jumper") val frecencyEntJumper: String?,
		@SerializedName("jumbomoji") val jumbomoji: Boolean?,
		@SerializedName("newxp_seen_last_message") val newxpSeenLastMessage: Int?,
		@SerializedName("show_memory_instrument") val showMemoryInstrument: Boolean?,
		@SerializedName("enable_unread_view") val enableUnreadView: Boolean?,
		@SerializedName("seen_unread_view_coachmark") val seenUnreadViewCoachmark: Boolean?,
		@SerializedName("measure_css_usage") val measureCssUsage: Boolean?,
		@SerializedName("enable_react_emoji_picker") val enableReactEmojiPicker: Boolean?,
		@SerializedName("seen_custom_status_badge") val seenCustomStatusBadge: Boolean?,
		@SerializedName("seen_custom_status_callout") val seenCustomStatusCallout: Boolean?,
		@SerializedName("seen_guest_admin_slackbot_announcement") val seenGuestAdminSlackbotAnnouncement: Boolean?,
		@SerializedName("seen_threads_notification_banner") val seenThreadsNotificationBanner: Boolean?,
		@SerializedName("seen_name_tagging_coachmark") val seenNameTaggingCoachmark: Boolean?,
		@SerializedName("all_unreads_sort_order") val allUnreadsSortOrder: Boolean?,
		@SerializedName("locale") val locale: String?,
		@SerializedName("seen_intl_channel_names_coachmark") val seenIntlChannelNamesCoachmark: Boolean?,
		@SerializedName("seen_locale_change_message") val seenLocaleChangeMessage: Int?,
		@SerializedName("seen_japanese_locale_change_message") val seenJapaneseLocaleChangeMessage: Boolean?,
		@SerializedName("seen_shared_channels_coachmark") val seenSharedChannelsCoachmark: Boolean?,
		@SerializedName("seen_shared_channels_opt_in_change_message") val seenSharedChannelsOptInChangeMessage: Boolean?,
		@SerializedName("has_recently_shared_a_channel") val hasRecentlySharedAChannel: Boolean?,
		@SerializedName("seen_channel_browser_admin_coachmark") val seenChannelBrowserAdminCoachmark: Boolean?,
		@SerializedName("allow_calls_to_set_current_status") val allowCallsToSetCurrentStatus: Boolean?,
		@SerializedName("in_interactive_mas_migration_flow") val inInteractiveMasMigrationFlow: Boolean?,
		@SerializedName("shdep_promo_code_submitted") val shdepPromoCodeSubmitted: Boolean?,
		@SerializedName("seen_shdep_slackbot_message") val seenShdepSlackbotMessage: Boolean?,
		@SerializedName("seen_calls_interactive_coachmark") val seenCallsInteractiveCoachmark: Boolean?,
		@SerializedName("gdrive_authed") val gdriveAuthed: Boolean?,
		@SerializedName("gdrive_enabled") val gdriveEnabled: Boolean?,
		@SerializedName("seen_gdrive_coachmark") val seenGdriveCoachmark: Boolean?,
		@SerializedName("overloaded_message_enabled") val overloadedMessageEnabled: Boolean?,
		@SerializedName("seen_highlights_coachmark") val seenHighlightsCoachmark: Boolean?,
		@SerializedName("seen_highlights_arrows_coachmark") val seenHighlightsArrowsCoachmark: Boolean?,
		@SerializedName("seen_highlights_warm_welcome") val seenHighlightsWarmWelcome: Boolean?,
		@SerializedName("a11y_font_size") val a11yFontSize: String?,
		@SerializedName("a11y_animations") val a11yAnimations: Boolean?,
		@SerializedName("seen_keyboard_shortcuts_coachmark") val seenKeyboardShortcutsCoachmark: Boolean?,
		@SerializedName("lessons_enabled") val lessonsEnabled: Boolean?,
		@SerializedName("highlight_words") val highlightWords: String?,
		@SerializedName("threads_everything") val threadsEverything: Boolean?,
		@SerializedName("no_text_in_notifications") val noTextInNotifications: Boolean?,
		@SerializedName("push_show_preview") val pushShowPreview: Boolean?,
		@SerializedName("growls_enabled") val growlsEnabled: Boolean?,
		@SerializedName("all_channels_loud") val allChannelsLoud: Boolean?,
		@SerializedName("push_dm_alert") val pushDmAlert: Boolean?,
		@SerializedName("push_mention_alert") val pushMentionAlert: Boolean?,
		@SerializedName("push_everything") val pushEverything: Boolean?,
		@SerializedName("push_idle_wait") val pushIdleWait: Int?,
		@SerializedName("push_sound") val pushSound: String?,
		@SerializedName("new_msg_snd") val newMsgSnd: String?,
		@SerializedName("push_loud_channels") val pushLoudChannels: String?,
		@SerializedName("push_mention_channels") val pushMentionChannels: String?,
		@SerializedName("push_loud_channels_set") val pushLoudChannelsSet: String?,
		@SerializedName("loud_channels") val loudChannels: String?,
		@SerializedName("never_channels") val neverChannels: String?,
		@SerializedName("loud_channels_set") val loudChannelsSet: String?,
		@SerializedName("at_channel_suppressed_channels") val atChannelSuppressedChannels: String?,
		@SerializedName("push_at_channel_suppressed_channels") val pushAtChannelSuppressedChannels: String?,
		@SerializedName("muted_channels") val mutedChannels: String?,
		@SerializedName("all_notifications_prefs") val allNotificationsPrefs: String?,
		@SerializedName("growth_msg_limit_approaching_cta_count") val growthMsgLimitApproachingCtaCount: Int?,
		@SerializedName("growth_msg_limit_approaching_cta_ts") val growthMsgLimitApproachingCtaTs: Int?,
		@SerializedName("growth_msg_limit_reached_cta_count") val growthMsgLimitReachedCtaCount: Int?,
		@SerializedName("growth_msg_limit_reached_cta_last_ts") val growthMsgLimitReachedCtaLastTs: Int?,
		@SerializedName("growth_msg_limit_long_reached_cta_count") val growthMsgLimitLongReachedCtaCount: Int?,
		@SerializedName("growth_msg_limit_long_reached_cta_last_ts") val growthMsgLimitLongReachedCtaLastTs: Int?,
		@SerializedName("growth_msg_limit_sixty_day_banner_cta_count") val growthMsgLimitSixtyDayBannerCtaCount: Int?,
		@SerializedName("growth_msg_limit_sixty_day_banner_cta_last_ts") val growthMsgLimitSixtyDayBannerCtaLastTs: Int?,
		@SerializedName("growth_all_banners_prefs") val growthAllBannersPrefs: String?,
		@SerializedName("intro_to_apps_message_seen") val introToAppsMessageSeen: Boolean?,
		@SerializedName("analytics_upsell_coachmark_seen") val analyticsUpsellCoachmarkSeen: Boolean?,
		@SerializedName("seen_app_space_coachmark") val seenAppSpaceCoachmark: Boolean?,
		@SerializedName("seen_app_space_tutorial") val seenAppSpaceTutorial: Boolean?,
		@SerializedName("purchaser") val purchaser: Boolean?,
		@SerializedName("locales_enabled") val localesEnabled: LocalesEnabled?
)

data class LocalesEnabled(
		@SerializedName("de-DE") val deDE: String?,
		@SerializedName("en-US") val enUS: String?,
		@SerializedName("es-ES") val esES: String?,
		@SerializedName("fr-FR") val frFR: String?,
		@SerializedName("ja-JP") val jaJP: String?
)

data class Group(
		@SerializedName("id") val id: String?,
		@SerializedName("name") val name: String?,
		@SerializedName("is_group") val isGroup: Boolean?,
		@SerializedName("created") val created: Int?,
		@SerializedName("creator") val creator: String?,
		@SerializedName("is_archived") val isArchived: Boolean?,
		@SerializedName("name_normalized") val nameNormalized: String?,
		@SerializedName("is_mpim") val isMpim: Boolean?,
		@SerializedName("has_pins") val hasPins: Boolean?,
		@SerializedName("is_open") val isOpen: Boolean?,
		@SerializedName("last_read") val lastRead: String?,
		@SerializedName("members") val members: List<String?>?,
		@SerializedName("topic") val topic: Topic?,
		@SerializedName("purpose") val purpose: Purpose?
)

data class Channel(
		@SerializedName("id") val id: String?,
		@SerializedName("name") val name: String?,
		@SerializedName("is_channel") val isChannel: Boolean?,
		@SerializedName("created") val created: Int?,
		@SerializedName("is_archived") val isArchived: Boolean?,
		@SerializedName("is_general") val isGeneral: Boolean?,
		@SerializedName("unlinked") val unlinked: Int?,
		@SerializedName("creator") val creator: String?,
		@SerializedName("name_normalized") val nameNormalized: String?,
		@SerializedName("is_shared") val isShared: Boolean?,
		@SerializedName("is_org_shared") val isOrgShared: Boolean?,
		@SerializedName("has_pins") val hasPins: Boolean?,
		@SerializedName("is_member") val isMember: Boolean?,
		@SerializedName("is_private") val isPrivate: Boolean?,
		@SerializedName("is_mpim") val isMpim: Boolean?,
		@SerializedName("last_read") val lastRead: String?,
		@SerializedName("members") val members: List<String?>?,
		@SerializedName("topic") val topic: Topic?,
		@SerializedName("purpose") val purpose: Purpose?,
		@SerializedName("previous_names") val previousNames: List<Any?>?
)

data class Topic(
		@SerializedName("value") val value: String?,
		@SerializedName("creator") val creator: String?,
		@SerializedName("last_set") val lastSet: Int?
)

data class Purpose(
		@SerializedName("value") val value: String?,
		@SerializedName("creator") val creator: String?,
		@SerializedName("last_set") val lastSet: Int?
)

data class Im(
		@SerializedName("id") val id: String?,
		@SerializedName("created") val created: Int?,
		@SerializedName("is_im") val isIm: Boolean?,
		@SerializedName("is_org_shared") val isOrgShared: Boolean?,
		@SerializedName("user") val user: String?,
		@SerializedName("has_pins") val hasPins: Boolean?,
		@SerializedName("last_read") val lastRead: String?,
		@SerializedName("is_open") val isOpen: Boolean?
)

data class Subteams(
		@SerializedName("self") val self: List<Any?>?,
		@SerializedName("all") val all: List<Any?>?
)

data class Team(
		@SerializedName("id") val id: String?,
		@SerializedName("name") val name: String?,
		@SerializedName("email_domain") val emailDomain: String?,
		@SerializedName("domain") val domain: String?,
		@SerializedName("msg_edit_window_mins") val msgEditWindowMins: Int?,
		@SerializedName("prefs") val prefs: TeamPrefs?,
		@SerializedName("icon") val icon: Icon?,
		@SerializedName("over_storage_limit") val overStorageLimit: Boolean?,
		@SerializedName("messages_count") val messagesCount: Int?,
		@SerializedName("plan") val plan: String?,
		@SerializedName("avatar_base_url") val avatarBaseUrl: String?,
		@SerializedName("over_integrations_limit") val overIntegrationsLimit: Boolean?
)

data class TeamPrefs(
		@SerializedName("locale") val locale: String?,
		@SerializedName("invites_only_admins") val invitesOnlyAdmins: Boolean?,
		@SerializedName("show_join_leave") val showJoinLeave: Boolean?,
		@SerializedName("default_channels") val defaultChannels: List<String?>?,
		@SerializedName("hide_referers") val hideReferers: Boolean?,
		@SerializedName("msg_edit_window_mins") val msgEditWindowMins: Int?,
		@SerializedName("allow_message_deletion") val allowMessageDeletion: Boolean?,
		@SerializedName("calling_app_name") val callingAppName: String?,
		@SerializedName("allow_calls") val allowCalls: Boolean?,
		@SerializedName("allow_calls_interactive_screen_sharing") val allowCallsInteractiveScreenSharing: Boolean?,
		@SerializedName("display_real_names") val displayRealNames: Boolean?,
		@SerializedName("who_can_at_everyone") val whoCanAtEveryone: String?,
		@SerializedName("who_can_at_channel") val whoCanAtChannel: String?,
		@SerializedName("who_can_create_channels") val whoCanCreateChannels: String?,
		@SerializedName("who_can_archive_channels") val whoCanArchiveChannels: String?,
		@SerializedName("who_can_create_groups") val whoCanCreateGroups: String?,
		@SerializedName("who_can_post_general") val whoCanPostGeneral: String?,
		@SerializedName("who_can_kick_channels") val whoCanKickChannels: String?,
		@SerializedName("who_can_kick_groups") val whoCanKickGroups: String?,
		@SerializedName("retention_type") val retentionType: Int?,
		@SerializedName("retention_duration") val retentionDuration: Int?,
		@SerializedName("group_retention_type") val groupRetentionType: Int?,
		@SerializedName("group_retention_duration") val groupRetentionDuration: Int?,
		@SerializedName("dm_retention_type") val dmRetentionType: Int?,
		@SerializedName("dm_retention_duration") val dmRetentionDuration: Int?,
		@SerializedName("file_retention_type") val fileRetentionType: Int?,
		@SerializedName("file_retention_duration") val fileRetentionDuration: Int?,
		@SerializedName("allow_retention_override") val allowRetentionOverride: Boolean?,
		@SerializedName("require_at_for_mention") val requireAtForMention: Boolean?,
		@SerializedName("default_rxns") val defaultRxns: List<String?>?,
		@SerializedName("compliance_export_start") val complianceExportStart: Int?,
		@SerializedName("warn_before_at_channel") val warnBeforeAtChannel: String?,
		@SerializedName("disallow_public_file_urls") val disallowPublicFileUrls: Boolean?,
		@SerializedName("who_can_create_delete_user_groups") val whoCanCreateDeleteUserGroups: String?,
		@SerializedName("who_can_edit_user_groups") val whoCanEditUserGroups: String?,
		@SerializedName("who_can_change_team_profile") val whoCanChangeTeamProfile: String?,
		@SerializedName("display_email_addresses") val displayEmailAddresses: Boolean?,
		@SerializedName("who_has_team_visibility") val whoHasTeamVisibility: String?,
		@SerializedName("disable_file_uploads") val disableFileUploads: String?,
		@SerializedName("disable_file_editing") val disableFileEditing: Boolean?,
		@SerializedName("disable_file_deleting") val disableFileDeleting: Boolean?,
		@SerializedName("uses_customized_custom_status_presets") val usesCustomizedCustomStatusPresets: Boolean?,
		@SerializedName("disable_email_ingestion") val disableEmailIngestion: Boolean?,
		@SerializedName("who_can_manage_guests") val whoCanManageGuests: WhoCanManageGuests?,
		@SerializedName("who_can_create_shared_channels") val whoCanCreateSharedChannels: String?,
		@SerializedName("who_can_manage_shared_channels") val whoCanManageSharedChannels: WhoCanManageSharedChannels?,
		@SerializedName("who_can_post_in_shared_channels") val whoCanPostInSharedChannels: WhoCanPostInSharedChannels?,
		@SerializedName("allow_shared_channel_perms_override") val allowSharedChannelPermsOverride: Boolean?,
		@SerializedName("gdrive_enabled_team") val gdriveEnabledTeam: Boolean?,
		@SerializedName("can_receive_shared_channels_invites") val canReceiveSharedChannelsInvites: Boolean?,
		@SerializedName("enterprise_team_creation_request") val enterpriseTeamCreationRequest: EnterpriseTeamCreationRequest?,
		@SerializedName("enterprise_default_channels") val enterpriseDefaultChannels: List<Any?>?,
		@SerializedName("enterprise_mandatory_channels") val enterpriseMandatoryChannels: List<Any?>?,
		@SerializedName("enterprise_mdm_level") val enterpriseMdmLevel: Int?,
		@SerializedName("enterprise_mdm_date_enabled") val enterpriseMdmDateEnabled: Int?,
		@SerializedName("all_users_can_purchase") val allUsersCanPurchase: Boolean?,
		@SerializedName("loud_channel_mentions_limit") val loudChannelMentionsLimit: Int?,
		@SerializedName("enable_shared_channels") val enableSharedChannels: Int?,
		@SerializedName("custom_tos") val customTos: Boolean?,
		@SerializedName("dnd_enabled") val dndEnabled: Boolean?,
		@SerializedName("dnd_start_hour") val dndStartHour: String?,
		@SerializedName("dnd_end_hour") val dndEndHour: String?,
		@SerializedName("custom_status_presets") val customStatusPresets: List<List<String?>?>?,
		@SerializedName("custom_status_default_emoji") val customStatusDefaultEmoji: String?,
		@SerializedName("auth_mode") val authMode: String?,
		@SerializedName("who_can_manage_integrations") val whoCanManageIntegrations: WhoCanManageIntegrations?,
		@SerializedName("discoverable") val discoverable: String?,
		@SerializedName("invites_limit") val invitesLimit: Boolean?
)

data class WhoCanManageSharedChannels(
		@SerializedName("type") val type: List<String?>?
)

data class WhoCanPostInSharedChannels(
		@SerializedName("type") val type: List<String?>?
)

data class WhoCanManageIntegrations(
		@SerializedName("type") val type: List<String?>?
)

data class WhoCanManageGuests(
		@SerializedName("type") val type: List<String?>?
)

data class EnterpriseTeamCreationRequest(
		@SerializedName("is_enabled") val isEnabled: Boolean?
)

data class Icon(
		@SerializedName("image_34") val image34: String?,
		@SerializedName("image_44") val image44: String?,
		@SerializedName("image_68") val image68: String?,
		@SerializedName("image_88") val image88: String?,
		@SerializedName("image_102") val image102: String?,
		@SerializedName("image_132") val image132: String?,
		@SerializedName("image_230") val image230: String?,
		@SerializedName("image_default") val imageDefault: Boolean?
)