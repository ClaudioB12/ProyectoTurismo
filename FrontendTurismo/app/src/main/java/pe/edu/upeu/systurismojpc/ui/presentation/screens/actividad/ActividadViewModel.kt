package pe.edu.upeu.systurismojpc.ui.presentation.screens.actividad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upeu.systurismojpc.modelo.ActividadDto
import pe.edu.upeu.systurismojpc.modelo.ActividadResp
import pe.edu.upeu.systurismojpc.modelo.DestinoResp
import pe.edu.upeu.systurismojpc.repository.ActividadRepository
import pe.edu.upeu.systurismojpc.repository.DestinoRepository
import javax.inject.Inject

@HiltViewModel
class ActividadViewModel @Inject constructor(
    private val actividadRepository: ActividadRepository,
    private val destinoRepository: DestinoRepository
) : ViewModel() {

    private val _lista = MutableStateFlow<List<ActividadResp>>(emptyList())
    val lista: StateFlow<List<ActividadResp>> = _lista

    private val _destinos = MutableStateFlow<List<DestinoResp>>(emptyList())
    val destinos: StateFlow<List<DestinoResp>> = _destinos

    fun getActividades() {
        viewModelScope.launch {
            _lista.value = actividadRepository.reportarActividades()
        }
    }

    fun obtenerDestinos() {
        viewModelScope.launch {
            _destinos.value = destinoRepository.reportarDestinos()
        }
    }

    fun eliminarActividad(actividad: ActividadDto, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val rpta = actividadRepository.deleteActividad(actividad)
            onResult(rpta)
            getActividades()
        }
    }

    fun guardarActividad(actividad: ActividadDto, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val rpta = actividadRepository.insertarActividad(actividad)
            println("Respuesta insertar: $rpta")
            onResult(rpta)
            if (rpta) getActividades()
        }
    }

    fun modificarActividad(actividad: ActividadDto, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val rpta = actividadRepository.modificarActividad(actividad)
            onResult(rpta)
            if (rpta) getActividades()
        }
    }

    suspend fun buscarActividad(id: Long): ActividadResp {
        return actividadRepository.buscarActividadId(id)
    }
}
