package komc.kel4.rencanain.workspace.announcement

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import komc.kel4.rencanain.R

data class Announcement(val creator: String, val createdAt: String, val isi: String)

class AnnouncementAdapter(private val context: Context, private var announcementList: List<Announcement>) : RecyclerView.Adapter<AnnouncementAdapter.AnnouncementViewHolder>() {

    // ViewHolder class
    class AnnouncementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val labelCreatorName: TextView = itemView.findViewById(R.id.labelCreatorName)
        val labelCreatedAt: TextView = itemView.findViewById(R.id.labelCreatedAt)
        val isiAnnouncement: TextView = itemView.findViewById(R.id.isiAnnouncement)
    }

    // Update data
    fun updateData(newAnnouncementList: List<Announcement>) {
        announcementList = newAnnouncementList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_project, parent, false)
        return AnnouncementViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnnouncementViewHolder, position: Int) {
        val announcement = announcementList[position]
        holder.labelCreatorName.text = announcement.creator
        holder.labelCreatedAt.text = announcement.createdAt
        holder.isiAnnouncement.text = announcement.isi
    }

    override fun getItemCount(): Int {
        return announcementList.size
    }
}
