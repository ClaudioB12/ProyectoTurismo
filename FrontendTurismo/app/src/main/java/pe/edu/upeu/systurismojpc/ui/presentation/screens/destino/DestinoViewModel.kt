package pe.edu.upeu.systurismojpc.ui.presentation.screens.destino

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upeu.systurismojpc.modelo.DestinoDto
import pe.edu.upeu.systurismojpc.modelo.DestinoResp
import pe.edu.upeu.systurismojpc.repository.DestinoRepository
import javax.inject.Inject

@HiltViewModel
class DestinoViewModel @Inject constructor(
    private val destinoRepository: DestinoRepository
) : ViewModel() {

    private val _lista = MutableStateFlow<List<DestinoResp>>(emptyList())
    val lista: StateFlow<List<DestinoResp>> = _lista

    fun getDestinos() {
        viewModelScope.launch {
            _lista.value = destinoRepository.reportarDestinos()
        }
    }

    fun eliminarDestino(destino: DestinoDto, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val rpta = destinoRepository.deleteDestino(destino)
            onResult(rpta)
            getDestinos()
        }
    }

    fun guardarDestino(destino: DestinoDto, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            onResult(destinoRepository.insertarDestino(destino))
        }
    }

    fun modificarDestino(destino: DestinoDto, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            onResult(destinoRepository.modificarDestino(destino))
        }
    }

    suspend fun buscarDestino(id: Long): DestinoResp {
        return destinoRepository.buscarDestinoId(id)
    }
}
