package controlador;

import database.PuntajeDAOImp;
import modelo.BossFactory;
import modelo.Dialogo;
import modelo.Entidad;
import modelo.EventoBatallaListener;
import modelo.EventoFinBatalla;
import modelo.FabricaBossRuinas;
import modelo.FabricaMonstruosRuinas;
import modelo.ModeloBatalla;
import modelo.ModeloJuego;
import modelo.MonstruoFactory;
import modelo.Personaje;
import modelo.ResultadoBatalla;
import vista.PanelPrincipal;
import vista.VentanaPrincipal;
import vista.VistaBatalla;
import vista.VistaFinBatalla;

public class ControladorJuego implements Runnable, EventoBatallaListener, EventoFinBatalla {

    private ModeloJuego modelo;
    private PanelPrincipal vista;
    private ControlTeclas input;
    private Thread gameThread;
    private int FPS;
    private GestorNavegacion navegador;
    private VistaBatalla vistaBatalla;
    private VistaFinBatalla vistaFinBatalla;
    private ControladorOpciones controladorOpciones;
    private ControladorDialogo controladorDialogo;
    private ControladorUI controladorUI;
    private boolean dialogoInicialMostrado = false;

    private PuntajeDAOImp puntajeDAO;

    private ControladorInventario controladorInventario;

    public ControladorJuego(ModeloJuego modelo, PanelPrincipal vista, int FPS, GestorNavegacion navegador, VistaBatalla vistaBatalla, ControladorOpciones controladorOp, PuntajeDAOImp puntaje, VistaFinBatalla vistaFinBatalla) {
        this.modelo = modelo;
        this.vista   = vista;
        this.FPS     = FPS;
        this.input   = vista.getControlTeclas();
        this.navegador = navegador;
        this.vistaBatalla = vistaBatalla;
        this.vistaFinBatalla = vistaFinBatalla;
        this.controladorOpciones = controladorOp;
        this.puntajeDAO = puntaje; // asignamos el DAO

        modelo.setListener(this);

        // Registrar el listener de teclado en la vista
        vista.addKeyListener(input);
        vista.setFocusable(true);
        vista.requestFocusInWindow();
        
        this.controladorUI = new ControladorUI(modelo, vista.getVistaUI());
        controladorUI.actualizarBatallas(); //para que no quede con el valor del modelo viejo
        Dialogo modeloDialogo = new Dialogo();
        controladorDialogo = new ControladorDialogo(vista.getVistaDialogo(), modeloDialogo, modelo, vista, navegador);
        
        //inventario
        controladorInventario = new ControladorInventario(
            input,
            vista.getInventario(),
            modelo.getJugador(),
            vista.getVistaInventario(),
            controladorUI,
            modelo
        );

    }
    
    /**
     * Arranca el hilo que ejecuta el bucle de juego.
     */
    public void comenzarJuegoThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Detiene el bucle de juego (sale del while).
     */
    public void detener() {
        gameThread = null;
    }

    private void guardarPuntajeFinal() { // no se estaria utilizando, porque en vistaFinBatalla no permite enviar nulos
    String nombreJugador = vistaFinBatalla.getNombreJugador();
    if (nombreJugador == null || nombreJugador.trim().isEmpty()) {
        nombreJugador = "Anónimo";
    }

    modelo.setNombreJugador(nombreJugador); 

    int puntajeFinal = modelo.getPuntajeTotal();
    String tipoPersonaje = modelo.getJugador().getNombre();

    try {
        puntajeDAO.guardarPuntaje(nombreJugador, tipoPersonaje, puntajeFinal);
        System.out.println("Puntaje guardado correctamente en la BD.");
    } catch (Exception e) {
        e.printStackTrace();
        System.err.println("Error al guardar el puntaje: " + e.getMessage());
    }
    }



   @Override
public void manejarFinBatalla(ResultadoBatalla resultado, Entidad enemigo) {
    if (resultado == ResultadoBatalla.VICTORIA && enemigo.esBossFinal()) {
       
        manejarFinJuego("¡Liberaste la mazmorra!", true);
    } else if (resultado == ResultadoBatalla.DERROTA) {
        
        manejarFinJuego("Has sido derrotado...", false);
    } else {
        manejarContinuacion(resultado);
    }
}

//metodo auxiliar para no repetir codigo
private void manejarFinJuego(String mensaje, boolean victoriaFinal) {
    detener(); // evento terminal

    vistaFinBatalla.mostrarResultado(mensaje, modelo.getPuntajeTotal()); 

    vistaFinBatalla.setAccionVolverMenu(e -> {
        if (vistaFinBatalla.fueNombreConfirmado()) {
            guardarPuntajeFinal();
        }
        navegador.limpiarVistas();
        navegador.mostrarPantalla(VentanaPrincipal.MENU);
    });

    vistaFinBatalla.setAccionSalir(e -> {
        if (vistaFinBatalla.fueNombreConfirmado()) {
            guardarPuntajeFinal();
        }
        System.exit(0);
    });

    navegador.mostrarPantalla(VentanaPrincipal.FIN);
}
//metodo auxiliar para no repetir codigo
private void manejarContinuacion(ResultadoBatalla resultado) {
    if (resultado == ResultadoBatalla.VICTORIA) {
        modelo.registrarVictoria();
        controladorUI.actualizarBatallas();
    }
    modelo.reanudarJuego();
    controladorUI.actualizarVida();
    navegador.mostrarPantalla(VentanaPrincipal.JUEGO);
    controladorInventario.reubicarInventarioEn(vista.getContenedorRaiz());

}

