package komc.kel4.rencanain.jadwalku

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import komc.kel4.rencanain.MainActivity
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
        // jgn di sentuh!!!
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_new_my_schedule)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // jgn di sentuh!!!

        // Inisialisasi semua view menggunakan findViewById
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val btnSubmitPersonalSchedule = findViewById<Button>(R.id.btnSubmitPersonalSchedule)
        val eTScheduleName = findViewById<EditText>(R.id.ETScheduleName)
        val eTProjectDesc = findViewById<EditText>(R.id.ETProjectDesc)
        val spinnerStatusSchedule = findViewById<Spinner>(R.id.spinnerStatusSchedule)
        val spinnerLevelPrioritas = findViewById<Spinner>(R.id.spinnerLevelPrioritas)

        // spinner status item
        val spinnerStatusId = spinnerStatusSchedule

        val status = arrayOf("Not Started", "In Progress", "Completed")
        val spinnerStatusAdapter =
            ArrayAdapter(this@AddNewMyScheduleActivity, android.R.layout.simple_spinner_item, status)
        spinnerStatusId.adapter = spinnerStatusAdapter

        spinnerStatusId?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@AddNewMyScheduleActivity, status[p2], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@AddNewMyScheduleActivity, "Harap Pilih Status!", Toast.LENGTH_SHORT).show()
            }

        }

        // spinner prioritas item
        val spinnerLevelPrioritasId = spinnerLevelPrioritas

        val levelPrioritas = arrayOf("Low", "Medium", "High")
        val spinnerLevelPrioritasAdapter =
            ArrayAdapter(this@AddNewMyScheduleActivity, android.R.layout.simple_spinner_item, levelPrioritas)
        spinnerLevelPrioritasId.adapter = spinnerLevelPrioritasAdapter

        spinnerLevelPrioritasId?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@AddNewMyScheduleActivity, levelPrioritas[p2], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@AddNewMyScheduleActivity, "Harap Pilih Level Prioritas!", Toast.LENGTH_SHORT).show()
            }

        }

        // CalendarView selected date
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
        // Mengambil token dari SharedPreferences
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN", null)
        Log.d("AddNewMyScheduleActivity", "Token yang digunakan: $token")

        // Validasi token
        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Token tidak ditemukan. Harap login ulang.", Toast.LENGTH_SHORT).show()
            return
        }

        // Membuat instance UserApi
        val retrofit = Retro().getRetroClientInstance()
        val userApi = retrofit.create(UserApi::class.java)

        Log.d("AddNewMyScheduleActivity", "Task Request: $taskRequest")

        // Mengirim request ke API
        userApi.tambahPersonalTasks("Bearer $token", taskRequest).enqueue(object : Callback<PersonalTaskResponse> {
            override fun onResponse(call: Call<PersonalTaskResponse>, response: Response<PersonalTaskResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AddNewMyScheduleActivity, "Berhasil menambahkan task!", Toast.LENGTH_SHORT).show()


//                    val intent = Intent(this@AddNewMyScheduleActivity, MainActivity::class.java)
//                    startActivity(intent)
//                    finishAndRemoveTask()
//                    finish()

                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Gagal menambahkan task"
                    Toast.makeText(this@AddNewMyScheduleActivity, "Gagal: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }


            override fun onFailure(call: Call<PersonalTaskResponse>, t: Throwable) {
                Toast.makeText(this@AddNewMyScheduleActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
