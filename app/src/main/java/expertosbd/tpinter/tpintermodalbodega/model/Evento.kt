package expertosbd.tpinter.tpintermodalbodega.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Evento(
        @SerializedName("id_unidad")
        val unidadID: Int,
        @SerializedName("fecha_evento")
        val fechaEvento: String,
        @SerializedName("num_cont")
        val numCont: String,
        @SerializedName("hora_llegada")
        val horaLlegada: String,
        @SerializedName("inicio_maniobra")
        val inicioManiobra: String,
        @SerializedName("fin_maniobra")
        val finManiobra: String,
        @SerializedName("hora_salida")
        val horaSalida: String,
        @SerializedName("tipo_evento")
        val tipoEvento: String,
        @SerializedName("tipo_operacion")
        val tipoOperacion: String,
        @SerializedName("tipo_maniobra")
        val tipoManiobra: String,
        val placas: String,
        val cliente: String,
        @SerializedName("id_evento")
        val eventoID: Int,
        @SerializedName("id_cliente")
        val clienteID: Int,
        @SerializedName("id_tipo_evento")
        val tipoEventoID: Int,
        @SerializedName("id_tipo_operacion")
        val tipoOperacionID: Int,
        @SerializedName("id_tipo_maniobra")
        val tipoManiobraID: Int
) : Serializable