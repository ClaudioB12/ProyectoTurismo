package pe.edu.upeu.systurismojpc.ui.presentation.screens.login

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.k0shk0sh.compose.easyforms.BuildEasyForms
import com.github.k0shk0sh.compose.easyforms.EasyFormsResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pe.edu.upeu.systurismojpc.R
import pe.edu.upeu.systurismojpc.ui.presentation.components.ErrorImageAuth
import pe.edu.upeu.systurismojpc.ui.presentation.components.ProgressBarLoading
import pe.edu.upeu.systurismojpc.ui.presentation.components.form.EmailTextField
import pe.edu.upeu.systurismojpc.ui.presentation.components.form.PasswordTextField
import pe.edu.upeu.systurismojpc.ui.theme.SysTurismoJPCTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(
    navigateToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.observeAsState(false)
    val isLogin by viewModel.isLogin.observeAsState(false)
    val isError by viewModel.isError.observeAsState(false)
    val loginResult by viewModel.loginResponse.observeAsState()
    val errorMessage by viewModel.errorMessage.observeAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Fondo
        Image(
            painter = painterResource(id = R.drawable.bg), // Usa tu propia imagen
            contentDescription = "Login Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Capa transparente
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )

        // Formulario
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Bienvenido", fontSize = 32.sp, color = MaterialTheme.colorScheme.primary)

                    Spacer(modifier = Modifier.height(16.dp))

                    BuildEasyForms { easyForm ->
                        EmailTextField(easyForms = easyForm, text = "", "Correo electrónico", "U")
                        PasswordTextField(easyForms = easyForm, text = "", label = "Contraseña")

                        Spacer(modifier = Modifier.height(16.dp))

                        // Botón con tamaño de texto ajustado
                        Button(
                            onClick = {
                                val dataForm = easyForm.formData()
                                val correo = (dataForm.get(0) as EasyFormsResult.StringResult).value
                                val contraseña = (dataForm.get(1) as EasyFormsResult.StringResult).value

                                viewModel.loginSys(correo, contraseña)

                                scope.launch {
                                    delay(2000L)
                                    if (viewModel.isLogin.value == true && loginResult != null) {
                                        navigateToHome.invoke()
                                    } else {
                                        Toast.makeText(context, "Credenciales incorrectas", Toast.LENGTH_LONG).show()
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text(
                                text = "Iniciar Sesión",
                                color = Color.White,
                                fontSize = 14.sp // Ajustar el tamaño de la fuente aquí
                            )
                        }
                    }

                    ErrorImageAuth(isImageValidate = isError)
                    ProgressBarLoading(isLoading = isLoading)
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearErrorMessage()
        }
    }
}
