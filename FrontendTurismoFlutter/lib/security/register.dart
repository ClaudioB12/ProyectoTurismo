import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:frontendturismoflutter/security/JwtRequest.dart';
import 'package:frontendturismoflutter/utils/constants.dart'; // Asegúrate que esta ruta sea correcta

Future<bool> register(JwtRequest request) async {
  final url = Uri.parse(AppConstants.usuarioGuardarEndpoint);

  final response = await http.post(
    url,
    headers: AppConstants.jsonHeaders,
    body: jsonEncode(request.toJson()),
  );

  return response.statusCode == 200 || response.statusCode == 201;
}
