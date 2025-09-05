package hit.the.biblioteca.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hit.the.biblioteca.R
import hit.the.biblioteca.adapters.PrestitoAdapter
import hit.the.biblioteca.models.Prestito

class PrestitiActivity : AppCompatActivity() {
    private val db by lazy { FirebaseFirestore.getInstance() }
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val lista = mutableListOf<Prestito>()
    private val adapter by lazy { PrestitoAdapter(lista) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prestiti)

        val rv = findViewById<RecyclerView>(R.id.rvPrestiti)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        val uid = auth.currentUser?.uid ?: return
        db.collection("prestiti").whereEqualTo("idUtente", uid).get()
            .addOnSuccessListener { res ->
                val data = res.documents.map { it.toObject(Prestito::class.java)!!.apply { id = it.id } }
                lista.clear()
                lista.addAll(data)
                adapter.notifyDataSetChanged()
            }
    }
}