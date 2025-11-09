package vista;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import modelo.Personaje;
import modelo.SpriteJugador;

/**
 * VistaPersonaje se encarga únicamente de dibujar al personaje en pantalla.
 * No maneja lógica ni entrada de usuario: sólo representa el modelo (Personaje).
 */
public class VistaPersonaje {

    private final Personaje jugador;
    private final SpriteJugador sprites;

    public VistaPersonaje(Personaje jugador, SpriteJugador sprites) {
        this.jugador = jugador;
        this.sprites = sprites;
    }

    /**
     * Obtiene el sprite actual del jugador (delegado anteriormente al modelo).
     */
     private BufferedImage getSpriteActual() {
        switch (jugador.direccion) {
            case "arriba":    return (jugador.numeroSprite == 1) ? sprites.getUp1() : sprites.getUp2();
            case "abajo":     return (jugador.numeroSprite == 1) ? sprites.getDown1() : sprites.getDown2();
            case "izquierda": return (jugador.numeroSprite == 1) ? sprites.getLeft1() : sprites.getLeft2();
            case "derecha":   return (jugador.numeroSprite == 1) ? sprites.getRight1() : sprites.getRight2();
            default: case "idle":  return sprites.getIdle();
        }
    }

    
    // Dibuja el personaje en pantalla.
     // g2 Graphics2D del JPanel.
     // dimensionTile Tamaño del tile (para escalar el sprite).
    
    public void dibujar(Graphics2D g2, int dimensionTile) {
        int x = jugador.pantallaX;
        int y = jugador.pantallaY;

        g2.drawImage(getSpriteActual(), x, y, dimensionTile, dimensionTile, null);
    }
}
