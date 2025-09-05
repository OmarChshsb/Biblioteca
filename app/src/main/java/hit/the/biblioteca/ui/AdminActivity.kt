package hit.the.biblioteca.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import hit.the.biblioteca.R
import hit.the.biblioteca.models.Libro

class AdminActivity : AppCompatActivity() {
    private val db by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val etTitolo = findViewById<EditText>(R.id.etTitolo)
        val etAutore = findViewById<EditText>(R.id.etAutore)
        val etCategoria = findViewById<EditText>(R.id.etCategoria)
        val etAnno = findViewById<EditText>(R.id.etAnno)
        val spTipo = findViewById<Spinner>(R.id.spTipo)

        spTipo.adapter = ArrayAdapter(this