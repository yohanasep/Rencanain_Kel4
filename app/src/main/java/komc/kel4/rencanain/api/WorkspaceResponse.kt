package komc.kel4.rencanain.api

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
    val data: List<Workspace>
)

data class Workspace(
    @SerializedName("id_projek")
    @Expose
    val idProjek: String?,

    @SerializedName("nama_projek")
    @Expose
    val namaProjek: String?,

    @SerializedName("deskripsi")
    @Expose
    val deskripsi: String?,

    @SerializedName("status")
    @Expose
    val status: String?,

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
