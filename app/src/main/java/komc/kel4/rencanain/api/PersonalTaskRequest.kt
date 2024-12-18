package komc.kel4.rencanain.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PersonalTaskRequest {
    @SerializedName("nama_task")
    @Expose
    val namaTask: String?,

    @SerializedName("label")
    @Expose
    val label: String?,

    @SerializedName("deskripsi")
    @Expose
    val deskripsi: String?,

    @SerializedName("due_date")
    @Expose
    val dueDate: String?,

    @SerializedName("status")
    @Expose
    val status: String?,

    @SerializedName("level_prioritas")
    @Expose
    val levelPrioritas: String?
}