class JwtRequest {
  final int idUsuario;
  final String correo;
  final String clave;
  final String rol;

  JwtRequest({
    this.idUsuario = 0,
    required this.correo,
    required this.clave,
    this.rol = 'USUARIO',
  });

  Map<String, dynamic> toJson() => {
    'idUsuario': idUsuario,
    'correo': correo,
    'clave': clave,
    'rol': rol,
  };
}
