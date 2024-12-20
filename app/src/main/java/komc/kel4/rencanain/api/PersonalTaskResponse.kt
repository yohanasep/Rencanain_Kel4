package komc.kel4.rencanain.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PersonalTaskResponse(
    @SerializedName("status")
    @Expose
    val status: Boolean,

    @SerializedName("message")
    @Expose
    val message: String,

    @SerializedName("data")
    @Expose
    val data: List<PersonalTask>
)

data class PersonalTask(
    @SerializedName("id_personal_task")
    @Expose
    val idPersonalTask: String?,

    @SerializedName("nama_task")
    @Expose
    val namaTask: String?,

    @SerializedName("deskripsi")
    @Expose
    val deskripsi: String?,

    @SerializedName("due_date")
    @Expose
    val dueDate: String?,

    @SerializedName("status")
    @Expose
    val statusTask: String?,

    @SerializedName("level_prioritas")
    @Expose
    val levelPrioritasTask: String?
)
