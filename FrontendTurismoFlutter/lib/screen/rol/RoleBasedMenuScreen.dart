import 'package:flutter/material.dart';
import 'package:frontendturismoflutter/screen/LoginScreen.dart';
import 'package:frontendturismoflutter/screen/rol/AdminScreen.dart';
import 'package:frontendturismoflutter/screen/rol/EmprendedorScreen.dart';
import 'package:frontendturismoflutter/screen/rol/UsuarioScreen.dart';
import 'package:frontendturismoflutter/security/auth_service.dart';


class RoleBasedMenuScreen extends StatefulWidget {
  const RoleBasedMenuScreen({super.key});

  @override
  State<RoleBasedMenuScreen> createState() => _RoleBasedMenuScreenState();
}

class _RoleBasedMenuScreenState extends State<RoleBasedMenuScreen> {
  final AuthService _authService = AuthService();
  bool _loading = true;
  Widget? _targetScreen;

  @override
  void initState() {
    super.initState();
    verificarRol();
  }

  Future<void> verificarRol() async {
    final rol = await _authService.obtenerRolDesdeToken();

    setState(() {
      if (rol == null) {
        _targetScreen = const LoginScreen(); // o Pantalla de error
      } else if (rol == 'ADMIN') {
        _targetScreen = const AdminScreen();
      } else if (rol == 'EMPRENDEDOR') {
        _targetScreen = const EmprendedorScreen();
      } else {
        _targetScreen = const UsuarioScreen();
      }
      _loading = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    if (_loading) {
      return const Scaffold(
        body: Center(child: CircularProgressIndicator()),
      );
    }
    return _targetScreen!;
  }
}
