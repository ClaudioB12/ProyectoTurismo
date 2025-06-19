import 'dart:convert';
import 'package:frontendturismoflutter/model/Hospedaje.dart';
import 'package:frontendturismoflutter/security/token_interceptor.dart';
import 'package:frontendturismoflutter/utils/DioClient.dart';
import 'package:frontendturismoflutter/utils/rest_client.dart';

class HospedajeService {
  late RestClientHospedaje _client;

  HospedajeService(String baseUrl) {
    final dio = TokenInterceptor().crearDioConInterceptor();
    final dioClient = DioClient(baseUrl, dio: dio);
    _client = RestClientHospedaje(dioClient.dio, baseUrl: baseUrl);
  }

  Future<List<Hospedaje>> listarHospedajes() => _client.listarHospedajes();

  Future<List<Hospedaje>> listarHospedajesPorDestino(int idDestino) =>
      _client.listarHospedajesPorDestino(idDestino);

  Future<Hospedaje> guardarHospedaje(Hospedaje h) {
    print('JSON enviado a guardarHospedaje: ${json.encode(h.toJson())}');
    return _client.guardarHospedaje(h.toJson());
  }

  Future<Hospedaje> editarHospedaje(Hospedaje h) {
    print('JSON enviado a editarHospedaje: ${json.encode(h.toJson())}');
    return _client.editarHospedaje(h.toJson());
  }

  Future<Hospedaje> buscarHospedajePorId(int id) => _client.buscarHospedajePorId(id);

  Future<void> eliminarHospedaje(int id) => _client.eliminarHospedaje(id);
}
