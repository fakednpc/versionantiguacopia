package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Gestiona la conexión a la base de datos SQLite utilizando el patrón Singleton.
 * Se encarga de inicializar la base de datos y proveer un punto de acceso único
 * a la conexión.
 * 
 * Adaptado para el juego "Ecos de las Mazmorras".
 */
public class ConexionBaseDeDatos {

    private static ConexionBaseDeDatos instancia;
    private Connection conexion;

    // Nombre del archivo de base de datos
    private static final String URL = "jdbc:sqlite:rankingJuego.db";

    // Constructor privado para aplicar Singleton
    private ConexionBaseDeDatos() {
        try {
            this.conexion = DriverManager.getConnection(URL);
            System.out.println("Conexión con la base de datos SQLite establecida.");
            inicializarBaseDeDatos();
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    // Devuelve la instancia única de la conexión
    public static synchronized ConexionBaseDeDatos getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBaseDeDatos();
        }
        return instancia;
    }

    // Devuelve el objeto Connection para que lo usen los DAOs
    public Connection getConexion() {
        return this.conexion;
    }

    // Inicializa las tablas si no existen
    private void inicializarBaseDeDatos() {
        try (Statement stmt = this.conexion.createStatement()) {

             // Tabla de puntajes
            String sqlRanking = """
            CREATE TABLE IF NOT EXISTS ranking (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                personaje TEXT NOT NULL,
                puntaje INTEGER NOT NULL
            );
            """;

            stmt.execute(sqlRanking);
            System.out.println("Tabla 'puntaje' creada o ya existente.");

        } catch (SQLException e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
        }
    }

    // Método opcional para cerrar conexión
    public void cerrarConexion() {
        try {
            if (this.conexion != null && !this.conexion.isClosed()) {
                this.conexion.close();
                System.out.println("Conexión SQLite cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
