package modelo.item;

import modelo.Personaje;

public class Pocion extends Item {
    
    private final int puntos; // Puede ser cura o lo que sea

    public Pocion (String nombre, String descripcion, int puntos){
        super(nombre, descripcion);
        this.puntos = puntos;
    }

     @Override
    public void usarItem(Personaje objetivo) {
        if (objetivo == null || !objetivo.estaVivo()) return;
        int antes = objetivo.getVida();
        objetivo.curar(puntos);
        int curado = objetivo.getVida() - antes;
        System.out.println(objetivo.getNombre() + " recuperó " + curado + " puntos de vida.");
    }

}
