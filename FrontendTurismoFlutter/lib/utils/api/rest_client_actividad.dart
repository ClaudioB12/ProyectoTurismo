import 'package:dio/dio.dart';
import 'package:frontendturismoflutter/model/Actividad.dart';
import 'package:retrofit/retrofit.dart';

part 'rest_client_actividad.g.dart';

@RestApi()
abstract class RestClientActividad {
  factory RestClientActividad(Dio dio, {String baseUrl}) = _RestClientActividad;

  @GET('/api/actividad/listar')
  Future<List<Actividad>> listarActividades();

  @GET('/api/actividad/por-destino/{idDestino}')
  Future<List<Actividad>> listarActividadesPorDestino(@Path('idDestino') int idDestino);

  @POST('/api/actividad/guardar')
  Future<Actividad> guardarActividad(@Body() Map<String, dynamic> actividad);

  @PUT('/api/actividad/editar')
  Future<Actividad> editarActividad(@Body() Map<String, dynamic> actividad);

  @GET('/api/actividad/buscar/{id}')
  Future<Actividad> buscarActividadPorId(@Path('id') int id);

  @DELETE('/api/actividad/eliminar/{id}')
  Future<void> eliminarActividad(@Path('id') int id);
}
