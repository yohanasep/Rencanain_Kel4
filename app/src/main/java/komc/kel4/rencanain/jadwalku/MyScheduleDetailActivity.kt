package komc.kel4.rencanain.jadwalku

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import komc.kel4.rencanain.R

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
    }
}
