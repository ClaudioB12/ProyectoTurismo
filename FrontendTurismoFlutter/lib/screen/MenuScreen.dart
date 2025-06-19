import 'package:flutter/material.dart';
import 'package:frontendturismoflutter/screen/ActividadScreen.dart';
import 'package:frontendturismoflutter/screen/ClienteInfoScreen.dart';
import 'package:frontendturismoflutter/screen/DestinoScreen.dart';
import 'package:frontendturismoflutter/screen/HospedajeScreen.dart';
import 'package:frontendturismoflutter/screen/PaqueteActividadesScreen.dart';
import 'package:frontendturismoflutter/screen/PaqueteDestinosScreen.dart';
import 'package:frontendturismoflutter/screen/PaqueteTuristicoScreen.dart';
import 'package:frontendturismoflutter/screen/ResenaScreen.dart';
import 'package:frontendturismoflutter/screen/ReservaScreen.dart';
import 'package:frontendturismoflutter/screen/RestauranteScreen.dart';
import 'package:frontendturismoflutter/model/Cliente.dart';
import 'package:frontendturismoflutter/security/auth_service.dart';
import 'package:frontendturismoflutter/service/ClienteService.dart';
import 'package:frontendturismoflutter/utils/constants.dart';

class MenuScreen extends StatefulWidget {
  const MenuScreen({super.key});

  @override
  State<MenuScreen> createState() => _MenuScreenState();
}

class _MenuScreenState extends State<MenuScreen> {
  int _currentIndex = -1;
  int _bottomNavIndex = 0;
  Cliente? cliente;
  bool _loadingCliente = true;

