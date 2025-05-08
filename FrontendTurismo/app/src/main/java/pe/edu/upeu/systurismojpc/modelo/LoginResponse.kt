package pe.edu.upeu.systurismojpc.modelo

data class LoginResponse(
    val message: String,
    val correo: String,
    val status: Boolean,
    val token: String
)