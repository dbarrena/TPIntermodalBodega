package expertosbd.tpinter.tpintermodalbodega.api

import expertosbd.tpinter.tpintermodalbodega.model.*
import expertosbd.tpinter.tpintermodalbodega.utils.BASE_URL
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiServiceInterface {

    @GET("version")
    fun version(): Single<ApexApiResponse<Version>>

    @GET("login/{user}")
    fun login(@Path("user") user: String): Observable<ApexApiResponse<Login>>

    @GET("entrada")
    fun entrada(): Observable<ApexApiResponse<Evento>>

    @GET("salida")
    fun salida(): Observable<ApexApiResponse<Evento>>

    @GET("paquetes/completado/{id_evento}")
    fun paquetesCompletado(@Path("id_evento") eventoID: Int): Observable<ApexApiResponse<Paquete>>

    @GET("paquetes/pendiente/{id_evento}")
    fun paquetesPendiente(@Path("id_evento") eventoID: Int): Observable<ApexApiResponse<Paquete>>

    @GET("estatusdetevento")
    fun estatusDetalleEvento(): Observable<ApexApiResponse<EstatusDetalleEvento>>

    @POST("updatepaquete")
    fun postPaquete(@Body postPaquete: PostPaquete): Single<UpdateResponse>

    @PUT("updatepaquete")
    fun updatePaquete(@Body updatePaquete: UpdatePaquete): Single<UpdateResponse>

    companion object Factory {
        fun create(): ApiServiceInterface {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)

            val retrofit = retrofit2.Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .build()

            return retrofit.create(ApiServiceInterface::class.java)
        }
    }
}