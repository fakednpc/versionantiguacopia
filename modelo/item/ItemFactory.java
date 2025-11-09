
package modelo.item;

public abstract class ItemFactory {

     public final Item fabricar() {
        Item item = crearItem();
        return item;
    }

    public abstract Item crearItem();

}
