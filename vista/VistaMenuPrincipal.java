package vista;

import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class VistaMenuPrincipal extends JPanel {

    private JButton botonIniciar;
    private JButton botonOpciones; 
    private JButton botonSalir;
    private JButton botonRanking;
    
    public VistaMenuPrincipal(){

        setLayout(new BorderLayout());

        Fondo fondo = new Fondo("/assets/img/fondo.png");
        add(fondo, BorderLayout.CENTER);

        // Contenedor de  botones
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS)); // Para acomodar los elementos en el Y AXIS
        contenedor.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0)); // Define el padding interno
        contenedor.setOpaque(false); // Contenedor transparente

        botonIniciar = new Boton("Iniciar Juego"); 
        botonOpciones = new Boton("Opciones");
        botonSalir = new Boton("Salir");
        botonRanking = new Boton("Ver Ranking");

         // Alinear al centro
        botonIniciar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonOpciones.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonRanking.setAlignmentX(Component.CENTER_ALIGNMENT);


        contenedor.add(botonIniciar);
        contenedor.add(Box.createRigidArea(new Dimension(0, 15)));
        contenedor.add(botonOpciones);
        contenedor.add(Box.createRigidArea(new Dimension(0, 15))); 
        contenedor.add(botonRanking);
        contenedor.add(Box.createRigidArea(new Dimension(0, 15)));
        contenedor.add(botonSalir);
        

        fondo.add(contenedor, BorderLayout.SOUTH);
    } 

    // Getters para acceder desde el controlador
    public JButton getBotonIniciar() {
        return this.botonIniciar;
    }

    public JButton getBotonOpciones() {
        return this.botonOpciones;
    }
    
    public JButton getBotonRanking() {
    return this.botonRanking;
    }

    public JButton getBotonSalir() {
        return this.botonSalir;
    }

}
