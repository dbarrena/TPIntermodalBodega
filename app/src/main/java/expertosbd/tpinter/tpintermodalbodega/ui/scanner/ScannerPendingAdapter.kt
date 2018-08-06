package expertosbd.tpinter.tpintermodalbodega.ui.scanner

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import expertosbd.tpinter.tpintermodalbodega.R
import expertosbd.tpinter.tpintermodalbodega.model.Paquete

class ScannerPendingAdapter(val context: Context,
                            private var items: MutableList<Paquete> = mutableListOf(),
                            activity: Activity) :
        RecyclerView.Adapter<ScannerPendingAdapter.ScannerViewHolder>() {

    private val listener: ScannerPendingAdapter.OnItemClickListener =
            activity as ScannerPendingAdapter.OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScannerViewHolder {
        val itemView =
                LayoutInflater.from(context)
                        .inflate(R.layout.list_item_scanner_pending, parent, false)
        return ScannerPendingAdapter.ScannerViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ScannerViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            listener.onItemClicked(item)
        }
    }

    fun addPaquete(paquete: Paquete) {
        items.add(paquete)
    }

    fun removePaqueteByID(id: Int) {
        val iterator = items.iterator()
        while (iterator.hasNext()) {
            if (iterator.next().detalleEventoID == id)
                iterator.remove()
        }
    }

    fun findPaqueteByID(id: Int): Paquete? {
        for (paquete in items) {
            if (paquete.detalleEventoID == id) {
                return paquete
            }
        }
        return null
    }

    class ScannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val producto: TextView = itemView.findViewById(R.id.producto)
        val lote: TextView = itemView.findViewById(R.id.lote)
        val detalleEventoID: TextView = itemView.findViewById(R.id.id_detalle_evento)
        fun bind(item: Paquete) {
            producto.text = item.producto
            lote.text = item.lote
            detalleEventoID.text = item.detalleEventoID.toString()
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(paquete: Paquete)
    }
}