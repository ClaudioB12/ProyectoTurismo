import 'package:flutter/material.dart';
import 'package:frontendturismoflutter/security/JwtRequest.dart';
import 'package:frontendturismoflutter/security/auth_service.dart';

class RegisterScreen extends StatefulWidget {
  const RegisterScreen({super.key});

  @override
  State<RegisterScreen> createState() => _RegisterScreenState();
}

class _RegisterScreenState extends State<RegisterScreen> {
  final _formKey = GlobalKey<FormState>();
  final TextEditingController correoController = TextEditingController();
  final TextEditingController claveController = TextEditingController();
  bool isLoading = false;

  void _register() async {
    if (!_formKey.currentState!.validate()) return;

    setState(() => isLoading = true);

    final authService = AuthService();
    final jwtRequest = JwtRequest(
      correo: correoController.text.trim(),
      clave: claveController.text.trim(),
      rol: 'USUARIO', // o 'ADMIN' si es necesario
    );

    final success = await authService.register(jwtRequest);

    setState(() => isLoading = false);

    if (success) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('✅ Registro exitoso')),
        );
        Navigator.pop(context); // Vuelve al login
      }
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('❌ Error al registrar usuario')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        // Fondo
        SizedBox.expand(
          child: Image.asset(
            'assets/images/login.png',
            fit: BoxFit.cover,
          ),
        ),
        // Capa oscura translúcida
        Container(
          color: Colors.black.withOpacity(0.5),
        ),
        // Contenido principal
        Scaffold(
          backgroundColor: Colors.transparent,
          body: Center(
            child: SingleChildScrollView(
              padding: const EdgeInsets.all(24),
              child: Card(
                color: Colors.white.withOpacity(0.95),
                elevation: 8,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(20),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(24),
                  child: Form(
                    key: _formKey,
                    child: Column(
                      mainAxisSize: MainAxisSize.min,
                      children: [
                        Text(
                          'Crear Cuenta',
                          style: TextStyle(
                            fontSize: 28,
                            fontWeight: FontWeight.bold,
                            color: Theme.of(context).primaryColor,
                          ),
                        ),
                        const SizedBox(height: 24),
                        TextFormField(
                          controller: correoController,
                          keyboardType: TextInputType.emailAddress,
                          decoration: const InputDecoration(
                            labelText: 'Correo electrónico',
                            border: OutlineInputBorder(),
                          ),
                          validator: (value) {
                            if (value == null || value.isEmpty) {
                              return 'Ingrese su correo';
                            }
                            if (!RegExp(r'^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$')
                                .hasMatch(value)) {
                              return 'Ingrese un correo válido';
                            }
                            return null;
                          },
                        ),
                        const SizedBox(height: 16),
                        TextFormField(
                          controller: claveController,
                          obscureText: true,
                          decoration: const InputDecoration(
                            labelText: 'Contraseña',
                            border: OutlineInputBorder(),
                          ),
                          validator: (value) {
                            if (value == null || value.isEmpty) {
                              return 'Ingrese una contraseña';
                            }
                            if (value.length < 6) {
                              return 'Mínimo 6 caracteres';
                            }
                            return null;
                          },
                        ),
                        const SizedBox(height: 16),
                        ElevatedButton.icon(
                          icon: isLoading
                              ? const SizedBox(
                            height: 20,
                            width: 20,
                            child: CircularProgressIndicator(
                                color: Colors.white, strokeWidth: 2),
                          )
                              : const Icon(Icons.person_add,
                              color: Colors.white),
                          label: Text(
                            isLoading ? 'Registrando...' : 'Registrarse',
                            style: const TextStyle(color: Colors.white),
                          ),
                          style: ElevatedButton.styleFrom(
                            backgroundColor: Theme.of(context).primaryColor,
                            minimumSize: const Size(double.infinity, 48),
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(12),
                            ),
                          ),
                          onPressed: isLoading ? null : _register,
                        ),
                        const SizedBox(height: 16),
                        TextButton.icon(
                          onPressed: () {
                            Navigator.pushReplacementNamed(context, '/login');
                          },
                          icon: const Icon(Icons.arrow_back,
                              color: Colors.white),
                          label: const Text(
                            '¿Ya tienes una cuenta? Inicia sesión',
                            style: TextStyle(
                              color: Colors.blueAccent,
                              fontWeight: FontWeight.bold,
                              fontSize: 16,
                            ),
                          ),
                          style: TextButton.styleFrom(
                            foregroundColor: Colors.white,
                            padding: const EdgeInsets.symmetric(
                                horizontal: 16, vertical: 12),
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
              ),
            ),
          ),
        ),
      ],
    );
  }
}
