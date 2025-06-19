import 'dart:io';

import 'package:flutter/material.dart';
import 'package:frontendturismoflutter/security/auth_service.dart';
import 'package:image_picker/image_picker.dart';

class ClienteInfoScreen extends StatefulWidget {
  const ClienteInfoScreen({super.key});

  @override
  State<ClienteInfoScreen> createState() => _ClienteInfoScreenState();
}

class _ClienteInfoScreenState extends State<ClienteInfoScreen> {
  final TextEditingController nombreController = TextEditingController();
  final TextEditingController telefonoController = TextEditingController();
  final TextEditingController direccionController = TextEditingController();

  // Imagen seleccionada
  final ImagePicker _picker = ImagePicker();
  XFile? _imageFile;

  bool isSaving = false;

  Future<void> _seleccionarFoto() async {
    final XFile? pickedFile =
    await _picker.pickImage(source: ImageSource.gallery, imageQuality: 85);
    if (pickedFile != null) {
      setState(() {
        _imageFile = pickedFile;
      });
    }
  }

  Future<void> _submitInfo() async {
    if (nombreController.text.trim().isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Por favor, ingresa tu nombre')),
      );
      return;
    }

    setState(() {
      isSaving = true;
    });

    final authService = AuthService();

    // Aquí puedes enviar la imagen si tu backend lo soporta
    // Por ejemplo, authService.guardarDatosCliente con imagen
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
            GestureDetector(
              onTap: _seleccionarFoto,
              child: CircleAvatar(
                radius: 125,
                backgroundColor: Colors.grey[300],
                backgroundImage: _imageFile != null
                    ? FileImage(File(_imageFile!.path))
                    : null,
                child: _imageFile == null
                    ? const Icon(Icons.camera_alt,
                    size: 40, color: Colors.white)
                    : null,
              ),
            ),
            const SizedBox(height: 20),
            TextField(
              controller: nombreController,
              decoration: const InputDecoration(labelText: 'Nombre completo'),
            ),
            TextField(
              controller: telefonoController,
              decoration: const InputDecoration(labelText: 'Teléfono'),
              keyboardType: TextInputType.phone,
            ),
            TextField(
              controller: direccionController,
              decoration: const InputDecoration(labelText: 'Dirección'),
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: isSaving ? null : _submitInfo,
              child: isSaving
                  ? const SizedBox(
                width: 24,
                height: 24,
                child: CircularProgressIndicator(
                  color: Colors.white,
                  strokeWidth: 3,
                ),
              )
                  : const Text('Guardar y continuar'),
            ),
          ],
        ),
      ),
    );
  }
}
