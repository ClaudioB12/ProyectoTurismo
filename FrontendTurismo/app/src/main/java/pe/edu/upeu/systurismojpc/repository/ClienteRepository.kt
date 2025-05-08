package pe.edu.upeu.systurismojpc.repository

import pe.edu.upeu.systurismojpc.modelo.ClienteDto
import pe.edu.upeu.systurismojpc.modelo.ClienteResp

interface ClienteRepository {
    suspend fun deleteCliente(cliente: ClienteDto): Boolean
    suspend fun reportarClientes(): List<ClienteResp>
    suspend fun buscarClienteId(id: Long): ClienteResp
    suspend fun insertarCliente(cliente: ClienteDto): Boolean
    suspend fun modificarCliente(cliente: ClienteDto): Boolean
}