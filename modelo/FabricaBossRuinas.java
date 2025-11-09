package modelo;

public class FabricaBossRuinas extends BossFactory {

    @Override
    public BossFinal crearBoss() {
       return new BossFinal("HIDRA", 50, 7, 5,"/res/monstruos/jefes/mazmorra_1/mob_finalboss_1_reposo.png","/res/monstruos/jefes/mazmorra_1/mob_finalboss_0_ataque.png"); 
       //único boss en mapa Ruinas. Para los otros mapas se deberia añadir clase abstracta FabricaBossAcuatica...
       // y hace lo mismo que esta
    }
    
}
