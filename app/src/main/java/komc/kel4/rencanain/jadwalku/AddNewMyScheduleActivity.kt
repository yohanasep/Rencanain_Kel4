package komc.kel4.rencanain.jadwalku

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import komc.kel4.rencanain.R
import komc.kel4.rencanain.api.PersonalTaskResponse
import komc.kel4.rencanain.api.PersonalTaskRequest
import komc.kel4.rencanain.api.Retro
import komc.kel4.rencanain.api.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddNewMyScheduleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_new_my_schedule)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val btnSubmitPersonalSchedule = findViewById<Button>(R.id.btnSubmitPersonalSchedule)
        val eTScheduleName = findViewById<EditText>(R.id.ETScheduleName)
        val eTProjectDesc = findViewById<EditText>(R.id.ETProjectDesc)
        val spinnerStatusSchedule = findViewById<Spinner>(R.id.spinnerStatusSchedule)
        val spinnerLevelPrioritas = findViewById<Spinner>(R.id.spinnerLevelPrioritas)

        val status = arrayOf("Not Started", "In Progress", "Completed")
        val spinnerStatusAdapter =
            ArrayAdapter(this@AddNewMyScheduleActivity, android.R.layout.simple_spinner_item, status)
        spinnerStatusSchedule.adapter = spinnerStatusAdapter

        spinnerStatusSchedule.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@AddNewMyScheduleActivity, "Harap Pilih Status!", Toast.LENGTH_SHORT).show()
            }
        }

        val levelPrioritas = arrayOf("Low", "Medium", "High")
        val spinnerLevelPrioritasAdapter =
            ArrayAdapter(this@AddNewMyScheduleActivity, android.R.layout.simple_spinner_item, levelPrioritas)
        spinnerLevelPrioritas.adapter = spinnerLevelPrioritasAdapter

        spinnerLevelPrioritas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@AddNewMyScheduleActivity, "Harap Pilih Level Prioritas!", Toast.LENGTH_SHORT).show()
            }
        }

        var selectedDate = ""
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$year-${month + 1}-$dayOfMonth"
        }

        btnSubmitPersonalSchedule.setOnClickListener {
            val name = eTScheduleName.text.toString()
            val description = eTProjectDesc.text.toString()
            val statusSchedule = spinnerStatusSchedule.selectedItem.toString()
            val priority = spinnerLevelPrioritas.selectedItem.toString()

            if (name.isEmpty() || description.isEmpty() || selectedDate.isEmpty()) {
                Toast.makeText(this, "Harap isi semua field", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val taskRequest = PersonalTaskRequest().apply {
                namaTask = name
                deskripsi = description
                dueDate = selectedDate
                statusTask = statusSchedule
                levelPrioritasTask = priority
            }

            tambahPersonalTasks(taskRequest)
        }
    }

    private fun tambahPersonalTasks(taskRequest: PersonalTaskRequest) {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN", null)
        println("Token yang digunakan: $token")

        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Token tidak ditemukan. Harap login ulang.", Toast.LENGTH_SHORT).show()
            return
        }

        val retrofit = Retro().getRetroClientInstance()
        val userApi = retrofit.create(UserApi::class.java)

        userApi.tambahPersonalTasks("Bearer $token", taskRequest).enqueue(object : Callback<PersonalTaskResponse> {
            override fun onResponse(call: Call<PersonalTaskResponse>, response: Response<PersonalTaskResponse>) {
                println("Response code: ${response.code()}")
                println("Task Request: $taskRequest")
                if (response.isSuccessful) {
                    Toast.makeText(this@AddNewMyScheduleActivity, "Berhasil menambahkan task!", Toast.LENGTH_SHORT)
                        .show()
                    println("Response body: ${response.body()}")
                    finish()
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Gagal menambahkan task"
                    println("Error: $errorMessage")
                    println("Error response: ${response.errorBody()?.string()}")
                    Toast.makeText(this@AddNewMyScheduleActivity, "Gagal: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PersonalTaskResponse>, t: Throwable) {
                println("Error: ${t.message}")
                Toast.makeText(this@AddNewMyScheduleActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
