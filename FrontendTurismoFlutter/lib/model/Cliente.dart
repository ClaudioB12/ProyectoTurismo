class Cliente {
  final int? idCliente;
  final String nombreCompleto;
  final String correo; // NO editable
  final String telefono;
  final String direccion;
  final String rol; // Campo requerido
  final String? fotoPerfilUrl; // ✅ Aquí lo agregamos como opcional (nullable)


  Cliente({
    this.idCliente,
    required this.nombreCompleto,
    required this.correo,
    required this.telefono,
    required this.direccion,
    required this.rol,
    this.fotoPerfilUrl, // ✅ Asegúrate de agregarlo aquí

  });

  factory Cliente.fromJson(Map<String, dynamic> json) {
    return Cliente(
      idCliente: json['idCliente'],
      nombreCompleto: json['nombreCompleto'] ?? '',
      correo: json['correo'] ?? '',
      telefono: json['telefono'] ?? '',
      direccion: json['direccion'] ?? '',
      rol: json['rol'] ?? 'USUARIO', // Valor por defecto
      fotoPerfilUrl: json['fotoPerfilUrl'], // ✅ lo mapeamos directamente

    );
  }

  Cliente copyWith({
    int? idCliente,
    String? nombreCompleto,
    // ⚠️ correo no editable
    String? telefono,
    String? direccion,

    String? rol, // ✅ ahora es parámetro opcional aquí
  }) {
    return Cliente(
      idCliente: idCliente ?? this.idCliente,
      nombreCompleto: nombreCompleto ?? this.nombreCompleto,
      correo: this.correo,
      telefono: telefono ?? this.telefono,
      direccion: direccion ?? this.direccion,
      rol: rol ?? this.rol,
    );
  }
}
