package komc.kel4.rencanain.workspace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import komc.kel4.rencanain.MainActivity
import komc.kel4.rencanain.R
import komc.kel4.rencanain.databinding.ActivityLoginBinding

class ProjectDetailActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_project_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Ambil objek Project dari intent
        val intent = this.intent

        if(intent.extras != null) {
            val title = intent.getStringExtra("title")
            val deadline = intent.getStringExtra("deadline")
            val progress = intent.getIntExtra("progress", 0)

            val labelTitle = findViewById<TextView>(R.id.labelProjectTitle)
            val labelDeadline = findViewById<TextView>(R.id.labelProjectDeadline)
            val progressBar = findViewById<ProgressBar>(R.id.progressBarProject)

            labelTitle.text = title
            labelDeadline.text = deadline
            progressBar.progress = progress
        }
    }
}