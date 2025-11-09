package vista;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;


public class Panel extends JPanel {
    private BufferedImage fondo;
   
    public Panel (){
        try {
            fondo = ImageIO.read(getClass().getResourceAsStream("/res/fondo/fondo_batalla_ruinas.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        setLayout(new GridBagLayout());
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        if (fondo != null ){
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), null);
    }
}
}
