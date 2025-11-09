package vista;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PanelSeccion extends JPanel {

private final String titulo;
    private final JPanel contenido;

    // Estética
    private final int radio     = 18;
    private final int altoPestaña = 24;
    
    private final Color fondoPanel   = new Color(50, 55, 65);     // gris azulado oscuro, base tipo piedra fría
    private final Color bordePanel   = new Color(100, 110, 120);  // gris neutro con leve matiz azul
    private final Color fondoPestaña = new Color(60, 65, 75);     // un poco más claro para distinguir secciones
    private final Color colorTexto   = new Color(230, 230, 225);  // blanco grisáceo suave, no tan cálido

    // Espaciado interno del recuadro
    private int margenSuperior = 10;
    private int margenLados    = 20;
    private int margenInferior = 10;

    public PanelSeccion(String titulo) {
        this.titulo = titulo;
        setOpaque(false); // Se hace transparente, solo se pintara lo que pinte en paintComponent()
        setLayout(new BorderLayout()); // Se divide el panel en NORTH, SOUTH, WEST, EAST Y CENTER
        setFont(UIManager.getFont("Label.font").deriveFont(Font.BOLD, 16f));

        contenido = new JPanel(new FlowLayout(FlowLayout.CENTER, 24, 12));
        contenido.setOpaque(false);

        // EmptyBorder no dibuja nada, solo deja espacio vacio alrededor del componente
        contenido.setBorder(new EmptyBorder(altoPestaña + margenSuperior, margenLados, margenInferior, margenLados));
        add(contenido, BorderLayout.CENTER);

    }

    public JPanel getContenido() {
         return contenido; 
    }

    @Override
    public Dimension getMaximumSize() {

        Dimension ps = getPreferredSize();
        return new Dimension(ps.width, ps.height);
    }

    // Dibujo del recuadro + pestaña
    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        // Activa el antialiasing para suavizar bordes de figuras y texto
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Recuadro base
        int baseY = altoPestaña / 2;
        Shape base = new RoundRectangle2D.Float(8, baseY, w - 16, h - baseY - 8, radio, radio);
        g2.setColor(fondoPanel);
        g2.fill(base);
        g2.setStroke(new BasicStroke(3f));
        g2.setColor(bordePanel);
        g2.draw(base);

        // Pestaña (ancho proporcional al panel)
        int anchoPestaña = Math.min(w - 80, 380);
        int pestañaX = (w - anchoPestaña) / 2;
        Shape pestaña = new RoundRectangle2D.Float(pestañaX, 0, anchoPestaña, altoPestaña, radio, radio);
        g2.setColor(fondoPestaña);
        g2.fill(pestaña);
        g2.setColor(bordePanel);
        g2.draw(pestaña);

        // Título centrado
        g2.setColor(colorTexto);
        String txt = titulo.toUpperCase();
        FontMetrics fm = g2.getFontMetrics(getFont());
        int tx = pestañaX + (anchoPestaña - fm.stringWidth(txt)) / 2;
        int ty = (altoPestaña - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(txt, tx, ty);

        g2.dispose(); // Para descartar el lienzo una vez pintado, libera recursos
    }
}
