package komc.kel4.rencanain.jadwalku

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import komc.kel4.rencanain.R

class myScheduleDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_schedule_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Ambil objek Project dari intent
        val intent = this.intent

        if(intent.extras != null) {
            val namaSchedule = intent.getStringExtra("namaSchedule")
            val descSchedule = intent.getStringExtra("descSchedule")
            val status = intent.getStringExtra("status")
            val levelPrioritas = intent.getIntExtra("levelPrioritas", -1)
            val tenggat = intent.getStringExtra("tenggat")

            val labelNamaSchedule = findViewById<TextView>(R.id.labelScheduleName)
            val labelDescSchedule = findViewById<TextView>(R.id.labelScheduleDescription)
            val labelStatus = findViewById<TextView>(R.id.labelStatus)
            val labelLevelPrioritas = findViewById<TextView>(R.id.labelLevelPrioritas)
            val labelTenggat = findViewById<TextView>(R.id.labelTenggat)

            labelNamaSchedule.text = namaSchedule
            labelDescSchedule.text = descSchedule
            labelStatus.text = status
            labelLevelPrioritas.text = levelPrioritas.toString()
            labelTenggat.text = tenggat
        }
    }

}