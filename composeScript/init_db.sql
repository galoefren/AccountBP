-- init_db.sql

-- Create schemas for microservices
CREATE SCHEMA client_schema;
CREATE SCHEMA account_schema;

-- Create tables in client_schema
CREATE TABLE client_schema.Persona (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100),
    genero CHAR(1),
    edad INT,
    identificacion VARCHAR(50) UNIQUE,
    direccion VARCHAR(255),
    telefono VARCHAR(20)
);

CREATE TABLE client_schema.Cliente (
    clienteid SERIAL PRIMARY KEY,
    persona_id INT REFERENCES client_schema.Persona(id),
    contrasena VARCHAR(100),
    estado BOOLEAN,
    UNIQUE (persona_id)
);

-- Create tables in account_schema
CREATE TABLE account_schema.Cuenta (
    id SERIAL PRIMARY KEY,
	numero_cuenta VARCHAR(50) UNIQUE,
	clienteid INT UNIQUE,
    tipo_cuenta VARCHAR(50),
    saldo_inicial DECIMAL(15, 2),
    estado BOOLEAN
);

CREATE TABLE account_schema.Movimientos (
    id SERIAL PRIMARY KEY,
    fecha TIMESTAMP,
    tipo_movimiento VARCHAR(50),
    valor DECIMAL(15, 2),
    saldo DECIMAL(15, 2),
    cuenta_id INT REFERENCES account_schema.Cuenta(id)
);