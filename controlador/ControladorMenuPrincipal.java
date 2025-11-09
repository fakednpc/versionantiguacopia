package controlador;

import database.PuntajeDAOImp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import modelo.Sonido;
import vista.VentanaPrincipal;
import vista.VistaMenuPrincipal;
import vista.VistaRankingModal;


public class ControladorMenuPrincipal {

    private VistaMenuPrincipal vista;
    private GestorNavegacion navegador;
    private VentanaPrincipal ventanaPrincipal;

    public ControladorMenuPrincipal (VistaMenuPrincipal vista, GestorNavegacion nav, VentanaPrincipal ventanaPrincipal) {
        
        this.vista = vista;
        this.navegador = nav;
        this.ventanaPrincipal = ventanaPrincipal;
        acciones(); //utilizar el metodo.

        Sonido.getInstancia().setSonido(0); //se escucha la cancion principal
        Sonido.getInstancia().play();
        Sonido.getInstancia().loop();
    }

    private void acciones() { 
        
        vista.getBotonIniciar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navegador.mostrarPantalla("seleccion");
                Sonido.getInstancia().playEfecto(2);
            }
        });


        this.vista.getBotonOpciones().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navegador.mostrarPantalla("opciones");
                Sonido.getInstancia().playEfecto(2);
           }
        });
    
        // Esto simula una ventana Modal es un poco más de código pero
        vista.getBotonRanking().addActionListener(e -> {
            Sonido.getInstancia().playEfecto(2);
            PuntajeDAOImp dao = new PuntajeDAOImp();
            List<String> top5 = dao.obtenerPuntajes(); // se obtiene en tiempo real
            VistaRankingModal ranking = new VistaRankingModal(ventanaPrincipal, top5);
            ranking.setVisible(true);
        });

        vista.getBotonSalir().addActionListener(e -> System.exit(0));
    
    }

}
