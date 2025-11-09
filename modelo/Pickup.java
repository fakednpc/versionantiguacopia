package modelo;

import modelo.item.Item;

public class Pickup {

    private final Item item;      // referencia al ítem (por ejemplo, una poción)
    private boolean recogido;     // si el jugador ya lo tomó

    public Pickup(int x, int y, Item item) {
        this.item = item;
        this.recogido = false;
    }

    public Item getItem() { return item; }

    public boolean fueRecogido() { return recogido; }
    public void recoger() { this.recogido = true; }
}
