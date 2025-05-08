package pe.edu.upeu.systurismojpc.repository

import pe.edu.upeu.systurismojpc.modelo.DestinoDto
import pe.edu.upeu.systurismojpc.modelo.DestinoResp

interface DestinoRepository {
    suspend fun deleteDestino(destino: DestinoDto): Boolean
    suspend fun reportarDestinos(): List<DestinoResp>
    suspend fun buscarDestinoId(id: Long): DestinoResp
    suspend fun insertarDestino(destino: DestinoDto): Boolean
    suspend fun modificarDestino(destino: DestinoDto): Boolean
}
