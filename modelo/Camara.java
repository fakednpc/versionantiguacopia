package modelo;
//Calcula posicion de personaje en pantalla, extrae lógica de posicionamiento de personaje
//permite reutilizar lógica de cámara en otras entidades o vistas
public class Camara {
    private final int pantallaAncho;
    private final int pantallaAltura;
    private final int dimensionTile;

    public Camara (int pantallaAncho, int pantallaAltura, int dimensionTile){
        this.pantallaAncho = pantallaAncho;
        this.pantallaAltura = pantallaAltura;
        this.dimensionTile = dimensionTile;

    }

    public int getPantallaX(){
        return pantallaAncho/2 - dimensionTile/ 2;
    }

    public int getPantallaY(){
        return pantallaAltura/2 - dimensionTile/ 2;
    }
}
