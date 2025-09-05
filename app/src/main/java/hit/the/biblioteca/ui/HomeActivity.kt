package hit.the.biblioteca.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hit.the.biblioteca.R

class HomeActivity : AppCompatActivity() {
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val db by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val btnAdmin = findViewById<Button>(R.id.btnAdmin)

        val uid = auth.currentUser?.uid
        if (uid == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        db.collection("utenti").document(uid).get().addOnSuccessListener { doc ->
            val nome = doc.getString("nome") ?: "Utente"
            val ruolo = doc.getString("ruolo") ?: "user"
            tvWelcome.text = "Ciao, $nome"
            btnAdmin.visibility = if (ruolo == "admin") View.VISIBLE else View.GONE
        }

        findViewById<Button>(R.id.btnSearch).setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
        findViewById<Button>(R.id.btnNovita).setOnClickListener {
            startActivity(Intent(this, NovitaActivity::class.java))
        }
        findViewById<Button>(R.id.btnPrestiti).setOnClickListener {
            startActivity(Intent(this, PrestitiActivity::class.java))
        }
        btnAdmin.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
        }
        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}