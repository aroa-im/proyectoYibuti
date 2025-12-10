package io;

import java.sql.Connection;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.main;

public class CrearBBDD {
	private Connection conexionBD;
	private Logger logger;

	public CrearBBDD() {
		this.conexionBD = main.getConexionBD();
		this.logger = main.getLogger();

		if (conexionBD == null) {
			System.err.println("La conexión a la base de datos no está disponible.");
			return;
		}

		String sql = """
					CREATE TABLE IF NOT EXISTS "Usuario" (
					          "dni" TEXT NOT NULL UNIQUE,
					          "nombre" TEXT,
					          "email" TEXT,
					          "contrasena" TEXT,
					          PRIMARY KEY("dni")
					 );
	
					CREATE TABLE IF NOT EXISTS "Admin" (
					    "dni" TEXT,
					    PRIMARY KEY("dni"),
					    FOREIGN KEY("dni") REFERENCES "Usuario"("dni") ON DELETE CASCADE
					);

					CREATE TABLE IF NOT EXISTS "Cliente" (
					    "dni" TEXT,
					    "amonestaciones" INTEGER,
					    PRIMARY KEY("dni"),
					    FOREIGN KEY("dni") REFERENCES "Usuario"("dni") ON DELETE CASCADE
					);

					CREATE TABLE IF NOT EXISTS "LogAccion" (
					    "id" INTEGER PRIMARY KEY AUTOINCREMENT,
					    "fecha" TEXT NOT NULL,
					    "descripcion" TEXT NOT NULL,
					    "dni_admin" TEXT NOT NULL,
					    FOREIGN KEY("dni_admin") REFERENCES "Admin"("dni") ON DELETE CASCADE
					);

					CREATE TABLE IF NOT EXISTS "Producto" (
					    "id" INTEGER PRIMARY KEY AUTOINCREMENT,
					    "titulo" TEXT NOT NULL,
					    "sinopsis" TEXT,
					    "precio" REAL NOT NULL,
					    "rating" INTEGER NOT NULL DEFAULT 0,
					    "url_foto" TEXT,
					    "tipo_producto" TEXT NOT NULL CHECK(tipo_producto IN ('PELICULA', 'VIDEOJUEGO')),
					
					    -- CAMPOS PARA PELÍCULA
					    "tipo_pelicula" TEXT,
					    "genero_pelicula" TEXT,
					    "director" TEXT,
					    "duracion" INTEGER,
					
					    -- CAMPOS PARA VIDEOJUEGO
					    "genero_videojuego" TEXT,
					    "tipo_consola" TEXT,
					    "autor_videojuego" TEXT
					);

					CREATE TABLE IF NOT EXISTS "Pelicula" (
					    "id" INTEGER PRIMARY KEY,
					    "id_tipo_pelicula" INTEGER NOT NULL,
					    "id_genero_pelicula" INTEGER NOT NULL,
					    "director" TEXT NOT NULL,
					    "duracion" INTEGER NOT NULL,
					    FOREIGN KEY("id") REFERENCES "Producto"("id") ON DELETE CASCADE,
					    FOREIGN KEY("id_tipo_pelicula") REFERENCES "TipoPelicula"("id"),
					    FOREIGN KEY("id_genero_pelicula") REFERENCES "GeneroPelicula"("id")
					);

					CREATE TABLE IF NOT EXISTS "TipoPelicula" (
					    "id" INTEGER PRIMARY KEY AUTOINCREMENT,
					    "nombre" TEXT UNIQUE

					);
					CREATE TABLE IF NOT EXISTS "GeneroPelicula" (
					    "id" INTEGER PRIMARY KEY AUTOINCREMENT,
					    "nombre" TEXT NOT NULL UNIQUE
					);

					CREATE TABLE IF NOT EXISTS "Videojuego" (
					    "id" INTEGER PRIMARY KEY,
					    "id_genero_videojuego" INTEGER NOT NULL,
					    "id_tipo_consola" INTEGER NOT NULL,
					    "autor" TEXT NOT NULL,
					    FOREIGN KEY("id") REFERENCES "Producto"("id") ON DELETE CASCADE,
					    FOREIGN KEY("id_genero_videojuego") REFERENCES "GeneroVideojuego"("id"),
					    FOREIGN KEY("id_tipo_consola") REFERENCES "TipoConsola"("id")
					);

					CREATE TABLE IF NOT EXISTS "TipoConsola" (
					    "id" INTEGER PRIMARY KEY AUTOINCREMENT,
					    "nombre" TEXT NOT NULL
					);

					CREATE TABLE IF NOT EXISTS "GeneroVideojuego" (
					    "id" INTEGER PRIMARY KEY AUTOINCREMENT,
					    "nombre" TEXT NOT NULL UNIQUE
					);

					CREATE TABLE IF NOT EXISTS "Alquiler" (
					    "id" INTEGER PRIMARY KEY AUTOINCREMENT,
					    "fecha_inicio" TEXT NOT NULL,
					    "fecha_fin" TEXT NOT NULL,
					    "devuelto" INTEGER DEFAULT 0 NOT NULL CHECK(devuelto IN (0, 1)),
					    "id_producto" INTEGER NOT NULL,
					    "dni_cliente" TEXT NOT NULL,
					    FOREIGN KEY("dni_cliente") REFERENCES "Cliente"("dni") ON DELETE CASCADE,
					    FOREIGN KEY("id_producto") REFERENCES "Producto"("id") ON DELETE CASCADE
					);

					CREATE TABLE IF NOT EXISTS "Review" (
					    "id" INTEGER PRIMARY KEY AUTOINCREMENT,
					    "comentario" TEXT,
					    "rating" INTEGER NOT NULL CHECK(rating >= 0 AND rating <= 10),
					    "id_producto" INTEGER NOT NULL,
					    "dni_cliente" TEXT NOT NULL,
					    "fecha_creacion" TEXT NOT NULL,
					    FOREIGN KEY("dni_cliente") REFERENCES "Cliente"("dni") ON DELETE CASCADE,
					    FOREIGN KEY("id_producto") REFERENCES "Producto"("id") ON DELETE CASCADE
					);

					INSERT INTO "Usuario" ("dni","nombre","email","contrasena")
							      VALUES
							          ('00000000A','cliente','cliente@cliente.com','cliente'),
							          ('11111111B','admin','admin@admin.com','admin');
							INSERT  INTO "Admin" ("dni") VALUES ('11111111B');
							      INSERT INTO "Cliente" ("dni","amonestaciones") VALUES ('00000000A',0);
					   """;

		// Ejecutar las consultas SQL
		String[] scripts = sql.split(";");
		try (Statement stmt = conexionBD.createStatement()) {
			for (String script : scripts) {
				if (!script.trim().isEmpty()) {
					stmt.execute(script.trim());
				}
			}
			System.out.println("Tablas creadas exitosamente.");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error al crear las tablas: ", e);
		}
	}
}
