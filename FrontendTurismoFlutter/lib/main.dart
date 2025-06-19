import 'package:flutter/material.dart';
import 'package:frontendturismoflutter/screen/ActividadScreen.dart';
import 'package:frontendturismoflutter/screen/ClienteInfoScreen.dart';
import 'package:frontendturismoflutter/screen/DestinoScreen.dart';
import 'package:frontendturismoflutter/screen/HospedajeScreen.dart';
import 'package:frontendturismoflutter/screen/MenuScreen.dart';
import 'package:frontendturismoflutter/screen/PaqueteActividadesScreen.dart';
import 'package:frontendturismoflutter/screen/PaqueteDestinosScreen.dart';
import 'package:frontendturismoflutter/screen/PaqueteTuristicoScreen.dart';
import 'package:frontendturismoflutter/screen/RegisterScreen.dart';
import 'package:frontendturismoflutter/screen/ResenaScreen.dart';
import 'package:frontendturismoflutter/screen/ReservaScreen.dart';
import 'package:frontendturismoflutter/screen/RestauranteScreen.dart';
import 'package:frontendturismoflutter/screen/WelcomeScreen.dart';
import 'package:frontendturismoflutter/screen/LoginScreen.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
// Nuevas importaciones

import 'package:frontendturismoflutter/screen/rol/AdminScreen.dart';
import 'package:frontendturismoflutter/screen/rol/EmprendedorScreen.dart';
import 'package:frontendturismoflutter/screen/rol/RoleBasedMenuScreen.dart';
import 'package:frontendturismoflutter/screen/rol/UsuarioScreen.dart';
import 'package:mapbox_maps_flutter/mapbox_maps_flutter.dart';

void main() async{

  await setup();
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'App de Turismo',
      debugShowCheckedModeBanner: false,
      initialRoute: '/', // ← inicio con RoleBasedMenuScreen
      routes: {
        '/': (context) => const RoleBasedMenuScreen(), // ← ¡Aquí cambiamos!
        '/login': (context) => const LoginScreen(),
        '/register': (context) => const RegisterScreen(),
        '/menu': (context) => const MenuScreen(),
        '/cliente_info': (context) => const ClienteInfoScreen(),

        '/hospedaje': (context) => const HospedajeScreen(),
        '/actividad': (context) => const ActividadScreen(),
        '/paquete_turistico': (context) => const PaqueteTuristicoScreen(),
        '/paquete_actividades': (context) => const PaqueteActividadesScreen(),
        '/paquete_destinos': (context) => const PaqueteDestinoScreen(),
        '/restaurante': (context) => const RestauranteScreen(),
        '/resena': (context) => const ResenaScreen(),
        '/reserva': (context) => const ReservaScreen(),
        '/destino': (context) => const DestinoScreen(),

        // Rutas por rol
        '/admin': (context) => const AdminScreen(),
        '/emprendedor': (context) => const EmprendedorScreen(),
        '/usuario': (context) => const UsuarioScreen(),
      },
    );
  }
}

Future<void> setup() async {
  await dotenv.load(
    fileName: ".env",
  );
  MapboxOptions.setAccessToken(
    dotenv.env["MAPBOX_ACCESS_TOKEN"]!,
  );
}