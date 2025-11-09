package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PuntajeDAOImp implements DAOI{

    private Connection conexion;

    public PuntajeDAOImp() {
        conexion = ConexionBaseDeDatos.getInstancia().getConexion();
    }

    // Guardar puntaje con nombre y personaje
    @Override
    public void guardarPuntaje(String nombre, String personaje, int puntaje) {
        String sql = "INSERT INTO ranking (nombre, personaje, puntaje) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, personaje);
            pstmt.setInt(3, puntaje);
            pstmt.executeUpdate();
            System.out.println("Puntaje guardado: " + nombre + " - " + personaje + " - " + puntaje);
        } catch (SQLException e) {
            System.err.println("Error al guardar puntaje: " + e.getMessage());
        }
    }
    //Solo guarda el top 5
    @Override
    public List<String> obtenerPuntajes() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT nombre, personaje, puntaje FROM ranking ORDER BY puntaje DESC LIMIT 5";

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String linea = rs.getString("nombre") + " (" + rs.getString("personaje") + ") - " + rs.getInt("puntaje") + " puntos";
                lista.add(linea);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener puntajes: " + e.getMessage());
        }

        return lista;
    }

}
