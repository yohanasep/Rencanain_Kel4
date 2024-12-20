package komc.kel4.rencanain.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PersonalTaskRequest {
    @SerializedName("nama_task")
    @Expose
    var namaTask: String? = null

    @SerializedName("deskripsi")
    @Expose
    var deskripsi: String? = null

    @SerializedName("due_date")
    @Expose
    var dueDate: String? = null

    @SerializedName("status")
    @Expose
    var statusTask: String? = null

    @SerializedName("level_prioritas")
    @Expose
    var levelPrioritasTask: String? = null
}