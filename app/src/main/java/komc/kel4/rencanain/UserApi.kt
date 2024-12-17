package komc.kel4.rencanain

import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET

interface UserApi {
    // login endpoint
    @POST("login")
    fun login(
        @Body userRequest: UserRequest
    ): Call<UserResponse>

    // userprofile endpoint
    @GET("userprofile")
    fun userprofile(
        @Header("Authorization") token: String
    ): Call<UserResponse>

}