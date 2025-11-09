package vista;

import javax.swing.JProgressBar;
import javax.swing.ImageIcon;
import java.awt.*;

public class BarraMonstruo extends JProgressBar {
    
    private Image iconoCalavera;

    public BarraMonstruo(String rutaIcono, int min, int max) { // El constructor recibe la ruta y las dimensiones
        super(min, max);
        
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource(rutaIcono));
            // Escalar al tamaño 
            this.iconoCalavera = icono.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH); //Modificar el tamañp del icono de calavera
        } catch (Exception e) {
            System.err.println("Error al cargar ícono para la JProgressBar.");
        }
        
        setBackground(new Color(40, 40, 40));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (iconoCalavera != null) {
            Graphics2D g2 = (Graphics2D) g;

            int anchoIcono = 24;
            int altoIcono = 24;

            int xPos = (getWidth() - anchoIcono) / 2;
            int yPos = (getHeight() - altoIcono) / 2;

            g2.drawImage(iconoCalavera, xPos, yPos, this);
        }
    }


}