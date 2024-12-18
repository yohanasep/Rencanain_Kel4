package komc.kel4.rencanain.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PersonalTaskRequest {
    @SerializedName("nama_task")
    @Expose
    val namaTask: String? = null

    @SerializedName("label")
    @Expose
    val label: String? = null

    @SerializedName("deskripsi")
    @Expose
    val deskripsi: String? = null

    @SerializedName("due_date")
    @Expose
    val dueDate: String? = null

    @SerializedName("status")
    @Expose
    val status: String? = null

    @SerializedName("level_prioritas")
    @Expose
    val levelPrioritas: String? = null
}