package komc.kel4.rencanain.api

import com.google.gson.JsonElement
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AnnouncementResponse(
    @SerializedName("success")
    @Expose
    val success: Boolean,

    @SerializedName("message")
    @Expose
    val message: String,

    @SerializedName("data")
    @Expose
    val data: JsonElement
)

data class AnnouncementData(
    @SerializedName("id_announcement")
    @Expose
    val idAnnouncement: String? = "",

    @SerializedName("announcement")
    @Expose
    val isiAnnouncement: String? = "Tidak ada isi pengumuman.",

    @SerializedName("created_by")
    @Expose
    val creator: String? = "Tidak diketahui",

    @SerializedName("created_at")
    @Expose
    val createdAt: String? = ""
)

