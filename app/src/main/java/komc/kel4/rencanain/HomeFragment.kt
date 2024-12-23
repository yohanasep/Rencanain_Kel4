package komc.kel4.rencanain

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import komc.kel4.rencanain.jadwalku.MyScheduleAdapter
import komc.kel4.rencanain.jadwalku.MyScheduleDetailActivity
import komc.kel4.rencanain.jadwalku.PersonalSchedule
import komc.kel4.rencanain.profil.ProfilActivity
import komc.kel4.rencanain.utils.MyScheduleHelper
import komc.kel4.rencanain.utils.WorkspaceHelper
import komc.kel4.rencanain.workspace.ProjectAdapter
import komc.kel4.rencanain.workspace.ProjectDetailActivity
import komc.kel4.rencanain.workspace.myWorkspace

class HomeFragment : Fragment() {
    private lateinit var projectAdapter: ProjectAdapter
    private val projectList = mutableListOf<myWorkspace>()

    private lateinit var scheduleAdapter: MyScheduleAdapter
    private val scheduleList = mutableListOf<PersonalSchedule>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val WorkspacesView: ListView = view.findViewById(R.id.lvProject_HOME)
        projectAdapter = ProjectAdapter(requireContext(), projectList) { project ->
            val intent = Intent(requireContext(), ProjectDetailActivity::class.java).apply {
                putExtra("idProject", project.idProjek)
                putExtra("namaProject", project.namaProject)
                putExtra("statusProject", project.statusProject)
                putExtra("deskripsiProject", project.deskripsiProject)
                putExtra("creator", project.creator)
            }

            startActivity(intent)
        }
        WorkspacesView.adapter = projectAdapter

        val PersonalScheduleView: ListView = view.findViewById(R.id.lvSchedule_HOME)
        scheduleAdapter = MyScheduleAdapter(requireContext(), scheduleList) { schedule ->
            val intent = Intent(requireContext(), MyScheduleDetailActivity::class.java).apply {
                putExtra("namaSchedule", schedule.namaSchedule)
                putExtra("descSchedule", schedule.descSchedule)
                putExtra("status", schedule.status)
                putExtra("levelPrioritas", schedule.levelPrioritas)
                putExtra("tenggat", schedule.tenggat)
            }
            startActivity(intent)
        }
        PersonalScheduleView.adapter = scheduleAdapter


        // Find the button or clickable view
        val btnProfile = view.findViewById<View>(R.id.imgProfileHome)

        // Set an OnClickListener
        btnProfile.setOnClickListener {
            // Use Intent to navigate to ProfilActivity
            val intent = Intent(requireContext(), ProfilActivity::class.java)
            startActivity(intent)
        }

        fun setButtonClickListener(buttonId: Int, fragment: Fragment, menuId: Int) {
            val button = view.findViewById<View>(buttonId)
            button.setOnClickListener {
                replaceFragment(fragment)
                (requireActivity() as MainActivity).setActiveNavItem(menuId)
            }
        }


        daftarWorkspace()
        daftarPersonalTask()

        return view
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    private fun daftarWorkspace() {
        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN", null)

        if (token == null) {
            Toast.makeText(requireContext(), "Token tidak ditemukan", Toast.LENGTH_SHORT).show()
            return
        }

        val workspaceHelper = WorkspaceHelper()
        workspaceHelper.daftarWorkspace(requireContext(), token, limit = 3) { projects ->
            projectAdapter.updateData(projects)
        }
    }

    private fun daftarPersonalTask() {
        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN", null)

        if (token == null) {
            Toast.makeText(requireContext(), "Token tidak ditemukan", Toast.LENGTH_SHORT).show()
            return
        }

        val myScheduleHelper = MyScheduleHelper()
        myScheduleHelper.daftarPersonalTasks(requireContext(), token, limit = 3) { tasks ->
            scheduleAdapter.updateData(tasks)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
            }
    }
}
