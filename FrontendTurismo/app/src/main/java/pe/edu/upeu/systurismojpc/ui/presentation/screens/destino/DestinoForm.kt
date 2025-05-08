package pe.edu.upeu.systurismojpc.ui.presentation.screens.destino

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import pe.edu.upeu.systurismojpc.modelo.DestinoDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestinoFormScreen(
    navController: NavController,
    destinoId: String?,
    destinoViewModel: DestinoViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }

    LaunchedEffect(destinoId) {
        if (!destinoId.isNullOrBlank()) {
            val destino = destinoViewModel.buscarDestino(destinoId.toLong())
            nombre = destino.nombre
            descripcion = destino.descripcion
            ubicacion = destino.ubicacion
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (destinoId.isNullOrBlank()) "Registrar Destino" else "Editar Destino") }
            )
        }
    ) { paddingValues ->
        BoxWithConstraints(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val isCompact = this@BoxWithConstraints.maxWidth < 600.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isCompact) {
                    Column {
                        DestinoFields(
                            nombre, { nombre = it },
                            descripcion, { descripcion = it },
                            ubicacion, { ubicacion = it }
                        )
                    }
                } else {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            DestinoFields(
                                nombre, { nombre = it },
                                descripcion, { descripcion = it },
                                "", {}
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            DestinoFields(
                                "", {},
                                "", {},
                                ubicacion, { ubicacion = it }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val dto = DestinoDto(
                            idDestino = destinoId?.toLongOrNull() ?: 0L,
                            nombre = nombre,
                            descripcion = descripcion,
                            ubicacion = ubicacion
                        )
                        scope.launch {
                            if (destinoId.isNullOrBlank()) {
                                destinoViewModel.guardarDestino(dto) {}
                            } else {
                                destinoViewModel.modificarDestino(dto) {}
                            }
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (destinoId.isNullOrBlank()) "Guardar" else "Actualizar")
                }
            }
        }
    }
}

@Composable
fun DestinoFields(
    nombre: String, onNombreChange: (String) -> Unit,
    descripcion: String, onDescripcionChange: (String) -> Unit,
    ubicacion: String, onUbicacionChange: (String) -> Unit
) {
    if (onNombreChange !== {}) {
        OutlinedTextField(
            value = nombre,
            onValueChange = onNombreChange,
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
    }
    if (onDescripcionChange !== {}) {
        OutlinedTextField(
            value = descripcion,
            onValueChange = onDescripcionChange,
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
    }
    if (onUbicacionChange !== {}) {
        OutlinedTextField(
            value = ubicacion,
            onValueChange = onUbicacionChange,
            label = { Text("Ubicación") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
    }
}
