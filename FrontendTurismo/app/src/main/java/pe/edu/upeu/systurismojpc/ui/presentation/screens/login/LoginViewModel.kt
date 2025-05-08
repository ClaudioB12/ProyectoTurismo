package pe.edu.upeu.systurismojpc.ui.presentation.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okio.IOException
import pe.edu.upeu.systurismojpc.modelo.LoginRequest
import pe.edu.upeu.systurismojpc.modelo.LoginResponse
import pe.edu.upeu.systurismojpc.repository.UsuarioRepository
import pe.edu.upeu.systurismojpc.utils.TokenUtils
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepo: UsuarioRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isLogin = MutableLiveData(false)
    val isLogin: LiveData<Boolean> get() = _isLogin

    val isError = MutableLiveData(false)
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    val loginResponse = MutableLiveData<LoginResponse>()

    fun loginSys(correo: String, contraseña: String) {
        Log.i("LOGIN", correo)
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            try {
                _isLogin.postValue(false)

                val request = LoginRequest(correo = correo, contraseña = contraseña)
                val response = userRepo.loginUsuario(request)

                delay(1500L) // Solo para mostrar loading un poquito más

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status) {
                        // Login correcto
                        TokenUtils.USER_LOGIN = body.correo
                        TokenUtils.TOKEN_CONTENT ="Bearer ${body.token}"  // Puedes poner aquí el JWT si después implementas token real

                        loginResponse.postValue(body)
                        _isLogin.postValue(true)
                        Log.i("LOGIN_SUCCESS", "Bienvenido: ${body.correo}")
                        Log.i("LOGIN_SUCCESS", "Token : ${body.token}")
                    } else {
                        // Error de credenciales
                        isError.postValue(true)
                        _errorMessage.postValue("Credenciales incorrectas, intenta nuevamente")
                    }
                } else {
                    Log.e("LOGIN_RESPONSE", "Error en login. Código HTTP: ${response.code()}") // <<--- Aquí lo agregué
                    isError.postValue(true)
                    _errorMessage.postValue("Error del servidor al iniciar sesión")
                }

                _isLoading.postValue(false)

            } catch (e: SocketTimeoutException) {
                isError.postValue(true)
                _errorMessage.postValue("Tiempo de espera agotado. Verifica tu red.")
                _isLoading.postValue(false)
            } catch (e: IOException) {
                isError.postValue(true)
                _errorMessage.postValue("Error de red: ${e.localizedMessage}")
                _isLoading.postValue(false)
            } catch (e: Exception) {
                isError.postValue(true)
                _errorMessage.postValue("Ocurrió un error inesperado: ${e.localizedMessage}")
                _isLoading.postValue(false)
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.postValue(null)
        isError.postValue(false)
        _isLoading.postValue(false)
    }
}
