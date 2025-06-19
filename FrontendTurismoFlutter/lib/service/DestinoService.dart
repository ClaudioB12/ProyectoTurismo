import 'package:frontendturismoflutter/model/Destino.dart';
import 'package:frontendturismoflutter/security/token_interceptor.dart';
import 'package:frontendturismoflutter/utils/DioClient.dart';
import 'package:frontendturismoflutter/utils/rest_client.dart';

class DestinoService {
  late RestClientDestino _client;

  DestinoService(String baseUrl) {
    final dio = TokenInterceptor().crearDioConInterceptor();
    final dioClient = DioClient(baseUrl, dio: dio); // ← ahora esto es válido
    _client = RestClientDestino(dioClient.dio, baseUrl: baseUrl);
  }

  Future<List<Destino>> listarDestinos() => _client.listarDestinos();
  Future<Destino> guardarDestino(Destino d) => _client.guardarDestino(d);
  Future<Destino> editarDestino(Destino d) => _client.editarDestino(d);
  Future<Destino> buscarDestinoPorId(int id) => _client.buscarDestinoPorId(id);
  Future<void> eliminarDestino(int id) => _client.eliminarDestino(id);
}
