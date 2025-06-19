import 'dart:convert';
import 'package:frontendturismoflutter/model/PaqueteTuristico.dart';
import 'package:frontendturismoflutter/security/token_interceptor.dart';
import 'package:frontendturismoflutter/utils/DioClient.dart';
import 'package:frontendturismoflutter/utils/api/rest_client_paqueteturistico.dart';
import 'package:frontendturismoflutter/utils/rest_client.dart';

class PaqueteTuristicoService {
  late RestClientPaqueteTuristico _client;

  PaqueteTuristicoService(String baseUrl) {
    final dio = TokenInterceptor().crearDioConInterceptor();
    final dioClient = DioClient(baseUrl, dio: dio);
    _client = RestClientPaqueteTuristico(dioClient.dio, baseUrl: baseUrl);
  }

  Future<List<PaqueteTuristico>> listarPaquetes() => _client.listarPaquetes();

  Future<PaqueteTuristico> buscarPaquetePorId(int id) => _client.buscarPaquetePorId(id);

  Future<PaqueteTuristico> guardarPaquete(PaqueteTuristico p) {
    print('JSON enviado a guardarPaquete: ${json.encode(p.toJson())}');
    return _client.guardarPaquete(p.toJson());
  }

  Future<PaqueteTuristico> editarPaquete(PaqueteTuristico p) {
    print('JSON enviado a editarPaquete: ${json.encode(p.toJson())}');
    return _client.editarPaquete(p.toJson());
  }

  Future<void> eliminarPaquete(int id) => _client.eliminarPaquete(id);
}
