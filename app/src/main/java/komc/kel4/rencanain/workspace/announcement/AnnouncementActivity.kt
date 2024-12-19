package komc.kel4.rencanain.workspace.announcement

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

        daftarAnnouncements()
    }

    fun daftarAnnouncements(){
//        adapter.updateData(
//            tasks.map {
//                PersonalSchedule(
//                    creator = it.namaCreator ?: "Unknown creator",
//                    createdAt = it.createdAt ?: null,
//                    isi = it.isiAnnouncement ?: "Unknown announcement",
//                )
//            }
//        )
    }
}