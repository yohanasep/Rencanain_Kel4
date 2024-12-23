package komc.kel4.rencanain.api

import com.google.gson.JsonElement
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AnnouncementResponse(
    @SerializedName("status")
    @Expose
    val status: Boolean,

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
    val idAnnouncement: String?,

    @SerializedName("announcement")
    @Expose
    val isiAnnouncement: String?,

    @SerializedName("created_by")
    @Expose
    val creator: String?,
)
