import 'dart:convert'; // 👈 Asegúrate de importar esto para usar json.encode
import 'package:frontendturismoflutter/model/Actividad.dart';
import 'package:frontendturismoflutter/security/token_interceptor.dart';
import 'package:frontendturismoflutter/utils/DioClient.dart';
import 'package:frontendturismoflutter/utils/rest_client.dart';

class ActividadService {
  late RestClientActividad _client;

  ActividadService(String baseUrl) {
    final dio = TokenInterceptor().crearDioConInterceptor();
    final dioClient = DioClient(baseUrl, dio: dio);
    _client = RestClientActividad(dioClient.dio, baseUrl: baseUrl);
  }

  Future<List<Actividad>> listarActividades() => _client.listarActividades();

  Future<List<Actividad>> listarActividadesPorDestino(int idDestino) =>
      _client.listarActividadesPorDestino(idDestino);

  Future<Actividad> guardarActividad(Actividad a) {
    // 👇 IMPRIMIR JSON ANTES DE ENVIARLO
    print('JSON enviado a guardarActividad: ${json.encode(a.toJson())}');
    return _client.guardarActividad(a.toJson());
  }

  Future<Actividad> editarActividad(Actividad a) {
    // 👇 IMPRIMIR JSON ANTES DE ENVIARLO
    print('JSON enviado a editarActividad: ${json.encode(a.toJson())}');
    return _client.editarActividad(a.toJson());
  }

  Future<Actividad> buscarActividadPorId(int id) => _client.buscarActividadPorId(id);

  Future<void> eliminarActividad(int id) => _client.eliminarActividad(id);
}
