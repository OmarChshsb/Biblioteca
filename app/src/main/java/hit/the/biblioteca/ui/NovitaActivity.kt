package hit.the.biblioteca.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import hit.the.biblioteca.R
import hit.the.biblioteca.adapters.LibroAdapter
import hit.the.biblioteca.models.Libro

class NovitaActivity : AppCompatActivity() {
    private val db by lazy { FirebaseFirestore.getInstance() }
    private val lista = mutableListOf<Libro>()
    private val adapter by lazy { LibroAdapter(lista) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novita)

        val rv = findViewById<RecyclerView>(R.id.rvNovita)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        val sp = findViewById<Spinner>(R.id.spFiltroTipo)
        sp.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,
            listOf("tutti", "libro", "musica", "film"))

        sp.setOnItemSelectedListenerCompat { value ->
            carica(value)
        }
    }

    private fun carica(tipo: String) {
        var q: Query = db.collection("libri").orderBy("createdAt", Query.Direction.DESCENDING).limit(50)
        if (tipo != "tutti") q = q.whereEqualTo("tipo", tipo)
        q.get().addOnSuccessListener { res ->
            val data = res.documents.map { it.toObject(Libro::class.java)!!.apply { id = it.id } }
            lista.clear()
            lista.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }
}

fun Spinner.setOnItemSelectedListenerCompat(onChange: (String) -> Unit) {
    this.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: android.widget.AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
            onChange(parent.getItemAtPosition(position).toString())
        }
        override fun onNothingSelected(parent: android.widget.AdapterView<*>) {}
    }
}