package komc.kel4.rencanain.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import komc.kel4.rencanain.api.*
import komc.kel4.rencanain.jadwalku.PersonalSchedule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyScheduleHelper {
    fun daftarPersonalTasks(context: Context, token: String, limit: Int? = null, callback: (List<PersonalSchedule>) -> Unit) {
        if (token.isEmpty()) {
            Toast.makeText(context, "Sesi Anda telah habis, harap lakukan login ulang", Toast.LENGTH_SHORT).show()
            return
        }

        val retro = Retro().getRetroClientInstance(token)
        val userApi = retro.create(UserApi::class.java)

        userApi.daftarPersonalTasks("Bearer $token").enqueue(object : Callback<PersonalTaskResponse> {
            override fun onResponse(call: Call<PersonalTaskResponse>, response: Response<PersonalTaskResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    var data = response.body()!!.data

                    // Parse the JSON data into a list of Workspace objects
                    val gson = Gson()
                    val workspaceType = object : TypeToken<List<PersonalTask>>() {}.type
                    val tasks: List<PersonalTask> = gson.fromJson(data, workspaceType)

                    // Apply limit if provided
                    val limitedTasks = if (limit != null) tasks.take(limit) else tasks

                    callback(limitedTasks.map {
                        PersonalSchedule(
                            idSchedule = it.idPersonalTask ?: "Unknown ID",
                            namaSchedule = it.namaTask ?: "Unnamed Task",
                            descSchedule = it.deskripsi ?: "No Description",
                            status = it.statusTask ?: "Unknown Status",
                            levelPrioritas = it.levelPrioritasTask ?: "Unknown",
                            tenggat = it.dueDate ?: "No Due Date"
                        )
                    })
                } else {
                    Toast.makeText(context, "Gagal memuat data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PersonalTaskResponse>, t: Throwable) {
                Toast.makeText(context, "Kesalahan koneksi. Silakan coba lagi.", Toast.LENGTH_SHORT).show()
                Log.d("MyScheduleHelper", "Gagal memproses permintaan: ${t.message}")
            }
        })
    }
}