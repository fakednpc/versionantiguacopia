package vista;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelBotones extends JPanel{
    
    private JButton botonAtacar = new JButton();
    private JButton botonDefender = new JButton();
    private JButton botonInventario = new JButton();
    private JButton botonHuir = new JButton();
   
    public PanelBotones(){
        setOpaque(false);
        
        addIcono("icon_ATACAR.png", botonAtacar);
        addIcono("icon_DEFENDER.png", botonDefender);
        addIcono("icon_ITEMS.png", botonInventario);
        addIcono("icon_HUIR.png", botonHuir);
        

        add(botonAtacar);
        add(botonDefender);
        add(botonHuir);
        add(botonInventario);
        
    }

    public void addIcono (String ruta, JButton boton){
        boton.setContentAreaFilled(false);
        boton.setBorder(null);
        try {
            File archivo = new File("res/iconos/" + new File(ruta).getName());
            System.out.println("Buscando icono en: " + archivo.getAbsolutePath());
            BufferedImage imgOriginal = ImageIO.read(archivo);
            Image imgEscalada = imgOriginal.getScaledInstance(120,75,Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(imgEscalada);

            boton.setIcon(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }

    public JButton getBotonAtacar() {
        return botonAtacar;
    }

    public JButton getBotonDefender() {
        return botonDefender;
    }

    public JButton getBotonHuir() {
        return botonHuir;
    }

    public JButton getBotonInventario(){
        return botonInventario;
    }
    
    public void habilitarBotones (boolean valor){
        botonAtacar.setEnabled(valor);
        botonDefender.setEnabled(valor);
        botonHuir.setEnabled(valor);
    }
    
}
