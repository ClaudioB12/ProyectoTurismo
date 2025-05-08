package pe.edu.upeu.systurismojpc.modelo

data class LoginRequest(
    val correo: String,
    val contraseña: String
)