     @Override
    public void alIniciarBatalla (Personaje jugador, String mazmorra){
        
       // input.habilitarTeclas(false);
        input.limpiarTeclas();
        modelo.pausarJuego();

        
        Entidad enemigo;
        //NUEVO
        if(modelo.debeActivarBoss() && modelo.siJugadorCercaJefe()){
            BossFactory fabricaBoss = switch (mazmorra) {
            case "Ruinas" -> new FabricaBossRuinas();
            //case "Acuatica" -> new FabricaBossAcuatica(); //si tuvieramos acuatica
            default -> throw new IllegalArgumentException("Mazmorra no reconocida");
        };
        enemigo = fabricaBoss.crearBoss();
        } else {
            MonstruoFactory fabricaMonstruos = switch (mazmorra) {
            case "Ruinas" -> new FabricaMonstruosRuinas();
            //case "Acuatica" -> new FabricaMonstruosAcuatica(); //si tuvieramos acuatica
            default -> throw new IllegalArgumentException("Mazmorra no reconocida");
        };
        enemigo = fabricaMonstruos.crearMonstruo();
        }
        // de aca para abajo todo igual
        ModeloBatalla modeloBatalla = new ModeloBatalla(jugador, enemigo);
        vistaBatalla.setModelo(modeloBatalla);
        controladorInventario.setModeloBatalla(modeloBatalla); //para poder consumir en batallla y que se actualicen los label
        new ControladorBatalla(vistaBatalla, modeloBatalla, modelo, vista, navegador, controladorUI, this, controladorInventario);
        controladorInventario.reubicarInventarioEn(vistaBatalla.getCapas());

        vistaBatalla.setVistaInventario(controladorInventario.getVistaInventario());


        navegador.mostrarPantalla(VentanaPrincipal.BATALLA);
        vistaBatalla.addKeyListener(input);
        vistaBatalla.setFocusable(true);
        vistaBatalla.requestFocusInWindow();  
        vistaBatalla.revalidate();
        vistaBatalla.repaint();
    }

      @Override
    public void run() {
        double intervalo     = 1_000_000_000.0 / FPS;
        double delta         = 0;
        long   ultimoTiempo  = System.nanoTime();
        long   timer         = 0;
        int    contadorFPS   = 0;

        while (gameThread != null) {
            long ahora = System.nanoTime();
            delta    += (ahora - ultimoTiempo) / intervalo;
            timer    += (ahora - ultimoTiempo);
            ultimoTiempo = ahora;

            if (delta >= 1) {

            if(!modelo.EstaPausado()){
                modelo.update(input);
            }
            
            // Siempre actualizar inventario, incluso si el modelo está pausado
            if (navegador.getPantallaActual().equalsIgnoreCase(VentanaPrincipal.BATALLA)) {
                
            controladorInventario.actualizarInventario();
            } else {
                controladorInventario.actualizarInventario();
                if (!controladorInventario.isInventarioVisible() && !modelo.EstaPausado()) {
                    modelo.update(input);
             }
            }

                if(!modelo.EstaPausado()){
               
                if (!dialogoInicialMostrado) {
                    input.habilitarTeclas(false);
                    input.limpiarTeclas();
                    modelo.pausarJuego();

                    dialogoInicialMostrado = true; // marca que ya se mostró
                    controladorDialogo.iniciarDialogo();
                }

                // Chequea si ESC se presiono dentro del juego: abre la ventana de opciones. 
                if (input.escPressed) {
                    input.escPressed = false;
                    //modelo.pausarJuego();
                    controladorOpciones.setPantallaAnterior(VentanaPrincipal.JUEGO);
                    navegador.mostrarPantalla(VentanaPrincipal.OPCIONES); //muestra pantalla de opciones
                }


                // 2) Render
                vista.repaint();

                delta--;
                contadorFPS++;
            }

            // Mostrar FPS cada segundo
            if (timer >= 1_000_000_000) {
                //System.out.println("FPS: " + contadorFPS);
                contadorFPS = 0;
                timer       = 0;
            }
        }

    }
}
}   

