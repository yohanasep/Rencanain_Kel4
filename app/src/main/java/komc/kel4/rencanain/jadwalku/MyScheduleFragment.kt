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
import komc.kel4.rencanain.utils.MyScheduleHelper
import komc.kel4.rencanain.utils.WorkspaceHelper


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

//        SchedulesView.onItemClickListener = SchedulesView.adapter.OnItemClickListener { _, _, position, _ ->
//            // Ambil data Project berdasarkan posisi yang diklik
//            if (position < scheduleList.size) {
//                val schedule = scheduleList[position]
//                // Lanjutkan dengan intent
//                val intent = Intent(requireContext(), myScheduleDetailActivity::class.java).apply {
//                    putExtra("namaSchedule", schedule.namaSchedule)
//                    putExtra("descSchedule", schedule.descSchedule)
//                    putExtra("status", schedule.status)
//                    putExtra("levelPrioritas", schedule.levelPrioritas)
//                    putExtra("tenggat", schedule.tenggat)
//                }
//
//                startActivity(intent)
//            }
//        }

        // button untuk pindah ke halaman add new schedule
        val btnGoAddNewSchedule = view.findViewById<View>(R.id.btnGoAddNewSchedule)
        btnGoAddNewSchedule.setOnClickListener {
            val intent = Intent(activity, AddNewMyScheduleActivity::class.java)
            startActivity(intent)
        }

        daftarPersonalTasks()

        return view
    }

    override fun onResume() {
        super.onResume()
        daftarPersonalTasks()
    }

    private fun daftarPersonalTasks() {
        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN", null)

        if (token == null) {
            Toast.makeText(requireContext(), "Token tidak ditemukan", Toast.LENGTH_SHORT).show()
            return
        }

        val MyScheduleHelper = MyScheduleHelper()
        MyScheduleHelper.daftarPersonalTasks(requireContext(), token) { tasks ->
            adapter.updateData(tasks)
        }
    }
}