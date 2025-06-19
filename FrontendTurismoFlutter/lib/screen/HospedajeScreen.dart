// imports
import 'package:flutter/material.dart';
import 'package:frontendturismoflutter/model/Hospedaje.dart';
import 'package:frontendturismoflutter/model/Destino.dart';
import 'package:frontendturismoflutter/screen/SeleccionUbicacionScreen.dart';
import 'package:frontendturismoflutter/service/HospedajeService.dart';
import 'package:frontendturismoflutter/service/DestinoService.dart';
import 'package:frontendturismoflutter/utils/constants.dart';

class HospedajeScreen extends StatefulWidget {
  const HospedajeScreen({super.key});

  @override
  State<HospedajeScreen> createState() => _HospedajeScreenState();
}

class _HospedajeScreenState extends State<HospedajeScreen> {
  final _formKey = GlobalKey<FormState>();
  final _nombreCtrl = TextEditingController();
  final _descripcionCtrl = TextEditingController();
  final _precioCtrl = TextEditingController();
  final _latitudController = TextEditingController();
  final _longitudController = TextEditingController();

  late HospedajeService _hospedajeService;
  late DestinoService _destinoService;

  List<Hospedaje> _hospedajes = [];
  List<Destino> _destinos = [];
  Destino? _destinoSeleccionado;
  Hospedaje? _editando;

  bool _isLoading = false;

  @override
  void initState() {
    super.initState();
    _hospedajeService = HospedajeService(AppConstants.baseUrl);
    _destinoService = DestinoService(AppConstants.baseUrl);
    _cargarDatos();
  }

