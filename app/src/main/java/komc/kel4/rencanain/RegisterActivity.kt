package komc.kel4.rencanain

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import komc.kel4.rencanain.api.RegisterRequest
import komc.kel4.rencanain.api.Retro
import komc.kel4.rencanain.api.UserApi
import komc.kel4.rencanain.api.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnLogin = findViewById<TextView>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // For login
        val emailInput = findViewById<EditText>(R.id.ETextEmail)
        val namaInput = findViewById<EditText>(R.id.ETextUname)
        val passwordInput = findViewById<EditText>(R.id.ETextInputPassword)
        val registerButton = findViewById<Button>(R.id.btnRegister)

        registerButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val name = namaInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                registerUser(email, name, password)
            } else {
                Toast.makeText(this, " Harap isi semua field!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(email: String, name: String, password: String) {
        val retrofit = Retro().getRetroClientInstance()
        val userApi = retrofit.create(UserApi::class.java)

        val registerRequest = RegisterRequest().apply {
            this.email = email
            this.name = name
            this.password = password
        }

        userApi.register(registerRequest).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful && response.body()?.data?.token != null) {
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Gagal melakukan registrasi."
                    Toast.makeText(this@RegisterActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("RegisterActivity", "Error registering user", t)
                Toast.makeText(this@RegisterActivity, "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}