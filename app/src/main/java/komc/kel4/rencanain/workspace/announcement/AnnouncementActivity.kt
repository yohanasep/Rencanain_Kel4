package komc.kel4.rencanain.workspace.announcement

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import komc.kel4.rencanain.R
import komc.kel4.rencanain.api.AnnouncementResponse
import komc.kel4.rencanain.api.Retro
import komc.kel4.rencanain.api.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnnouncementActivity : AppCompatActivity() {
    private lateinit var AnnouncementView: RecyclerView
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

        AnnouncementView = findViewById<RecyclerView>(R.id.rvAnnouncement)

        adapter = AnnouncementAdapter(this, announcementList)
        AnnouncementView.adapter = adapter

        val idProject = intent.getStringExtra("idProject") ?: ""
        daftarAnnouncement(idProject)
    }

    fun daftarAnnouncement(idProject: String) {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN", null)

        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Sesi Anda telah habis, harap login ulang.", Toast.LENGTH_SHORT).show()
            return
        }

        val userApi = Retro().getRetroClientInstance(token).create(UserApi::class.java)

        userApi.daftarAnnouncement("Bearer $token", idProject).enqueue(object : Callback<AnnouncementResponse> {
            override fun onResponse(call: Call<AnnouncementResponse>, response: Response<AnnouncementResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data

                    val gson = Gson()
                    val announcementType = object : TypeToken<List<Announcement>>() {}.type
                    val announcementDataList: List<Announcement> = gson.fromJson(data, announcementType)

                    // Map the AnnouncementData to Announcement
                    val announcements = announcementDataList.map {
                        Announcement(
                            creator = it.creator ?: "Unknown Creator",
                            createdAt = it.createdAt ?: "Unknown Date",
                            isi = it.isi ?: "No content"
                        )
                    }

                    // Update the RecyclerView with the new data
                    announcementList.clear()
                    announcementList.addAll(announcements)
                    adapter.updateData(announcementList)
                } else {
                    Log.d("AnnouncementActivity", "Gagal mengambil data: ${response.body()}")
                    Toast.makeText(this@AnnouncementActivity, "Gagal mengambil data. Silakan coba lagi.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AnnouncementResponse>, t: Throwable) {
                Log.d("AnnouncementActivity", "Error: ${t.message}")
                Toast.makeText(this@AnnouncementActivity, "Kesalahan koneksi. Silakan coba lagi.", Toast.LENGTH_SHORT).show()
            }
        })
    }

}