package pe.edu.upeu.systurismojpc.data.remote

import pe.edu.upeu.systurismojpc.modelo.DestinoDto
import pe.edu.upeu.systurismojpc.modelo.DestinoResp
import pe.edu.upeu.systurismojpc.modelo.Message
import retrofit2.Response
import retrofit2.http.*

interface RestDestino {

    @GET(BASE_RUTA + "/listar")
    suspend fun reportarDestinos(@Header("Authorization") token: String): Response<List<DestinoResp>>

    @GET(BASE_RUTA + "/buscar/{id}")
    suspend fun getDestinoId(@Header("Authorization") token: String, @Path("id") id: Long): Response<DestinoResp>

    @DELETE(BASE_RUTA + "/eliminar/{id}")
    suspend fun deleteDestino(@Header("Authorization") token: String, @Path("id") id: Long): Response<Unit>

    @PUT(BASE_RUTA + "/editar")
    suspend fun actualizarDestino(
        @Header("Authorization") token: String,
        @Body destino: DestinoDto
    ): Response<DestinoResp>

    @POST(BASE_RUTA + "/guardar")
    suspend fun insertarDestino(
        @Header("Authorization") token: String,
        @Body destino: DestinoDto
    ): Response<DestinoResp>

    companion object {
        const val BASE_RUTA = "/api/destino"
    }
}