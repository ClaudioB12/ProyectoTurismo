import 'package:flutter/material.dart';
import 'package:frontendturismoflutter/model/Destino.dart';
import 'package:frontendturismoflutter/service/DestinoService.dart';
import 'package:frontendturismoflutter/utils/constants.dart';

class DestinoScreen extends StatefulWidget {
  const DestinoScreen({super.key});

  @override
  State<DestinoScreen> createState() => _DestinoScreenState();
}

class _DestinoScreenState extends State<DestinoScreen> {
  // Usa la constante en lugar del string literal
  late DestinoService _destinoService;

  List<Destino> _destinos = [];

  final _formKey = GlobalKey<FormState>();
  final TextEditingController _nombreController = TextEditingController();
  final TextEditingController _descripcionController = TextEditingController();
  final TextEditingController _ubicacionController = TextEditingController();

  int? _idEditing;
  bool _isLoading = false;

  @override
  void initState() {
    super.initState();
    _destinoService = DestinoService(AppConstants.baseUrl);
    _cargarDestinos();
  }

  void _cargarDestinos() async {
    setState(() => _isLoading = true);
    try {
      final data = await _destinoService.listarDestinos();
      setState(() {
        _destinos = data;
      });
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error al cargar destinos: $e')),
      );
    } finally {
      setState(() => _isLoading = false);
    }
  }

  void _guardarDestino() async {
    if (!_formKey.currentState!.validate()) return;

    setState(() => _isLoading = true);
    try {
      final destino = Destino(
        idDestino: _idEditing,
        nombre: _nombreController.text,
        descripcion: _descripcionController.text,
        ubicacion: _ubicacionController.text,
      );

      if (_idEditing == null) {
        await _destinoService.guardarDestino(destino);
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Destino guardado correctamente')),
        );
      } else {
        await _destinoService.editarDestino(destino);
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Destino actualizado correctamente')),
        );
      }

      _limpiarFormulario();
      _cargarDestinos();
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error al guardar destino: $e')),
      );
    } finally {
      setState(() => _isLoading = false);
    }
  }

  void _confirmarEliminar(int id) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Confirmar eliminación'),
        content: const Text('¿Estás seguro de eliminar este destino?'),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('Cancelar'),
          ),
          TextButton(
            onPressed: () {
              Navigator.pop(context);
              _eliminarDestino(id);
            },
            child: const Text('Eliminar', style: TextStyle(color: Colors.red)),
          ),
        ],
      ),
    );
  }

  void _eliminarDestino(int id) async {
    setState(() => _isLoading = true);
    try {
      await _destinoService.eliminarDestino(id);
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Destino eliminado correctamente')),
      );
      _cargarDestinos();
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error al eliminar destino: $e')),
      );
    } finally {
      setState(() => _isLoading = false);
    }
  }





  void _editarDestino(Destino destino) {
    setState(() {
      _idEditing = destino.idDestino;
      _nombreController.text = destino.nombre;
      _descripcionController.text = destino.descripcion;
      _ubicacionController.text = destino.ubicacion;
    });
  }

  void _limpiarFormulario() {
    setState(() {
      _idEditing = null;
      _nombreController.clear();
      _descripcionController.clear();
      _ubicacionController.clear();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Gestión de Destinos'),
          leading: IconButton(
          icon: const Icon(Icons.arrow_back),
      onPressed: () {
        Navigator.pushReplacementNamed(context, '/menu'); // ← cambia la ruta aquí
      },
    ),),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            Form(
              key: _formKey,
              child: Column(
                children: [
                  TextFormField(
                    controller: _nombreController,
                    decoration: const InputDecoration(labelText: 'Nombre'),
                    validator: (value) =>
                    value!.isEmpty ? 'Campo requerido' : null,
                  ),
                  TextFormField(
                    controller: _descripcionController,
                    decoration: const InputDecoration(labelText: 'Descripción'),
                    validator: (value) =>
                    value!.isEmpty ? 'Campo requerido' : null,
                  ),
                  TextFormField(
                    controller: _ubicacionController,
                    decoration: const InputDecoration(labelText: 'Ubicación'),
                    validator: (value) =>
                    value!.isEmpty ? 'Campo requerido' : null,
                  ),
                  const SizedBox(height: 10),
                  _isLoading
                      ? const CircularProgressIndicator()
                      : ElevatedButton(
                    onPressed: _guardarDestino,
                    child:
                    Text(_idEditing == null ? 'Guardar' : 'Actualizar'),
                  ),
                ],
              ),
            ),
            const Divider(height: 30),
            Expanded(
              child: _isLoading && _destinos.isEmpty
                  ? const Center(child: CircularProgressIndicator())
                  : _destinos.isEmpty
                  ? const Center(child: Text('No hay destinos registrados.'))
                  : ListView.builder(
                itemCount: _destinos.length,
                itemBuilder: (context, index) {
                  final d = _destinos[index];
                  return Card(
                    child: ListTile(
                      title: Text(d.nombre),
                      subtitle: Text('${d.descripcion}\n${d.ubicacion}'),
                      isThreeLine: true,
                      trailing: Wrap(
                        spacing: 10,
                        children: [
                          IconButton(
                            icon:
                            const Icon(Icons.edit, color: Colors.blue),
                            onPressed: () => _editarDestino(d),
                          ),
                          IconButton(
                            icon: const Icon(Icons.delete, color: Colors.red),
                            onPressed: () => _confirmarEliminar(d.idDestino!),
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
