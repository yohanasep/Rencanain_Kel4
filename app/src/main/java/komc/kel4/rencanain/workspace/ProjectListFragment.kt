package komc.kel4.rencanain.workspace

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import komc.kel4.rencanain.R
import komc.kel4.rencanain.utils.WorkspaceHelper

class ProjectListFragment : Fragment() {
    private lateinit var adapter: ProjectAdapter
    private val scheduleList = mutableListOf<myWorkspace>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_project_list, container, false)

        val WorkspacesView: ListView = view.findViewById(R.id.lvProject)

        adapter = ProjectAdapter(requireContext(), scheduleList)
        WorkspacesView.adapter = adapter

        // Button untuk pindah ke halaman add new project
        val btnGoAddNewProject = view.findViewById<View>(R.id.btnGoAddNewProject)
        btnGoAddNewProject.setOnClickListener {
            val intent = Intent(activity, AddNewProjectActivity::class.java)
            startActivity(intent)
        }

        daftarWorkspace()

        return view
    }

    private fun daftarWorkspace() {
        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN", null)

        if (token == null) {
            Toast.makeText(requireContext(), "Token tidak ditemukan", Toast.LENGTH_SHORT).show()
            return
        }

        val workspaceHelper = WorkspaceHelper()
        workspaceHelper.daftarWorkspace(requireContext(), token) { tasks ->
             adapter.updateData(tasks)
         }
    }

    override fun onResume() {
        super.onResume()
        daftarWorkspace()
    }
}
