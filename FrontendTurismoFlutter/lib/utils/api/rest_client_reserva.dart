import 'package:dio/dio.dart';
import 'package:retrofit/retrofit.dart';
import 'package:frontendturismoflutter/model/Reserva.dart';

part 'rest_client_reserva.g.dart';

@RestApi()
abstract class RestClientReserva {
  factory RestClientReserva(Dio dio, {String baseUrl}) = _RestClientReserva;

  @GET('/api/reserva/listar')
  Future<List<Reserva>> listarReservas();

  @GET('/api/reserva/buscar/{id}')
  Future<Reserva> buscarReserva(@Path("id") int id);

  @POST('/api/reserva/guardar')
  Future<Reserva> guardarReserva(@Body() Map<String, dynamic> body);

  @PUT('/api/reserva/editar')
  Future<Reserva> editarReserva(@Body() Map<String, dynamic> body);

  @DELETE('/api/reserva/eliminar/{id}')
  Future<void> eliminarReserva(@Path("id") int id);
}
