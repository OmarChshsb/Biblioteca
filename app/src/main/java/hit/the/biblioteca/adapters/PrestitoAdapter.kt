package hit.the.biblioteca.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hit.the.biblioteca.R
import hit.the.biblioteca.models.Prestito
import java.text.SimpleDateFormat
import java.util.*

class PrestitoAdapter(private val items: MutableList<Prestito>) :
    RecyclerView.Adapter<PrestitoAdapter.VH>() {

    private val df = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val tvInfo: TextView = v.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val p = items[position]
        holder.tvInfo.text = "Libro: ${p.idLibro} • ${df.format(Date(p.dataInizio))} - ${df.format(Date(p.dataFine))} • ${p.stato}"
    }

    override fun getItemCount() = items.size
}