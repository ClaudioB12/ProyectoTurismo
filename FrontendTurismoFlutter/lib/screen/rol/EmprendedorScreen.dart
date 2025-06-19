import 'package:flutter/material.dart';

class EmprendedorScreen extends StatefulWidget {
  const EmprendedorScreen({super.key});

  @override
  State<EmprendedorScreen> createState() => _EmprendedorScreenState();
}

class _EmprendedorScreenState extends State<EmprendedorScreen> {
  int _currentIndex = 0;

  final List<Widget> _pages = [
    Center(child: Text('Mis paquetes')),
    Center(child: Text('Reservas')),
    Center(child: Text('Perfil')),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Emprendedor'),
        backgroundColor: Colors.orange,
      ),
      body: _pages[_currentIndex],
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _currentIndex,
        selectedItemColor: Colors.orange,
        unselectedItemColor: Colors.grey,
        showSelectedLabels: false,
        showUnselectedLabels: false,
        backgroundColor: Colors.white,
        items: const [
          BottomNavigationBarItem(icon: Icon(Icons.work), label: 'Mis paquetes'),
          BottomNavigationBarItem(icon: Icon(Icons.book_online), label: 'Reservas'),
          BottomNavigationBarItem(icon: Icon(Icons.person), label: 'Perfil'),
        ],
        onTap: (index) => setState(() => _currentIndex = index),
      ),
    );
  }
}
