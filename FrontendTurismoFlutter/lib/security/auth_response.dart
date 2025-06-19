class AuthResponse {
  final bool success;
  final String rol;
  final int idUsuario;

  AuthResponse({
    required this.success,
    required this.rol,
    required this.idUsuario,
  });

  factory AuthResponse.fromJson(Map<String, dynamic> json) {
    return AuthResponse(
      success: true,
      rol: json['rol'] ?? 'USUARIO',
      idUsuario: json['idUsuario'],
    );
  }
}
