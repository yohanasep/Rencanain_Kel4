package komc.kel4.rencanain.workspace

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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