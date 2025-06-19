import 'dart:convert';
import 'package:frontendturismoflutter/model/Reserva.dart';
import 'package:frontendturismoflutter/security/token_interceptor.dart';
import 'package:frontendturismoflutter/utils/DioClient.dart';
import 'package:frontendturismoflutter/utils/api/rest_client_reserva.dart';

class ReservaService {
  late RestClientReserva _client;

  ReservaService(String baseUrl) {
    final dio = TokenInterceptor().crearDioConInterceptor();
    final dioClient = DioClient(baseUrl, dio: dio);
    _client = RestClientReserva(dioClient.dio, baseUrl: baseUrl);
  }

  Future<List<Reserva>> listarReservas() => _client.listarReservas();

  Future<Reserva> buscarReserva(int id) => _client.buscarReserva(id);

  Future<Reserva> guardarReserva(Reserva r) {
    print('JSON enviado a guardarReserva: ${json.encode(r.toJson())}');
    return _client.guardarReserva(r.toJson());
  }

  Future<Reserva> editarReserva(Reserva r) {
    print('JSON enviado a editarReserva: ${json.encode(r.toJson())}');
    return _client.editarReserva(r.toJson());
  }

  Future<void> eliminarReserva(int id) => _client.eliminarReserva(id);
}
