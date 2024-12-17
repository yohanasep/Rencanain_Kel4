package komc.kel4.rencanain.api

import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.DELETE

interface UserApi {
    // PROFILE //
    // login endpoint
    @POST("login")
    fun login(
        @Body userRequest: UserRequest
    ): Call<UserResponse>

    // userprofile endpoint
    @GET("userprofile")
    fun userProfile(
        @Header("Authorization") token: String
    ): Call<UserResponse>

    // PERSONAL TASK //
    // daftar personal task endpoint
    @GET("personaltask")
    fun daftarPersonalTasks(
        @Header("Authorization") token: String
    ): Call<PersonalTaskResponse>
}