package modelo.item;

import modelo.Personaje;

public abstract class Item {
    private String nombre;
    private String descripcion;

    public Item(String nombre, String descripcion){
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre(){
        return this.nombre;
    }

    public String getDescripcion(){
        return this.descripcion;
    }

    public abstract void usarItem(Personaje objetivo);
}
