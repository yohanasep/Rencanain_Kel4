package komc.kel4.rencanain

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import komc.kel4.rencanain.workspace.ProjectListActivity
import komc.kel4.rencanain.workspace.HomeActivity


class MainActivity : AppCompatActivity() {
    private lateinit var btnGoToProject: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnGoToProject = findViewById(R.id.btnGoToProject)
        btnGoToProject.setOnClickListener {
            val intent = Intent(this, ProjectListActivity::class.java)
            startActivity(intent)
        }
    }
}