import 'package:dio/dio.dart';
import 'package:retrofit/retrofit.dart';
import 'package:frontendturismoflutter/model/PaqueteTuristico.dart';

part 'rest_client_paqueteturistico.g.dart';

@RestApi()
abstract class RestClientPaqueteTuristico {
  factory RestClientPaqueteTuristico(Dio dio, {String baseUrl}) = _RestClientPaqueteTuristico;

  @GET('/api/paquete/listar')
  Future<List<PaqueteTuristico>> listarPaquetes();

  @GET('/api/paquete/buscar/{id}')
  Future<PaqueteTuristico> buscarPaquetePorId(@Path('id') int id);

  @POST('/api/paquete/guardar')
  Future<PaqueteTuristico> guardarPaquete(@Body() Map<String, dynamic> paquete);

  @PUT('/api/paquete/editar')
  Future<PaqueteTuristico> editarPaquete(@Body() Map<String, dynamic> paquete);

  @DELETE('/api/paquete/eliminar/{id}')
  Future<void> eliminarPaquete(@Path('id') int id);
}
