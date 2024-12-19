package komc.kel4.rencanain.workspace

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import komc.kel4.rencanain.R

data class Project(val title: String, val deadline: String, val progress: Int)

class ProjectAdapter(private val context: Context, private val projectList: List<Project>) : BaseAdapter() {

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

        val imgProject = view.findViewById<ImageView>(R.id.imgProject)
        val labelTitle = view.findViewById<TextView>(R.id.labelProjectTitle)
        val labelDeadline = view.findViewById<TextView>(R.id.labelProjectDeadline)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBarProject)

        val project = projectList[position]

        labelTitle.text = project.title
        labelDeadline.text = project.deadline
        progressBar.progress = project.progress

        imgProject.setImageResource(R.drawable.baseline_image_24)

        return view
    }
}
