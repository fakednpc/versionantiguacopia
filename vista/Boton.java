package vista;

import java.awt.*;
import javax.swing.*;

public class Boton extends JButton {

    private final Color colorNormal = new Color(65, 65, 70);    
    private final Color colorHover  = new Color(95, 95, 100);   
    private final Color colorTexto  = new Color(240, 235, 220); 
    private final Color colorBorde  = new Color(130, 120, 110); 

    private boolean estaSobre = false;

    public Boton(String texto) {
        super(texto);
        setFont(getFont().deriveFont(17f));
        setForeground(colorTexto);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
    
        // Esto es para forzar el tamaño de los botones, ya que BoxLayout los ajusta segun su contenido
        Dimension d = new Dimension(180, 30);
        setPreferredSize(d);
        setMinimumSize(d);
        setMaximumSize(d); 

        setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia a una manito cuando esta encima

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                estaSobre = true;
                repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                estaSobre = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int radio = 15;

            // Fondo del botón
            g2.setColor(estaSobre ? colorHover : colorNormal);
            g2.fillRoundRect(0, 0, w, h, radio, radio);

            // Borde
            g2.setStroke(new BasicStroke(4f));
            g2.setColor(colorBorde);
            g2.drawRoundRect(0, 0, w , h , radio + 20, radio);

            // Texto centrado
            FontMetrics fm = g2.getFontMetrics();
            int textX = (w - fm.stringWidth(getText())) / 2;
            int textY = (h - fm.getHeight()) / 2 + fm.getAscent();
            g2.setColor(colorTexto);
            g2.drawString(getText(), textX, textY);

        } finally {
            g2.dispose();
        }
    }
}
