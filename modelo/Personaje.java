package modelo;

import controlador.ControlTeclas;
import java.awt.Rectangle;

public abstract class Personaje extends Entidad{

    private ControlTeclas control;
    private ChequearColision colision;

    public final int pantallaX;
    public final int pantallaY;

    private int ultimaX, ultimaY;
    private int distanciaAcumulada;
    public boolean seMovioTile;

    private Inventario inventario = new Inventario();


    public Personaje (String nombre, int vida, int ataque, int defensa, ConfiguracionPersonaje configuracion){
        super(nombre,vida,ataque,defensa);
        //NUEVO
        this.control = configuracion.control;
        this.colision = configuracion.colision;
        this.mundoX = configuracion.spawnX;
        this.mundoY = configuracion.spawnY;

        //NUEVO
        Camara camara = new Camara(configuracion.pantallaAncho,configuracion.pantallaAltura,configuracion.dimensionTile);
        this.pantallaX = camara.getPantallaX();
        this.pantallaY = camara.getPantallaY();

        areaSolida = new Rectangle(8, 16, 32, 32);
        velocidad = 2;
        direccion = "derecha";

        //para evento de batalla contar pasos
        ultimaX = mundoX;
        ultimaY = mundoY;
        distanciaAcumulada = 0;
        seMovioTile = false;


    }
    
    // Metodo para curar
    public void curar (int puntos){
        if (puntos <= 0 || !estaVivo()) return;
        vida = Math.min(vida + puntos, vidaMax);
    }

    public void actualizar(int[][] mapaTileNum, int dimensionTile) {
        boolean mover = false;

        if (control.upPressed)    { direccion = "arriba"; mover = true; }
        if (control.downPressed)  { direccion = "abajo"; mover = true; }
        if (control.leftPressed)  { direccion = "izquierda"; mover = true; }
        if (control.rightPressed) { direccion = "derecha"; mover = true; }

        enColision = false;
        colision.chequearTile(this, mapaTileNum, dimensionTile);

        // Si no se mueve queda en la posición de idle (se puede quitar para que quede mirando en la última dirección)
        // Por defecto es el frame 1 de la derecha
          if (!mover) {
            direccion = "idle";
            numeroSprite = 1;
            contadorSprite = 0;
        }

        if (mover && !enColision) {
            switch (direccion) {
                case "arriba":    mundoY -= velocidad; break;
                case "abajo":     mundoY += velocidad; break;
                case "izquierda": mundoX -= velocidad; break;
                case "derecha":   mundoX += velocidad; break;
            }
        }

        if (mover) {
            contadorSprite++;
            if (contadorSprite > 12) {
                numeroSprite = (numeroSprite % 2) + 1;
                contadorSprite = 0;
            }
        } else {
            numeroSprite = 1;
            contadorSprite = 0;
        }
        // Calcular distancia recorrida
        int dx = Math.abs(mundoX - ultimaX);
        int dy = Math.abs(mundoY - ultimaY);
        distanciaAcumulada += dx + dy;

       if (distanciaAcumulada >= dimensionTile){
        seMovioTile = true;
        distanciaAcumulada = 0;
       } else {
        seMovioTile = false;
       }
       
        ultimaX = mundoX;
        ultimaY = mundoY;

       }

    public Inventario getInventario() {
        return inventario;
    
    }
       // Métodos abstractos que deben implementar las subclases
    public abstract void atacar();

}