import 'package:flutter/material.dart';
import 'package:frontendturismoflutter/model/Reserva.dart';
import 'package:frontendturismoflutter/model/Cliente.dart';
import 'package:frontendturismoflutter/model/PaqueteTuristico.dart';
import 'package:frontendturismoflutter/security/auth_service.dart';
import 'package:frontendturismoflutter/service/ReservaService.dart';
import 'package:frontendturismoflutter/service/ClienteService.dart';
import 'package:frontendturismoflutter/service/PaqueteTuristicoService.dart';
import 'package:frontendturismoflutter/utils/constants.dart';

class ReservaScreen extends StatefulWidget {
  const ReservaScreen({super.key});

  @override
  State<ReservaScreen> createState() => _ReservaScreenState();
}

class _ReservaScreenState extends State<ReservaScreen> {
  final ReservaService reservaService = ReservaService(AppConstants.baseUrl);
  final ClienteService clienteService = ClienteService(AppConstants.baseUrl);
  final PaqueteTuristicoService paqueteService = PaqueteTuristicoService(AppConstants.baseUrl);

  List<Reserva> reservas = [];
  List<Cliente> clientes = [];
  List<PaqueteTuristico> paquetes = [];

  Cliente? clienteSeleccionado;
  PaqueteTuristico? paqueteSeleccionado;

  String? rolUsuario;
  bool loading = false;


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
      final dataReservas = await reservaService.listarReservas();
      final dataClientes = await clienteService.listarClientes();
      final dataPaquetes = await paqueteService.listarPaquetes();

