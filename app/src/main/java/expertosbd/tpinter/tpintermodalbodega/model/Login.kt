package expertosbd.tpinter.tpintermodalbodega.model

import com.google.gson.annotations.SerializedName

data class Login(
        val password_android: String,
        val nombre: String,
        @SerializedName("apellido_paterno")
        val apellidoPaterno: String
)