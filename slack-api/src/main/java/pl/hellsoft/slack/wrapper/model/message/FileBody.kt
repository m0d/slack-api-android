package pl.hellsoft.slack.wrapper.model.message


/**
 * @author Grzegorz Pawełczuk
 * @email grzegorz.pawelczuk@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 22.12.2017
 */
data class FileBody(
        val id: String,
        val created: Int,
        val timestamp: Int,
        val name: String,
        val title: String,
        val mimetype: String,
        val filetype: String,
        val pretty_type: String,
        val user: String,
        val editable: Boolean,
        val size: Int,
        val mode: String,
        val is_external: Boolean,
        val external_type: String,
        val is_public: Boolean,
        val public_url_shared: Boolean,
        val display_as_bot: Boolean,
        val username: String,
        val url_private: String,
        val url_private_download: String,
        val thumb_64: String?,
        val thumb_80: String?,
        val thumb_360: String?,
        val thumb_360_w: Int?,
        val thumb_360_h: Int?,
        val thumb_160: String?,
        val image_exif_rotation: Int?,
        val original_w: Int,
        val original_h: Int,
        val permalink: String?,
        val permalink_public: String?,
        val channels: List<Any>?,
        val groups: List<Any>?,
        val ims: List<Any>?,
        val comments_count: Int
)