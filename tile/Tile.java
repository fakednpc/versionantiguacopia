package tile;

import java.awt.image.BufferedImage;

public class Tile {

    private BufferedImage[] frames;
    private int frameActual;
    private boolean colision;

    private int contadorFrames;   // cuenta cuántas veces se dibujó
    private int velocidadAnim;    // cada cuántos ticks cambia (menor = más rápido)

    public Tile(BufferedImage[] frames, boolean colision) {
        this(frames, colision, 10); // velocidad por defecto
    }

    public Tile(BufferedImage[] frames, boolean colision, int velocidadAnim) {
        this.frames = frames;
        this.colision = colision;
        this.frameActual = 0;
        this.contadorFrames = 0;
        this.velocidadAnim = velocidadAnim;
    }

    public BufferedImage getImagen() {
        return frames[frameActual];
    }

    public boolean tieneColision() {
        return colision;
    }

    public void actualizarAnimacion() {
        contadorFrames++;

        if (contadorFrames >= velocidadAnim) {
            frameActual = (frameActual + 1) % frames.length;
            contadorFrames = 0;
        }
    }

    public void setVelocidadAnim(int velocidadAnim) {
        this.velocidadAnim = Math.max(1, velocidadAnim);
    }
}