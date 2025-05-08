package pe.edu.upeu.systurismojpc.ui.presentation.screens.actividad

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pe.edu.upeu.systurismojpc.modelo.toDto
import pe.edu.upeu.systurismojpc.ui.navegation.Destinations

@Composable
fun ActividadMainScreen(
    navController: NavController,
    actividadViewModel: ActividadViewModel = hiltViewModel()
) {
    val lista by actividadViewModel.lista.collectAsState()

    LaunchedEffect(true) {
        actividadViewModel.getActividades()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Destinations.ActividadFormSC.passId(null))
            }) {
                Text("+")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            items(lista.size) { index ->
                val actividad = lista[index]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 50.dp),
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = actividad.nombre, style = MaterialTheme.typography.titleMedium)
                        Text(text = actividad.descripcion, style = MaterialTheme.typography.bodySmall)
                        Text(text = "Precio: S/ ${actividad.precio}", style = MaterialTheme.typography.bodySmall)
                        Text(text = "Destino: ${actividad.destino.nombre}", style = MaterialTheme.typography.bodySmall)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = {
                                navController.navigate(Destinations.ActividadFormSC.passId(actividad.idActividad.toString()))
                            }) {
                                Text("Editar")
                            }
                            TextButton(onClick = {
                                actividadViewModel.eliminarActividad(actividad.toDto()) {}
                            }) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}
