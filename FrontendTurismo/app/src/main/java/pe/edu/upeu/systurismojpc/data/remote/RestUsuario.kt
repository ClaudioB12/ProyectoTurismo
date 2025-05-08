package pe.edu.upeu.systurismojpc.data.remote

import pe.edu.upeu.systurismojpc.modelo.LoginRequest
import pe.edu.upeu.systurismojpc.modelo.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RestUsuario {

    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}
