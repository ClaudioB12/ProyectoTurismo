import 'dart:convert';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:http/http.dart' as http;
import 'package:jwt_decode/jwt_decode.dart';
import 'package:frontendturismoflutter/security/JwtRequest.dart';
import 'package:frontendturismoflutter/utils/constants.dart';

class AuthResponse {
  final bool success;
  final bool isNewUser;

  AuthResponse({required this.success, this.isNewUser = false});
}

class AuthService {
  final _storage = const FlutterSecureStorage();

  // Método login
  Future<AuthResponse> login(JwtRequest request) async {
    final response = await http.post(
      Uri.parse(AppConstants.loginEndpoint),
      headers: AppConstants.jsonHeaders,
      body: jsonEncode(request.toJson()),
    );

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      if (data['status'] == true && data.containsKey('token')) {
        await _storage.write(key: 'jwt', value: data['token']);

        final bool clienteExiste = data['clienteExiste'] ?? false;
        final bool datosClienteCompletos = data['datosClienteCompletos'] ?? false;

        final bool isNewUser = !clienteExiste || !datosClienteCompletos;

        return AuthResponse(success: true, isNewUser: isNewUser);
      }
    }

    return AuthResponse(success: false);
  }

  // Método registro
  Future<bool> register(JwtRequest request) async {
    final url = Uri.parse(AppConstants.usuarioGuardarEndpoint);

    final response = await http.post(
      url,
      headers: AppConstants.jsonHeaders,
      body: jsonEncode(request.toJson()),
    );

    print('STATUS: ${response.statusCode}');
    print('BODY: ${response.body}');

    return response.statusCode == 200 || response.statusCode == 201;
  }

  // Obtener token almacenado
  Future<String?> getToken() async {
    return await _storage.read(key: 'jwt');
  }

  // Cerrar sesión
  Future<void> logout() async {
    await _storage.delete(key: 'jwt');
  }

  // Extraer correo desde el token JWT
  Future<String?> obtenerCorreoDelToken() async {
    final token = await getToken();
    if (token == null) return null;

    Map<String, dynamic> payload = Jwt.parseJwt(token);

    return payload['correo'] ?? payload['email'] ?? payload['sub'];
  }


  // Guardar o actualizar datos del cliente incluyendo el correo extraído del token
  Future<bool> guardarDatosCliente({
    required String nombreCompleto,
    required String telefono,
    required String direccion,
  }) async {
    final token = await getToken();
    final correo = await obtenerCorreoDelToken();

    if (token == null || correo == null) {
      print('Error: Token o correo no disponibles');
      return false;
    }

    final headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token',
    };

    final body = json.encode({
      "nombreCompleto": nombreCompleto,
      "correo": correo,  // ¡IMPORTANTE! incluir el correo para que backend pueda filtrar
      "telefono": telefono,
      "direccion": direccion,
    });

    print('Headers: $headers');
    print('Body JSON: $body');

    // Intentar actualizar con PUT
    final putResponse = await http.put(
      Uri.parse(AppConstants.clienteActualizarEndpoint),
      headers: headers,
      body: body,
    );

    print("PUT cliente - STATUS: ${putResponse.statusCode}");
    print("PUT cliente - BODY: ${putResponse.body}");

    if (putResponse.statusCode == 200) {
      return true;
    }

    // Si no existe, intentar guardarlo con POST
    final postResponse = await http.post(
      Uri.parse(AppConstants.clienteGuardarEndpoint),
      headers: headers,
      body: body,
    );

    print("POST cliente - STATUS: ${postResponse.statusCode}");
    print("POST cliente - BODY: ${postResponse.body}");

    return postResponse.statusCode == 200 || postResponse.statusCode == 201;
  }
}
