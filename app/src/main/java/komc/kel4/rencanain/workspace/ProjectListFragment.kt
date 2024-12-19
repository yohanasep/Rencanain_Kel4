package komc.kel4.rencanain.workspace

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import komc.kel4.rencanain.R
import komc.kel4.rencanain.api.*

class ProjectListFragment : Fragment() {
    private lateinit var scheduleListView: ListView
    private lateinit var adapter: ProjectAdapter
    private val scheduleList = mutableListOf<myWorkspace>() // PersonalSchedule ganti ke myWorkspace

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_project_list, container, false)

        val WorkspacesView: ListView = view.findViewById(R.id.lvProject)

        adapter = ProjectAdapter(requireContext(), scheduleList)
        WorkspacesView.adapter = adapter

        // button untuk pindah ke halaman add new project
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
        println("Token saat ini: $token")

        if (token == null) {
            Toast.makeText(requireContext(), "Token tidak ditemukan", Toast.LENGTH_SHORT).show()
            return
        }

        val retro = Retro().getRetroClientInstance(token)
        val userApi = retro.create(UserApi::class.java)

        userApi.daftarWorkspace("Bearer $token").enqueue(object : Callback<WorkspaceResponse> {
            override fun onResponse(call: Call<WorkspaceResponse>, response: Response<WorkspaceResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val tasks = response.body()!!.data

                    adapter.updateData(
                        tasks.map {
                            myWorkspace(
                                idProjek = it.idProjek ?: "Unknown ID",
                                namaProjek = it.namaProjek ?: "Unknown Project",
                                status = it.status ?: "Unknown Status"
                            )
                        }
                    )

                    println("Response code: ${response.code()}")
                    println("Data yang diterima: ${response.body()?.data}")
                    println("Error body: ${response.errorBody()?.string()}")
                } else {
                    Toast.makeText(requireContext(), "Gagal memuat data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<WorkspaceResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Kesalahan koneksi: ${t.message}", Toast.LENGTH_SHORT).show()
                println("Kesalahan koneksi: ${t.message}")

            }
        })
    }
}