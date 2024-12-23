package komc.kel4.rencanain.jadwalku

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import komc.kel4.rencanain.R
import komc.kel4.rencanain.api.PersonalTaskResponse
import komc.kel4.rencanain.api.PersonalTaskRequest
import komc.kel4.rencanain.api.Retro
import komc.kel4.rencanain.api.UserApi
import komc.kel4.rencanain.api.WorkspaceResponse
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
        val spinnerStatusAdapter = ArrayAdapter(this@AddNewMyScheduleActivity, android.R.layout.simple_spinner_item, status)
        spinnerStatusSchedule.adapter = spinnerStatusAdapter

        spinnerStatusSchedule.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@AddNewMyScheduleActivity, "Harap Pilih Status!", Toast.LENGTH_SHORT).show()
            }
        }

        val levelPrioritas = arrayOf("Low", "Medium", "High")
        val spinnerLevelPrioritasAdapter = ArrayAdapter(this@AddNewMyScheduleActivity, android.R.layout.simple_spinner_item, levelPrioritas)
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

        val userApi = Retro().getRetroClientInstance(token).create(UserApi::class.java)

        userApi.tambahPersonalTasks("Bearer $token", taskRequest).enqueue(object : Callback<PersonalTaskResponse> {
            override fun onResponse(call: Call<PersonalTaskResponse>, response: Response<PersonalTaskResponse>) {
                if (response.isSuccessful && response.body()?.status == true) {
                    val responseBody = response.body()
                    val workspaceList = mutableListOf<PersonalTaskResponse>()

                    responseBody?.data?.let { dataElement ->
                        if (dataElement.isJsonArray) {
                            val type = object : TypeToken<List<PersonalTaskResponse>>() {}.type
                            workspaceList.addAll(Gson().fromJson(dataElement, type))
                        } else if (dataElement.isJsonObject) {
                            val personalTask = Gson().fromJson(dataElement, PersonalTaskResponse::class.java)
                            workspaceList.add(personalTask)
                        } else {
                            println("Data element is neither JSON Array nor JSON Object")
                        }
                    }
                    Toast.makeText(this@AddNewMyScheduleActivity, "Personal Task berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Gagal menambahkan Personal Task"
                    Log.d("Error", errorMessage)
                    Toast.makeText(this@AddNewMyScheduleActivity, errorMessage, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<PersonalTaskResponse>, t: Throwable) {
                println("Error: ${t.message}")
                Log.d("Error", t.message.toString())
            }
        })
    }
}
