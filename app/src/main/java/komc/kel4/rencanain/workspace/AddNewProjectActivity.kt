package komc.kel4.rencanain.workspace

import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import komc.kel4.rencanain.R
import komc.kel4.rencanain.api.Retro
import komc.kel4.rencanain.api.UserApi
import komc.kel4.rencanain.api.WorkspaceRequest
import komc.kel4.rencanain.api.Detail
import komc.kel4.rencanain.api.WorkspaceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddNewProjectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_project)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val inviteMembersContainer = findViewById<LinearLayout>(R.id.inviteMembersContainer)
        val btnAddMoreMembers = findViewById<Button>(R.id.btnAddMoreMembers)
        val eTProjectName = findViewById<EditText>(R.id.ETProjectName)
        val eTProjectDesc = findViewById<EditText>(R.id.ETProjectDesc)
        val spinnerStatusProject = findViewById<Spinner>(R.id.spinnerStatusProject)
        val btnSubmitAddProject = findViewById<Button>(R.id.btnSubmitAddProject)

        btnAddMoreMembers.setOnClickListener { val newEditText = EditText(this)
            newEditText.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            newEditText.hint = "Masukkan Email Member"
            newEditText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            inviteMembersContainer.addView(newEditText)
        }

        val status = arrayOf("Not Started", "In Progress", "Done")
        val spinnerStatusAdapter =
            ArrayAdapter(this@AddNewProjectActivity, android.R.layout.simple_spinner_item, status)
        spinnerStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerStatusProject.adapter = spinnerStatusAdapter

        btnSubmitAddProject.setOnClickListener {
            val projectName = eTProjectName.text.toString()
            val projectDesc = eTProjectDesc.text.toString()
            val projectStatus = spinnerStatusProject.selectedItem.toString()
            val memberEmails = mutableListOf<String>()

            for (i in 0 until inviteMembersContainer.childCount) {
                val child = inviteMembersContainer.getChildAt(i)
                if (child is EditText) {
                    val email = child.text.toString()
                    if (email.isNotBlank()) {
                        memberEmails.add(email)
                    }
                }
            }

            if (projectName.isBlank() || projectDesc.isBlank() || memberEmails.isEmpty()) {
                Toast.makeText(this, "Harap isi semua field dan tambahkan minimal satu anggota.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val details = memberEmails.map { email ->
                Detail().apply {
                    statusInv = "Pending"
                    this.email = email
                }
            }

            val workspaceRequest = WorkspaceRequest().apply {
                namaProjek = projectName
                deskripsi = projectDesc
                statusWorkspace = projectStatus
                this.details = details
            }

            tambahWorkspace(workspaceRequest)
        }
    }

    private fun tambahWorkspace(workspaceRequest: WorkspaceRequest) {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN", null)
        println("Token yang digunakan: $token")

        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Sesi Anda telah habis, harap lakukan login ulang.", Toast.LENGTH_SHORT).show()
            return
        }

        val userApi = Retro().getRetroClientInstance(token).create(UserApi::class.java)

        userApi.tambahWorkspace("Bearer $token", workspaceRequest).enqueue(object : Callback<WorkspaceResponse> {
            override fun onResponse(call: Call<WorkspaceResponse>, response: Response<WorkspaceResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    val responseBody = response.body()
                    val workspaceList = mutableListOf<WorkspaceResponse>()

                    responseBody?.data?.let { dataElement ->
                        if (dataElement.isJsonArray) {
                            val type = object : TypeToken<List<WorkspaceResponse>>() {}.type
                            workspaceList.addAll(Gson().fromJson(dataElement, type))
                        } else if (dataElement.isJsonObject) {
                            val workspace = Gson().fromJson(dataElement, WorkspaceResponse::class.java)
                            workspaceList.add(workspace)
                        } else {
                        println("Data element is neither JSON Array nor JSON Object")
                        }
                    }
                    Toast.makeText(this@AddNewProjectActivity, "Workspace berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Gagal menambahkan workspace"
                    println("Error: $errorMessage")
                    Toast.makeText(this@AddNewProjectActivity, errorMessage, Toast.LENGTH_LONG).show()
                }
            }


            override fun onFailure(call: Call<WorkspaceResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(
                    this@AddNewProjectActivity,
                    "Gagal menghubungi server: ${t.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
