package pe.edu.upeu.systurismojpc.ui.presentation.screens.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pe.edu.upeu.systurismojpc.modelo.ClienteDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteForm(
    text: String,
    darkMode: Boolean,
    navController: NavController,
    viewModel: ClienteViewModel = hiltViewModel()
) {
    val id = text.toLongOrNull() ?: 0L

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }

    LaunchedEffect(id) {
        if (id != 0L) {
            val cliente = viewModel.buscarPorId(id)
            cliente?.let {
                nombre = it.nombreCompleto
                correo = it.correo
                telefono = it.telefono
                direccion = it.direccion
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (id == 0L) "Registrar Cliente" else "Editar Cliente") })
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
                        ClienteFields(
                            nombre, { nombre = it },
                            correo, { correo = it },
                            telefono, { telefono = it },
                            direccion, { direccion = it }
                        )
                    }
                } else {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            ClienteFields(
                                nombre, { nombre = it },
                                correo, { correo = it },
                                "", {},
                                "", {}
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            ClienteFields(
                                "", {},
                                "", {},
                                telefono, { telefono = it },
                                direccion, { direccion = it }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val cliente = ClienteDto(
                            idCliente = id,
                            nombreCompleto = nombre,
                            correo = correo,
                            telefono = telefono,
                            direccion = direccion
                        )

                        if (id == 0L) {
                            viewModel.insertarCliente(cliente)
                        } else {
                            viewModel.modificarCliente(cliente)
                        }

                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (id == 0L) "Guardar" else "Actualizar")
                }
            }
        }
    }
}

@Composable
fun ClienteFields(
    nombre: String, onNombreChange: (String) -> Unit,
    correo: String, onCorreoChange: (String) -> Unit,
    telefono: String, onTelefonoChange: (String) -> Unit,
    direccion: String, onDireccionChange: (String) -> Unit
) {
    if (onNombreChange !== {}) {
        OutlinedTextField(
            value = nombre,
            onValueChange = onNombreChange,
            label = { Text("Nombre Completo") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
    }
    if (onCorreoChange !== {}) {
        OutlinedTextField(
            value = correo,
            onValueChange = onCorreoChange,
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
    }
    if (onTelefonoChange !== {}) {
        OutlinedTextField(
            value = telefono,
            onValueChange = onTelefonoChange,
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
    }
    if (onDireccionChange !== {}) {
        OutlinedTextField(
            value = direccion,
            onValueChange = onDireccionChange,
            label = { Text("Dirección") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
    }
}
