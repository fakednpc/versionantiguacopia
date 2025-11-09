package database;

public class Puntaje {
    private String nombreJugador;
    private int puntos;
    private String fecha;

    public String getNombreJugador() { return nombreJugador; }
    public int getPuntos() { return puntos; }
    public String getFecha() { return fecha; }

    public void setNombreJugador(String nombreJugador) { this.nombreJugador = nombreJugador; }
    public void setPuntos(int puntos) { this.puntos = puntos; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}
