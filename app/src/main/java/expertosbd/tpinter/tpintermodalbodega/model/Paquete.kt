package expertosbd.tpinter.tpintermodalbodega.model

import com.google.gson.annotations.SerializedName

data class Paquete(
        @SerializedName("id_detalle_evento")
        val detalleEventoID: Int,
        @SerializedName("id_evento_entrada")
        val eventoEntradaID: Int,
        @SerializedName("id_producto")
        val productoID: Int,
        @SerializedName("id_estatus_det_evento")
        var estatusDetalleEventoID: Int = 0,
        val lote: String,
        val producto: String,
        var estatus: String = "NO DEFINIDO"
)