package vista;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelSprites extends JPanel {
    private JLabel imagenMonstruo;

    

    public PanelSprites (){
  
        setLayout(new BorderLayout());
        imagenMonstruo = new JLabel();
        imagenMonstruo.setHorizontalAlignment(JLabel.CENTER);
        add(imagenMonstruo, BorderLayout.CENTER);
        setOpaque(false);
        
    }
    
    public void setImagen (String ruta){
        if (ruta == null || ruta.trim().isEmpty()) {
        System.err.println("Advertencia: nombre de imagen nulo o vacío");
        return;
    }
    
        try {
            BufferedImage imagenOriginal = ImageIO.read(getClass().getResourceAsStream(ruta));
            Image escalada = imagenOriginal.getScaledInstance(240,240,Image.SCALE_SMOOTH);
            imagenMonstruo.setIcon(new ImageIcon(escalada));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
