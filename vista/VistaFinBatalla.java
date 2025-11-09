package vista;

import javax.swing.*;

import modelo.Limpiable;

import java.awt.*;
import java.awt.event.ActionListener;

public class VistaFinBatalla extends JPanel implements Limpiable {
    private JLabel mensajeLabel;
    private JLabel puntuacionLabel;
    private JTextField campoNombre;
    private JLabel mensajeConfirmacion;
    private JPanel panelMensaje;
    private Boton botonMenu, botonSalir, botonEnviar;
    private boolean nombreConfirmado = false;

    public VistaFinBatalla() {
        setLayout(new BorderLayout());

        Fondo fondo = new Fondo("/assets/img/fondo_mazmorra.png");
        fondo.setLayout(new BoxLayout(fondo, BoxLayout.Y_AXIS));
        fondo.setOpaque(false);
        add(fondo, BorderLayout.CENTER);

        // Mensaje principal
        mensajeLabel = new JLabel("", SwingConstants.CENTER);
        mensajeLabel.setFont(new Font("Serif", Font.BOLD, 28));
        mensajeLabel.setForeground(Color.WHITE);
        mensajeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Puntuación
        puntuacionLabel = new JLabel("", SwingConstants.CENTER);
        puntuacionLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        puntuacionLabel.setForeground(new Color(255, 215, 0)); // dorado
        puntuacionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Campo de texto
        campoNombre = new JTextField();
        campoNombre.setToolTipText("Podés corregir tu nombre y volver a enviarlo");
        campoNombre.setMaximumSize(new Dimension(300, 30));
        campoNombre.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Mensaje de confirmación con fondo traslúcido
        mensajeConfirmacion = new JLabel("");
        mensajeConfirmacion.setFont(new Font("SansSerif", Font.PLAIN, 14));
        mensajeConfirmacion.setForeground(Color.WHITE);
        mensajeConfirmacion.setHorizontalAlignment(SwingConstants.CENTER);

        panelMensaje = new JPanel(new BorderLayout());
        panelMensaje.setOpaque(true);
        panelMensaje.setBackground(new Color(0, 0, 0, 120)); // negro semitransparente
        panelMensaje.setMaximumSize(new Dimension(300, 30));
        panelMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMensaje.add(mensajeConfirmacion, BorderLayout.CENTER);

        // Botón Enviar
        botonEnviar = new Boton("Enviar");
        botonEnviar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Acción compartida para Enter y botón
        ActionListener accionGuardarNombre = e -> {
            String nombre = campoNombre.getText().trim();
            if (!nombre.isEmpty()) {
               nombreConfirmado=true;
                mensajeConfirmacion.setText("Nombre guardado: " + nombre);
                
            } else {
                mensajeConfirmacion.setText("Por favor ingresá un nombre válido");
            }
        };

        campoNombre.addActionListener(accionGuardarNombre);
        botonEnviar.addActionListener(accionGuardarNombre);

        // Botones de navegación
        botonMenu = new Boton("Volver al menú");
        botonSalir = new Boton("Salir del juego");

        JPanel botones = new JPanel(new FlowLayout());
        botones.setOpaque(false);
        botones.add(botonMenu);
        botones.add(botonSalir);
        botones.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Agregar componentes con espaciado
        fondo.add(Box.createVerticalGlue());
        fondo.add(mensajeLabel);
        fondo.add(Box.createVerticalStrut(10));
        fondo.add(puntuacionLabel);
        fondo.add(Box.createVerticalStrut(10));
        fondo.add(campoNombre);
        fondo.add(Box.createVerticalStrut(5));
        fondo.add(botonEnviar);
        fondo.add(Box.createVerticalStrut(5));
        fondo.add(panelMensaje);
        fondo.add(Box.createVerticalStrut(20));
        fondo.add(botones);
        fondo.add(Box.createVerticalGlue());
    }

    public boolean fueNombreConfirmado(){
        return nombreConfirmado;
    }
    public void mostrarResultado(String mensaje, int puntuacion) {
        mensajeLabel.setText(mensaje);
        puntuacionLabel.setText("Puntuación: " + puntuacion);
        campoNombre.setText("");
        mensajeConfirmacion.setText(""); // limpiar mensaje anterior
    }

    public String getNombreJugador() {
        return campoNombre.getText().trim();
    }

    public void setAccionVolverMenu(ActionListener listener) {
        botonMenu.addActionListener(listener);
    }

    public void setAccionSalir(ActionListener listener) {
        botonSalir.addActionListener(listener);
    }

    @Override
    public void limpiar() {
    campoNombre.setText("");
    mensajeConfirmacion.setText("");
    nombreConfirmado = false;
    }

}

