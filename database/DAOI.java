package database;

import java.util.List;

public interface DAOI {

     /**
     * Guarda un nuevo puntaje en la base de datos.
     * @param nombre nombre del jugador
     * @param puntaje valor del puntaje obtenido
     */
    void guardarPuntaje(String nombre, String personaje, int puntaje);

    /**
     * Devuelve una lista de todos los puntajes guardados,
     * ordenados de mayor a menor.
     * @return lista de strings con formato "nombre - puntaje puntos"
     */
    List<String> obtenerPuntajes();

}