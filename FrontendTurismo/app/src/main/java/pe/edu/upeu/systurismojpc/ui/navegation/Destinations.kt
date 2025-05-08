package pe.edu.upeu.systurismojpc.ui.navegation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destinations(
    val route: String,
    val title: String,
    val icon: ImageVector) {
    object Login:Destinations("login", "Login",
        Icons.Filled.Settings)
    object Pantalla1 : Destinations( "pantalla1", "Pantalla1", Icons.Filled.Home )
    object Pantalla2 :
        Destinations("pantalla2/?newText={newText}",
            "Pantalla 2",
            Icons.Filled.Settings) {
        fun createRoute(newText: String) =
            "pantalla2/?newText=$newText"
    }
    object Pantalla3 : Destinations("pantalla3", "Pantalla3", Icons.Filled.Favorite)
    object Pantalla4 : Destinations("pantalla4", "Pantalla4x", Icons.Filled.Face )object Pantalla5 : Destinations("pantalla5", "Pantalla 5x", Icons.Filled.AccountCircle )

    object ClienteMainSC: Destinations("clientemain", "Adm. Clientes", Icons.Filled.Person)

    object ClienteFormSC: Destinations("clienteForm?cliId={cliId}", "Form Cliente", Icons.Filled.Add) {
        fun passId(cliId: String?): String {
            return "clienteForm?cliId=$cliId"
        }
    }

    object DestinoMainSC: Destinations("destinoMain", "Adm. Destinos", Icons.Filled.Place)

    object DestinoFormSC: Destinations("destinoForm?destinoId={destinoId}", "Form Destino", Icons.Filled.Add) {
        fun passId(destinoId: String?): String {
            return "destinoForm?destinoId=${destinoId ?: ""}"
        }
    }

    object ActividadMainSC: Destinations("actividadMain", "Adm. Actividades", Icons.Filled.List)

    object ActividadFormSC: Destinations("actividadForm?actividadId={actividadId}", "Form Actividad", Icons.Filled.Add) {
        fun passId(actividadId: String?): String {
            return "actividadForm?actividadId=${actividadId ?: ""}"
        }
    }
}