-- Tabla Usuario
CREATE TABLE usuario (
                         id_usuario BIGINT PRIMARY KEY AUTO_INCREMENT,
                         correo VARCHAR(255) NOT NULL UNIQUE,
                         clave VARCHAR(255) NOT NULL,
                         rol VARCHAR(50)
);

-- Tabla Cliente
CREATE TABLE cliente (
                         id_cliente BIGINT PRIMARY KEY AUTO_INCREMENT,
                         nombre_completo VARCHAR(255),
                         correo VARCHAR(255),
                         telefono VARCHAR(50),
                         direccion VARCHAR(255),
                         fotoPerfilUrl VARCHAR(255)
);

-- Tabla Destino
CREATE TABLE destino (
                         id_destino BIGINT PRIMARY KEY AUTO_INCREMENT,
                         nombre VARCHAR(255),
                         descripcion TEXT,
                         ubicacion VARCHAR(255)
);

-- Tabla Actividad
CREATE TABLE actividad (
                           id_actividad BIGINT PRIMARY KEY AUTO_INCREMENT,
                           nombre VARCHAR(255),
                           descripcion TEXT,
                           precio DOUBLE,
                           id_destino BIGINT,
                           FOREIGN KEY (id_destino) REFERENCES destino(id_destino)
);

-- Tabla Hospedaje
CREATE TABLE hospedaje (
                           id_hospedaje BIGINT PRIMARY KEY AUTO_INCREMENT,
                           nombre VARCHAR(255),
                           descripcion TEXT,
                           precio_por_noche DOUBLE,
                           id_destino BIGINT,
                           FOREIGN KEY (id_destino) REFERENCES destino(id_destino)
);

-- Tabla PaqueteTuristico
CREATE TABLE paquete_turistico (
                                   id_paquete BIGINT PRIMARY KEY AUTO_INCREMENT,
                                   nombre VARCHAR(255),
                                   descripcion TEXT,
                                   precio_total DOUBLE,
                                   id_destino BIGINT,
                                   FOREIGN KEY (id_destino) REFERENCES destino(id_destino)
);

-- Tabla Intermedia: paquete_actividad
CREATE TABLE paquete_actividad (
                                   id_paquete BIGINT,
                                   id_actividad BIGINT,
                                   PRIMARY KEY (id_paquete, id_actividad),
                                   FOREIGN KEY (id_paquete) REFERENCES paquete_turistico(id_paquete),
                                   FOREIGN KEY (id_actividad) REFERENCES actividad(id_actividad)
);

-- Tabla Reserva
CREATE TABLE reserva (
                         id_reserva BIGINT PRIMARY KEY AUTO_INCREMENT,
                         fecha_reserva DATE,
                         fecha_inicio DATE,
                         fecha_fin DATE,
                         numero_personas INT,
                         id_cliente BIGINT,
                         id_paquete BIGINT,
                         FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
                         FOREIGN KEY (id_paquete) REFERENCES paquete_turistico(id_paquete)
);

-- Tabla Pago
CREATE TABLE pago (
                      id_pago BIGINT PRIMARY KEY AUTO_INCREMENT,
                      monto DOUBLE,
                      metodo_pago VARCHAR(50),
                      fecha_pago TIMESTAMP,
                      id_reserva BIGINT,
                      FOREIGN KEY (id_reserva) REFERENCES reserva(id_reserva)
);

-- Tabla Inventario
CREATE TABLE inventario (
                            id_inventario BIGINT PRIMARY KEY AUTO_INCREMENT,
                            nombre_item VARCHAR(255),
                            cantidad_disponible INT,
                            id_destino BIGINT,
                            FOREIGN KEY (id_destino) REFERENCES destino(id_destino)
);

-- Tabla Resena
CREATE TABLE resena (
                        id_resena BIGINT PRIMARY KEY AUTO_INCREMENT,
                        comentario TEXT,
                        calificacion INT,
                        id_cliente BIGINT,
                        id_destino BIGINT,
                        FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
                        FOREIGN KEY (id_destino) REFERENCES destino(id_destino)
);