  void _cargarDatos() async {
    setState(() => _isLoading = true);
    try {
      _hospedajes = await _hospedajeService.listarHospedajes();
      _destinos = await _destinoService.listarDestinos();
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error al cargar datos: $e')),
      );
    } finally {
      setState(() => _isLoading = false);
    }
  }

  void _guardar() async {
    if (!_formKey.currentState!.validate() || _destinoSeleccionado == null) return;

    setState(() => _isLoading = true);

    try {
      final hospedaje = Hospedaje(
        idHospedaje: _editando?.idHospedaje,
        nombre: _nombreCtrl.text,
        descripcion: _descripcionCtrl.text,
        precioPorNoche: double.tryParse(_precioCtrl.text) ?? 0.0,
        latitud: double.tryParse(_latitudController.text) ?? 0.0,
        longitud: double.tryParse(_longitudController.text) ?? 0.0,

        destino: _destinoSeleccionado!,
      );

      if (_editando == null) {
        await _hospedajeService.guardarHospedaje(hospedaje);
      } else {
        await _hospedajeService.editarHospedaje(hospedaje);
      }

      _limpiarFormulario();
      _cargarDatos();
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error al guardar: $e')),
      );
    } finally {
      setState(() => _isLoading = false);
    }
  }

  void _editar(Hospedaje h) {
    setState(() {
      _editando = h;
      _nombreCtrl.text = h.nombre ?? '';
      _descripcionCtrl.text = h.descripcion ?? '';
      _precioCtrl.text = h.precioPorNoche?.toString() ?? '';
      _latitudController.text = h.latitud?.toString() ?? '';
      _longitudController.text = h.longitud?.toString() ?? '';
      _destinoSeleccionado =
          _destinos.firstWhere((d) => d.idDestino == h.destino.idDestino);
    });
  }

  void _eliminar(int id) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Confirmar eliminación'),
        content: const Text('¿Estás seguro de eliminar este hospedaje?'),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(context).pop(),
            child: const Text('Cancelar'),
          ),
          ElevatedButton(
            style: ElevatedButton.styleFrom(backgroundColor: Colors.red),
            onPressed: () async {
              Navigator.of(context).pop();
              setState(() => _isLoading = true);
              try {
                await _hospedajeService.eliminarHospedaje(id);
                _cargarDatos();
              } catch (e) {
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(content: Text('Error al eliminar: $e')),
                );
              } finally {
                setState(() => _isLoading = false);
              }
            },
            child: const Text('Eliminar'),
          ),
        ],
      ),
    );
  }

  void _limpiarFormulario() {
    setState(() {
      _editando = null;
      _nombreCtrl.clear();
      _descripcionCtrl.clear();
      _precioCtrl.clear();
      _latitudController.clear();
      _longitudController.clear();
      _destinoSeleccionado = null;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Gestión de Hospedajes'),
        leading: IconButton(
          icon: const Icon(Icons.arrow_back),
          onPressed: () {
            Navigator.pushReplacementNamed(context, '/menu');
          },
        ),
      ),
      body: _isLoading && _hospedajes.isEmpty
          ? const Center(child: CircularProgressIndicator())
          : Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            Expanded(
              child: SingleChildScrollView(
                child: Form(
                  key: _formKey,
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.stretch,
                    children: [
                      TextFormField(
                        controller: _nombreCtrl,
                        decoration: const InputDecoration(
                          labelText: 'Nombre',
                          border: OutlineInputBorder(),
                        ),
                        validator: (value) =>
                        value!.isEmpty ? 'Campo requerido' : null,
                      ),
                      const SizedBox(height: 10),
                      TextFormField(
                        controller: _descripcionCtrl,
                        decoration: const InputDecoration(
                          labelText: 'Descripción',
                          border: OutlineInputBorder(),
                        ),
                        maxLines: 2,
                      ),
                      const SizedBox(height: 10),
                      TextFormField(
                        controller: _precioCtrl,
                        decoration: const InputDecoration(
                          labelText: 'Precio por noche',
                          border: OutlineInputBorder(),
                        ),
                        keyboardType: TextInputType.number,
                      ),
                      const SizedBox(height: 10),
                      DropdownButtonFormField<Destino>(
                        value: _destinoSeleccionado,
                        items: _destinos.map((d) {
                          return DropdownMenuItem(
                            value: d,
                            child: Text('${d.nombre} - ${d.ubicacion}'),
                          );
                        }).toList(),
                        onChanged: (value) =>
                            setState(() => _destinoSeleccionado = value),
                        decoration: const InputDecoration(
                          labelText: 'Destino',
                          border: OutlineInputBorder(),
                        ),
                        validator: (value) =>
                        value == null ? 'Seleccione un destino' : null,
                      ),
                      const SizedBox(height: 10),
                      ElevatedButton(
                        onPressed: () async {
                          final resultado = await Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (_) =>
                              const SeleccionUbicacionScreen(),
                            ),
                          );

                          if (resultado != null) {
                            final lat = resultado['lat'] as double;
                            final lng = resultado['lng'] as double;

                            _latitudController.text = lat.toString();
                            _longitudController.text = lng.toString();
                            setState(() {});
                          }
                        },
                        child: Text(
                          (_latitudController.text.isNotEmpty &&
                              _longitudController.text.isNotEmpty)
                              ? 'Lat: ${_latitudController.text}, Lng: ${_longitudController.text}'
                              : 'Seleccionar ubicación en el mapa',
                          textAlign: TextAlign.center,
                        ),
                      ),
                      if (_latitudController.text.isNotEmpty &&
                          _longitudController.text.isNotEmpty)
                        Card(
                          margin: const EdgeInsets.only(top: 16),
                          child: Padding(
                            padding: const EdgeInsets.all(12),
                            child: Column(
                              crossAxisAlignment:
                              CrossAxisAlignment.start,
                              children: [
                                const Text(
                                  'Ubicación seleccionada:',
                                  style: TextStyle(
                                      fontWeight: FontWeight.bold),
                                ),
                                const SizedBox(height: 8),
                                ClipRRect(
                                  borderRadius: BorderRadius.circular(8),
                                  child: SizedBox(
                                    width: double.infinity,
                                    height: 200,
                                    child: Image.network(
                                      'https://api.mapbox.com/styles/v1/mapbox/streets-v11/static/pin-s+ff0000(${_longitudController.text},${_latitudController.text})/${_longitudController.text},${_latitudController.text},14/600x200?access_token=pk.eyJ1Ijoiam9zdWUyMDAzIiwiYSI6ImNtYWI5eHB4aDFrOXQyam9pY2toMHg1dTEifQ.mioI8UDDcUa9pqKXIsEC6A',
                                      fit: BoxFit.cover,
                                    ),
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                      const SizedBox(height: 20),
                      ElevatedButton(
                        onPressed: _guardar,
                        child:
                        Text(_editando == null ? 'Guardar' : 'Actualizar'),
                      ),
                    ],
                  ),
                ),
              ),
            ),
            const Divider(),
            Expanded(
              child: _hospedajes.isEmpty
                  ? const Center(child: Text('No hay hospedajes registrados.'))
                  : ListView.builder(
                itemCount: _hospedajes.length,
                itemBuilder: (context, index) {
                  final h = _hospedajes[index];
                  return Card(
                    shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(10)),
                    margin: const EdgeInsets.symmetric(vertical: 6),
                    child: ListTile(
                      title: Text(h.nombre ?? '',
                          style: const TextStyle(
                              fontWeight: FontWeight.bold)),
                      subtitle: Text(
                        '${h.descripcion ?? ''}\n'
                            'Precio: S/.${h.precioPorNoche?.toStringAsFixed(2) ?? '0.00'}\n'
                            'Destino: ${h.destino.nombre}\n'
                            'Ubicación: ${h.destino.ubicacion}',
                      ),
                      isThreeLine: true,
                      trailing: Wrap(
                        spacing: 10,
                        children: [
                          IconButton(
                            icon: const Icon(Icons.edit,
                                color: Colors.blue),
                            onPressed: () => _editar(h),
                          ),
                          IconButton(
                            icon: const Icon(Icons.delete,
                                color: Colors.red),
                            onPressed: () =>
                                _eliminar(h.idHospedaje!),
                          ),
                        ],
                      ),
                    ),
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}
