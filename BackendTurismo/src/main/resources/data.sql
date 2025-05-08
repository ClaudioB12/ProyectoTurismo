-- Inserción de datos en la tabla Cliente
INSERT INTO cliente (nombres, apellidos, dni, telefono, correo, direccion, fecha_registro)
VALUES
    ('José', 'Quispe', '74185296', '951234567', 'jose.quispe@capachica.pe', 'Comunidad Llachón', CURRENT_TIMESTAMP),
    ('Ana', 'Cruz', '85296374', '984563214', 'ana.cruz@capachica.pe', 'Isla Tikonata', CURRENT_TIMESTAMP);

-- Inserción de datos en la tabla Destino
INSERT INTO destino (nombre, descripcion, ubicacion, imagen_url)
VALUES
    ('Llachón', 'Hermosa comunidad quechua a orillas del lago Titicaca, conocida por su turismo vivencial.', 'Capachica, Puno', 'llachon.jpg'),
    ('Isla Tikonata', 'Isla en el lago Titicaca ideal para caminatas, cultura ancestral y paisajes únicos.', 'Capachica, Puno', 'tikonata.jpg');

-- Inserción de datos en la tabla Hospedaje
INSERT INTO hospedaje (nombre, descripcion, direccion, telefono, precio_por_noche, imagen_url, destino_id)
VALUES
    ('Hospedaje Llachón', 'Hospedaje familiar con vista al lago Titicaca y comida típica.', 'Comunidad Llachón, Capachica', '951234567', 60.00, 'hospedaje_llachon.jpg', 1),
    ('Alojamiento Tikonata', 'Alojamiento rural con actividades culturales en la isla.', 'Isla Tikonata, Capachica', '984563214', 80.00, 'alojamiento_tikonata.jpg', 2);

-- Inserción de datos en la tabla Actividad
INSERT INTO actividad (nombre, descripcion, duracion, precio)
VALUES
    ('Pesca tradicional en Llachón', 'Actividad guiada para conocer técnicas de pesca en el lago.', 180, 25.00),
    ('Caminata por Tikonata', 'Recorrido guiado por la isla con explicación de su historia y cultura.', 120, 20.00);

-- Inserción de datos en la tabla PaqueteTuristico
INSERT INTO paquete_turistico (nombre, descripcion, precio, duracion_dias)
VALUES
    ('Experiencia Vivencial en Llachón', 'Incluye hospedaje, pesca tradicional y alimentación.', 150.00, 2),
    ('Aventura Cultural en Tikonata', 'Incluye hospedaje, caminata cultural y alimentación.', 180.00, 2);

-- Relacionando Paquetes con Actividades
INSERT INTO paquete_actividades (paquete_id, actividad_id)
VALUES
    (1, 1),  -- Paquete Vivencial Llachón con Pesca tradicional
    (2, 2);  -- Paquete Tikonata con Caminata cultural

-- Relacionando Paquetes con Destinos
INSERT INTO paquete_destinos (paquete_id, destino_id)
VALUES
    (1, 1),  -- Paquete Vivencial Llachón con Destino Llachón
    (2, 2);  -- Paquete Tikonata con Destino Isla Tikonata

-- Inserción de datos en la tabla Restaurante
INSERT INTO restaurante (nombre, direccion, telefono, descripcion, destino_id)
VALUES
    ('Comedor Llachón', 'Sector central, Comunidad Llachón', '951234567', 'Ofrece trucha fresca y platos típicos andinos.', 1),
    ('Cocina Tikonata', 'Zona norte, Isla Tikonata', '984563214', 'Comida local con insumos cultivados en la isla.', 2);

-- Inserción de datos en la tabla Reseña
INSERT INTO resena (comentario, calificacion, fecha, cliente_id, paquete_turistico_id)
VALUES
    ('Una experiencia única, muy acogedores.', 5, CURRENT_DATE, 1, 1),
    ('La caminata fue increíble, vistas inolvidables.', 5, CURRENT_DATE, 2, 2);

-- Inserción de datos en la tabla Reserva
INSERT INTO reserva (fecha_reserva, fecha_inicio, fecha_fin, cantidad_personas, estado, cliente_id, paquete_id, usuario_id)
VALUES
    (CURRENT_DATE, CURRENT_DATE, '2025-05-10', 2, 'CONFIRMADA', 1, 1, 1),
    (CURRENT_DATE, '2025-06-05', '2025-06-07', 1, 'PENDIENTE', 2, 2, 2);

-- Inserción de datos en la tabla CheckIn
INSERT INTO check_in (cliente_id, fecha_check_in, numero_habitacion, observaciones)
VALUES
    (1, CURRENT_TIMESTAMP, 'A1', 'Habitación con vista al lago'),
    (2, CURRENT_TIMESTAMP, 'B2', 'Alojamiento cerca del muelle');

-- Inserción de datos en la tabla CheckOut
INSERT INTO check_out (cliente_id, fecha_check_out, numero_habitacion, monto_pago, comentarios)
VALUES
    (1, CURRENT_TIMESTAMP, 'A1', 60.00, 'Excelente atención y paisaje.'),
    (2, CURRENT_TIMESTAMP, 'B2', 80.00, 'Muy buena experiencia cultural.');
