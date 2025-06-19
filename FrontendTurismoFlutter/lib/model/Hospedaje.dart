import 'package:frontendturismoflutter/model/Destino.dart';

class Hospedaje {
  final int? idHospedaje;
  final String nombre;
  final String descripcion;
  final double precioPorNoche;
  final double longitud;
  final double latitud;


  final Destino destino;

  Hospedaje({
    this.idHospedaje,
    required this.nombre,
    required this.descripcion,
    required this.precioPorNoche,
    required this.destino,
    required this.longitud,
    required this.latitud,
  });

  factory Hospedaje.fromJson(Map<String, dynamic> json) {
    return Hospedaje(
      idHospedaje: json['idHospedaje'],
      nombre: json['nombre'] ?? '',
      descripcion: json['descripcion'] ?? '',
      precioPorNoche: (json['precioPorNoche'] as num?)?.toDouble() ?? 0.0,
      latitud: (json['latitud'] as num?)?.toDouble() ?? 0.0,
      longitud: (json['longitud'] as num?)?.toDouble() ?? 0.0,


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
      'precioPorNoche': precioPorNoche,
      'longitud': longitud,
      'latitud': latitud,
      'idDestino': destino.idDestino ?? 0, // importante para backend
    };
    if (idHospedaje != null) {
      data['idHospedaje'] = idHospedaje!;
    }
    return data;
  }
}
