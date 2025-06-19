import 'package:flutter/material.dart';

class AdminScreen extends StatefulWidget {
  const AdminScreen({super.key});

  @override
  State<AdminScreen> createState() => _AdminScreenState();
}

class _AdminScreenState extends State<AdminScreen> {
  int _currentIndex = 0;

  final List<Widget> _pages = [
    Center(child: Text('Panel principal')),
    Center(child: Text('Gestión de usuarios')),
    Center(child: Text('Configuración')),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Administrador'),
        backgroundColor: Colors.teal,
      ),
      body: _pages[_currentIndex],
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _currentIndex,
        selectedItemColor: Colors.teal,
        unselectedItemColor: Colors.grey,
        showSelectedLabels: false,
        showUnselectedLabels: false,
        backgroundColor: Colors.white,
        items: const [
          BottomNavigationBarItem(icon: Icon(Icons.dashboard), label: 'Inicio'),
          BottomNavigationBarItem(icon: Icon(Icons.supervised_user_circle), label: 'Usuarios'),
          BottomNavigationBarItem(icon: Icon(Icons.settings), label: 'Config'),
        ],
        onTap: (index) => setState(() => _currentIndex = index),
      ),
    );
  }
}
