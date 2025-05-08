package pe.edu.upeu.systurismojpc.repository

import android.util.Log
import pe.edu.upeu.systurismojpc.data.remote.RestCliente
import pe.edu.upeu.systurismojpc.modelo.ClienteDto
import pe.edu.upeu.systurismojpc.modelo.ClienteResp
import pe.edu.upeu.systurismojpc.utils.TokenUtils
import javax.inject.Inject
import retrofit2.Response


class ClienteRepositoryImp @Inject constructor(
    private val restCliente: RestCliente,
) : ClienteRepository {

    override suspend fun deleteCliente(cliente: ClienteDto): Boolean {
        val response = restCliente.deleteCliente(TokenUtils.TOKEN_CONTENT, cliente.idCliente)
        return response.isSuccessful
    }

    override suspend fun reportarClientes(): List<ClienteResp> {
        try {
            var response = restCliente.reportarClientes(TokenUtils.TOKEN_CONTENT)
            Log.i("ERROR", "VERRR")

            return if (response.isSuccessful) response.body() ?: emptyList() else emptyList()
        }catch (e: Exception){
            Log.e("ERROR", "${e.message}")
            return emptyList()
        }
    }

    override suspend fun buscarClienteId(id: Long): ClienteResp {
        val response = restCliente.getClienteId(TokenUtils.TOKEN_CONTENT, id)
        return response.body() ?: throw Exception("Cliente no encontrado")
    }

    override suspend fun insertarCliente(cliente: ClienteDto): Boolean {
        val response = restCliente.insertarCliente(TokenUtils.TOKEN_CONTENT, cliente)
        return response.isSuccessful && response.body()?.idCliente != null
    }

    override suspend fun modificarCliente(cliente: ClienteDto): Boolean {
        val response = restCliente.actualizarCliente(TokenUtils.TOKEN_CONTENT, cliente)
        return response.isSuccessful && response.body()?.idCliente != null
    }
}