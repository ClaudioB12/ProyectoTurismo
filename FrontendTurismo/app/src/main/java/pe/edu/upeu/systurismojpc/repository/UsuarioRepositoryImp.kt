package pe.edu.upeu.systurismojpc.repository

import pe.edu.upeu.systurismojpc.data.remote.RestUsuario
import pe.edu.upeu.systurismojpc.modelo.LoginRequest
import pe.edu.upeu.systurismojpc.modelo.LoginResponse
import retrofit2.Response
import javax.inject.Inject

class UsuarioRepositoryImp @Inject constructor(
    private val restUsuario: RestUsuario
) : UsuarioRepository {

    override suspend fun loginUsuario(loginRequest: LoginRequest): Response<LoginResponse> {
        return restUsuario.login(loginRequest)
    }
}