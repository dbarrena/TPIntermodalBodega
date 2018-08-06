package expertosbd.tpinter.tpintermodalbodega.model

import com.google.gson.annotations.SerializedName

data class EstatusDetalleEvento(
        @SerializedName("id_estatus_det_evento")
        val estatusDetalleEventoID: Int,
        @SerializedName("estatus_det_evento")
        val estatusDetalleEvento: String
) {
    override fun toString(): String {
        return this.estatusDetalleEvento
    }
}