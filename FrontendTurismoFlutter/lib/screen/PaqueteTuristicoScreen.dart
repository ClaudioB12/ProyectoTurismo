import 'package:flutter/material.dart';
import 'package:frontendturismoflutter/model/Actividad.dart';
import 'package:frontendturismoflutter/model/Destino.dart';
import 'package:frontendturismoflutter/model/PaqueteTuristico.dart';
import 'package:frontendturismoflutter/security/auth_service.dart';
import 'package:frontendturismoflutter/service/PaqueteTuristicoService.dart';
import 'package:frontendturismoflutter/service/ActividadService.dart';
import 'package:frontendturismoflutter/service/DestinoService.dart';
import 'package:frontendturismoflutter/utils/constants.dart';

class PaqueteTuristicoScreen extends StatefulWidget {
  const PaqueteTuristicoScreen({super.key});

  @override
  State<PaqueteTuristicoScreen> createState() => _PaqueteTuristicoScreenState();
}

class _PaqueteTuristicoScreenState extends State<PaqueteTuristicoScreen> {
  final PaqueteTuristicoService service = PaqueteTuristicoService(AppConstants.baseUrl);
  final ActividadService actividadService = ActividadService(AppConstants.baseUrl);
  final DestinoService destinoService = DestinoService(AppConstants.baseUrl);

  List<PaqueteTuristico> paquetes = [];
  List<Actividad> todasLasActividades = [];
  List<Destino> todosLosDestinos = [];

  List<Actividad> actividadesSeleccionadas = [];
  Destino? destinoSeleccionado;

  String? rolUsuario;
  bool loading = false;


  bool get esAdmin => rolUsuario == "ROLE_ADMIN";

  @override
  void initState() {
    super.initState();
    _cargarRolYDatos();
  }

  Future<void> _cargarRolYDatos() async {
    setState(() => loading = true);
    final authService = AuthService();
    final rol = await authService.obtenerRolDesdeToken();
    await _cargarDatos();
    setState(() {
      rolUsuario = rol;
      loading = false;
    });
  }

  Future<void> _cargarDatos() async {
    try {
      final dataPaquetes = await service.listarPaquetes();
      final dataActividades = await actividadService.listarActividades();
      final dataDestinos = await destinoService.listarDestinos();

      setState(() {
        paquetes = dataPaquetes;
        todasLasActividades = dataActividades;
        todosLosDestinos = dataDestinos;
      });
    } catch (e) {
      _mostrarMensaje('Error cargando datos: $e');
    }
  }

