package modelo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteJugador {

    private BufferedImage idle, up1, up2, down1, down2, left1, left2, right1, right2;

    public SpriteJugador() {
        try {
            idle   = ImageIO.read(getClass().getResourceAsStream("/res/jugador/arquero/sprite_0.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/jugador/arquero/sprite_derecha_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/jugador/arquero/sprite_derecha_2.png"));
            left1  = ImageIO.read(getClass().getResourceAsStream("/res/jugador/arquero/sprite_izquierda_1.png"));
            left2  = ImageIO.read(getClass().getResourceAsStream("/res/jugador/arquero/sprite_izquierda_2.png"));
            up1    = ImageIO.read(getClass().getResourceAsStream("/res/jugador/arquero/sprite_arriba_1.png"));
            up2    = ImageIO.read(getClass().getResourceAsStream("/res/jugador/arquero/sprite_arriba_2.png"));
            down1  = ImageIO.read(getClass().getResourceAsStream("/res/jugador/arquero/sprite_abajo_1.png"));
            down2  = ImageIO.read(getClass().getResourceAsStream("/res/jugador/arquero/sprite_abajo_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public BufferedImage getIdle() { return idle; }
    public BufferedImage getRight1() { return right1; }
    public BufferedImage getRight2() { return right2; }
    public BufferedImage getLeft1() { return left1; }
    public BufferedImage getLeft2() { return left2; }
    public BufferedImage getUp1() { return up1; }
    public BufferedImage getUp2() { return up2; }
    public BufferedImage getDown1() { return down1; }
    public BufferedImage getDown2() { return down2; }
}
