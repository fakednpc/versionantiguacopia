package modelo;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Entidad {
    protected String nombre;
    protected int vida;
    protected int vidaMax; // Agregado en la implementacion de Item
    protected int ataque;
    protected int defensa;
    protected boolean defensaActiva;

    public int mundoX,mundoY;
    public int velocidad;

    public BufferedImage idle,right1,right2,left1,left2,up1,up2,down1,down2;
    public String direccion;

    public int contadorSprite = 0;
    public int numeroSprite = 1;
     //Para resolver las colisiones utilizamos un cuadrado invisible escalado en x e y
    //sobre el personaje, a la vez puede servir de hitbox
    public Rectangle areaSolida;
    public boolean enColision = false;

    public Entidad (String nombre, int vidaMax, int ataque, int defensa) {
        this.nombre = nombre;
        this.vidaMax = vidaMax; // <-- nuevo
        this.vida = vidaMax; // Arranca lleno
        this.ataque = ataque;
        this.defensa = defensa;
        this.defensaActiva = false;
    }

    public String getNombre() { return nombre; }
    public int getVida() { return vida; }
    public int getAtaque() { return ataque; }
    public int getDefensa() { return defensa; }
    public int getVidaMax(){ return vidaMax;} // Nuevo getter

    public boolean estaVivo() {
        return vida > 0;
    }

   
    public void defender (boolean valor){
        defensaActiva = valor;
    }
    
    public boolean estaDefendiendo (){
        return defensaActiva;
    }
    public void recibirDanio(int daño) {
        if (defensaActiva) {
            daño -= defensa;
            if (daño > 0) vida -= daño;

        } else {
        vida -= daño;
        }

        if (vida < 0) vida = 0;
    }
    

    //utiliza aventurero y enemigo
    public void atacar (Entidad personaje){
        personaje.recibirDanio(ataque);
    }

    // para respetar herencia
    public String getSpriteReposo() {
    return null; 
    }

    public String getSpriteAtaque() {
    return null; 
    }

    public boolean esBossFinal(){
    return false; 
    //por defecto no son bossFinal, es para no romper con la lógica de turnos y sobreescribir en bossFinal
    }
}