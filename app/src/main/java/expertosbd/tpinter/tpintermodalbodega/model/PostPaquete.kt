package expertosbd.tpinter.tpintermodalbodega.model

import com.google.gson.annotations.SerializedName

data class PostPaquete(
        @SerializedName("fecha_confirmado")
        val fecha_confirmado: String,
        val barcode: String,
        val estatusDetalleEventoID: Int
)