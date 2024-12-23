package komc.kel4.rencanain.workspace.announcement

import android.content.Context
import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import komc.kel4.rencanain.R

class AnnouncementActivity : AppCompatActivity() {
    private lateinit var AnnouncementRecyclerView: ListView
    private lateinit var adapter: AnnouncementAdapter
    private val announcementList = mutableListOf<Announcement>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_announcement)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val AnnouncementView: RecyclerView = findViewById<RecyclerView>(R.id.rvAnnouncement)

        adapter = AnnouncementAdapter(this, announcementList)
        AnnouncementRecyclerView.adapter = adapter

        daftarAnnouncement()
    }

    fun daftarAnnouncement() {
//        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
//        val token = sharedPreferences.getString("TOKEN", null)
//
//        if (token == null) {
//            Toast.makeText(requireContext(), "Token tidak ditemukan", Toast.LENGTH_SHORT).show()
//            return
//        }
    }
}