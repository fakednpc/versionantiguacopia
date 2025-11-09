package modelo;

public class Arquero extends Personaje {
    public Arquero(ConfiguracionPersonaje configuracion){
        super("Arquero", 15, 5, 3, configuracion);
    }

    @Override
    public void atacar() {
        //logica posible de ataque
    }
}
