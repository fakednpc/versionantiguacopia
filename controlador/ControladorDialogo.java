package controlador;

import modelo.Dialogo;
import modelo.ModeloJuego;
import modelo.Sonido;
import vista.PanelPrincipal;
import vista.VentanaPrincipal;
import vista.VistaDialogo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorDialogo {
    
    private VistaDialogo vistaDialogo;
    private Dialogo modeloDialogo;
    private int indiceDialogo = 0;
    private boolean dialogoIniciado = false;
    private PanelPrincipal panelJuego;
    private GestorNavegacion navegador;
    private ModeloJuego modeloJuego;

    public ControladorDialogo(VistaDialogo vista, Dialogo modelo, ModeloJuego modeloJuego, PanelPrincipal panelJuego, GestorNavegacion navegador) {
        this.vistaDialogo = vista;
        this.modeloDialogo = modelo;
        this.modeloJuego = modeloJuego;
        this.panelJuego = panelJuego;
        this.navegador = navegador;

        // Registrar el listener una sola vez
        vista.getbotonContinuar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Se presiono el boton CONTINUAR");
                avanzarDialogo();
                Sonido.getInstancia().playEfecto(5);
            }
        });

    }

        // Inicia la secuencia de diálogo
    public void iniciarDialogo() {
        if (dialogoIniciado) return;
        dialogoIniciado = true;
        indiceDialogo = 0;

        String texto = modeloDialogo.getDialogo(indiceDialogo);
        vistaDialogo.setTexto(texto);
        vistaDialogo.setVisible(true);
        vistaDialogo.getbotonContinuar().setVisible(true);
    }


    private void avanzarDialogo() {
        indiceDialogo++;
        if (indiceDialogo < modeloDialogo.cantidadDialogos()) {
            vistaDialogo.setTexto(modeloDialogo.getDialogo(indiceDialogo));
        } else {
            finalizarDialogo();
        }
    }


    public boolean estaActivo() {
        return dialogoIniciado;
    }

    public void finalizarDialogo() {
        vistaDialogo.setVisible(false);
        vistaDialogo.getbotonContinuar().setVisible(false);
        indiceDialogo = 0;
        dialogoIniciado = false;

        panelJuego.getVistaUI().setVisible(true); //cuando finaliza el dialogo MUESTRA la UI
        modeloJuego.reanudarJuego();
        navegador.mostrarPantalla(VentanaPrincipal.JUEGO);
        panelJuego.requestFocusInWindow();

        Sonido.getInstancia().playEfecto(4);

    }

}
