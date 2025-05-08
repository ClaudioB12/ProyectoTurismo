package pe.edu.upeu.systurismojpc.modelo

data class ClienteDto(
    var idCliente: Long,
    var nombreCompleto: String,
    var correo: String,
    var telefono: String,
    var direccion: String
)

data class ClienteResp(
    val idCliente: Long,
    val nombreCompleto: String,
    val correo: String,
    val telefono: String,
    val direccion: String
)

fun ClienteResp.toDto(): ClienteDto {
    return ClienteDto(
        idCliente = this.idCliente,
        nombreCompleto = this.nombreCompleto,
        correo = this.correo,
        telefono = this.telefono,
        direccion = this.direccion
    )
}
