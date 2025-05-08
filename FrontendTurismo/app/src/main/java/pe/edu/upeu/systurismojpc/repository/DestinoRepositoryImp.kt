package pe.edu.upeu.systurismojpc.repository

import pe.edu.upeu.systurismojpc.data.remote.RestDestino
import pe.edu.upeu.systurismojpc.modelo.DestinoDto
import pe.edu.upeu.systurismojpc.modelo.DestinoResp
import pe.edu.upeu.systurismojpc.utils.TokenUtils
import javax.inject.Inject

class DestinoRepositoryImp @Inject constructor(
    private val restDestino: RestDestino
) : DestinoRepository {

    override suspend fun deleteDestino(destino: DestinoDto): Boolean {
        val response = restDestino.deleteDestino(TokenUtils.TOKEN_CONTENT, destino.idDestino)
        return response.isSuccessful
    }

    override suspend fun reportarDestinos(): List<DestinoResp> {
        val response = restDestino.reportarDestinos(TokenUtils.TOKEN_CONTENT)
        return if (response.isSuccessful) response.body() ?: emptyList() else emptyList()
    }

    override suspend fun buscarDestinoId(id: Long): DestinoResp {
        val response = restDestino.getDestinoId(TokenUtils.TOKEN_CONTENT, id)
        return response.body() ?: throw Exception("Destino no encontrado")
    }

    override suspend fun insertarDestino(destino: DestinoDto): Boolean {
        val response = restDestino.insertarDestino(TokenUtils.TOKEN_CONTENT, destino)
        return response.isSuccessful && response.body()?.idDestino != null
    }

    override suspend fun modificarDestino(destino: DestinoDto): Boolean {
        val response = restDestino.actualizarDestino(TokenUtils.TOKEN_CONTENT, destino)
        return response.isSuccessful && response.body()?.idDestino != null
    }
}