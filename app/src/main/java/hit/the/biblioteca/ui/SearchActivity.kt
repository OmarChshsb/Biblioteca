package hit.the.biblioteca.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import hit.the.biblioteca.R
import hit.the.biblioteca.adapters.LibroAdapter
import hit.the.biblioteca.models.Libro

class SearchActivity : AppCompatActivity() {
    private val db by lazy { FirebaseFirestore.getInstance() }
    private val lista = mutableListOf<Libro>()
    private val adapter by lazy { LibroAdapter(lista) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val rv = findViewById<RecyclerView>(R.id.rvLibri)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        findViewById<Button>(R.id.btnSearch).setOnClickListener {
            val q = findViewById<EditText>(R.id.etSearch).text.toString().trim()
            cerca(q)
        }
    }

    private fun cerca(q: String) {
        if (q.isEmpty()) {
            lista.clear()
            adapter.notifyDataSetChanged()
            return
        }
        val ref = db.collection("libri")
        ref.whereGreaterThanOrEqualTo("titolo", q)
            .whereLessThanOrEqualTo("titolo", q + '\uf8ff')
            .get().addOnSuccessListener { r1 ->
                val tmp = r1.documents.map { it.toObject(Libro::class.java)!!.apply { id = it.id } }.toMutableList()
                ref.whereGreaterThanOrEqualTo("autore", q)
                    .whereLessThanOrEqualTo("autore", q + '\uf8ff')
                    .get().addOnSuccessListener { r2 ->
                        val second = r2.documents.map { it.toObject(Libro::class.java)!!.apply { id = it.id } }
                        val ids = tmp.map { it.id }.toMutableSet()
                        second.forEach { if (ids.add(it.id)) tmp.add(it) }
                        lista.clear()
                        lista.addAll(tmp)
                        adapter.notifyDataSetChanged()