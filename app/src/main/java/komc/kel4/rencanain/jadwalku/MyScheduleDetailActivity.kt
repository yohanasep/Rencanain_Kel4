package komc.kel4.rencanain.jadwalku

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import komc.kel4.rencanain.R
import komc.kel4.rencanain.api.UserApi
import komc.kel4.rencanain.api.WorkspaceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyScheduleDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_schedule_detail)

        // Ambil data dari Intent
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

        val btnDeleteSchedule = findViewById<ImageButton>(R.id.btnDeleteSchedule)
        btnDeleteSchedule.setOnClickListener{
//            userApi.tambahWorkspace("Bearer $token", workspaceRequest).enqueue(object : Callback<Void> {
//                override fun onResponse(call: Call<Void>, response: Response<Void>) {
//                    if (response.isSuccessful) {
//                        println("Personal task deleted successfully!")
//                    } else {
//                        println("Failed to delete personal task: ${response.code()}")
//                    }
//                }
//
//                override fun onFailure(call: Call<Void>, t: Throwable) {
//                    println("Error: ${t.message}")
//                }
//            })
        }
    }
}
