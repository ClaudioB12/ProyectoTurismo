import 'package:flutter/material.dart';

class MenuScreen extends StatefulWidget {
  const MenuScreen({super.key});

  @override
  State<MenuScreen> createState() => _MenuScreenState();
}

class _MenuScreenState extends State<MenuScreen> {
  int _currentIndex = -1; // Ninguna selección por defecto
  bool _isPanelVisible = false;

  final List<Widget> _screens = [
    Center(child: Text("Hospedaje")),
    Center(child: Text("Actividad")),
    Center(child: Text("Paquete Turístico")),
    Center(child: Text("Paquete Actividades")),
    Center(child: Text("Paquete Destinos")),
    Center(child: Text("Restaurante")),
    Center(child: Text("Reseña")),
    Center(child: Text("Reserva")),
    Center(child: Text("Destino")),
  ];

  final List<String> _titles = [
    "Hospedaje",
    "Actividad",
    "Paquete Turístico",
    "Paquete Actividades",
    "Paquete Destinos",
    "Restaurante",
    "Reseña",
    "Reserva",
    "Destino",
  ];

  final List<IconData> _icons = [
    Icons.hotel,
    Icons.directions_run,
    Icons.card_travel,
    Icons.category,
    Icons.map,
    Icons.restaurant,
    Icons.reviews,
    Icons.book_online,
    Icons.place,
  ];

  @override
  Widget build(BuildContext context) {
    final primaryColor = Theme.of(context).primaryColor;
    final highlightColor = Colors.redAccent;

    return Scaffold(
      appBar: AppBar(
        title: Text(_currentIndex >= 0 ? _titles[_currentIndex] : ''),
        backgroundColor: primaryColor,
        automaticallyImplyLeading: false,
        leading: IconButton(
          icon: Icon(_isPanelVisible ? Icons.close : Icons.menu),
          onPressed: () {
            setState(() {
              _isPanelVisible = !_isPanelVisible;
            });
          },
        ),
      ),
      body: Stack(
        children: [
          Center(
            child: _currentIndex == -1
                ? const Text(
              "Selecciona una opción del menú",
              style: TextStyle(fontSize: 20, color: Colors.black54),
            )
                : _screens[_currentIndex],
          ),
          if (_isPanelVisible)
            Align(
              alignment: Alignment.centerLeft,
              child: Container(
                width: 220,
                height: double.infinity,
                color: Colors.white.withOpacity(0.95),
                child: Column(
                  children: [
                    Expanded(
                      child: ListView.builder(
                        itemCount: _titles.length,
                        itemBuilder: (context, index) {
                          final isSelected = _currentIndex == index;
                          return ListTile(
                            leading: Icon(
                              _icons[index],
                              color: isSelected ? highlightColor : primaryColor,
                            ),
                            title: Text(
                              _titles[index],
                              style: TextStyle(
                                color:
                                isSelected ? highlightColor : Colors.black87,
                                fontWeight:
                                isSelected ? FontWeight.bold : FontWeight.normal,
                              ),
                            ),
                            selected: isSelected,
                            onTap: () {
                              setState(() {
                                _currentIndex = index;
                                _isPanelVisible = false;
                              });
                            },
                          );
                        },
                      ),
                    ),
                    const Divider(),
                    ListTile(
                      leading: const Icon(Icons.exit_to_app, color: Colors.black54),
                      title: const Text('Salir',
                          style: TextStyle(color: Colors.black54)),
                      onTap: () {
                        Navigator.of(context).pop();
                      },
                    ),
                  ],
                ),
              ),
            ),
        ],
      ),
    );
  }
}
