package komc.kel4.rencanain.workspace

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import komc.kel4.rencanain.R

class myWorkspace(val idProjek: String, val namaProject: String, val statusProject: String, val deskripsiProject: String, val creator: String)

class ProjectAdapter(private val context: Context, private var projectList: List<myWorkspace>, private val onItemClick: (myWorkspace) -> Unit) : BaseAdapter() {

    // Update data
    fun updateData(newWorkspaceList: List<myWorkspace>) {
        projectList = newWorkspaceList.toMutableList()
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return projectList.size
    }

    override fun getItem(position: Int): Any {
        return projectList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.row_project, parent, false)

        val labelTitle = view.findViewById<TextView>(R.id.labelProjectTitle)
        val labelStatus = view.findViewById<TextView>(R.id.labelProjectStatus)
        val project = projectList[position]

        labelTitle.text = project.namaProject
        labelStatus.text = project.statusProject

        view.setOnClickListener {
            onItemClick(project) // Callback untuk klik item
        }

        return view
    }
}