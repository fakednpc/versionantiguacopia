package modelo;
//se añade para desacoplar la lógica de fin de batalla de ControladorBatalla, que rompia todo al querer detener hilo
public interface EventoFinBatalla {
    void manejarFinBatalla(ResultadoBatalla resultado, Entidad enemigo);
}
