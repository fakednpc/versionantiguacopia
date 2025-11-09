package controlador;

import vista.VentanaPrincipal;
import vista.VistaCreditos;
import vista.VistaOpciones;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import modelo.Sonido;
import modelo.ModeloJuego;
import vista.PanelPrincipal;


public class ControladorOpciones {

    private VistaOpciones vista;
    private GestorNavegacion navegador;
    private VistaCreditos vistaCreditos;
    private String pantallaAnterior = "menu"; //esta variable sirve para saber de donde se abrio la vistaOpciones.
    private ModeloJuego modeloJuego;
    private PanelPrincipal panelPrincipal;

    public ControladorOpciones(VistaOpciones vista, GestorNavegacion nav, VistaCreditos vistaCreditos) {
        this.vista = vista;
        this.navegador = nav;
        this.vistaCreditos = vistaCreditos;
        acciones();
    }

    private void acciones () {

        this.vista.getSubirMusica().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Sonido.getInstancia().subirVolumen(); 
                vista.setVolumen(Sonido.getInstancia().getVolumenActual());
                System.out.println("Volumen actual: " + Sonido.getInstancia().getVolumenActual());
                Sonido.getInstancia().playEfecto(2);

            }
        });

        this.vista.getBajarMusica().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Sonido.getInstancia().bajarVolumen();
                vista.setVolumen(Sonido.getInstancia().getVolumenActual());
                System.out.println("Volumen actual: " + Sonido.getInstancia().getVolumenActual());
                Sonido.getInstancia().playEfecto(2);
            }
        });


        this.vista.getBajarEfecto().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Sonido.getInstancia().bajarVolumenEfecto();
                vista.setVolumenEfecto(Sonido.getInstancia().getVolumenEfectoActual());
                System.out.println("Volumen Efecto: " + Sonido.getInstancia().getVolumenEfectoActual());
                Sonido.getInstancia().playEfecto(2);
            }
        });
        

        this.vista.getSubireEfecto().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Sonido.getInstancia().subirVolumenEfecto();
                vista.setVolumenEfecto(Sonido.getInstancia().getVolumenEfectoActual());
                
                System.out.println("Volumen Efecto: " + Sonido.getInstancia().getVolumenEfectoActual());
                Sonido.getInstancia().playEfecto(2);
            }
        });



        this.vista.getBotonComoJugar().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Sonido.getInstancia().playEfecto(2);
                JOptionPane.showMessageDialog(vista, "Tu objetivo es explorar las distintas mazmorras, eliminar monstruos y derrotar al jefe final. \n" + "Controles: Usa las flechas para moverte.", "¿CÓMO JUGAR?", JOptionPane.INFORMATION_MESSAGE); 
            }
        });
        
        this.vista.getBotonCreditos().addActionListener(e -> navegador.mostrarPantalla("creditos"));
        this.vistaCreditos.getBotonVolver().addActionListener(e -> navegador.mostrarPantalla("opciones"));
        
        this.vista.getBotonVolver().addActionListener(e -> {
            if (pantallaAnterior.equals(VentanaPrincipal.JUEGO)) {
                modeloJuego.reanudarJuego();
            }
            navegador.mostrarPantalla(pantallaAnterior);

            SwingUtilities.invokeLater(() -> { 
                panelPrincipal.setFocusable(true);
                panelPrincipal.requestFocusInWindow();
            });

        });
    }

    public void setPanelJuego(PanelPrincipal panelJuego) {
        this.panelPrincipal = panelJuego;
    }

    public void setModeloJuego(ModeloJuego modeloJuego) {
        this.modeloJuego = modeloJuego;
    }

    public void setPantallaAnterior(String pantalla) { // Seteo asi cuando quiera saber si se abrio desde el juego solo modifico mi variable pantallaAnterior
        this.pantallaAnterior = pantalla;
    }

    
}
