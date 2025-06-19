import 'package:dio/dio.dart';
import 'package:frontendturismoflutter/model/Hospedaje.dart';
import 'package:retrofit/retrofit.dart';

part 'rest_client_hospedaje.g.dart';

@RestApi()
abstract class RestClientHospedaje {
  factory RestClientHospedaje(Dio dio, {String baseUrl}) = _RestClientHospedaje;

  @GET('/api/hospedaje/listar')
  Future<List<Hospedaje>> listarHospedajes();

  @POST('/api/hospedaje/guardar')
  Future<Hospedaje> guardarHospedaje(@Body() Map<String, dynamic> hospedaje);

  @PUT('/api/hospedaje/editar')
  Future<Hospedaje> editarHospedaje(@Body() Map<String, dynamic> hospedaje);

  @GET('/api/hospedaje/buscar/{id}')
  Future<Hospedaje> buscarHospedajePorId(@Path('id') int id);

  @DELETE('/api/hospedaje/eliminar/{id}')
  Future<void> eliminarHospedaje(@Path('id') int id);

  @GET('/api/hospedaje/por-destino/{idDestino}')
  Future<List<Hospedaje>> listarHospedajesPorDestino(@Path('idDestino') int idDestino);

}
