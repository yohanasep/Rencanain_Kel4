package komc.kel4.rencanain.utils

import android.content.Context
import android.widget.Toast
import komc.kel4.rencanain.api.*
import komc.kel4.rencanain.workspace.myWorkspace
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                    var tasks = response.body()!!.data

                    // Jika limit diberikan, batasi jumlah item
                    if (limit != null) {
                        tasks = tasks.take(limit)
                    }

                    callback(tasks.map {
                        myWorkspace(
                            idProjek = it.idProjek ?: "Unknown ID",
                            namaProjek = it.namaProjek ?: "Unknown Project",
                            status = it.status ?: "Unknown Status"
                        )
                    })
                } else {
                    Toast.makeText(context, "Gagal memuat data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<WorkspaceResponse>, t: Throwable) {
                Toast.makeText(context, "Kesalahan koneksi: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
