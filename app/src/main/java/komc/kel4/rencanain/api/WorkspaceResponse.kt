package komc.kel4.rencanain.api

import com.google.gson.JsonElement
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WorkspaceResponse(
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

data class Workspace(
    @SerializedName("id_projek")
    @Expose
    val idProject: String?,

    @SerializedName("nama_projek")
    @Expose
    val namaProject: String?,

    @SerializedName("deskripsi")
    @Expose
    val deskripsiProject: String?,

    @SerializedName("status")
    @Expose
    val statusProject: String?,

    @SerializedName("creator")
    @Expose
    val creator: String?,

    @SerializedName("members")
    @Expose
    val members: List<Member>
)

// menyimpan nama member dari workspace
data class Member(
    @SerializedName("id_user")
    @Expose
    val idUser: String?,

    @SerializedName("name")
    @Expose
    val name: String?,

    @SerializedName("email")
    @Expose
    val email: String?
)
