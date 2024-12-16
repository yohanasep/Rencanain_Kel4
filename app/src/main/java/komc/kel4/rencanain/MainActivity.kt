package komc.kel4.rencanain

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import komc.kel4.rencanain.databinding.ActivityMainBinding
import komc.kel4.rencanain.jadwalku.MyScheduleFragment
import komc.kel4.rencanain.workspace.ProjectListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cek apakah token sudah ada
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN", null)

        if (token.isNullOrEmpty()) {
            // Jika token tidak ada, kembali ke LoginActivity
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
            finish() // Tutup MainActivity
            return
        }

        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menuHome -> replaceFragment(HomeFragment())
                R.id.menuSchedules -> replaceFragment(MyScheduleFragment())
                R.id.menuProjects -> replaceFragment(ProjectListFragment())
                else -> replaceFragment(HomeFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    fun setActiveNavItem(menuItemId: Int) {
        binding.bottomNavigationView.menu.findItem(menuItemId).isChecked = true
    }
}