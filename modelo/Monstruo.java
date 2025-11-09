package modelo;

public class Monstruo extends Entidad{

    private String spriteReposo;
    private String spriteAtaque;

    public Monstruo (String nombre, int vida, int ataque, int defensa, String spriteReposo, String spriteAtaque){
        super(nombre,vida,ataque,defensa);
        this.spriteAtaque = spriteAtaque;
        this.spriteReposo = spriteReposo;
    }

    @Override
    public String getSpriteReposo() {
        return spriteReposo;
    }

    @Override
    public String getSpriteAtaque() {
        return spriteAtaque;
    }


    
}