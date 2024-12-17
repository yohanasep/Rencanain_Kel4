package komc.kel4.rencanain.jadwalku

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import komc.kel4.rencanain.R
import komc.kel4.rencanain.jadwalku.AddNewMyScheduleActivity

class MyScheduleFragment : Fragment() {
    private lateinit var btnAddNewSchedule: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //  inflate layout
        val view = inflater.inflate(R.layout.fragment_my_schedule, container, false)

        // list view personal schedule
        val listView: ListView = view.findViewById<ListView>(R.id.lvSchedule)
        val scheduleList = arrayOf(
            "Jadwal 1",
            "Jadwal 2",
        )

        val myScheduleAdapter = MyScheduleAdapter(requireContext(), scheduleList)
        listView.adapter = myScheduleAdapter

        // button untuk pindah ke halaman add new schedule
        val btnGoAddNewSchedule = view.findViewById<View>(R.id.btnGoAddNewSchedule)
        btnGoAddNewSchedule.setOnClickListener {
            val intent = Intent(activity, AddNewMyScheduleActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyScheduleFragment().apply {
            }
    }
}