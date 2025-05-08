package pe.edu.upeu.systurismojpc.repository

import pe.edu.upeu.systurismojpc.modelo.LoginRequest
import pe.edu.upeu.systurismojpc.modelo.LoginResponse
import retrofit2.Response

interface UsuarioRepository {
    suspend fun loginUsuario(loginRequest: LoginRequest): Response<LoginResponse>
}
