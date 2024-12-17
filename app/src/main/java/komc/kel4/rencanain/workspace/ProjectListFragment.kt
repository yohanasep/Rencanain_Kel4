package komc.kel4.rencanain.workspace

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import komc.kel4.rencanain.R

class ProjectListFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_project_list, container, false)

        val listView: ListView = view.findViewById<ListView>(R.id.lvProject)

        val projects = listOf(
            Project("Project A", "10 Jan 2024", 60),
            Project("Project B", "15 Feb 2024", 80),
            Project("Project C", "1 Mar 2024", 40)
        )

        // Atur adapter
        val projectAdapter = ProjectAdapter(requireContext(), projects)
        listView.adapter = projectAdapter


        // button untuk pindah ke halaman add new project
        val btnGoAddNewProject = view.findViewById<View>(R.id.btnGoAddNewProject)
        btnGoAddNewProject.setOnClickListener {
            val intent = Intent(activity, AddNewProjectActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProjectListFragment().apply {
            }
    }
}