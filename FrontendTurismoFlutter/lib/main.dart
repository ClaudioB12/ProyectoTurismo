import 'package:flutter/material.dart';
import 'package:frontendturismoflutter/screen/ClienteInfoScreen.dart';
import 'package:frontendturismoflutter/screen/MenuScreen.dart';
import 'package:frontendturismoflutter/screen/RegisterScreen.dart';
import 'package:frontendturismoflutter/screen/WelcomeScreen.dart';
import 'package:frontendturismoflutter/screen/LoginScreen.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'App de Turismo',
      debugShowCheckedModeBanner: false,
      initialRoute: '/',
      routes: {
        '/': (context) => const BienvenidoScreen(),
        '/login': (context) => const LoginScreen(),
        '/register': (context) => const RegisterScreen(), // <--- Agrega esto
        '/menu': (context) => const MenuScreen(),
        '/cliente_info': (context) => const ClienteInfoScreen(), // <--- Nueva ruta

      },
    );
  }
}
