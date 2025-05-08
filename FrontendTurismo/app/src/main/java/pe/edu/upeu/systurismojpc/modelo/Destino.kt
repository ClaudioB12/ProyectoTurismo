package pe.edu.upeu.systurismojpc.modelo

data class DestinoDto(
    var idDestino: Long,
    var nombre: String,
    var descripcion: String,
    var ubicacion: String
)

data class DestinoSimpleDto(
    var idDestino: Long
)

data class DestinoResp(
    val idDestino: Long,
    val nombre: String,
    val descripcion: String,
    val ubicacion: String
)

fun DestinoResp.toDto(): DestinoDto {
    return DestinoDto(
        idDestino = this.idDestino,
        nombre = this.nombre,
        descripcion = this.descripcion,
        ubicacion = this.ubicacion
    )
}
