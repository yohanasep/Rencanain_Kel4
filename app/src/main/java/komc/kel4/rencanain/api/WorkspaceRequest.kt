package komc.kel4.rencanain.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WorkspaceRequest {
    @SerializedName("nama_projek")
    @Expose
    var namaProjek: String? = null

    @SerializedName("deskripsi")
    @Expose
    var deskripsi: String? = null

    @SerializedName("status")
    @Expose
    var statusWorkspace: String? = null

    @SerializedName("details")
    @Expose
    var details: List<Detail>? = null
}

class Detail {
    @SerializedName("statusinv")
    @Expose
    var statusInv: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null
}