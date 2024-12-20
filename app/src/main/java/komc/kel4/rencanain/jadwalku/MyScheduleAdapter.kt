package komc.kel4.rencanain.jadwalku

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import komc.kel4.rencanain.R

class PersonalSchedule(val namaSchedule: String, val idSchedule: String, val descSchedule: String, val status: String, val levelPrioritas: String, val tenggat: String)

class MyScheduleAdapter(private val context: Context, private var scheduleList: MutableList<PersonalSchedule>) : BaseAdapter() {

    // Update data
    fun updateData(newScheduleList: List<PersonalSchedule>) {
        scheduleList = newScheduleList.toMutableList()
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return scheduleList.size
    }

    override fun getItem(position: Int): Any {
        return scheduleList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.row_schedule, parent, false)

        val labelTitle = view.findViewById<TextView>(R.id.myScheduleList)
        val labelStatus = view.findViewById<TextView>(R.id.personalTaskStatus)
        val schedule = scheduleList[position]

        labelTitle.text = schedule.namaSchedule
        labelStatus.text = schedule.status

        return view
    }
}

