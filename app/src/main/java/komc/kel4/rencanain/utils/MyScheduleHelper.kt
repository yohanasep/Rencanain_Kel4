package komc.kel4.rencanain.utils

import android.content.Context
import android.widget.Toast
import komc.kel4.rencanain.api.*
import komc.kel4.rencanain.jadwalku.PersonalSchedule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyScheduleHelper {
    fun daftarPersonalTasks(context: Context, token: String, limit: Int? = null, callback: (List<PersonalSchedule>) -> Unit) {
        if (token.isEmpty()) {
            Toast.makeText(context, "Token tidak ditemukan", Toast.LENGTH_SHORT).show()
            return
        }

        val retro = Retro().getRetroClientInstance(token)
        val userApi = retro.create(UserApi::class.java)

        userApi.daftarPersonalTasks("Bearer $token").enqueue(object : Callback<PersonalTaskResponse> {
            override fun onResponse(call: Call<PersonalTaskResponse>, response: Response<PersonalTaskResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    var tasks = response.body()!!.data

                    // Jika limit diberikan, batasi jumlah item
                    if (limit != null) {
                        tasks = tasks.take(limit)
                    }

                    callback(tasks.map {
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
                Toast.makeText(context, "Kesalahan koneksi: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}