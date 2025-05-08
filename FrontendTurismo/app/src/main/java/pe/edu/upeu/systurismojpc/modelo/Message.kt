package pe.edu.upeu.systurismojpc.modelo

data class Message(
    val idCliente: Long,
    val nombreCompleto: String,
    val correo: String,
    val telefono: String,
    val direccion: String
)