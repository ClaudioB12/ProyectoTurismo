package pe.edu.upeu.systurismojpc.ui.presentation.screens.cliente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upeu.systurismojpc.modelo.ClienteDto
import pe.edu.upeu.systurismojpc.modelo.ClienteResp
import pe.edu.upeu.systurismojpc.modelo.toDto
import pe.edu.upeu.systurismojpc.repository.ClienteRepository
import javax.inject.Inject


@HiltViewModel
class ClienteViewModel @Inject constructor(
    private val clienteRepository: ClienteRepository
) : ViewModel() {

    private val _listaClientes = MutableStateFlow<List<ClienteResp>>(emptyList())
    val listaClientes: StateFlow<List<ClienteResp>> = _listaClientes

    fun cargarClientes() {
        viewModelScope.launch {
            _listaClientes.value = clienteRepository.reportarClientes()
        }
    }

    suspend fun buscarPorId(id: Long): ClienteResp? {
        return try {
            clienteRepository.buscarClienteId(id)
        } catch (e: Exception) {
            null
        }
    }

    fun insertarCliente(cliente: ClienteDto) {
        viewModelScope.launch {
            clienteRepository.insertarCliente(cliente)
            cargarClientes()
        }
    }

    fun modificarCliente(cliente: ClienteDto) {
        viewModelScope.launch {
            clienteRepository.modificarCliente(cliente)
            cargarClientes()
        }
    }

    fun eliminarCliente(cliente: ClienteResp) {
        viewModelScope.launch {
            clienteRepository.deleteCliente(cliente.toDto())
            cargarClientes()
        }
    }
}