package komc.kel4.rencanain.workspace

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import komc.kel4.rencanain.R
import komc.kel4.rencanain.workspace.announcement.AnnouncementActivity

class ProjectDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_project_detail)

        // Ambil data dari Intent
        val namaProject = intent.getStringExtra("namaProject") ?: "N/A"
        val deskripsiProject = intent.getStringExtra("deskripsiProject") ?: "N/A"
        val creator = intent.getStringExtra("creator") ?: "N/A"
        val statusProject = intent.getStringExtra("statusProject") ?: "N/A"
        val idProject = intent.getStringExtra("idProject") ?: ""

        // Tampilkan data di TextView
        findViewById<TextView>(R.id.labelProjectTitle).text = namaProject
        findViewById<TextView>(R.id.labelProjectDescription).text = deskripsiProject
        findViewById<TextView>(R.id.labelCreator).text = creator
        findViewById<TextView>(R.id.labelStatus).text = statusProject

        findViewById<Button>(R.id.btnAnnouncement).setOnClickListener {
            if (idProject.isNotEmpty()) {
                val intent = Intent(this, AnnouncementActivity::class.java).apply {
                    putExtra("idProject", idProject)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "ID Project tidak valid.", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<ImageButton>(R.id.btnDaftarMember).setOnClickListener {
            if (idProject.isNotEmpty()) {
                val intentMemberList = Intent(this, MemberListActivity::class.java).apply {
                    putExtra("idProject", idProject)
                }
                startActivity(intentMemberList)
            } else {
                Toast.makeText(this, "ID Project tidak valid.", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<ImageButton>(R.id.imgBtnAddProjectTask).setOnClickListener {
            if (idProject.isNotEmpty()) {
                val intentSubTask = Intent(this, AddSubTaskActivity::class.java).apply {
                    putExtra("idProject", idProject)
                }
                startActivity(intentSubTask)
            } else {
                Toast.makeText(this, "ID Project tidak valid.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
