package komc.kel4.rencanain.jadwalku

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import komc.kel4.rencanain.R

class MyScheduleAdapter(private val context: Context, private val scheduleList: Array<String>) : BaseAdapter() {

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

        val checkBox = view.findViewById<CheckBox>(R.id.myScheduleList)
        checkBox.text = scheduleList[position]

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            println("Item ${scheduleList[position]} checked: $isChecked")
        }

        return view
    }
}
