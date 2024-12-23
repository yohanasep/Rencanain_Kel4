package komc.kel4.rencanain.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import komc.kel4.rencanain.api.*
import komc.kel4.rencanain.workspace.myWorkspace
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WorkspaceHelper {
    fun daftarWorkspace(context: Context, token: String, limit: Int? = null, callback: (List<myWorkspace>) -> Unit) {
        if (token.isEmpty()) {
            Toast.makeText(context, "Token tidak ditemukan", Toast.LENGTH_SHORT).show()
            return
        }

        val retro = Retro().getRetroClientInstance(token)
        val userApi = retro.create(UserApi::class.java)

        userApi.daftarWorkspace("Bearer $token").enqueue(object : Callback<WorkspaceResponse> {
            override fun onResponse(call: Call<WorkspaceResponse>, response: Response<WorkspaceResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()!!.data

                    // Parse the JSON data into a list of Workspace objects
                    val gson = Gson()
                    val workspaceType = object : TypeToken<List<Workspace>>() {}.type
                    val tasks: List<Workspace> = gson.fromJson(data, workspaceType)

                    // Apply limit if provided
                    val limitedTasks = if (limit != null) tasks.take(limit) else tasks

                    callback(limitedTasks.map {
                        myWorkspace(
                            idProjek = it.idProject ?: "Unknown ID",
                            namaProject = it.namaProject ?: "Unknown Project",
                            statusProject = it.statusProject ?: "Unknown Status",
                            deskripsiProject = it.deskripsiProject ?: "No Description",
                            creator = it.creator ?: "Unknown Creator"
                        )
                    })
                } else {
                    Toast.makeText(context, "Gagal memuat data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<WorkspaceResponse>, t: Throwable) {
                Log.d("WorkspaceHelper", "Error: ${t.message}")
            }
        })
    }
}