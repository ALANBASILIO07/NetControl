CREATE DATABASE IF NOT EXISTS netcontrol;
USE netcontrol;

-- Crear tabla sucursales
CREATE TABLE sucursales (
  Nombre VARCHAR(100) NOT NULL,
  Gerente VARCHAR(100),
  Direccion VARCHAR(255),
  PRIMARY KEY (Nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Crear tabla torres
CREATE TABLE torres (
  NoControl INT NOT NULL AUTO_INCREMENT,
  Zona VARCHAR(100),
  Direccion VARCHAR(255),
  Etiqueta VARCHAR(50),
  NombreSucursal VARCHAR(100),
  PRIMARY KEY (NoControl),
  KEY NombreSucursal (NombreSucursal),
  CONSTRAINT torres_ibfk_1 FOREIGN KEY (NombreSucursal) REFERENCES sucursales (Nombre) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Crear tabla antenas
CREATE TABLE antenas (
  NoControl VARCHAR(50) NOT NULL,
  Categoria VARCHAR(50),
  Frecuencia INT,
  Modelo VARCHAR(100),
  Titular VARCHAR(100),
  NoControlTorre INT,
  PRIMARY KEY (NoControl),
  KEY antenas_ibfk_1 (NoControlTorre),
  CONSTRAINT antenas_ibfk_1 FOREIGN KEY (NoControlTorre) REFERENCES torres (NoControl),
  CONSTRAINT chk_categoria CHECK (Categoria IN ('Particular', 'Enrutamiento', 'Interconexión'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Crear tabla clientes
CREATE TABLE clientes (
  ID INT NOT NULL AUTO_INCREMENT,
  Nombre VARCHAR(100),
  IP VARCHAR(15),
  Vigencia DATE,
  Domicilio VARCHAR(255),
  Status VARCHAR(50) DEFAULT 'Pagado',
  NombreSucursal VARCHAR(100),
  NoControlTorre INT,
  PRIMARY KEY (ID),
  KEY NombreSucursal (NombreSucursal),
  KEY clientes_ibfk_2 (NoControlTorre),
  CONSTRAINT clientes_ibfk_1 FOREIGN KEY (NombreSucursal) REFERENCES sucursales (Nombre) ON DELETE CASCADE,
  CONSTRAINT clientes_ibfk_2 FOREIGN KEY (NoControlTorre) REFERENCES torres (NoControl),
  CONSTRAINT chk_status CHECK (Status IN ('Pagado', 'Con Adeudo', 'Suspendido'))
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Crear tabla routers
CREATE TABLE routers (
  ID INT NOT NULL AUTO_INCREMENT,
  IPCliente VARCHAR(15) NOT NULL,
  Titular VARCHAR(100),
  Modelo VARCHAR(50),
  NoControlAntena VARCHAR(50),
  PRIMARY KEY (ID),
  KEY NoControlAntena (NoControlAntena),
  CONSTRAINT routers_ibfk_1 FOREIGN KEY (NoControlAntena) REFERENCES antenas (NoControl) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Crear tabla pagos
CREATE TABLE pagos (
  NoControl INT NOT NULL AUTO_INCREMENT,
  Fecha DATE,
  Importe DECIMAL(10,2),
  MesAplicado VARCHAR(20),
  Tipo VARCHAR(50),
  ClienteID INT,
  PRIMARY KEY (NoControl),
  KEY ClienteID (ClienteID),
  CONSTRAINT pagos_ibfk_1 FOREIGN KEY (ClienteID) REFERENCES clientes (ID) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Crear tabla usuarios
CREATE TABLE usuarios (
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  bdpass VARCHAR(255) NOT NULL DEFAULT '100%Freestyle',
  PRIMARY KEY (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
