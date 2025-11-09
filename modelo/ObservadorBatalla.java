package modelo;

public interface ObservadorBatalla {
    
    void alTerminarBatalla();
    void alCambiarEstado();
    void alElegirAccion(int accion, String nombre, int puntos);
    void alTurnoEnemigo();
}
