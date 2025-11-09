package controlador;

import modelo.ModeloJuego;
import modelo.Observador;
import modelo.Personaje;
import modelo.Sonido;
import vista.VistaUI;

public class ControladorUI implements Observador {
    
    private ModeloJuego modeloJuego;
    private Personaje personaje;
    private VistaUI vistaUI;

    public ControladorUI(ModeloJuego modeloJuego, VistaUI vistaUI) {
        this.modeloJuego = modeloJuego;
        this.vistaUI = vistaUI;
        personaje = modeloJuego.getJugador();
        modeloJuego.registrarObservador(this); //registro como observador
        actualizarVida();
        actualizarPuntaje();
    }

    @Override
    public void actualizar(){
        actualizarPuntaje();
        
    }

    public void actualizarPuntaje(){
        int puntaje = modeloJuego.getPuntajeTotal();
        vistaUI.setTextoPuntaje(puntaje);
        System.out.println("Actualice el puntaje en la UI: " + puntaje);
    }

    public void actualizarVida (){ //del personaje
        vistaUI.setNivelBarra(personaje.getVida());

    }

    //Nota: el cont actua como una flag, lo que pasa es que siempre se escuchaba el sonido de la barra cargandose (camino del else). 
    // si quitas ese cont se va a escuchar el sonido de cuando se active el final boss.

    public void actualizarBatallas(){ //actualiza la barra de batallas
        int cont = 0;
        if(modeloJuego.debeActivarBoss() == true){ //se actualiza la barra 5/5 y aparece texto indicando que esta el final boss
            Sonido.getInstancia().playEfecto(6);
            vistaUI.actualizarTextoFinalBoss();
        }
        else if (cont==0){ 
                vistaUI.setBatallasRestantes(modeloJuego.getBatallasGanadas());
        } else {
                vistaUI.setBatallasRestantes(modeloJuego.getBatallasGanadas());
                Sonido.getInstancia().playEfecto(8);
        }

    }
}
