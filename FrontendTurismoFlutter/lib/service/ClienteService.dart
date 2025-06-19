import 'package:dio/dio.dart';
import 'package:frontendturismoflutter/model/Cliente.dart';
import 'package:frontendturismoflutter/security/token_interceptor.dart';

class ClienteService {
  final Dio _dio;

  ClienteService(String baseUrl)
      : _dio = TokenInterceptor().crearDioConInterceptor()..options.baseUrl = baseUrl;

  Future<Cliente> actualizarCliente(Cliente cliente) async {
    try {
      final response = await _dio.put(
        '/api/cliente/actualizar',
        data: {
          'idCliente': cliente.idCliente,
          'nombreCompleto': cliente.nombreCompleto,
          'correo': cliente.correo,
          'telefono': cliente.telefono,
          'direccion': cliente.direccion,
        },
      );

      if (response.statusCode == 200) {
        return Cliente.fromJson(response.data);
      } else {
        throw Exception('Error al actualizar: ${response.statusCode}');
      }
    } on DioException catch (e) {
      throw Exception('Error al actualizar cliente: ${e.response?.statusCode} → ${e.message}');
    }
  }

  Future<List<Cliente>> listarClientes() async {
    try {
      final response = await _dio.get('/api/cliente/listar');

      if (response.statusCode == 200) {
        final List<dynamic> data = response.data;
        return data.map((json) => Cliente.fromJson(json)).toList();
      } else {
        throw Exception('Error al listar: ${response.statusCode}');
      }
    } on DioException catch (e) {
      throw Exception('Error al listar clientes: ${e.response?.statusCode} → ${e.message}');
    }
  }
}
