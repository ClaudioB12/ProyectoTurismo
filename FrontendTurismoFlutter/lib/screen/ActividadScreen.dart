import 'package:flutter/material.dart';
import 'package:frontendturismoflutter/model/Actividad.dart';
import 'package:frontendturismoflutter/model/Destino.dart';
import 'package:frontendturismoflutter/service/ActividadService.dart';
import 'package:frontendturismoflutter/service/DestinoService.dart';
import 'package:frontendturismoflutter/utils/constants.dart';

class ActividadScreen extends StatefulWidget {
  const ActividadScreen({super.key});

  @override
  State<ActividadScreen> createState() => _ActividadScreenState();
}

class _ActividadScreenState extends State<ActividadScreen> {
  late ActividadService _actividadService;
  late DestinoService _destinoService;

  final _formKey = GlobalKey<FormState>();
  final TextEditingController _nombreController = TextEditingController();
  final TextEditingController _descripcionController = TextEditingController();
  final TextEditingController _precioController = TextEditingController();

  List<Actividad> _actividades = [];
  List<Destino> _destinos = [];

  int? _idEditing;
  int? _idDestinoSeleccionado;
  bool _isLoading = false;

  @override
  void initState() {
    super.initState();
    _actividadService = ActividadService(AppConstants.baseUrl);
    _destinoService = DestinoService(AppConstants.baseUrl);
    _cargarDestinos();
    _cargarActividades();
  }