  final List<Widget> _screens = [
    const HospedajeScreen(),
    const ActividadScreen(),
    const PaqueteTuristicoScreen(),
    const PaqueteActividadesScreen(),
    const PaqueteDestinoScreen(),
    const RestauranteScreen(),
    const ResenaScreen(),
    const ReservaScreen(),
    const DestinoScreen(),
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

  final List<IconData> _bottomIcons = [
    Icons.home,
    Icons.favorite,
    Icons.person,
  ];

  @override
  void initState() {
    super.initState();
    _cargarCliente();
  }

  Future<void> _cargarCliente() async {
    final authService = AuthService();
    setState(() => _loadingCliente = true);
    try {
      final data = await authService.obtenerPerfilCliente();
      setState(() {
        cliente = data;
        _loadingCliente = false;
      });
    } catch (e) {
      setState(() {
        cliente = null;
        _loadingCliente = false;
      });
    }
  }

  void mostrarFormularioEditarCliente(BuildContext context) {
    final TextEditingController nombreController =
    TextEditingController(text: cliente?.nombreCompleto ?? '');
    final TextEditingController telefonoController =
    TextEditingController(text: cliente?.telefono ?? '');
    final TextEditingController direccionController =
    TextEditingController(text: cliente?.direccion ?? '');

    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Editar Datos del Cliente'),
        content: SingleChildScrollView(
          child: Column(
            children: [
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
              const SizedBox(height: 10),
              Text(
                'Correo: ${cliente?.correo ?? ''}',
                style: const TextStyle(color: Colors.grey),
              ),
            ],
          ),
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(context).pop(),
            child: const Text('Cancelar'),
          ),
          ElevatedButton(
            onPressed: () async {
              try {
                final actualizado = Cliente(
                  idCliente: cliente?.idCliente,
                  nombreCompleto: nombreController.text.trim(),
                  correo: cliente!.correo,
                  telefono: telefonoController.text.trim(),
                  direccion: direccionController.text.trim(),
                  rol: cliente?.rol ?? 'USUARIO', // ✅ Añadido

                );
                final nuevoCliente = await ClienteService(AppConstants.baseUrl).actualizarCliente(actualizado);

                setState(() {
                  cliente = nuevoCliente;
                });
                Navigator.of(context).pop();
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text('Datos actualizados correctamente')),
                );
              } catch (e) {
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(content: Text('Error al actualizar: $e')),
                );
              }
            },
            child: const Text('Guardar'),
          ),
        ],
      ),
    );
  }

  Widget _buildDashboardCard(String title, IconData icon, Color color, String valor) {
    return Card(
      elevation: 4,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      child: Container(
        width: 150,
        padding: const EdgeInsets.all(16),
        decoration: BoxDecoration(
          color: color.withOpacity(0.1),
          borderRadius: BorderRadius.circular(12),
        ),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Icon(icon, size: 40, color: color),
            const SizedBox(height: 8),
            Text(title,
                style: TextStyle(fontWeight: FontWeight.bold, color: color)),
            const SizedBox(height: 4),
            Text(valor,
                style: TextStyle(fontSize: 18, fontWeight: FontWeight.w600)),
          ],
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    final primaryColor = Theme.of(context).primaryColor;
    final highlightColor = Colors.orangeAccent.shade700;

    return Scaffold(
      appBar: AppBar(
        title: Text(
          _currentIndex >= 0 && _currentIndex < _titles.length
              ? _titles[_currentIndex]
              : 'Dashboard Principal',
        ),
        backgroundColor: primaryColor,
      ),
      drawer: Drawer(
        child: Container(
          color: Colors.orange.shade50,
          child: Column(
            children: [
              DrawerHeader(
                decoration: BoxDecoration(color: primaryColor),
                child: Row(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const Icon(Icons.travel_explore, size: 48, color: Colors.white),
                    const SizedBox(width: 16),
                    Expanded(
                      child: _loadingCliente
                          ? const Center(
                        child: CircularProgressIndicator(color: Colors.white),
                      )
                          : Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          const Text(
                            'Menú Turismo',
                            style: TextStyle(
                              color: Colors.white,
                              fontSize: 24,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                          const SizedBox(height: 8),
                          if (cliente != null)
                            Row(
                              children: [
                                Expanded(
                                  child: Text(
                                    cliente!.nombreCompleto,
                                    style: const TextStyle(
                                        color: Colors.white, fontSize: 18),
                                    overflow: TextOverflow.ellipsis,
                                  ),
                                ),
                                IconButton(
                                  icon: const Icon(Icons.edit, color: Colors.white),
                                  onPressed: () =>
                                      mostrarFormularioEditarCliente(context),
                                )
                              ],
                            )
                          else
                            const Text('Invitado',
                                style: TextStyle(
                                    color: Colors.white70, fontSize: 18)),
                        ],
                      ),
                    ),
                  ],
                ),
              ),
              ListTile(
                leading: Icon(Icons.home,
                    color: _currentIndex == -1 ? highlightColor : primaryColor),
                title: Text(
                  'Inicio',
                  style: TextStyle(
                    color: _currentIndex == -1 ? highlightColor : Colors.black87,
                    fontWeight:
                    _currentIndex == -1 ? FontWeight.bold : FontWeight.normal,
                  ),
                ),
                onTap: () {
                  setState(() {
                    _currentIndex = -1;
                  });
                  Navigator.of(context).pop();
                },
              ),
              const Divider(),
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
                          color: isSelected ? highlightColor : Colors.black87,
                          fontWeight:
                          isSelected ? FontWeight.bold : FontWeight.normal,
                        ),
                      ),
                      selected: isSelected,
                      selectedTileColor: Colors.orange.shade100,
                      onTap: () {
                        setState(() {
                          _currentIndex = index;
                        });
                        Navigator.of(context).pop();
                      },
                    );
                  },
                ),
              ),
              const Divider(),
              ListTile(
                leading: const Icon(Icons.exit_to_app, color: Colors.black54),
                title:
                const Text('Salir', style: TextStyle(color: Colors.black54)),
                onTap: () async {
                  final confirm = await showDialog<bool>(
                    context: context,
                    builder: (context) => AlertDialog(
                      title: const Text('Confirmación'),
                      content:
                      const Text('¿Seguro que desea abandonar la sesión?'),
                      actions: [
                        TextButton(
                          onPressed: () => Navigator.of(context).pop(false),
                          child: const Text('Cancelar'),
                        ),
                        TextButton(
                          onPressed: () => Navigator.of(context).pop(true),
                          child: const Text('Salir'),
                        ),
                      ],
                    ),
                  );
                  if (confirm == true) {
                    Navigator.of(context).pushReplacementNamed('/login');
                  }
                },
              ),
              const SizedBox(height: 20),
            ],
          ),
        ),
      ),
      body: Center(
        child: _currentIndex == -1
            ? Padding(
          padding: const EdgeInsets.all(16.0),
          child: GridView.count(
            crossAxisCount: 2,
            crossAxisSpacing: 16,
            mainAxisSpacing: 16,
            children: [
              _buildDashboardCard("Destinos", Icons.place, Colors.blue, "12"),
              _buildDashboardCard("Actividades", Icons.run_circle,
                  Colors.green, "24"),
              _buildDashboardCard(
                  "Reservas", Icons.book_online, Colors.red, "8"),
              _buildDashboardCard(
                  "Paquetes", Icons.card_travel, Colors.purple, "6"),
            ],
          ),
        )
            : _screens[_currentIndex],
      ),
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _bottomNavIndex,
        onTap: (index) {
          setState(() {
            _bottomNavIndex = index;
            _currentIndex = -1;
          });

          if (index == 0) {
            Navigator.push(
              context,
              MaterialPageRoute(
                builder: (context) => const MenuScreen(),
              ),
            );
          } else if (index == 1) {
            Navigator.push(
              context,
              MaterialPageRoute(
                builder: (context) => const PaqueteTuristicoScreen(),
              ),
            );
          } else if (index == 2 && cliente != null) {
            Navigator.push(
              context,
              MaterialPageRoute(
                builder: (context) => ClienteInfoScreen(),
              ),
            );
          }
        },
        items: [
          BottomNavigationBarItem(icon: Icon(_bottomIcons[0]), label: ''),
          BottomNavigationBarItem(icon: Icon(_bottomIcons[1]), label: ''),
          BottomNavigationBarItem(icon: Icon(_bottomIcons[2]), label: ''),
        ],
        selectedItemColor: highlightColor,
        unselectedItemColor: Colors.grey,
        showSelectedLabels: false,
        showUnselectedLabels: false,
        backgroundColor: Colors.white,
      ),
    );
  }
}
