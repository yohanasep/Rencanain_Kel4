package komc.kel4.rencanain.jadwalku

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import komc.kel4.rencanain.R
import komc.kel4.rencanain.api.*


class MyScheduleFragment : Fragment() {
    private lateinit var scheduleListView: ListView
    private lateinit var adapter: MyScheduleAdapter
    private val scheduleList = mutableListOf<PersonalSchedule>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout
        val view = inflater.inflate(R.layout.fragment_my_schedule, container, false)

        // List view personal schedule
        val SchedulesView: ListView = view.findViewById(R.id.lvSchedule)

        adapter = MyScheduleAdapter(requireContext(), scheduleList)
        SchedulesView.adapter = adapter

        // Load personal tasks
        daftarPersonalTasks()

        // button untuk pindah ke halaman add new schedule
        val btnGoAddNewSchedule = view.findViewById<View>(R.id.btnGoAddNewSchedule)
        btnGoAddNewSchedule.setOnClickListener {
            val intent = Intent(activity, AddNewMyScheduleActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        daftarPersonalTasks()
    }

    private fun daftarPersonalTasks() {
        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN", null)
        println("Token saat ini: $token")

        if (token == null) {
            Toast.makeText(requireContext(), "Token tidak ditemukan", Toast.LENGTH_SHORT).show()
            return
        }

        val retro = Retro().getRetroClientInstance(token)
        val userApi = retro.create(UserApi::class.java)

        userApi.daftarPersonalTasks("Bearer $token").enqueue(object : Callback<PersonalTaskResponse> {
            override fun onResponse(call: Call<PersonalTaskResponse>, response: Response<PersonalTaskResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val tasks = response.body()!!.data

                    adapter.updateData(
                        tasks.map {
                            PersonalSchedule(
                                idSchedule = it.idPersonalTask ?: "Unknown ID",
                                namaSchedule = it.namaTask ?: "Unnamed Task",
                                descSchedule = it.deskripsi ?: "No Description",
                                status = it.statusTask ?: "Unknown Status",
                                levelPrioritas = it.levelPrioritasTask ?: "Unknown",
                                tenggat = it.dueDate ?: "No Due Date"
                            )
                        }
                    )

                    println("Response code: ${response.code()}")
                    println("Data yang diterima: ${response.body()?.data}")
                } else {
                    Toast.makeText(requireContext(), "Gagal memuat data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PersonalTaskResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Kesalahan koneksi: ${t.message}", Toast.LENGTH_SHORT).show()
            }



        })
    }
}