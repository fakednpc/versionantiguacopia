package controlador;


import database.PuntajeDAOImp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.Entidad;
import modelo.EventoFinBatalla;
import modelo.ModeloBatalla;
import modelo.ModeloJuego;
import modelo.ObservadorBatalla;
import modelo.ResultadoBatalla;
import vista.PanelPrincipal;
import vista.VentanaPrincipal;
import vista.VistaBatalla;

public class ControladorBatalla implements ObservadorBatalla {
    
    private VistaBatalla vista;
    private ModeloBatalla modelo;
    private ModeloJuego modeloJuego;
    private PanelPrincipal panelJuego;
    private GestorNavegacion navegador;
    private ControladorUI controladorUI;
    private ControladorInventario controladorInventario;
    private PuntajeDAOImp puntaje;


    private EventoFinBatalla eventoFinBatalla;
    // listeners guardados para poder removerlos en limpiar()
    private final ActionListener atacarListener;
    private final ActionListener defenderListener;
    private final ActionListener huirListener;
    private final ActionListener inventarioListener;

    public ControladorBatalla (VistaBatalla vista, ModeloBatalla modelo, ModeloJuego modeloJuego, PanelPrincipal panelJuego, GestorNavegacion navegador, ControladorUI controladorUI, EventoFinBatalla eventoFinBatalla, ControladorInventario controladorInventario){
        this.vista = vista;
        this.modelo = modelo;
        this.modeloJuego = modeloJuego;
        this.panelJuego = panelJuego;
        this.navegador = navegador;
        this.controladorUI = controladorUI;
        this.controladorInventario = controladorInventario;
        this.eventoFinBatalla = eventoFinBatalla;
        
         // listeners como campos para poder removerlos después
        this.atacarListener = (ActionEvent e) -> modelo.setAccionElegida(1); 
        this.defenderListener = (ActionEvent e) -> modelo.setAccionElegida(2);
        this.huirListener = (ActionEvent e) -> {
            controladorInventario.cerrarInventario();
            limpiar();
                controladorUI.actualizarVida();
                controladorUI.actualizarBatallas();
                modeloJuego.sumarPuntaje(-100);
                System.out.println("Huyo de la batalla: Se resto -100 puntos!!!");
                modeloJuego.reanudarJuego();
                navegador.mostrarPantalla(VentanaPrincipal.JUEGO);
                controladorInventario.reubicarInventarioEn(panelJuego);
                panelJuego.requestFocusInWindow();
        };

        System.out.println("Registrando botón inventario...");
        System.out.println("Vista: " + vista);
        System.out.println("Botón: " + vista.getBotonInventario());
        System.out.println("ControladorInventario: " + controladorInventario);


        this.inventarioListener = (ActionEvent e) ->{
            System.out.println("Botón inventario presionado");
            controladorInventario.alternarInventario();
            panelJuego.requestFocusInWindow();
        };

        modelo.agregarObservador(this);

        // añadir listeners a la vista
        this.vista.getBotonAtacar().addActionListener(atacarListener);
        this.vista.getBotonDefender().addActionListener(defenderListener);
        this.vista.getBotonHuir().addActionListener(huirListener);
        this.vista.getBotonInventario().addActionListener(inventarioListener);

        vista.actualizarEstado();
        vista.setVistaInventario(controladorInventario.getVistaInventario());
        vista.setFocusable(false);
        vista.requestFocusInWindow();
    }

    private void limpiar(){
        modelo.quitarObservador(this); 
        vista.getBotonAtacar().removeActionListener(atacarListener); 
        vista.getBotonDefender().removeActionListener(defenderListener); 
        vista.getBotonHuir().removeActionListener(huirListener); 
        vista.getBotonInventario().removeActionListener(inventarioListener);

        vista.setModelo(null); 
    }

    @Override
    public void alTerminarBatalla() {
    controladorInventario.cerrarInventario();
    ResultadoBatalla resultado = modelo.getResultadoBatalla();        
    Entidad enemigo = modelo.getEnemigo();

    
    if (resultado == ResultadoBatalla.VICTORIA) {
        if (enemigo.esBossFinal()) {
            System.out.println("Victoria contra BOSS FINAL y la registro despues");
            modeloJuego.registrarVictoriaBossFinal(); // +500 puntos
        }
    }
    
    // Delegar el resultado al controlador principal
    eventoFinBatalla.manejarFinBatalla(resultado, enemigo);
    // Limpieza para reutilizar la vista
    limpiar();
    }

//no los usa
    @Override
    public void alCambiarEstado() {
    }
    @Override
    public void alElegirAccion(int accion, String nombre, int puntos) {
    }
    @Override
    public void alTurnoEnemigo() {
    }
}
