package pe.edu.upeu.systurismojpc.ui.presentation.screens.destino

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pe.edu.upeu.systurismojpc.modelo.toDto
import pe.edu.upeu.systurismojpc.ui.navegation.Destinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestinoMainScreen(
    navController: NavController,
    destinoViewModel: DestinoViewModel = hiltViewModel()
) {
    val lista by destinoViewModel.lista.collectAsState()

    LaunchedEffect(true) {
        destinoViewModel.getDestinos()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("destinomain") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Destinations.DestinoFormSC.passId(null))
            }) {
                Text("+")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier
            .padding(padding)
            .padding(16.dp)
        ) {
            items(lista) { destino ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = destino.nombre, style = MaterialTheme.typography.titleMedium)
                        Text(text = destino.descripcion, style = MaterialTheme.typography.bodyMedium)
                        Text(text = destino.ubicacion, style = MaterialTheme.typography.bodySmall)
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IconButton(onClick = {
                                navController.navigate(Destinations.DestinoFormSC.passId(destino.idDestino.toString()))
                            }) {
                                Icon(Icons.Default.Edit, contentDescription = "Editar")
                            }
                            IconButton(onClick = {
                                destinoViewModel.eliminarDestino(destino.toDto()) {}
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}
