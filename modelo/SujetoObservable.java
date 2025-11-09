package modelo;

public interface SujetoObservable {

    void registrarObservador(Observador observador);
    void removerObservador(Observador observador);
    void notificarObservadores();
}
