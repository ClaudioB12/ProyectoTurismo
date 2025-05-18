import 'package:flutter/material.dart';
import 'package:frontendturismoflutter/security/JwtRequest.dart';
import 'package:frontendturismoflutter/security/auth_service.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final TextEditingController correoController = TextEditingController();
  final TextEditingController claveController = TextEditingController();

  bool isLoading = false;

  void _doLogin() async {
    setState(() {
      isLoading = true;
    });

    final authService = AuthService();
    final jwtRequest = JwtRequest(
      correo: correoController.text,
      clave: claveController.text,
    );

    final authResponse = await authService.login(jwtRequest);

    setState(() {
      isLoading = false;
    });

    if (authResponse.success) {
      if (mounted) {
        // Si el cliente no existe o no tiene datos completos, pedimos completar datos
        if (authResponse.isNewUser) {
          Navigator.pushReplacementNamed(context, '/cliente_info');
        } else {
          Navigator.pushReplacementNamed(context, '/menu');
        }
      }
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('❌ Credenciales incorrectas')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        // Imagen de fondo
        SizedBox.expand(
          child: Image.asset(
            'assets/images/capachica.png',
            fit: BoxFit.cover,
          ),
        ),

        // Capa negra transparente
        Container(
          color: Colors.black.withOpacity(0.5),
        ),

        Scaffold(
          backgroundColor: Colors.transparent,
          body: Center(
            child: SingleChildScrollView(
              padding: const EdgeInsets.all(24),
              child: Card(
                shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
                elevation: 8,
                color: Colors.white.withOpacity(0.95),
                child: Padding(
                  padding: const EdgeInsets.all(24),
                  child: Column(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      Text(
                        'Bienvenido',
                        style: TextStyle(
                          fontSize: 32,
                          color: Theme.of(context).primaryColor,
                        ),
                      ),
                      const SizedBox(height: 16),
                      TextField(
                        controller: correoController,
                        decoration: const InputDecoration(labelText: 'Correo electrónico'),
                      ),
                      const SizedBox(height: 12),
                      TextField(
                        controller: claveController,
                        decoration: const InputDecoration(labelText: 'Contraseña'),
                        obscureText: true,
                      ),
                      const SizedBox(height: 20),
                      ElevatedButton(
                        onPressed: isLoading ? null : _doLogin,
                        style: ElevatedButton.styleFrom(
                          backgroundColor: Theme.of(context).primaryColor,
                          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
                          minimumSize: const Size(double.infinity, 48),
                        ),
                        child: isLoading
                            ? const CircularProgressIndicator(color: Colors.white)
                            : const Text(
                          'Iniciar Sesión',
                          style: TextStyle(fontSize: 14, color: Colors.white),
                        ),
                      ),
                      TextButton(
                        onPressed: () {
                          Navigator.pushNamed(context, '/register');
                        },
                        child: const Text(
                          '¿No tienes cuenta? Crear una',
                          style: TextStyle(fontSize: 16, color: Colors.blueAccent),
                        ),
                      ),
                    ],
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
