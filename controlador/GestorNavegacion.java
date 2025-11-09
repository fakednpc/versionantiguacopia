package controlador;

import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import modelo.Limpiable;
import vista.VentanaPrincipal;

public class GestorNavegacion{
    private String pantallaActual;
    private JPanel contenedor;
    private CardLayout cartas;
    private final List<Limpiable> vistasLimpiables = new ArrayList<>();

    public GestorNavegacion(VentanaPrincipal ventana){
        this.contenedor = ventana.getContenedor();
        this.cartas = (CardLayout) contenedor.getLayout();
    }

public void mostrarPantalla(String id){
    pantallaActual = id; //nuevo
    cartas.show(contenedor , id);
    contenedor.revalidate();
    contenedor.repaint();
}
public void registrar(String id, JPanel vista){
    contenedor.add(vista, id);
}

public void registrarLimpiable (Limpiable vista){
    vistasLimpiables.add(vista); //a la lista de vistas limpiables
}

public void limpiarVistas() { // por medio de Gestor puede limpiar 
    for (Limpiable vista : vistasLimpiables) {
        vista.limpiar();
    }
}

public String getPantallaActual(){
    return pantallaActual;
}

}
