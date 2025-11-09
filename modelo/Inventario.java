package modelo;

import java.util.ArrayList;
import java.util.List;
import modelo.item.Item;

public class Inventario implements SujetoObservable {
    
    private int capacidadMaxima;
    private List<Item> items;
    private List<Observador> observadores;

    public Inventario(){
        this.capacidadMaxima = 5;
        this.items = new ArrayList<>();
        this.observadores = new ArrayList<>();
        
    }
    
    // Métodos de Observer
    @Override
    public void registrarObservador(Observador obs) {
        observadores.add(obs);
    }
    @Override
    public void removerObservador(Observador obs) {
        observadores.remove(obs);
    }
    @Override
    public void notificarObservadores() {
        for (Observador obs : observadores) {
            obs.actualizar();
        }
    }

    public boolean agregarItem(Item item){
        if (items.size() < capacidadMaxima) {
            items.add(item);
            notificarObservadores();
            return true;
        }
        return false;
    }

    public boolean eliminarItem(Item item){
        boolean eliminado = items.remove(item);
        if (eliminado) {
            notificarObservadores();
        }
        return eliminado;
    }

    public List<Item> getItems(){
        return items;
    }

    public int getCapacidadMaxima(){
        return capacidadMaxima;
    }

}
