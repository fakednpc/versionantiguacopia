package vista;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import modelo.Personaje;

public class Iluminacion {

    private int ancho;        // Ancho del panel
    private int alto;         // Alto del panel
    private int radioLuz;     // Radio de luz alrededor del jugador
    private int opacidadFondo; // Opacidad del fondo oscuro (0-255)
    private boolean activa;

    public Iluminacion(int ancho, int alto, int radioLuz, int opacidadFondo) {
        this.ancho = ancho;
        this.alto = alto;
        this.radioLuz = radioLuz;
        this.opacidadFondo = opacidadFondo;
        this.activa = true;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setRadioLuz(int radioLuz) {
        this.radioLuz = radioLuz;
    }

    public void setOpacidadFondo(int opacidadFondo) {
        this.opacidadFondo = opacidadFondo;
    }

    /**
     * Dibuja la oscuridad con un gradiente radial iluminando al jugador
     */
    public void dibujar(Graphics2D g2, Personaje jugador) {
        if (!activa) return;

        // Activar suavizado
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                            java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        // Centro de la luz
        float luzX = jugador.pantallaX + jugador.areaSolida.width / 2f;
        float luzY = jugador.pantallaY + jugador.areaSolida.height / 2f;

        // Definimos el gradiente radial: transparente en el centro, negro en los bordes
        Point2D center = new Point2D.Float(luzX, luzY);
        float radius = radioLuz;

        float[] dist = {0f, 1f};
        Color[] colors = {new Color(0,0,0,0), new Color(0,0,0,opacidadFondo)};

        RadialGradientPaint grad = new RadialGradientPaint(center, radius, dist, colors, CycleMethod.NO_CYCLE);
        g2.setPaint(grad);

        // Pintamos sobre todo el panel
        g2.fillRect(0, 0, ancho, alto);
    }
}
