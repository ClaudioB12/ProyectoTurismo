class AppConstants {
  static const String baseUrl = "http://192.168.100.39:8090";

  // Endpoints
  static const String loginEndpoint = "$baseUrl/api/auth/login";
  static const String usuarioGuardarEndpoint = "$baseUrl/api/auth/registrar";
  // Cambié el endpoint para que coincida con el backend que sugerí
  static const String clienteActualizarEndpoint  = "$baseUrl/api/cliente/actualizar";
  static const String clienteGuardarEndpoint  = "$baseUrl/api/cliente/guardar";


  // Headers comunes
  static const Map<String, String> jsonHeaders = {
    'Content-Type': 'application/json',
  };
}
