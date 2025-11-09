package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JToggleButton;

import modelo.Limpiable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class VistaSeleccion extends JPanel implements Limpiable {

    private JPanel contenedor;

    private BotonSeleccion botonMazmorraAcuatica;
    private BotonSeleccion botonMazmorraRuinas;

    private BotonSeleccion botonGuerrero;
    private BotonSeleccion botonMago;
    private BotonSeleccion botonArquero;

    private ButtonGroup agrupacionMazmorras;
    private ButtonGroup agrupacionPersonajes;

    private Boton botonConfirmar;

    public VistaSeleccion() {
    
        setLayout(new BorderLayout()); // Para que contenedor pueda estirarse todo el tamaño de la pantalla

        Fondo fondo = new Fondo("/assets/img/fondo_mazmorra.png");
        fondo.setLayout(new BoxLayout(fondo, BoxLayout.Y_AXIS));
        add(fondo, BorderLayout.CENTER);

        // Contenedor principal
        contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS)); // Para acomodar los elementos en el Y AXIS
        contenedor.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0)); // Define el padding interno
        contenedor.setOpaque(false); // Contenedor transparente
        
        // Titulo
        JLabel titulo = new JLabel("Seleccion de Mapa y Personaje");
        titulo.setFont(titulo.getFont().deriveFont(24f));
        titulo.setAlignmentX(CENTER_ALIGNMENT);
        titulo.setForeground(new Color(230,230,230));
        contenedor.add(titulo);

        // ===== SECCION MAPAS =====
        PanelSeccion seccionMapa = new PanelSeccion("Seleccion de Mapa");

        botonMazmorraAcuatica = new BotonSeleccion("/assets//img/icono_aqua_tomb.png");
        botonMazmorraRuinas   = new BotonSeleccion( "/assets/img/icono_dark_cave.png");

        agrupacionMazmorras = new ButtonGroup();
        agrupacionMazmorras.add(botonMazmorraAcuatica);
        agrupacionMazmorras.add(botonMazmorraRuinas);

        seccionMapa.getContenido().add(botonMazmorraAcuatica);
        seccionMapa.getContenido().add(botonMazmorraRuinas);

        contenedor.add(seccionMapa);

        contenedor.add(Box.createVerticalGlue()); // Lo uso para que ambas secciones esten lo mas separado posible entre si

        // ===== SECCION PERSONAJES =====
        PanelSeccion seccionPersonajes = new PanelSeccion("Seleccion de Personaje");

        botonGuerrero = new BotonSeleccion("/assets/img/icono_guerrero.png");
        botonGuerrero.setToolTipText("ATQ: 7 DEF:4");
        botonMago     = new BotonSeleccion("/assets/img/icono_mago.png"); 
        botonMago.setToolTipText("ATQ: 4 DEF 5");
        botonArquero  = new BotonSeleccion("/assets/img/icono_arquero.png");
        botonArquero.setToolTipText("ATQ: 5 DEF: 3");

        agrupacionPersonajes = new ButtonGroup();
        agrupacionPersonajes.add(botonGuerrero);
        agrupacionPersonajes.add(botonMago);
        agrupacionPersonajes.add(botonArquero);

        seccionPersonajes.getContenido().add(botonGuerrero);
        seccionPersonajes.getContenido().add(botonMago);
        seccionPersonajes.getContenido().add(botonArquero);

        contenedor.add(seccionPersonajes);

        // Boton confirmar
        botonConfirmar = new Boton("Confirmar Selección");
        botonConfirmar.setAlignmentX(Component.CENTER_ALIGNMENT);

        contenedor.add(Box.createRigidArea(new Dimension(0,30)));
        contenedor.add(botonConfirmar);

        fondo.add(contenedor);

    }


    public JToggleButton getBotonMazmorraAcuatica(){
        return botonMazmorraAcuatica;
    }

    public JToggleButton getBotonMazmorraRuinas(){
        return botonMazmorraRuinas;
    }

    public JToggleButton getBotonGuerrero(){
        return botonGuerrero;
    }

    public JToggleButton getBotonMago(){
        return botonMago;
    }

    public JToggleButton getBotonArquero(){
        return botonArquero;
    }

    public JButton getBotonConfirmar() {
        return botonConfirmar;
    }
   
    @Override
    public void limpiar() {
        agrupacionMazmorras.clearSelection();
        agrupacionPersonajes.clearSelection();

        botonMazmorraAcuatica.resetEstilo();
        botonMazmorraRuinas.resetEstilo();
        botonGuerrero.resetEstilo();
        botonMago.resetEstilo();
        botonArquero.resetEstilo();

        
        revalidate();
        repaint();
    }
}
