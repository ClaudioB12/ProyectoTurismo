package pe.edu.upeu.systurismojpc.ui.presentation.screens.actividad

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import pe.edu.upeu.systurismojpc.modelo.ActividadDto
import pe.edu.upeu.systurismojpc.modelo.DestinoDto
import pe.edu.upeu.systurismojpc.modelo.DestinoResp
import pe.edu.upeu.systurismojpc.modelo.DestinoSimpleDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActividadFormScreen(
    navController: NavController,
    actividadId: String?,
    actividadViewModel: ActividadViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var destinoSeleccionado by remember { mutableStateOf<DestinoResp?>(null) }
    var expanded by remember { mutableStateOf(false) }

    val destinos by actividadViewModel.destinos.collectAsState()

    LaunchedEffect(Unit) {
        actividadViewModel.obtenerDestinos()
    }

    LaunchedEffect(actividadId) {
        if (!actividadId.isNullOrBlank()) {
            val actividad = actividadViewModel.buscarActividad(actividadId.toLong())
            nombre = actividad.nombre
            descripcion = actividad.descripcion
            precio = actividad.precio.toString()
            destinoSeleccionado = actividad.destino
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (actividadId.isNullOrBlank()) "Registrar Actividad" else "Editar Actividad") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                textStyle = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                textStyle = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio") },
                textStyle = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = destinoSeleccionado?.nombre ?: "Seleccionar destino",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Destino") },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    destinos.forEach { destino ->
                        DropdownMenuItem(
                            text = { Text(destino.nombre) },
                            onClick = {
                                destinoSeleccionado = destino
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val dto = ActividadDto(
                        idActividad = actividadId?.toLongOrNull() ?: 0L,
                        nombre = nombre,
                        descripcion = descripcion,
                        precio = precio.toDoubleOrNull() ?: 0.0,
                        destino = DestinoSimpleDto(destinoSeleccionado?.idDestino ?: 0L)
                    )
                    scope.launch {
                        if (actividadId.isNullOrBlank()) {
                            actividadViewModel.guardarActividad(dto) { exito ->
                                if (exito) actividadViewModel.getActividades()
                                navController.popBackStack()
                            }
                        } else {
                            actividadViewModel.modificarActividad(dto) { exito ->
                                if (exito) actividadViewModel.getActividades()
                                navController.popBackStack()
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }
        }
    }
}
