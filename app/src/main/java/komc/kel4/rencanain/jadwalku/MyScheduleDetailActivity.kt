package komc.kel4.rencanain.jadwalku

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import komc.kel4.rencanain.R
import komc.kel4.rencanain.api.PersonalTaskResponse
import komc.kel4.rencanain.api.Retro
import komc.kel4.rencanain.api.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyScheduleDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_schedule_detail)

        // Ambil data dari Intent
        val idSchedule = intent.getStringExtra("idSchedule") ?: ""
        val namaSchedule = intent.getStringExtra("namaSchedule") ?: "N/A"
        val descSchedule = intent.getStringExtra("descSchedule") ?: "N/A"
        val status = intent.getStringExtra("status") ?: "N/A"
        val levelPrioritas = intent.getStringExtra("levelPrioritas") ?: "N/A"
        val tenggat = intent.getStringExtra("tenggat") ?: "N/A"

        // Tampilkan data di TextView
        findViewById<TextView>(R.id.labelScheduleName).text = namaSchedule
        findViewById<TextView>(R.id.labelScheduleDescription).text = descSchedule
        findViewById<TextView>(R.id.labelStatus).text = status
        findViewById<TextView>(R.id.labelLevelPrioritas).text = levelPrioritas
        findViewById<TextView>(R.id.labelTenggat).text = tenggat

        findViewById<ImageButton>(R.id.btnDeleteSchedule).setOnClickListener {
            deletePersonalTask(idSchedule)
        }

//        findViewById<ImageButton>(R.id.btnEditSchedule).setOnClickListener {
//            editPersonalTask(idSchedule)
//        }

//        findViewById<Button>(R.id.btnTandaiSelesai).setOnClickListener {
//            tandaiSelesaiPersonalTask(idSchedule)
//        }
    }

    private fun deletePersonalTask(idSchedule: String) {
        if (idSchedule.isEmpty()) {
            Toast.makeText(this, "ID Task tidak valid.", Toast.LENGTH_SHORT).show()
            return
        }

        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN", null)

        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Sesi Anda telah habis, harap login ulang.", Toast.LENGTH_SHORT).show()
            return
        }

        val userApi = Retro().getRetroClientInstance(token).create(UserApi::class.java)

        userApi.hapusPersonalTask("Bearer $token", idSchedule).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MyScheduleDetailActivity, "Task berhasil dihapus!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Log.d("MyScheduleDetailActivity", "Gagal menghapus task: ${response.code()}")
                    Toast.makeText(this@MyScheduleDetailActivity, "Gagal menghapus task. Silakan coba lagi.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("MyScheduleDetailActivity", "Error: ${t.message}")
                Toast.makeText(this@MyScheduleDetailActivity, "Kesalahan koneksi. Silakan coba lagi.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
