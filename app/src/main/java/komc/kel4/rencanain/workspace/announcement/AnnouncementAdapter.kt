package komc.kel4.rencanain.workspace.announcement

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import komc.kel4.rencanain.R

data class Announcement(val creator: String, val createdAt: String, val isi: String)

class AnnouncementAdapter(private val context: Context, private val announcementList: MutableList<Announcement>) : BaseAdapter() {
    override fun getCount(): Int {
        return announcementList.size
    }

    override fun getItem(position: Int): Any {
        return announcementList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.row_project, parent, false)

        var labelCreatorName = view.findViewById<TextView>(R.id.labelCreatorName)
        var labelCreatedAt = view.findViewById<TextView>(R.id.labelCreatedAt)
        var isiAnnouncement = view.findViewById<TextView>(R.id.isiAnnouncement)

        val announcement = announcementList[position]

        labelCreatorName.text = announcement.creator
        labelCreatedAt.text = announcement.createdAt
        isiAnnouncement.text = announcement.isi

        return view
    }
}
