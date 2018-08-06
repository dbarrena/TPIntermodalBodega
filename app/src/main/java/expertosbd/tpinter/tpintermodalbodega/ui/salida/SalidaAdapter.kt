package expertosbd.tpinter.tpintermodalbodega.ui.salida

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import expertosbd.tpinter.tpintermodalbodega.R
import expertosbd.tpinter.tpintermodalbodega.model.Evento

class SalidaAdapter(val context: Context, private val items: MutableList<Evento>,
                     fragment: Fragment) :
        RecyclerView.Adapter<SalidaAdapter.EventoViewHolder>(), Filterable {

    private val listener: SalidaAdapter.OnItemClickListener
    private var filteredList: MutableList<Evento>

    init {
        this.listener = fragment as SalidaAdapter.OnItemClickListener
        filteredList = items
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    filteredList = items
                } else {
                    val filteredEventoList = mutableListOf<Evento>()
                    for (item in items) {
                        // Edit filters here
                        if (item.placas.toLowerCase().contains(
                                        charString.toLowerCase())) {
                            filteredEventoList.add(item)
                        }
                    }

                    filteredList = filteredEventoList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence,
                                        filterResults: Filter.FilterResults) {
                filteredList = filterResults.values as MutableList<Evento>
                notifyDataSetChanged()
            }
        }
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            listener.onItemClicked(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
        val itemView =
                LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return SalidaAdapter.EventoViewHolder(itemView)
    }

    class EventoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventoID: TextView = itemView.findViewById(R.id.id_evento)
        val placas: TextView = itemView.findViewById(R.id.placas)
        val cliente: TextView = itemView.findViewById(R.id.cliente)
        fun bind(item: Evento) {
            placas.text = item.placas
            cliente.text = item.cliente
            eventoID.text = item.eventoID.toString()
        }
    }


    interface OnItemClickListener {
        fun onItemClicked(item: Evento)
    }

}