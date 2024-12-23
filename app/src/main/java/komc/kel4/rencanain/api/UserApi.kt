package komc.kel4.rencanain.api

import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.GET


interface UserApi {
    // PROFILE //
    // login endpoint
    @POST("login")
    fun login(
        @Body userRequest: UserRequest
    ): Call<UserResponse>

    // register endpoint
    @POST("register")
    fun register(
        @Body registerRequest: RegisterRequest
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

    // tambah personal task endpoint
    @POST("personaltask")
    fun tambahPersonalTasks(
        @Header("Authorization") token: String,
        @Body personalTaskRequest: PersonalTaskRequest
    ): Call<PersonalTaskResponse>

    // hapus personal task endpoint
    @DELETE("personaltask/{id}")
    fun hapusPersonalTask(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<Void>

    // WORKSPACE //
    // daftar workspace endpoint
    @GET("workspaces")
    fun daftarWorkspace(
        @Header("Authorization") token: String
    ): Call<WorkspaceResponse>

    // tambah workspace endpoint
    @POST("workspaces")
    fun tambahWorkspace(
        @Header("Authorization") token: String,
        @Body workspaceRequest: WorkspaceRequest
    ): Call<WorkspaceResponse>

    // daftar workspace endpoint
    @GET("workspaces/{ws_id}/announcement")
    fun daftarAnnouncement(
        @Path("ws_id") ws_id: String,
        @Header("Authorization") token: String
    ): Call<AnnouncementResponse>

}