      setState(() {
        reservas = dataReservas;
        clientes = dataClientes;
        paquetes = dataPaquetes;
      });
    } catch (e) {
      _mostrarMensaje('Error cargando datos: $e');
    }
  }

  Future<void> _guardarOEditarReserva([Reserva? reserva]) async {
    final formKey = GlobalKey<FormState>();

    final fechaInicioController = TextEditingController(
        text: reserva?.fechaInicio.toIso8601String().split('T')[0] ?? '');
    final fechaFinController = TextEditingController(
        text: reserva?.fechaFin.toIso8601String().split('T')[0] ?? '');
    final numeroPersonasController = TextEditingController(
        text: reserva?.numeroPersonas?.toString() ?? '');

    clienteSeleccionado = reserva?.cliente ?? (clientes.isNotEmpty ? clientes.first : null);
    paqueteSeleccionado = reserva?.paqueteTuristico ?? (paquetes.isNotEmpty ? paquetes.first : null);

    await showDialog(
      context: context,
      builder: (_) => AlertDialog(
        title: Text(reserva == null ? 'Crear Reserva' : 'Editar Reserva'),
        content: Form(
          key: formKey,
          child: SingleChildScrollView(
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                TextFormField(
                  controller: fechaInicioController,
                  readOnly: true,
                  decoration: InputDecoration(labelText: 'Fecha Inicio'),
                  onTap: () async {
                    DateTime? picked = await showDatePicker(
                      context: context,
                      initialDate: reserva?.fechaInicio ?? DateTime.now(),
                      firstDate: DateTime(2000),
                      lastDate: DateTime(2100),
                    );
                    if (picked != null) {
                      setState(() {
                        fechaInicioController.text = picked.toIso8601String().split('T')[0];
                      });
                    }
                  },
                  validator: (v) => v == null || v.isEmpty ? 'Ingrese fecha de inicio' : null,
                ),
                TextFormField(
                  controller: fechaFinController,
                  readOnly: true,
                  decoration: InputDecoration(labelText: 'Fecha Fin'),
                  onTap: () async {
                    DateTime? picked = await showDatePicker(
                      context: context,
                      initialDate: reserva?.fechaFin ?? DateTime.now(),
                      firstDate: DateTime(2000),
                      lastDate: DateTime(2100),
                    );
                    if (picked != null) {
                      setState(() {
                        fechaFinController.text = picked.toIso8601String().split('T')[0];
                      });
                    }
                  },
                  validator: (v) => v == null || v.isEmpty ? 'Ingrese fecha de fin' : null,
                ),

                TextFormField(
                  controller: numeroPersonasController,
                  decoration: InputDecoration(labelText: 'Número de personas'),
                  keyboardType: TextInputType.number,
                  validator: (v) => v == null || v.isEmpty ? 'Ingrese cantidad' : null,
                ),
                DropdownButtonFormField<Cliente>(
                  value: clienteSeleccionado,
                  items: clientes.map((c) {
                    return DropdownMenuItem(
                      value: c,
                      child: Text(c.nombreCompleto),
                    );
                  }).toList(),
                  onChanged: (c) => setState(() => clienteSeleccionado = c),
                  decoration: InputDecoration(labelText: 'Cliente'),
                ),
                DropdownButtonFormField<PaqueteTuristico>(
                  value: paqueteSeleccionado,
                  items: paquetes.map((p) {
                    return DropdownMenuItem(
                      value: p,
                      child: Text(p.nombre),
                    );
                  }).toList(),
                  onChanged: (p) => setState(() => paqueteSeleccionado = p),
                  decoration: InputDecoration(labelText: 'Paquete Turístico'),
                ),
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
                  final nuevaReserva = Reserva(
                    idReserva: reserva?.idReserva,
                    fechaReserva: DateTime.now(), // <-- agrega esta línea obligatoria
                    fechaInicio: DateTime.parse(fechaInicioController.text),
                    fechaFin: DateTime.parse(fechaFinController.text),
                    numeroPersonas: int.parse(numeroPersonasController.text),
                    cliente: clienteSeleccionado!,
                    paqueteTuristico: paqueteSeleccionado!,
                  );


                  if (reserva == null) {
                    await reservaService.guardarReserva(nuevaReserva);
                    _mostrarMensaje('Reserva creada');
                  } else {
                    await reservaService.editarReserva(nuevaReserva);
                    _mostrarMensaje('Reserva actualizada');
                  }
                  _cargarDatos();
                } catch (e) {
                  _mostrarMensaje('Error guardando reserva: $e');
                }
              }
            },
            child: Text(reserva == null ? 'Crear' : 'Guardar'),
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
        content: Text('¿Seguro que quieres eliminar esta reserva?'),
        actions: [
          TextButton(onPressed: () => Navigator.pop(context, false), child: Text('Cancelar')),
          ElevatedButton(onPressed: () => Navigator.pop(context, true), child: Text('Eliminar')),
        ],
      ),
    );

    if (confirmado == true) {
      try {
        await reservaService.eliminarReserva(id);
        _mostrarMensaje('Reserva eliminada');
        _cargarDatos();
      } catch (e) {
        _mostrarMensaje('Error eliminando reserva: $e');
      }
    }
  }

  void _mostrarMensaje(String msg) {
    ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(msg)));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Reservas')),
      body: loading
          ? Center(child: CircularProgressIndicator())
          : reservas.isEmpty
          ? Center(child: Text('No hay reservas', style: TextStyle(fontSize: 18)))
          : ListView.builder(
        itemCount: reservas.length,
        itemBuilder: (context, i) {
          final r = reservas[i];
          return Card(
            margin: EdgeInsets.symmetric(horizontal: 12, vertical: 6),
            child: ListTile(
              title: Text('Reserva ${r.idReserva} - ${r.cliente.nombreCompleto}'),
              subtitle: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text('Paquete: ${r.paqueteTuristico.nombre}'),
                  Text('Desde: ${r.fechaInicio} hasta ${r.fechaFin}'),
                  Text('Personas: ${r.numeroPersonas}'),
                ],
              ),
              trailing:
                   PopupMenuButton<String>(
                onSelected: (value) {
                  if (value == 'editar') {
                    _guardarOEditarReserva(r);
                  } else if (value == 'eliminar') {
                    _confirmarEliminar(r.idReserva!.toInt());
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
        onPressed: () => _guardarOEditarReserva(),
        child: Icon(Icons.add),
        tooltip: 'Agregar Reserva',
      )
    ,
    );
  }
}
