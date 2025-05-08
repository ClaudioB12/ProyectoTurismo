package pe.edu.upeu.systurismojpc.repository

import pe.edu.upeu.systurismojpc.data.remote.RestActividad
import pe.edu.upeu.systurismojpc.modelo.ActividadDto
import pe.edu.upeu.systurismojpc.modelo.ActividadResp
import pe.edu.upeu.systurismojpc.modelo.DestinoResp
import pe.edu.upeu.systurismojpc.utils.TokenUtils
import javax.inject.Inject

class ActividadRepositoryImp @Inject constructor(
    private val restActividad: RestActividad
) : ActividadRepository {

    override suspend fun deleteActividad(actividad: ActividadDto): Boolean {
        val response = restActividad.deleteActividad(TokenUtils.TOKEN_CONTENT, actividad.idActividad)
        return response.isSuccessful
    }

    override suspend fun reportarActividades(): List<ActividadResp> {
        val response = restActividad.reportarActividades(TokenUtils.TOKEN_CONTENT)
        return if (response.isSuccessful) response.body() ?: emptyList() else emptyList()
    }

    override suspend fun buscarActividadId(id: Long): ActividadResp {
        val response = restActividad.getActividadId(TokenUtils.TOKEN_CONTENT, id)
        return response.body() ?: throw Exception("Actividad no encontrada")
    }

    override suspend fun insertarActividad(actividad: ActividadDto): Boolean {
        val response = restActividad.insertarActividad(TokenUtils.TOKEN_CONTENT, actividad)
        println("Insertar actividad - Código: ${response.code()}, Body: ${response.body()}, Error: ${response.errorBody()?.string()}")
        return response.isSuccessful && response.body()?.idActividad != null
    }

    override suspend fun modificarActividad(actividad: ActividadDto): Boolean {
        val response = restActividad.actualizarActividad(TokenUtils.TOKEN_CONTENT, actividad)
        return response.isSuccessful && response.body()?.idActividad != null
    }

    override suspend fun obtenerDestinos(): List<DestinoResp> {
        val response = restActividad.obtenerDestinos(TokenUtils.TOKEN_CONTENT)
        return if (response.isSuccessful) response.body() ?: emptyList() else emptyList()
    }
}
