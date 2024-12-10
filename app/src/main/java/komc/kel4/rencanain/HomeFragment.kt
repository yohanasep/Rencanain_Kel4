package komc.kel4.rencanain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import komc.kel4.rencanain.workspace.ProjectListFragment
import komc.kel4.rencanain.jadwalku.MyScheduleFragment

class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        fun setButtonClickListener(buttonId: Int, fragment: Fragment, menuId: Int) {
            val button = view.findViewById<View>(buttonId)
            button.setOnClickListener {
                replaceFragment(fragment)
                (requireActivity() as MainActivity).setActiveNavItem(menuId)
            }
        }

        setButtonClickListener(R.id.btnProject_HOME, ProjectListFragment(), R.id.menuProjects)
        setButtonClickListener(R.id.btnMySchedule_HOME, MyScheduleFragment(), R.id.menuSchedules)

        return view
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
            }
    }
}
