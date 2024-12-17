package komc.kel4.rencanain.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserResponse {
    @SerializedName("data")
    @Expose
    val data: User? = null

    class User {
        @SerializedName("id_user")
        @Expose
        val userId: String? = null

        @SerializedName("email")
        @Expose
        val email: String? = null

        @SerializedName("name")
        @Expose
        val name: String? = null

        @SerializedName("token")
        @Expose
        val token: String? = null
    }
}