package vista;

import controlador.ControlTeclas;
import java.awt.*;
import javax.swing.*;
import modelo.Inventario;
import modelo.Limpiable;
import modelo.ModeloJuego;
import modelo.Personaje;
import modelo.SpriteJugador;
import tile.CargadorTiles;
import tile.Tile;

public class PanelPrincipal extends JPanel implements Limpiable{

    private ModeloJuego modelo;
    private final ControlTeclas controlTeclas = new ControlTeclas();
    private final SpriteJugador sprites       = new SpriteJugador();
    private VistaPersonaje vistaPersonaje;
    private VistaMapa vistaMapa; // nueva vista del mapa
    private Iluminacion iluminacion;
    private VistaDialogo vistaDialogo;
    private VistaUI vistaUI;

    // Inventario
    private Inventario inventario;
    private VistaInventario vistaInventario;
   
    // Configuración de pantalla
    private final int dimensionOriginalTile = 16;
    private final int escala               = 3;
    public  final int dimensionTile        = dimensionOriginalTile * escala;
    public  final int maxPantallaCol       = 15;
    public  final int maxPantallaFil       = 14;
    public  final int pantallaAncho        = dimensionTile * maxPantallaCol;
    public  final int pantallaAltura       = dimensionTile * maxPantallaFil;

    public PanelPrincipal() {
        setPreferredSize(new Dimension(pantallaAncho, pantallaAltura));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setLayout(null);

        //Para el Dialogo 
        vistaDialogo = new VistaDialogo();
        vistaDialogo.setVisible(false);
        add(vistaDialogo);
        vistaDialogo.setBounds(0, 0, pantallaAncho, (pantallaAltura / 4) + 50);

        setComponentZOrder(vistaDialogo, 0);

        // Para la vista
        vistaUI = new VistaUI();
        vistaUI.setBounds(0, 0, pantallaAncho, pantallaAltura);
        vistaUI.setVisible(false);
        add(vistaUI);
        setComponentZOrder(vistaUI, 0); // que esté por encima del mapa

    }

    /** Inyecta el modelo cuando ya está listo */
    public void setModelo(ModeloJuego modelo) {
        this.modelo = modelo;
        
        Personaje p = modelo.getJugador();
        this.inventario = p.getInventario();
        
        // Inicializar vistas dependientes
        vistaPersonaje = new VistaPersonaje(p, sprites);
        iluminacion    = new Iluminacion(pantallaAncho, pantallaAltura, 180, 250);

        Tile[] tiles = CargadorTiles.cargarTiles("/res/tiles/mazmorra_1/");
        vistaMapa = new VistaMapa(modelo.getModeloMapa(), tiles, dimensionTile);
    
        vistaInventario = new VistaInventario(inventario);
        vistaInventario.setBounds(
            (pantallaAncho - 400) / 2,
            (pantallaAltura - 300) / 2,
             400, 300 );
        vistaInventario.setVisible(false);
        add(vistaInventario);
        setComponentZOrder(vistaInventario, 1);

    }

    public void setVistaMapa(VistaMapa vistaMapa) {
        this.vistaMapa = vistaMapa;
    }

    /** Para que ControladorJuego registre el listener */
    public ControlTeclas getControlTeclas() {
        return controlTeclas;
    }


    public VistaDialogo getVistaDialogo() {
        return vistaDialogo;
    }

    public VistaUI getVistaUI() {
        return vistaUI;
    }

    public VistaInventario getVistaInventario() {
        return vistaInventario;
    }

    public Inventario getInventario() {
        return inventario;
    }

    @Override
    protected void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        if (modelo == null) return;  

        Graphics2D g2 = (Graphics2D) gg.create();
        Personaje p = modelo.getJugador();
        
        // Mapa
        if (vistaMapa != null) {
        vistaMapa.dibujar(
                g2,
                p.mundoX,
                p.mundoY,
                p.pantallaX,
                p.pantallaY
            );
        }
        
       
        // Jugador
        vistaPersonaje.dibujar(g2, dimensionTile);
        // Iluminación
        iluminacion.dibujar(g2, p);
        // Mensaje de pickup si hay uno cerca
        if (modelo.isHayPickupCerca()) {
            g2.setFont(new Font("Monospaced", Font.BOLD, 16));
            g2.setColor(Color.WHITE);

            // Posición del mensaje sobre el jugador
            int msgX = p.pantallaX - 40;
            int msgY = p.pantallaY - 10;
            g2.drawString("[E] para recoger", msgX, msgY);
        }
        //
        if (modelo.siJugadorCercaJefe()) {
            g2.setFont(new Font("Monospaced", Font.BOLD, 18));
            g2.setColor(Color.WHITE);
            // Posición del mensaje sobre el jugador
            int msgX = p.pantallaX - 30;
            int msgY = p.pantallaY - 5;
        g2.drawString("[ESPACIO] para interactuar", msgX, msgY);
        }
        // Inventario
        vistaInventario.setVisible(modelo.isMostrarInventario());

        g2.dispose();
        super.paintChildren(gg);
    }

    @Override
    public void limpiar(){
        this.modelo = null; //modeloJuego
        //this.vistaMapa = null; por si acaso por ahora
        this.vistaPersonaje = null;
        this.iluminacion = null;

        if (vistaUI != null) {
            vistaUI.setVisible(false);
        }

        controlTeclas.limpiarTeclas();
        repaint(); //forzar repintado
    }

    public JPanel getContenedorRaiz() {
    return this;
    }

}


