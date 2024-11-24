package komc.kel4.rencanain

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import komc.kel4.rencanain.databinding.ActivityMainBinding
import komc.kel4.rencanain.workspace.ProjectListActivity
import komc.kel4.rencanain.jadwalku.myScheduleListActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set OnClickListener untuk tombol yang ada di bottom navigation
        binding.bottomNav.imgBtnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.bottomNav.imgBtnAddSchedule.setOnClickListener {
            val intent = Intent(this, myScheduleListActivity::class.java)
            startActivity(intent)
        }

        binding.bottomNav.imgBtnProjectList.setOnClickListener {
            val intent = Intent(this, ProjectListActivity::class.java)
            startActivity(intent)
        }
    }
}