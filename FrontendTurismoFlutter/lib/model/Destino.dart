class Destino {
  int? idDestino;
  String nombre;
  String descripcion;
  String ubicacion;

  Destino({
    this.idDestino,
    required this.nombre,
    required this.descripcion,
    required this.ubicacion,
  });

  factory Destino.fromJson(Map<String, dynamic> json) {
    return Destino(
      idDestino: json['idDestino'],
      nombre: json['nombre'],
      descripcion: json['descripcion'],
      ubicacion: json['ubicacion'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'idDestino': idDestino,
      'nombre': nombre,
      'descripcion': descripcion,
      'ubicacion': ubicacion,
    };
  }

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
          other is Destino &&
              runtimeType == other.runtimeType &&
              idDestino == other.idDestino;

  @override
  int get hashCode => idDestino?.hashCode ?? 0;

  @override
  String toString() => nombre;

}
