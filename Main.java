
import controlador.ControladorMenuPrincipal;
import controlador.ControladorOpciones;
import controlador.ControladorSeleccion;
import controlador.GestorNavegacion;
import javax.swing.SwingUtilities;
import modelo.Sonido;
import vista.PanelPrincipal;
import vista.VentanaPrincipal;
import vista.VistaBatalla;
import vista.VistaCreditos;
import vista.VistaFinBatalla;
import vista.VistaMenuPrincipal;
import vista.VistaOpciones;
import vista.VistaSeleccion;
import vista.VistaUI;

public class Main {
    public static void main(String[] args) {
        Sonido.getInstancia(); //Se crea la unica instancia de Sonido
        SwingUtilities.invokeLater(()-> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            GestorNavegacion navegador = new GestorNavegacion(ventana);

            // Intanciar vistas
            VistaMenuPrincipal vistaMenu = new VistaMenuPrincipal();
            VistaSeleccion vistaSeleccion = new VistaSeleccion();
            VistaOpciones vistaOpciones = new VistaOpciones();
            VistaCreditos vistaCreditos = new VistaCreditos();
            PanelPrincipal panelJuego = new PanelPrincipal();
            VistaBatalla vistaBatalla = new VistaBatalla();
            VistaUI vistaUI = new VistaUI();
            VistaFinBatalla vistaFinBatalla = new VistaFinBatalla();
            // Registrar las vistas
            navegador.registrar(VentanaPrincipal.MENU, vistaMenu);
            navegador.registrar(VentanaPrincipal.SELECCION, vistaSeleccion);
            navegador.registrar(VentanaPrincipal.OPCIONES,  vistaOpciones);
            navegador.registrar(VentanaPrincipal.CREDITOS,  vistaCreditos);
            navegador.registrar(VentanaPrincipal.JUEGO, panelJuego);
            navegador.registrar(VentanaPrincipal.BATALLA, vistaBatalla);
            navegador.registrar("UI", vistaUI);
            navegador.registrar(VentanaPrincipal.FIN, vistaFinBatalla);

            // Registrar vistas limpiables 
            navegador.registrarLimpiable(vistaSeleccion);
            navegador.registrarLimpiable(panelJuego);
            navegador.registrarLimpiable(vistaBatalla);
            navegador.registrarLimpiable(vistaUI);
            navegador.registrarLimpiable(vistaFinBatalla);   

            // Instanciar controladores
            new ControladorMenuPrincipal(vistaMenu, navegador, ventana);
            ControladorOpciones controladorOpciones = new ControladorOpciones(vistaOpciones, navegador, vistaCreditos);
            new ControladorSeleccion(vistaSeleccion, navegador, panelJuego, vistaBatalla, controladorOpciones, vistaFinBatalla);

            ventana.setVisible(true);
            navegador.mostrarPantalla(VentanaPrincipal.MENU);
        });
    
    }
}