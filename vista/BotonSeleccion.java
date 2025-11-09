package vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

public class BotonSeleccion extends JToggleButton {

    private final Image imagen;
    private final int radio = 10; // radio de redondeo de esquinas

    public BotonSeleccion(String rutaIcono) {
        super();

        ImageIcon icono = new ImageIcon(getClass().getResource(rutaIcono));
        this.imagen = icono.getImage();

        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
        setPreferredSize(new Dimension(80, 80));
    }

    @Override
    protected void paintComponent(Graphics g) {
        
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // "Recorte"
            Shape clip = new RoundRectangle2D.Float(0, 0, w, h, radio, radio);
            
            // A todo lo que dibuje se le "recortara el sobrante" para que tenga la misma forma de clip
            g2.setClip(clip);
            g2.drawImage(imagen, 0, 0, w, h, this);

            // Con esta linea "elimino" el clip,
            // yo lo dejo comentado porque quiero que el overlay tambien este redondeado
            
            // g2.setClip(null);
            
            // Overlay + borde si está seleccionado
            if (isSelected()) {
                g2.setColor(new Color(0, 200, 255, 60));
                g2.fill(clip); // Llena toda el area de clip

                g2.setStroke(new BasicStroke(5f));
                g2.setColor(new Color(0, 220, 255, 150));
                g2.draw(clip);
            }
        } finally {
            g2.dispose();
        }

        super.paintComponent(g);
    }

    //añadido para poder al limpiar que no queden marcados
    public void resetEstilo() {
    setSelected(false); 
    repaint();
    }
}
