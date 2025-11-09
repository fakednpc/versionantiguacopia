package modelo;

public class BossFinal extends Entidad{
     private String spriteReposo;
    private String spriteAtaque;
    //asi puedo darle un comportamiento particular, pero seguir tratando las batallas polimorficamente
    //sobrescribiendo métodos como atacar
    public BossFinal (String nombre, int vida, int ataque, int defensa, String spriteReposo, String spriteAtaque){
        super(nombre, vida, ataque, defensa);
        this.spriteReposo = spriteReposo;
        this.spriteAtaque = spriteAtaque;
    }

    @Override
    public boolean esBossFinal (){
        return true;
        //con esto ya puedo chequear en ControladorBatalla para cambiar la accion al resultado de una batalla
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
