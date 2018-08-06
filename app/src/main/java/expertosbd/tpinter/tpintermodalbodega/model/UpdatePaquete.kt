package expertosbd.tpinter.tpintermodalbodega.model

import com.google.gson.annotations.SerializedName

data class UpdatePaquete(
        val fecha_confirmado: String,
        @SerializedName("id_detalle_evento")
        val detalleEventoID: Int,
        @SerializedName("id_estatus_det_evento")
        val estatusDetalleEventoID: Int,
        @SerializedName("usuario_android")
        val usuarioAndroid: String
)