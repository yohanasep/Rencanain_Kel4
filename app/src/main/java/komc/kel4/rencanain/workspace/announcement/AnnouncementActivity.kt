package komc.kel4.rencanain.workspace.announcement

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import komc.kel4.rencanain.R
import komc.kel4.rencanain.api.AnnouncementData
import komc.kel4.rencanain.api.AnnouncementResponse
import komc.kel4.rencanain.api.Retro
import komc.kel4.rencanain.api.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class AnnouncementActivity : AppCompatActivity() {
    private lateinit var announcementView: ListView
    private lateinit var adapter: AnnouncementAdapter
    private val announcementList = mutableListOf<Announcement>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcement)

        announcementView = findViewById(R.id.lvAnnouncement)

        adapter = AnnouncementAdapter(this, announcementList)
        announcementView.adapter = adapter

        val idProject = intent.getStringExtra("idProject") ?: ""
        Log.d("AnnouncementActivity", "ID Project: $idProject")

        daftarAnnouncement(idProject) { announcements ->
            announcementList.clear()
            announcementList.addAll(announcements)
            adapter.updateData(announcementList)
        }

        val btnGoAddNewAnnouncement: FloatingActionButton = findViewById(R.id.btnGoAddNewAnnouncement)
        btnGoAddNewAnnouncement.setOnClickListener {
            val intent = Intent(this, AddNewAnnouncementActivity::class.java)
            intent.putExtra("idProject", idProject)
            startActivity(intent)
        }
    }

    fun daftarAnnouncement(idProject: String, callback: (List<Announcement>) -> Unit) {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN", null)

        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Sesi Anda telah habis, harap login ulang.", Toast.LENGTH_SHORT).show()
            return
        }

        val userApi = Retro().getRetroClientInstance(token).create(UserApi::class.java)

        userApi.daftarAnnouncement(idProject, "Bearer $token").enqueue(object : Callback<AnnouncementResponse> {
            override fun onResponse(call: Call<AnnouncementResponse>, response: Response<AnnouncementResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data

                    val gson = Gson()
                    val announcementType = object : TypeToken<List<AnnouncementData>>() {}.type
                    val announcements: List<AnnouncementData> = gson.fromJson(data, announcementType)

                    callback(announcements.map {
                        Announcement(
                            creator = it.creator ?: "Tidak diketahui",
                            createdAt = formatDate(it.createdAt ?: ""),
                            isi = it.isiAnnouncement ?: "Tidak ada isi pengumuman."
                        )
                    })
                } else {
                    Log.d("AnnouncementActivity", "Gagal mengambil data: ${response.code()}")
                    Toast.makeText(this@AnnouncementActivity, "Gagal mengambil data. Silakan coba lagi.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AnnouncementResponse>, t: Throwable) {
                Log.d("AnnouncementActivity", "Error: ${t.message}")
                Toast.makeText(this@AnnouncementActivity, "Kesalahan koneksi. Silakan coba lagi.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun formatDate(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            outputFormat.format(date ?: java.util.Date())
        } catch (e: Exception) {
            "Tanggal tidak valid"
        }
    }

}