  Future<void> _cargarActividades() async {
    setState(() => _isLoading = true);
    try {
      final data = await _actividadService.listarActividades();
      setState(() => _actividades = data);
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error al cargar actividades: $e')),
      );
    } finally {
      setState(() => _isLoading = false);
    }
  }

  Future<void> _cargarDestinos() async {
    try {
      final data = await _destinoService.listarDestinos();
      setState(() => _destinos = data);
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error al cargar destinos: $e')),
      );
    }
  }

  Future<void> _guardarActividad() async {
    if (!_formKey.currentState!.validate()) return;

    if (_idDestinoSeleccionado == null) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Seleccione un destino')),
      );
      return;
    }

    setState(() => _isLoading = true);

    try {
      final destinoSeleccionado = _destinos.firstWhere(
            (d) => d.idDestino == _idDestinoSeleccionado,
        orElse: () => Destino(idDestino: 0, nombre: 'Desconocido', descripcion: '', ubicacion: ''),
      );

      final actividad = Actividad(
        idActividad: _idEditing,
        nombre: _nombreController.text.trim(),
        descripcion: _descripcionController.text.trim(),
        precio: double.tryParse(_precioController.text.trim()) ?? 0.0,
        destino: destinoSeleccionado,
      );

      if (_idEditing == null) {
        await _actividadService.guardarActividad(actividad);
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Actividad guardada correctamente')),
        );
      } else {
        await _actividadService.editarActividad(actividad);
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Actividad actualizada correctamente')),
        );
      }

      _limpiarFormulario();
      _cargarActividades();
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error al guardar actividad: $e')),
      );
    } finally {
      setState(() => _isLoading = false);
    }
  }

  void _editarActividad(Actividad actividad) {
    final nombreCtrl = TextEditingController(text: actividad.nombre);
    final descripcionCtrl = TextEditingController(text: actividad.descripcion);
    final precioCtrl = TextEditingController(text: actividad.precio.toString());
    int? destinoIdSeleccionado = actividad.destino?.idDestino;

    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Editar Actividad'),
        content: SingleChildScrollView(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              _buildInputField(nombreCtrl, 'Nombre'),
              const SizedBox(height: 10),
              _buildInputField(descripcionCtrl, 'Descripción'),
              const SizedBox(height: 10),
              _buildInputField(precioCtrl, 'Precio', isNumber: true),
              const SizedBox(height: 10),
              DropdownButtonFormField<int>(
                value: destinoIdSeleccionado,
                decoration: _inputDecoration('Destino'),
                items: _destinos.map((d) {
                  return DropdownMenuItem(
                    value: d.idDestino,
                    child: Text(d.nombre),
                  );
                }).toList(),
                onChanged: (value) {
                  destinoIdSeleccionado = value;
                },
              ),
            ],
          ),
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('Cancelar'),
          ),
          ElevatedButton(
            onPressed: () async {
              Navigator.pop(context);

              setState(() => _isLoading = true);
              try {
                final destinoSeleccionado = _destinos.firstWhere(
                      (d) => d.idDestino == destinoIdSeleccionado,
                  orElse: () => Destino(idDestino: 0, nombre: '', descripcion: '', ubicacion: ''),
                );

                final actividadActualizada = Actividad(
                  idActividad: actividad.idActividad,
                  nombre: nombreCtrl.text.trim(),
                  descripcion: descripcionCtrl.text.trim(),
                  precio: double.tryParse(precioCtrl.text.trim()) ?? 0.0,
                  destino: destinoSeleccionado,
                );

                await _actividadService.editarActividad(actividadActualizada);

                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text('Actividad actualizada correctamente')),
                );

                _cargarActividades();
              } catch (e) {
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(content: Text('Error al actualizar actividad: $e')),
                );
              } finally {
                setState(() => _isLoading = false);
              }
            },
            child: const Text('Guardar cambios'),
          ),
        ],
      ),
    );
  }

  void _eliminarActividad(int id) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Confirmar eliminación'),
        content: const Text('¿Estás seguro de eliminar esta actividad?'),
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
                await _actividadService.eliminarActividad(id);
                _cargarActividades();
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
      _idEditing = null;
      _nombreController.clear();
      _descripcionController.clear();
      _precioController.clear();
      _idDestinoSeleccionado = null;
    });
  }

  InputDecoration _inputDecoration(String label) {
    return InputDecoration(
      labelText: label,
      border: OutlineInputBorder(borderRadius: BorderRadius.circular(10)),
      contentPadding: const EdgeInsets.symmetric(horizontal: 12, vertical: 14),
    );
  }

  Widget _buildInputField(TextEditingController controller, String label, {bool isNumber = false}) {
    return TextFormField(
      controller: controller,
      decoration: _inputDecoration(label),
      keyboardType: isNumber ? const TextInputType.numberWithOptions(decimal: true) : null,
      validator: (value) => value == null || value.isEmpty ? 'Campo requerido' : null,
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        title: const Text('Gestión de Actividades', style: TextStyle(color: Colors.red)),
        backgroundColor: Colors.grey[100],
        elevation: 1,
        iconTheme: const IconThemeData(color: Colors.red),
        leading: IconButton(
        icon: const Icon(Icons.arrow_back),
        onPressed: () {
          Navigator.pushReplacementNamed(context, '/menu'); // ← cambia la ruta aquí
        },
      ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            Form(
              key: _formKey,
              child: Column(
                children: [
                  _buildInputField(_nombreController, 'Nombre'),
                  const SizedBox(height: 10),
                  _buildInputField(_descripcionController, 'Descripción'),
                  const SizedBox(height: 10),
                  _buildInputField(_precioController, 'Precio', isNumber: true),
                  const SizedBox(height: 10),
                  DropdownButtonFormField<int>(
                    value: _idDestinoSeleccionado,
                    decoration: _inputDecoration('Seleccionar destino'),
                    items: _destinos.map((destino) => DropdownMenuItem<int>(
                      value: destino.idDestino,
                      child: Text(destino.nombre),
                    )).toList(),
                    onChanged: (value) {
                      setState(() => _idDestinoSeleccionado = value);
                    },
                    validator: (value) => value == null ? 'Campo requerido' : null,
                  ),
                  const SizedBox(height: 20),
                  _isLoading
                      ? const CircularProgressIndicator()
                      : SizedBox(
                    width: double.infinity,
                    child: ElevatedButton(
                      onPressed: _guardarActividad,
                      style: ElevatedButton.styleFrom(
                        backgroundColor: Colors.teal,
                        padding: const EdgeInsets.symmetric(vertical: 14),
                      ),
                      child: Text(
                        _idEditing == null ? 'Guardar' : 'Actualizar',
                        style: const TextStyle(fontSize: 16),
                      ),
                    ),
                  ),
                ],
              ),
            ),
            const SizedBox(height: 30),
            Expanded(
              child: _isLoading && _actividades.isEmpty
                  ? const Center(child: CircularProgressIndicator())
                  : _actividades.isEmpty
                  ? const Center(child: Text('No hay actividades registradas.'))
                  : ListView.separated(
                itemCount: _actividades.length,
                separatorBuilder: (_, __) => const SizedBox(height: 10),
                itemBuilder: (context, index) {
                  final a = _actividades[index];
                  final nombreDestino = a.destino?.nombre ?? 'Destino desconocido';

                  return Card(
                    shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
                    elevation: 2,
                    child: ListTile(
                      title: Text(a.nombre, style: const TextStyle(fontWeight: FontWeight.bold)),
                      subtitle: Padding(
                        padding: const EdgeInsets.only(top: 6),
                        child: Text(
                          '${a.descripcion}\nPrecio: \$${a.precio.toStringAsFixed(2)}\nDestino: $nombreDestino',
                          style: const TextStyle(height: 1.4),
                        ),
                      ),
                      isThreeLine: true,
                      trailing: Wrap(
                        spacing: 6,
                        children: [
                          IconButton(
                            icon: const Icon(Icons.edit, color: Colors.blue),
                            onPressed: () => _editarActividad(a),
                          ),
                          IconButton(
                            icon: const Icon(Icons.delete, color: Colors.red),
                            onPressed: () => _eliminarActividad(a.idActividad!),
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
