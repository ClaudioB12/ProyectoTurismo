import 'package:frontendturismoflutter/model/Destino.dart';
import 'package:frontendturismoflutter/model/Actividad.dart';

class PaqueteTuristico {
  final int? idPaquete;
  final String nombre;
  final String descripcion;
  final double precioTotal;

  // Lista de objetos Actividad (usada solo para mostrar)
  final List<Actividad> actividades;

  // Objeto anidado Destino
  final Destino destino;

  PaqueteTuristico({
    this.idPaquete,
    required this.nombre,
    required this.descripcion,
    required this.precioTotal,
    required this.actividades,
    required this.destino,
  });

  factory PaqueteTuristico.fromJson(Map<String, dynamic> json) {
    return PaqueteTuristico(
      idPaquete: json['idPaquete'] as int?,
      nombre: json['nombre'] ?? '',
      descripcion: json['descripcion'] ?? '',
      precioTotal: (json['precioTotal'] as num?)?.toDouble() ?? 0.0,
      actividades: (json['actividades'] as List<dynamic>?)
          ?.map((a) => Actividad.fromJson(a))
          .toList() ??
          [],
      destino: json['destino'] != null
          ? Destino.fromJson(json['destino'])
          : Destino(
        idDestino: 0,
        nombre: 'Desconocido',
        descripcion: 'Sin descripción',
        ubicacion: 'Sin ubicación',
      ),
    );
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = {
      'nombre': nombre,
      'descripcion': descripcion,
      'precioTotal': precioTotal,
      'actividadesIds':
      actividades.map((a) => a.idActividad ?? 0).toList(), // solo IDs
      'idDestino': destino.idDestino, // solo ID del destino
    };
    if (idPaquete != null) {
      data['idPaquete'] = idPaquete;
    }
    return data;
  }
}
