package modelo;

import java.util.Random;

//Creador concreto por mazmorra
public class FabricaMonstruosRuinas extends MonstruoFactory{
    private final Random random = new Random();

    @Override
    public Monstruo crearMonstruo() {
        int tipo = random.nextInt(2); //porque tenemos 2 tipos por ahora
        return switch (tipo){
            case 0 -> new Monstruo("CTHULU", 12, 6, 3,"/res/monstruos/mob_prueba_0.png","/res/monstruos/mob_prueba_0_ATAQUE.png");
            
            case 1 -> new Monstruo("MINOTAURO", 15, 5, 4, "/res/monstruos/mob_prueba_1.png", "/res/monstruos/mob_prueba_1_ATAQUE.png");
            default -> throw new IllegalStateException("Tipo de monstruo inválido");
        };
    }
    
}