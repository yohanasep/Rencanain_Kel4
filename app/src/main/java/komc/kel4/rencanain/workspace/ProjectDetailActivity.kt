package komc.kel4.rencanain.workspace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import komc.kel4.rencanain.R
import komc.kel4.rencanain.workspace.announcement.AnnouncementActivity

class ProjectDetailActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_project_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val namaProject = intent.getStringExtra("namaProject") ?: "N/A"
        val status = intent.getStringExtra("status") ?: "N/A"

        findViewById<TextView>(R.id.labelProjectTitle).text = namaProject
        findViewById<TextView>(R.id.labelProjectStatus).text = status
    }
}