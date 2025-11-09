package modelo.item;

public class PocionFactory extends ItemFactory {
    
    private final String nombre;
    private final String descripcion;
    private final int puntos;

    public PocionFactory(String nombre, String descripcion, int puntos) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.puntos = puntos;
    }

    @Override
    public Item crearItem() {
        return new Pocion(nombre, descripcion, puntos);
    }

}
