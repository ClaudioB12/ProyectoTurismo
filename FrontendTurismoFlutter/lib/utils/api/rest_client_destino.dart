import 'package:dio/dio.dart';
import 'package:frontendturismoflutter/model/Destino.dart';
import 'package:retrofit/retrofit.dart';

part 'rest_client_destino.g.dart';

@RestApi()
abstract class RestClientDestino {
  factory RestClientDestino(Dio dio, {String baseUrl}) = _RestClientDestino;

  @GET('/api/destino/listar')
  Future<List<Destino>> listarDestinos();

  @POST('/api/destino/guardar')
  Future<Destino> guardarDestino(@Body() Destino destino);

  @PUT('/api/destino/editar')
  Future<Destino> editarDestino(@Body() Destino destino);

  @GET('/api/destino/buscar/{id}')
  Future<Destino> buscarDestinoPorId(@Path('id') int id);

  @DELETE('/api/destino/eliminar/{id}')
  Future<void> eliminarDestino(@Path('id') int id);
}
