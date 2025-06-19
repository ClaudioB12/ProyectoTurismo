class AppConstants {
  static const String baseUrl = "http://10.80.97.117:8090";



  // Endpoints de autenticación y cliente
  static const String loginEndpoint = "$baseUrl/api/auth/login";
  static const String usuarioGuardarEndpoint = "$baseUrl/api/auth/registrar";
  static const String clienteActualizarEndpoint = "$baseUrl/api/cliente/actualizar";
  static const String clienteGuardarEndpoint = "$baseUrl/api/cliente/guardar";
  static const String clientePerfilEndpoint = "$baseUrl/api/cliente/perfil";

  // Endpoints de CRUD para Destino

  // Headers comunes
  static const Map<String, String> jsonHeaders = {
    'Content-Type': 'application/json',
  };
}
