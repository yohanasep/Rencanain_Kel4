package komc.kel4.rencanain.workspace.announcement

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import komc.kel4.rencanain.R
import komc.kel4.rencanain.api.AnnouncementRequest
import komc.kel4.rencanain.api.AnnouncementResponse
import komc.kel4.rencanain.api.Retro
import komc.kel4.rencanain.api.UserApi
import komc.kel4.rencanain.api.WorkspaceRequest
import komc.kel4.rencanain.api.WorkspaceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddNewAnnouncementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_new_announcement)

        // Mengatur padding untuk menyesuaikan dengan status bar dan navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Ambil data ID proyek dari intent
        val idProject = intent.getStringExtra("idProject") ?: ""
        if (idProject.isEmpty()) {
            Toast.makeText(this, "ID Proyek tidak ditemukan!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        findViewById<Button>(R.id.btnSubmitAddAnnouncement).setOnClickListener {
            val isi = findViewById<EditText>(R.id.ETAnnouncement).text.toString()

        // Buat request pengumuman
        val announcementRequest = AnnouncementRequest().apply {
            isiAnnouncement = isi
        }

        // Tambahkan pengumuman
        tambahAnnouncement(idProject, announcementRequest)
        }
    }

    private fun tambahAnnouncement(idProject: String, announcementRequest: AnnouncementRequest) {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN", null)

        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Sesi Anda telah habis, harap lakukan login ulang.", Toast.LENGTH_SHORT).show()
            return
        }

        // Membuat instance UserApi
        val userApi = Retro().getRetroClientInstance(token).create(UserApi::class.java)

        // Panggil API untuk menambahkan pengumuman
        userApi.tambahAnnouncement(idProject, announcementRequest,"Bearer $token").enqueue(object : Callback<AnnouncementResponse> {
            override fun onResponse(call: Call<AnnouncementResponse>, response: Response<AnnouncementResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    val responseBody = response.body()
                    val workspaceList = mutableListOf<AnnouncementResponse>()

                    responseBody?.data?.let { dataElement ->
                        if (dataElement.isJsonArray) {
                            val type = object : TypeToken<List<AnnouncementResponse>>() {}.type
                            workspaceList.addAll(Gson().fromJson(dataElement, type))
                        } else if (dataElement.isJsonObject) {
                            val workspace = Gson().fromJson(dataElement, AnnouncementResponse::class.java)
                            workspaceList.add(workspace)
                        } else {
                            println("Data element is neither JSON Array nor JSON Object")
                        }
                    }

                    Toast.makeText(this@AddNewAnnouncementActivity, "Pengumuman berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Gagal menambahkan pengumuman."
                    Toast.makeText(
                        this@AddNewAnnouncementActivity, "Error: $errorMessage", Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<AnnouncementResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@AddNewAnnouncementActivity, "Gagal terhubung dengan server", Toast.LENGTH_LONG).show()
                Log.d("AddNewAnnouncementActivity", "Error: ${t.message}")
            }
        })
    }
}
