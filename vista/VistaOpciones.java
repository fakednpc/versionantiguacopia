package vista;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import java.awt.*;

public class VistaOpciones extends JPanel {
    private JButton textoMusica;
    private JButton botonSubirMusica;
    private JButton botonBajarMusica;
    private JButton textoEfecto,botonSubirEfecto,botonBajarEfecto;
    private JProgressBar barraVolumen, barraVolumenEfecto;
    private JButton botonComoJugar;
    private JButton botonCreditos;
    private JButton botonVolver;
    private Fondo fondo;

    public VistaOpciones () {
       
        setLayout(new BorderLayout());
        fondo = new Fondo("/assets/img/fondoOpciones.png");
        add(fondo, BorderLayout.CENTER);

        //Contenedor de botones
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS)); // Para acomodar los elementos en el Y AXIS
        contenedor.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0)); // Define el padding interno
        contenedor.setOpaque(false); // Contenedor transparente

        textoMusica = new JButton("MUSICA");
        botonSubirMusica = new Boton("+");
        botonBajarMusica = new Boton("-");
        
        textoEfecto = new JButton("EFECTO");
        botonSubirEfecto = new Boton("+");
        botonBajarEfecto = new Boton("-");

        botonComoJugar = new Boton("Como Jugar");
        botonCreditos = new Boton("Creditos");
        botonVolver = new Boton("Volver");

        botonBajarMusica.setPreferredSize(new Dimension(40, 30));
        botonSubirMusica.setPreferredSize(new Dimension(40, 30));
        botonBajarEfecto.setPreferredSize(new Dimension(40, 30));
        botonSubirEfecto.setPreferredSize(new Dimension(40, 30));

        textoMusica.setForeground(Color.WHITE);
        textoMusica.setBorderPainted(false);
        textoMusica.setFocusPainted(false);
        textoMusica.setContentAreaFilled(false);
        textoMusica.setFont(this.textoMusica.getFont().deriveFont(17f));

        textoEfecto.setForeground(Color.WHITE);
        textoEfecto.setBorderPainted(false);
        textoEfecto.setFocusPainted(false);
        textoEfecto.setContentAreaFilled(false);
        textoEfecto.setFont(this.textoEfecto.getFont().deriveFont(17f));


        //CONFIGURACION DE LA BARRA DE MUSICA
        barraVolumen = new JProgressBar(0,5);
        barraVolumen.setValue(1);
        barraVolumen.setForeground(new Color(255, 200, 0)); 
        barraVolumen.setBackground(new Color(40, 40, 40));
        barraVolumen.setPreferredSize(new Dimension(150, 25)); //ancho y alto de la barra 

        //CONFIGURACION DE LA BARRA DE VOLUMEN DE EFECTO
        barraVolumenEfecto = new JProgressBar(0,5);
        barraVolumenEfecto.setValue(1);
        barraVolumenEfecto.setForeground(new Color(255, 200, 0)); 
        barraVolumenEfecto.setBackground(new Color(40, 40, 40));
        barraVolumenEfecto.setPreferredSize(new Dimension(150, 25)); //ancho y alto de la barra 

        //posicionamiento en el centro de todos los botones
        textoMusica.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonBajarMusica.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonSubirMusica.setAlignmentX(Component.CENTER_ALIGNMENT);
        barraVolumen.setAlignmentX(Component.CENTER_ALIGNMENT);

        textoEfecto.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonBajarEfecto.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonSubirEfecto.setAlignmentX(Component.CENTER_ALIGNMENT);
        barraVolumenEfecto.setAlignmentX(Component.CENTER_ALIGNMENT);

        botonComoJugar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonCreditos.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonVolver.setAlignmentX(Component.CENTER_ALIGNMENT);

        //cree un panelVolumen para la configuracion de la barra y los botones subir y bajar de LA MUSICA
        //-------------------------------------------------------------
        JPanel panelVolumen = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelVolumen.setOpaque(false);

        // Añadimos todos los elementos en una sola línea horizontal
        panelVolumen.add(textoMusica);
        panelVolumen.add(botonBajarMusica);
        panelVolumen.add(barraVolumen);
        panelVolumen.add(botonSubirMusica);


        JPanel panelVolumenEfectos = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelVolumenEfectos.setOpaque(false);

        // Añadimos todos los elementos en una sola línea horizontal
        panelVolumenEfectos.add(textoEfecto);
        panelVolumenEfectos.add(botonBajarEfecto);
        panelVolumenEfectos.add(barraVolumenEfecto);
        panelVolumenEfectos.add(botonSubirEfecto);

        //-------------------------------------------------------------
        //contenedor.add(Box.createVerticalStrut(40)); // espacio superior
        contenedor.add(panelVolumen);
        contenedor.add(Box.createVerticalStrut(40)); // espacio superior
        contenedor.add(panelVolumenEfectos);
        contenedor.add(Box.createVerticalStrut(20)); // espacio superior
        contenedor.add(botonComoJugar);
        contenedor.add(Box.createRigidArea(new Dimension(0, 15)));
        contenedor.add(botonCreditos);
        contenedor.add(Box.createRigidArea(new Dimension(0, 15)));
        contenedor.add(botonVolver);

        fondo.add(contenedor, BorderLayout.SOUTH);
    }

    //Getters para el controladorOpciones
    public JButton getSubirMusica(){
        return botonSubirMusica;
    }

    public JButton getBajarMusica(){
        return botonBajarMusica;
    }

    public JButton getSubireEfecto(){
        return botonSubirEfecto;
    }

    public JButton getBajarEfecto(){
        return botonBajarEfecto;
    }

    public JButton getBotonComoJugar () {
        return botonComoJugar;
    }

    public JButton getBotonCreditos(){
        return botonCreditos;
    }

    public JButton getBotonVolver() {
        return botonVolver;
    }

    //Estos setters son para que los cambios se vean en las barras de musica y efecto.
    public void setVolumen(int nivel) { 
        barraVolumen.setValue(nivel);
    }

    public void setVolumenEfecto(int nivel) {
        barraVolumenEfecto.setValue(nivel);
    }
}
