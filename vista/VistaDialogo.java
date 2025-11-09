package vista;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class VistaDialogo extends JPanel {
    private final Color colorFondo = new Color(0, 0, 0, 200);
    private final Color colorBorde = new Color(255, 255, 255);
    private final int radio = 15;
    private final int grosorBorde = 3;
    private String texto = "";
    

    // margenes 
    private final int margenIzquierdo = 20;
    private final int margenDerecho = 60;
    private final int margenVertical = 20;

    // boton
    private final JButton botonContinuar;

    public VistaDialogo() {
        setOpaque(false);

        // inicializar boton
        botonContinuar = new JButton("Continuar..");
        botonContinuar.setFocusable(false);
        botonContinuar.setContentAreaFilled(false);
        botonContinuar.setOpaque(false);
        botonContinuar.setForeground(Color.WHITE);
        botonContinuar.setFont(new Font("Monospaced", Font.PLAIN, 14));
        // Borde redondeado similar al del diálogo
        botonContinuar.setBorder(new LineBorder(colorBorde, 2, true));

        // Usamos layout
        setLayout(null);
        add(botonContinuar);

        // Reposicionar el botn cuando cambie el tamaño del panel
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                posicionarBoton();
            }
        });

    }

    // Getter y Setters para el controlador
    public JButton getbotonContinuar() {
        return botonContinuar;
    }

    public void setTexto(String texto) {
        this.texto = texto;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int altoDialogo = h - margenVertical * 2;

        //-----------------------------------------------------------------------------------
        // Fondo del diálogo
        Shape fondo = new RoundRectangle2D.Double(
            margenIzquierdo, margenVertical,
            w - margenIzquierdo - margenDerecho, altoDialogo, radio, radio
        );

        g2.setColor(colorFondo);
        g2.fill(fondo);

        g2.setStroke(new BasicStroke(grosorBorde));
        g2.setColor(colorBorde);
        g2.draw(fondo);

        //-----------------------------------------------------------------------------------
        // txto del dialogo
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Monospaced", Font.PLAIN, 18));
        FontMetrics fm = g2.getFontMetrics();
        
        // area disponible para el texto dentro del dialogo
        int anchoMaximoTexto = w - margenIzquierdo - margenDerecho - 30; // -30 para padding interno
        int alturaLinea = fm.getHeight();
        int textoX = margenIzquierdo + 15;
        int textoY = margenVertical + fm.getAscent() + 15;
        
        // llama al nuevo metodo para dibujar el texto con saltos de linea
        dibujarTextoConSaltos(g2, texto, textoX, textoY, anchoMaximoTexto, alturaLinea);
        g2.dispose();
        // ------------------------------------
    }

    // este metodo hace que dibuje el texto con salto de lineaaa
    private void dibujarTextoConSaltos(Graphics2D g2, String texto, int x, int y, int anchoMaximo, int alturaLinea) {
        if (texto == null || texto.isEmpty()) return;

        FontMetrics fm = g2.getFontMetrics();
        String[] palabras = texto.split(" ");
        String lineaActual = "";
        int currentY = y;

        // Verifica si la linea actual + la nueva palabra excede el ancho
        for (String palabra : palabras) {
            if (fm.stringWidth(lineaActual + " " + palabra) < anchoMaximo) {
                lineaActual += (lineaActual.isEmpty() ? "" : " ") + palabra;
            } else {
                // Si excede dibuja la linea actual y comienza una nueva
                g2.drawString(lineaActual, x, currentY);
                currentY += alturaLinea; // Mueve la posicion Y hacia abajo
                lineaActual = palabra;
            }
        }
        // Dibuja la ultima linea restante 
        if (!lineaActual.isEmpty()) {
            g2.drawString(lineaActual, x, currentY);
        }
    }

    private void posicionarBoton() {
        int w = getWidth();
        int h = getHeight();
        int altoDialogo = h - margenVertical * 2;

        int botonAncho = 120;
        int botonAlto = 34;

        // Alineado a la derecha pero dentro del rectángulo
        int x = (w - margenDerecho) - botonAncho - 10;

        // Posicionado dentro del rectangulo cerca del borde inferior
        int y = margenVertical + altoDialogo - botonAlto - 10;

        botonContinuar.setBounds(x, y, botonAncho, botonAlto);
    }





}