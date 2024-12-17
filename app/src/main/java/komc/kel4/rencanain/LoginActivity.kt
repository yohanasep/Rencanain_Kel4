package komc.kel4.rencanain

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import komc.kel4.rencanain.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnRegister = findViewById<TextView>(R.id.btnRegister)
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // For login
        val emailInput = findViewById<EditText>(R.id.ETextEmail)
        val passwordInput = findViewById<EditText>(R.id.ETextInputPassword)
        val loginButton = findViewById<Button>(R.id.btnMasuk)

        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Email dan Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        val retrofit = Retro().getRetroClientInstance()
        val userApi = retrofit.create(UserApi::class.java)

        val userRequest = UserRequest().apply {
            this.email = email
            this.password = password
        }

        userApi.login(userRequest).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val user = response.body()?.data
                    val token = user?.token

                    if (token != null) {
                        Toast.makeText(this@LoginActivity, "Login berhasil!", Toast.LENGTH_SHORT).show()
                        // Simpan token di SharedPreferences
                        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("TOKEN", token)
                        editor.apply()

                        // Navigasi ke MainActivity
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Token tidak ditemukan!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Login Gagal, Periksa kembali email dan password!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
