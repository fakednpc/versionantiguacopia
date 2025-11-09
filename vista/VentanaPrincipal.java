package vista;
import java.awt.*;
import javax.swing.*;

public class VentanaPrincipal extends JFrame{
    private CardLayout layout;
    private JPanel contenedor;

    //Nombre de las pantallas
    public static final String MENU = "menu";
    public static final String SELECCION = "seleccion";
    public static final String OPCIONES = "opciones";
    public static final String CREDITOS = "creditos";
    public static final String JUEGO = "juego";
    public static final String BATALLA = "batalla";
    public static final String FIN = "fin";
    
    private PanelPrincipal panelJuego;
    private VistaFinBatalla vistaFinBatalla;

    public VentanaPrincipal (){
        this.setTitle("Ecos de la Mazmorra");
        this.setSize(700,700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        layout = new CardLayout();
        contenedor = new JPanel(layout);

        setContentPane(contenedor);
    }

    public void mostrarPantalla(String nombre){
        layout.show(contenedor, nombre);
    }

    
    public PanelPrincipal getPanelJuego (){
        return panelJuego;
    }

    public VistaFinBatalla getVistaFinBatalla (){
        return vistaFinBatalla;
    }

    public JPanel getContenedor(){
        return contenedor;
    }

}
