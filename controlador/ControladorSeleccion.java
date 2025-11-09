package controlador;

import database.PuntajeDAOImp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.ChequearColision;
import modelo.ConfiguracionPersonaje;
import modelo.CuartelArquero;
import modelo.EntidadFactory;
import modelo.IModeloMapa;
import modelo.MazmorraFactory;
import modelo.ModeloJuego;
import modelo.Personaje;
import modelo.Sonido;
import vista.PanelPrincipal;
import vista.VentanaPrincipal;
import vista.VistaBatalla;
import vista.VistaFinBatalla;
import vista.VistaMapa;
import vista.VistaSeleccion;


public class ControladorSeleccion {

    private final VistaSeleccion vista;
    private final GestorNavegacion navegador;
    private VistaBatalla vistaBatalla;
    private VistaFinBatalla vistaFinBatalla;
    // selección del usuario
    private String mazmorraSeleccionada;
    private String personajeSeleccionado;
    private PanelPrincipal panelJuego;
    private ControladorOpciones controladorOpciones;

    public ControladorSeleccion(VistaSeleccion vista, GestorNavegacion nav, PanelPrincipal panelJuego, VistaBatalla vistaBatalla, ControladorOpciones controladorOp, VistaFinBatalla vistaFinBatalla) {
        this.vista = vista;
        this.panelJuego=panelJuego;
        this.navegador = nav;
        this.vistaBatalla = vistaBatalla;
        this.vistaFinBatalla = vistaFinBatalla;
        this.controladorOpciones = controladorOp;
        
        acciones();
    }

    private void acciones() {

        vista.getBotonArquero().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                personajeSeleccionado = "Arquero";
                Sonido.getInstancia().playEfecto(2);
            }
        });

        vista.getBotonMago().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                personajeSeleccionado = "Mago";
                JOptionPane.showMessageDialog(vista, "Todavia no disponible");
                Sonido.getInstancia().playEfecto(2);

            }
        });

        vista.getBotonGuerrero().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                personajeSeleccionado = "Guerrero";
                JOptionPane.showMessageDialog(vista, "Todavia no disponible");
                Sonido.getInstancia().playEfecto(2);

            }
        });

        vista.getBotonMazmorraAcuatica().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                mazmorraSeleccionada = "Acuatica";
                JOptionPane.showMessageDialog(vista, "Todavia no disponible");
                Sonido.getInstancia().playEfecto(2);
            }
        });

        vista.getBotonMazmorraRuinas().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                mazmorraSeleccionada = "Ruinas";
                Sonido.getInstancia().playEfecto(2);
            }
        });

        vista.getBotonConfirmar().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                Sonido.getInstancia().playEfecto(2);
                if (personajeSeleccionado == null || mazmorraSeleccionada == null) {
                    JOptionPane.showMessageDialog(vista, "Debe seleccionar un personaje y una mazmorra");
                    return;
                }
                

                //Gestiona el sonido con la otra cancion para el gameplay
                Sonido.getInstancia().stop(); 
                Sonido.getInstancia().setSonido(1);
                Sonido.getInstancia().play();
                Sonido.getInstancia().loop();

                ChequearColision colision = new ChequearColision();
                // cargar mapa según mazmorra
                
                MazmorraFactory mazmorrafactory = new MazmorraFactory();
                VistaMapa vistaMapa = mazmorrafactory.crearMazmorra(mazmorraSeleccionada, panelJuego.dimensionTile);
                panelJuego.setVistaMapa(vistaMapa);
                IModeloMapa modeloMapa = vistaMapa.getModelo();
                
                
                // Obtener spawn del mapa cargado
                int spawnX = modeloMapa.getSpawnX();
                int spawnY = modeloMapa.getSpawnY();
                // NUEVO, Crear configuracion objeto de configuración
        ConfiguracionPersonaje config = new ConfiguracionPersonaje();
        config.pantallaAncho = panelJuego.pantallaAncho;
        config.pantallaAltura = panelJuego.pantallaAltura;
        config.dimensionTile = panelJuego.dimensionTile;
        config.control = panelJuego.getControlTeclas();
        config.colision = colision;
        config.spawnX = spawnX;
        config.spawnY = spawnY;
                
                // NUEVO, Usar fábrica concreta según personaje
        EntidadFactory fabrica = switch (personajeSeleccionado) {
            case "Arquero" -> new CuartelArquero(config);
            //case "Guerrero" -> new CuartelGuerrero(config); Los otros jugadores necesitarian su particular
            //case "Mago" -> new CuartelMago(config);
            default -> throw new IllegalArgumentException("Personaje no disponible");
        };

        Personaje jugador = fabrica.crearPersonaje();
                 
     // Instanciar ModeloJuego
ModeloJuego modelo = new ModeloJuego(
    jugador, 
    modeloMapa, 
    panelJuego.dimensionTile);

// Crear DAO del puntaje y pasar al controlador del juego
PuntajeDAOImp puntajeDAO = new PuntajeDAOImp();

// Setear mazmorra elegida
    modelo.setMazmorraElegida(mazmorraSeleccionada);

// Inyectar en la vista que ya existe
    panelJuego.setModelo(modelo);

// Arrancar controlador y bucle
ControladorJuego controlador = new ControladorJuego(modelo, panelJuego, 60, navegador, vistaBatalla, controladorOpciones, puntajeDAO, vistaFinBatalla);
controlador.comenzarJuegoThread();
modelo.setListener(controlador);

controladorOpciones.setPanelJuego(panelJuego);
controladorOpciones.setModeloJuego(modelo);

// Mostrar pantalla de juego
navegador.mostrarPantalla(VentanaPrincipal.JUEGO);
panelJuego.requestFocusInWindow();
//Limpieza simple al final
        personajeSeleccionado = null;
        mazmorraSeleccionada = null;
            }
        });
    }

 
}
