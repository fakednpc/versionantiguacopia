package vista;

//libreria JavaSwing
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Fondo extends JPanel {
    private Image imagenFondo; 


    public Fondo(String url) {
        
        setLayout(new BorderLayout());

        try {
            this.imagenFondo = new ImageIcon(getClass().getResource(url)).getImage();
        } catch (Exception e) { // excepcion 
            System.err.println("No se pudo cargar la imagen de fondo: " + url);
            e.printStackTrace();
            this.imagenFondo = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.imagenFondo != null) {
            g.drawImage(this.imagenFondo, 0, 0, getWidth(), getHeight(), this); //dibuja la img cuando el panel se renderiza 
        }
    }
    
}
