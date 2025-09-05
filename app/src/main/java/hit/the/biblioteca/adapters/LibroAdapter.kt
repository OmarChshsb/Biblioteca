package hit.the.biblioteca.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hit.the.biblioteca.R
import hit.the.biblioteca.models.Libro

class LibroAdapter(private val items: MutableList<Libro>) :
    RecyclerView.Adapter<LibroAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val tvTitolo: TextView = v.findViewById(R.id.tvTitolo)
        val tvAutore: TextView = v.findViewById(R.id.tvAutore)
        val tvCategoriaAnno: TextView = v.findViewById(R.id.tvCategoriaAnno)
        val tvDisponibile: TextView = v.findViewById(R.id.tvDisponibile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_libro, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val it = items[position]
        holder.tvTitolo.text = it.titolo
        holder.tvAutore.text = it.autore
        holder.tvCategoriaAnno.text = "${it.categoria} • ${it.anno}"
        holder.tvDisponibile.text = if (it.disponibile) "Disponibile" else "Non disponibile"
    }

    override fun getItemCount() = items.size
}