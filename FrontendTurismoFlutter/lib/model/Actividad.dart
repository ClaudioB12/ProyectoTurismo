import 'package:frontendturismoflutter/model/Destino.dart';

class Actividad {
  final int? idActividad; // puede seguir siendo null para creación
  final String nombre;
  final String descripcion;
  final double precio;
  final Destino destino;

  Actividad({
    this.idActividad,
    required this.nombre,
    required this.descripcion,
    required this.precio,
    required this.destino,
  });

  factory Actividad.fromJson(Map<String, dynamic> json) {
    return Actividad(
      idActividad: json['idActividad'],
      nombre: json['nombre'] ?? '',
      descripcion: json['descripcion'] ?? '',
      precio: (json['precio'] as num?)?.toDouble() ?? 0.0,
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
    final data = {
      'nombre': nombre,
      'descripcion': descripcion,
      'precio': precio,
      'idDestino': destino.idDestino ?? 0, // ESTE ES EL CLAVE
    };
    if (idActividad != null) {
      data['idActividad'] = idActividad!;
    }
    return data;
  }

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
          other is Actividad && runtimeType == other.runtimeType && idActividad == other.idActividad;

  @override
  int get hashCode => idActividad.hashCode ?? 0;
}
