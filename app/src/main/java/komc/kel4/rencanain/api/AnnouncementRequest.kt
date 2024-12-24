package komc.kel4.rencanain.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AnnouncementRequest {
    @SerializedName("announcement")
    @Expose
    var isiAnnouncement: String? = null
}