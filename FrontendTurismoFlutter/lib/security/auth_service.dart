import 'dart:convert';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:frontendturismoflutter/model/Cliente.dart';
import 'package:http/http.dart' as http;
import 'package:jwt_decode/jwt_decode.dart';
import 'package:frontendturismoflutter/security/JwtRequest.dart';
import 'package:frontendturismoflutter/utils/constants.dart';

class AuthResponse {
  final bool success;
  final bool isNewUser;
  final String rol;
  final int idUsuario;
  final String token; // <-- agrega esto



  AuthResponse({
    required this.success,
    this.isNewUser = false,
    required this.rol,
    required this.idUsuario,
    required this.token,
  });

  factory AuthResponse.fromJson(Map<String, dynamic> json) {
    return AuthResponse(
      success: true,
      isNewUser: !(json['clienteExiste'] ?? false) || !(json['datosClienteCompletos'] ?? false),
      rol: json['rol'] ?? 'USUARIO',
      idUsuario: json['idUsuario'] ?? 0,
      token: json['token'] ?? '', // <-- obtén el token del JSON
    );
  }
}

class AuthService {
  final _storage = const FlutterSecureStorage();

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

        return AuthResponse(
          success: true,
          isNewUser: !(data['clienteExiste'] ?? false) || !(data['datosClienteCompletos'] ?? false),
          rol: data['rol'] ?? 'USUARIO',
          idUsuario: data['idUsuario'] ?? 0,
          token: data['token'] ?? '', // <-- obtén el token del JSON

        );
      }
    }

    return AuthResponse(success: false, rol: 'USUARIO', idUsuario: 0, token: '');
  }

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

  Future<String?> getToken() async {
    return await _storage.read(key: 'jwt');
  }

  Future<void> logout() async {
    await _storage.delete(key: 'jwt');
  }

  Future<String?> obtenerCorreoDelToken() async {
    final token = await getToken();
    if (token == null) return null;

    Map<String, dynamic> payload = Jwt.parseJwt(token);
    return payload['correo'] ?? payload['email'] ?? payload['sub'];
  }

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
      "correo": correo,
      "telefono": telefono,
      "direccion": direccion,
    });

    print('Headers: $headers');
    print('Body JSON: $body');

    final putResponse = await http.put(
      Uri.parse(AppConstants.clienteActualizarEndpoint),
      headers: headers,
      body: body,
    );

    print("PUT cliente - STATUS: ${putResponse.statusCode}");
    print("PUT cliente - BODY: ${putResponse.body}");

    if (putResponse.statusCode == 200) return true;

    final postResponse = await http.post(
      Uri.parse(AppConstants.clienteGuardarEndpoint),
      headers: headers,
      body: body,
    );

    print("POST cliente - STATUS: ${postResponse.statusCode}");
    print("POST cliente - BODY: ${postResponse.body}");

    return postResponse.statusCode == 200 || postResponse.statusCode == 201;
  }

  Future<Cliente?> obtenerPerfilCliente() async {
    final token = await getToken();
    if (token == null) return null;

    final response = await http.get(
      Uri.parse(AppConstants.clientePerfilEndpoint),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer $token',
      },
    );

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      return Cliente.fromJson(data);
    } else {
      print('Error al obtener perfil cliente: ${response.statusCode}');
      return null;
    }
  }

  Future<String?> obtenerRolDesdeToken() async {
    final token = await getToken();
    if (token == null) return null;

    Map<String, dynamic> payload = Jwt.parseJwt(token);
    return payload['rol']; // Asegúrate de que el backend lo incluye
  }
}
