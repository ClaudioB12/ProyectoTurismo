import 'package:frontendturismoflutter/model/Cliente.dart';
import 'package:frontendturismoflutter/model/Destino.dart';
import 'package:frontendturismoflutter/model/PaqueteTuristico.dart';

class Reserva {
  final int? idReserva;
  final DateTime fechaReserva;
  final DateTime fechaInicio;
  final DateTime fechaFin;
  final int numeroPersonas;
  final Cliente cliente;
  final PaqueteTuristico paqueteTuristico;

  Reserva({
    this.idReserva,
    required this.fechaReserva,
    required this.fechaInicio,
    required this.fechaFin,
    required this.numeroPersonas,
    required this.cliente,
    required this.paqueteTuristico,
  });

  factory Reserva.fromJson(Map<String, dynamic> json) {
    return Reserva(
      idReserva: json['idReserva'] as int?,
      fechaReserva: DateTime.parse(json['fechaReserva']),
      fechaInicio: DateTime.parse(json['fechaInicio']),
      fechaFin: DateTime.parse(json['fechaFin']),
      numeroPersonas: json['numeroPersonas'] ?? 1,
      cliente: json['cliente'] != null && json['cliente'] is Map<String, dynamic>
          ? Cliente.fromJson(json['cliente'])
          : Cliente(
        idCliente: 0,
        nombreCompleto: 'Desconocido',
        correo: '',
        telefono: '',
        direccion: '',
        rol: 'USUARIO',
        fotoPerfilUrl: null,
      ),
      paqueteTuristico: json['paqueteTuristico'] != null && json['paqueteTuristico'] is Map<String, dynamic>
          ? PaqueteTuristico.fromJson(json['paqueteTuristico'])
          : PaqueteTuristico(
        idPaquete: 0,
        nombre: '',
        descripcion: '',
        precioTotal: 0,
        actividades: [],
        destino: Destino(
          idDestino: 0,
          nombre: '',
          descripcion: '',
          ubicacion: '',
        ),
      ),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'idReserva': idReserva,
      'fechaReserva': fechaReserva.toIso8601String(),
      'fechaInicio': fechaInicio.toIso8601String(),
      'fechaFin': fechaFin.toIso8601String(),
      'numeroPersonas': numeroPersonas,
      'idCliente': cliente.idCliente,
      'idPaquete': paqueteTuristico.idPaquete,
    };
  }
}
