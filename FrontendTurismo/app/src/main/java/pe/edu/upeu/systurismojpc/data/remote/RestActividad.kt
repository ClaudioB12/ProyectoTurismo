package pe.edu.upeu.systurismojpc.data.remote

import pe.edu.upeu.systurismojpc.modelo.ActividadDto
import pe.edu.upeu.systurismojpc.modelo.ActividadResp
import pe.edu.upeu.systurismojpc.modelo.DestinoResp
import retrofit2.Response
import retrofit2.http.*

interface RestActividad {

    @GET(BASE_RUTA + "/listar")
    suspend fun reportarActividades(
        @Header("Authorization") token: String
    ): Response<List<ActividadResp>>

    @GET(BASE_RUTA + "/buscar/{id}")
    suspend fun getActividadId(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<ActividadResp>

    @DELETE(BASE_RUTA + "/eliminar/{id}")
    suspend fun deleteActividad(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<Void>

    @POST(BASE_RUTA + "/guardar")
    suspend fun insertarActividad(
        @Header("Authorization") token: String,
        @Body actividad: ActividadDto
    ): Response<ActividadResp>

    @PUT(BASE_RUTA + "/editar")
    suspend fun actualizarActividad(
        @Header("Authorization") token: String,
        @Body actividad: ActividadDto
    ): Response<ActividadResp>

    @GET("/api/destino/listar")
    suspend fun obtenerDestinos(
        @Header("Authorization") token: String
    ): Response<List<DestinoResp>>

    companion object {
        const val BASE_RUTA = "/api/actividad"
    }
}