  Future<void> _guardarOEditarPaquete([PaqueteTuristico? paquete]) async {
    final nombreController = TextEditingController(text: paquete?.nombre ?? '');
    final descripcionController = TextEditingController(text: paquete?.descripcion ?? '');
    final precioController = TextEditingController(text: paquete?.precioTotal?.toString() ?? '');

    actividadesSeleccionadas = List.from(paquete?.actividades ?? []);
    destinoSeleccionado = paquete != null
        ? todosLosDestinos.firstWhere(
          (d) => d.idDestino == paquete.destino.idDestino,
      orElse: () => todosLosDestinos.isNotEmpty
          ? todosLosDestinos.first
          : Destino(idDestino: 0, nombre: 'Ninguno', descripcion: '', ubicacion: ''),
    )
        : (todosLosDestinos.isNotEmpty ? todosLosDestinos.first : null);

    final formKey = GlobalKey<FormState>();

    await showDialog(
      context: context,
      builder: (_) => AlertDialog(
        title: Text(paquete == null ? 'Crear Paquete' : 'Editar Paquete'),
        content: Form(
          key: formKey,
          child: SingleChildScrollView(
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                TextFormField(
                  controller: nombreController,
                  decoration: InputDecoration(labelText: 'Nombre'),
                  validator: (v) => v == null || v.isEmpty ? 'Ingrese nombre' : null,
                ),
                TextFormField(
                  controller: descripcionController,
                  decoration: InputDecoration(labelText: 'Descripción'),
                  validator: (v) => v == null || v.isEmpty ? 'Ingrese descripción' : null,
                ),
                TextFormField(
                  controller: precioController,
                  decoration: InputDecoration(labelText: 'Precio Total'),
                  keyboardType: TextInputType.numberWithOptions(decimal: true),
                  validator: (v) {
                    if (v == null || v.isEmpty) return 'Ingrese precio';
                    if (double.tryParse(v) == null) return 'Precio inválido';
                    return null;
                  },
                ),
                SizedBox(height: 12),
                DropdownButtonFormField<Destino>(
                  value: destinoSeleccionado,
                  items: todosLosDestinos.map((d) {
                    return DropdownMenuItem(
                      value: d,
                      child: Text(d.nombre),
                    );
                  }).toList(),
                  onChanged: (d) => setState(() => destinoSeleccionado = d),
                  decoration: InputDecoration(labelText: 'Destino'),
                  validator: (value) => value == null ? 'Seleccione un destino' : null,
                ),
                SizedBox(height: 12),
                Text('Actividades', style: TextStyle(fontWeight: FontWeight.bold)),
                ...todasLasActividades.map((actividad) {
                  final seleccionado =
                  actividadesSeleccionadas.any((a) => a.idActividad == actividad.idActividad);
                  return CheckboxListTile(
                    title: Text(actividad.nombre),
                    value: seleccionado,
                    onChanged: (bool? value) {
                      setState(() {
                        if (value == true) {
                          actividadesSeleccionadas.add(actividad);
                        } else {
                          actividadesSeleccionadas
                              .removeWhere((a) => a.idActividad == actividad.idActividad);
                        }
                      });
                    },
                  );
                }).toList(),
              ],
            ),
          ),
        ),
        actions: [
          TextButton(onPressed: () => Navigator.pop(context), child: Text('Cancelar')),
          ElevatedButton(
            onPressed: () async {
              if (formKey.currentState!.validate()) {
                Navigator.pop(context);
                try {
                  final nuevoPaquete = PaqueteTuristico(
                    idPaquete: paquete?.idPaquete,
                    nombre: nombreController.text,
                    descripcion: descripcionController.text,
                    precioTotal: double.parse(precioController.text),
                    actividades: actividadesSeleccionadas,
                    destino: destinoSeleccionado!,
                  );

                  if (paquete == null) {
                    await service.guardarPaquete(nuevoPaquete);
                    _mostrarMensaje('Paquete creado');
                  } else {
                    await service.editarPaquete(nuevoPaquete);
                    _mostrarMensaje('Paquete actualizado');
                  }
                  _cargarDatos();
                } catch (e) {
                  _mostrarMensaje('Error guardando paquete: $e');
                }
              }
            },
            child: Text(paquete == null ? 'Crear' : 'Guardar'),
          ),
        ],
      ),
    );
  }

  Future<void> _confirmarEliminar(int id) async {
    final confirmado = await showDialog<bool>(
      context: context,
      builder: (_) => AlertDialog(
        title: Text('Confirmar eliminación'),
        content: Text('¿Seguro que quieres eliminar este paquete?'),
        actions: [
          TextButton(onPressed: () => Navigator.pop(context, false), child: Text('Cancelar')),
          ElevatedButton(onPressed: () => Navigator.pop(context, true), child: Text('Eliminar')),
        ],
      ),
    );

    if (confirmado == true) {
      try {
        await service.eliminarPaquete(id);
        _mostrarMensaje('Paquete eliminado');
        _cargarDatos();
      } catch (e) {
        _mostrarMensaje('Error eliminando paquete: $e');
      }
    }
  }

  void _mostrarMensaje(String msg) {
    ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(msg)));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Paquetes Turísticos')),
      body: loading
          ? Center(child: CircularProgressIndicator())
          : paquetes.isEmpty
          ? Center(child: Text('No hay paquetes turísticos', style: TextStyle(fontSize: 18)))
          : ListView.builder(
        itemCount: paquetes.length,
        itemBuilder: (context, i) {
          final p = paquetes[i];
          final destinoNombre = p.destino?.nombre ?? 'Sin destino';
          final actividadesNombres = p.actividades.isNotEmpty
              ? p.actividades.map((a) => a.nombre).join(', ')
              : 'Sin actividades';

          return Card(
            margin: EdgeInsets.symmetric(horizontal: 12, vertical: 6),
            child: ListTile(
              title: Text(p.nombre, style: TextStyle(fontWeight: FontWeight.bold)),
              subtitle: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(p.descripcion),
                  SizedBox(height: 4),
                  Text('Precio: S/ ${p.precioTotal.toStringAsFixed(2)}'),
                  SizedBox(height: 4),
                  Text('Destino: $destinoNombre',
                      style: TextStyle(fontStyle: FontStyle.italic)),
                  Text('Actividades: $actividadesNombres',
                      style: TextStyle(fontStyle: FontStyle.italic)),
                ],
              ),
              isThreeLine: true,
              trailing:
                  PopupMenuButton<String>(
                onSelected: (value) {
                  if (value == 'editar') {
                    _guardarOEditarPaquete(p);
                  } else if (value == 'eliminar') {
                    _confirmarEliminar(p.idPaquete!.toInt());
                  }
                },
                itemBuilder: (_) => [
                  PopupMenuItem(value: 'editar', child: Text('Editar')),
                  PopupMenuItem(value: 'eliminar', child: Text('Eliminar')),
                ],
              )
                  ,
            ),
          );
        },
      ),
      floatingActionButton:
          FloatingActionButton(
        onPressed: () => _guardarOEditarPaquete(),
        child: Icon(Icons.add),
        tooltip: 'Agregar Paquete',
      )
           ,
    );
  }
}
