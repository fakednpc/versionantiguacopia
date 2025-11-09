package controlador;

import java.awt.Container;
import java.util.Arrays;

import javax.swing.JPanel;

import modelo.Inventario;
import modelo.ModeloBatalla;
import modelo.ModeloJuego;
import modelo.Personaje;
import modelo.Sonido;
import modelo.item.Item;
import vista.VistaInventario;

public class ControladorInventario {

    private final ControlTeclas input;
    private final Inventario inventario;
    private final VistaInventario vista;
    private final Personaje personaje;
    private ControladorUI controladorUI;
    private ModeloJuego modeloJuego;
    private ModeloBatalla modeloBatalla;
    private boolean inventarioVisible = false;

    public ControladorInventario(ControlTeclas input, Inventario inventario, Personaje personaje, VistaInventario vista, ControladorUI controladorUI, ModeloJuego modeloJuego) {
        this.input = input;
        this.inventario = inventario;
        this.personaje = personaje;
        this.vista = vista;
        this.controladorUI = controladorUI;
        this.modeloJuego = modeloJuego;
    }

    public void actualizarInventario() {
        
        if (input.teclaInventario) {
            inventarioVisible = !inventarioVisible;
            vista.setVisible(!vista.isVisible());
            input.teclaInventario = false;
        }

        if (!vista.isVisible()) return;

        // Navegación
        if (input.upPressed) {
            System.out.println("↑ PRESSED");
            vista.moverSeleccion(-1);
            input.upPressed = false;
        }
        if (input.downPressed) {
            System.out.println("↓ PRESSED");
            vista.moverSeleccion(+1);
            input.downPressed = false;
        }

        // Consumir
        if (input.enterPressed) {
            Item item = vista.getItemSeleccionado();
            if (item != null) {
                item.usarItem(personaje); //
                Sonido.getInstancia().playEfecto(9);

                controladorUI.actualizarVida(); //actualiza la vida
                modeloJuego.sumarPuntaje(-30); // Si consumis una pocion te resta puntos en este caso -3O 
                
                inventario.eliminarItem(item);

                if (modeloBatalla != null) { //para actualizar los labels en vistaBatalla
                    modeloBatalla.notificarCambios();
                }
            } else {
                System.out.println("No hay items");
            }
            input.enterPressed = false;
        }
    }

    public boolean isInventarioVisible() {
        return inventarioVisible;
    }

    //para poder unir con sistema de batallas 
    public VistaInventario getVistaInventario(){ //nuevo
        return vista;
    }

    //Separados del método ActualizarInventario para poder enlazar en batalla con boton
    public void cerrarInventario() {
    if (inventarioVisible) {
        inventarioVisible = false;
        vista.setVisible(false);
        input.limpiarTeclas();
    }
    }

    public void alternarInventario() { //para boton inventario en vistaBatalla
    
    inventarioVisible = !inventarioVisible;
    vista.setVisible(inventarioVisible);
    vista.revalidate();
    vista.repaint();

    System.out.println("Inventario visible: " + inventarioVisible);
    System.out.println("Vista inventario: " + vista);
    System.out.println("Vista inventario visible: " + vista.isVisible());
    System.out.println("Inventario en layout: " + Arrays.toString(vista.getParent().getComponents()));

    }


    //ayuda a poder posicionarlo en la capa añadida a vistaBatalla, y a que no quede atrapado en vistaBatalla
    public void reubicarInventarioEn(Container nuevoContenedor) {
    Container contenedorAnterior = vista.getParent();
    if (contenedorAnterior != null) {
        contenedorAnterior.remove(vista); // sin instanceof
    }

    nuevoContenedor.setLayout(null); // aseguramos layout absoluto
    vista.setBounds((720 - 400) / 2, (672 - 300) / 2, 400, 300);
    nuevoContenedor.add(vista);
    nuevoContenedor.setComponentZOrder(vista, 0);
    nuevoContenedor.revalidate();
    nuevoContenedor.repaint();
    }
    
    //para poder actualizar los labels de batalla si se consume un item
    public void setModeloBatalla (ModeloBatalla modeloBatalla){
        this.modeloBatalla = modeloBatalla;
    }
    
}
