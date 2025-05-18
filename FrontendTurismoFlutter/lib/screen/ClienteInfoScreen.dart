import 'package:flutter/material.dart';
import 'package:frontendturismoflutter/security/auth_service.dart';

class ClienteInfoScreen extends StatefulWidget {
  const ClienteInfoScreen({super.key});

  @override
  State<ClienteInfoScreen> createState() => _ClienteInfoScreenState();
}

class _ClienteInfoScreenState extends State<ClienteInfoScreen> {
  final TextEditingController nombreController = TextEditingController();
  final TextEditingController telefonoController = TextEditingController();
  final TextEditingController direccionController = TextEditingController();

  bool isSaving = false;

  void _submitInfo() async {
    setState(() {
      isSaving = true;
    });

    final authService = AuthService();

    final success = await authService.guardarDatosCliente(
      nombreCompleto: nombreController.text.trim(),
      telefono: telefonoController.text.trim(),
      direccion: direccionController.text.trim(),
    );

    setState(() {
      isSaving = false;
    });

    if (success) {
      Navigator.pushReplacementNamed(context, '/menu');
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Error al guardar los datos')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Completa tus datos')),
      body: Padding(
        padding: const EdgeInsets.all(24),
        child: ListView(
          children: [
            TextField(
              controller: nombreController,
              decoration: const InputDecoration(labelText: 'Nombre completo'),
            ),
            TextField(
              controller: telefonoController,
              decoration: const InputDecoration(labelText: 'Teléfono'),
            ),
            TextField(
              controller: direccionController,
              decoration: const InputDecoration(labelText: 'Dirección'),
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: isSaving ? null : _submitInfo,
              child: isSaving
                  ? const CircularProgressIndicator(color: Colors.white)
                  : const Text('Guardar y continuar'),
            ),
          ],
        ),
      ),
    );
  }
}
