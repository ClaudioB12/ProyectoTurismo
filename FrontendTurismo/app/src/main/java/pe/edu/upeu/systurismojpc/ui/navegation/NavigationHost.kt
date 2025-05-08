package pe.edu.upeu.systurismojpc.ui.navegation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import pe.edu.upeu.systurismojpc.ui.presentation.screens.Pantalla1
import pe.edu.upeu.systurismojpc.ui.presentation.screens.Pantalla2
import pe.edu.upeu.systurismojpc.ui.presentation.screens.Pantalla3
import pe.edu.upeu.systurismojpc.ui.presentation.screens.Pantalla4
import pe.edu.upeu.systurismojpc.ui.presentation.screens.Pantalla5
import pe.edu.upeu.systurismojpc.ui.presentation.screens.actividad.ActividadFormScreen
import pe.edu.upeu.systurismojpc.ui.presentation.screens.actividad.ActividadMainScreen
import pe.edu.upeu.systurismojpc.ui.presentation.screens.cliente.ClienteForm
import pe.edu.upeu.systurismojpc.ui.presentation.screens.cliente.ClienteMain
import pe.edu.upeu.systurismojpc.ui.presentation.screens.destino.DestinoFormScreen
import pe.edu.upeu.systurismojpc.ui.presentation.screens.destino.DestinoMainScreen
import pe.edu.upeu.systurismojpc.ui.presentation.screens.login.LoginScreen

@Composable
fun NavigationHost(
    navController: NavHostController,
    darkMode: MutableState<Boolean>,
    modif: PaddingValues
) {
    NavHost(
        navController = navController, startDestination =
            Destinations.Login.route
    ) {
        composable(Destinations.Login.route){
            LoginScreen(navigateToHome = {
                navController.navigate(Destinations.Pantalla1.route)})
        }
        composable(Destinations.Pantalla1.route) {
            Pantalla1(navegarPantalla2 = { newText ->navController.navigate(Destinations.Pantalla2.createRoute(newText)) }
            )
        }
        composable( Destinations.Pantalla2.route, arguments =
            listOf(navArgument("newText") { defaultValue = "Pantalla 2"
            })
        ) { navBackStackEntry ->
            var newText
                    =navBackStackEntry.arguments?.getString("newText")
            requireNotNull(newText)
            Pantalla2(newText, darkMode)
        }
        composable(Destinations.Pantalla3.route) {
            Pantalla3()
        }
        composable(Destinations.Pantalla4.route) {
            Pantalla4()
        }
        composable(Destinations.Pantalla5.route) {
            Pantalla5()
        }
        composable(Destinations.ClienteMainSC.route) {
            ClienteMain(navegarEditarAct = { id ->
                navController.navigate(Destinations.ClienteFormSC.passId(id))
            }, navController = navController)
        }

        composable(Destinations.ClienteFormSC.route, arguments = listOf(navArgument("cliId") { defaultValue = "0" })) { navBackStackEntry ->
            val cliId = navBackStackEntry.arguments?.getString("cliId") ?: "0"
            ClienteForm(text = cliId, darkMode = darkMode.value, navController = navController)
        }

        composable(Destinations.DestinoMainSC.route) {DestinoMainScreen(navController = navController)}

        composable(Destinations.DestinoFormSC.route, arguments = listOf(navArgument("destinoId") { defaultValue = "" })
        ) { navBackStackEntry ->
            val destinoId = navBackStackEntry.arguments?.getString("destinoId")
            DestinoFormScreen(navController = navController, destinoId = destinoId)
        }

        composable(Destinations.ActividadMainSC.route) { ActividadMainScreen(navController = navController) }

        composable(Destinations.ActividadFormSC.route, arguments = listOf(navArgument("actividadId") { defaultValue = "" })
        ) { navBackStackEntry ->
            val actividadId = navBackStackEntry.arguments?.getString("actividadId")
            ActividadFormScreen(navController = navController, actividadId = actividadId)
        }


    }
}