package komc.kel4.rencanain.profil

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import komc.kel4.rencanain.LoginActivity
import komc.kel4.rencanain.MainActivity
import komc.kel4.rencanain.R
import komc.kel4.rencanain.api.Retro
import komc.kel4.rencanain.api.UserApi
import komc.kel4.rencanain.api.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilActivity : AppCompatActivity() {
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profil)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Views
        val btnLogout = findViewById<TextView>(R.id.btnLogout)
        tvName = findViewById(R.id.tvName)
        tvEmail = findViewById(R.id.tvEmail)

        btnLogout.setOnClickListener {
            logout()
        }

        // Fetch User Profile
        dataUserProfile()
    }

    private fun dataUserProfile() {
        // Ambil token dari SharedPreferences
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN", null)
        println("Token saat ini: $token")


        if (token != null) {
            // Retrofit instance
            val retrofit = Retro().getRetroClientInstance()
            val userApi = retrofit.create(UserApi::class.java)

            // Call API
            userApi.userProfile("Bearer $token").enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        val user = response.body()?.data
                        tvName.text = user?.name ?: "Nama tidak ditemukan"
                        tvEmail.text = user?.email ?: "Email tidak ditemukan"
                    } else {
                        println("Gagal mengambil data profile: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    println("Error: ${t.message}")
                }
            })
        } else {
            println("Token tidak ditemukan, harap login kembali.")
            logout()
        }
    }

    private fun logout() {
        // Hapus token dari SharedPreferences
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.remove("TOKEN") // Hapus token
        editor.apply()

        // Kembali ke halaman LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}