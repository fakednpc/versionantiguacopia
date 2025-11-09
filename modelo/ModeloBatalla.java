package modelo;

import java.util.ArrayList;
import java.util.List;


public class ModeloBatalla {

    // Estado de la batalla
    private boolean batallaActiva, turnoAventurero;
    private int accionElegida;
    private Entidad aventurero, enemigo;
    private List<ObservadorBatalla> observadores;
    

    public ModeloBatalla (Personaje aventurero, Entidad enemigo2){
        this.batallaActiva = true;
        this.turnoAventurero = true;
       
        this.aventurero = aventurero;
        this.enemigo = enemigo2;
        this.observadores = new ArrayList<>();
    }
    
    public void agregarObservador(ObservadorBatalla obs){
        observadores.add(obs);
    }
    
    public void quitarObservador(ObservadorBatalla obs){
        observadores.remove(obs);
    }

    private void notificarAccion(int accion, String nombre, int puntos){
        for (ObservadorBatalla obs : observadores) {
            obs.alElegirAccion(accion, nombre, puntos);

        }
    }

    public void notificarCambios(){
        for (ObservadorBatalla obs : observadores) {
            obs.alCambiarEstado();
        }
    }

    private void notificarFin(){
    List<ObservadorBatalla> copia;
        synchronized(observadores) {
        copia = new ArrayList<>(observadores);
        }
        for (ObservadorBatalla o : copia) 
        o.alTerminarBatalla();
    }

    private void notificarTurnoEnemigo(){
        for (ObservadorBatalla obs : observadores) {
            obs.alTurnoEnemigo();
        }
    }
    private void verificarEstado (){
        if (!aventurero.estaVivo() || !enemigo.estaVivo()) {
            batallaActiva = false;
            notificarFin();
        }
    }

    
    public void setAccionElegida(int accionElegida) {
        this.accionElegida = accionElegida;
        procesarTurnoJugador();
    }

    //Manejo de turnos, cambio por algo más abstracto y polimórfico
    
    public void procesarTurno(Entidad atacante, Entidad defensor, int accionElegida) {
    if (!batallaActiva) return;

    int valor = 0;

    if (accionElegida == 1) {
        if (atacante.estaDefendiendo()) 
        atacante.defender(false);
        atacante.atacar(defensor);
        valor = atacante.getAtaque();
    } else if (accionElegida == 2) {
        atacante.defender(true);
        valor = atacante.getDefensa();
    }

    verificarEstado();
    notificarCambios();
    notificarAccion(accionElegida, atacante.getNombre(), valor);
}

public void procesarTurnoJugador() {
    procesarTurno(aventurero, enemigo, accionElegida);
    turnoAventurero = false;
    notificarTurnoEnemigo();
}

public void procesarTurnoEnemigo() {
    procesarTurno(enemigo, aventurero, 1); // el enemigo siempre ataca
    turnoAventurero = true;
}

public ResultadoBatalla getResultadoBatalla (){
    //Define en qué resultó la batalla según vida de enemigo y aventurero
       
        if (!getAventurero().estaVivo()) {
            return ResultadoBatalla.DERROTA;
        } else if (!getEnemigo().estaVivo()) {
            return ResultadoBatalla.VICTORIA;
        } else {
            return ResultadoBatalla.EMPATE;
        }
}

    public boolean isBatallaActiva() {
        return batallaActiva;
    }

    public boolean isTurnoAventurero() {
        return turnoAventurero;
    }

     public Entidad getAventurero() {
        return aventurero;
    }

    public Entidad getEnemigo() {
        return enemigo;
    }

    public int getPuntajeBatalla() {
    // Ejemplo: 100 puntos por victoria, 0 por derrota o huida
    return getResultadoBatalla() == ResultadoBatalla.VICTORIA ? 100 : 0;
}

    
}