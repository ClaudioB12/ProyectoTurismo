package pe.edu.upeu.systurismojpc.data.remote

import pe.edu.upeu.systurismojpc.modelo.ClienteDto
import pe.edu.upeu.systurismojpc.modelo.ClienteResp
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.Response

interface RestCliente {

    @GET("/api/cliente/listar")
    suspend fun reportarClientes(
        @Header("Authorization") token: String
    ): Response<List<ClienteResp>>

    @GET("/api/cliente/buscar/{id}")
    suspend fun getClienteId(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<ClienteResp>

    @DELETE("/api/cliente/eliminar/{id}")
    suspend fun deleteCliente(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<Void>

    @PUT("/api/cliente/editar")
    suspend fun actualizarCliente(
        @Header("Authorization") token: String,
        @Body cliente: ClienteDto
    ): Response<ClienteResp>

    @POST("/api/cliente/guardar")
    suspend fun insertarCliente(
        @Header("Authorization") token: String,
        @Body cliente: ClienteDto
    ): Response<ClienteResp>
}
