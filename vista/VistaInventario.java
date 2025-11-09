package vista;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import modelo.Inventario;
import modelo.Observador;
import modelo.item.Item;

public class VistaInventario extends JPanel implements Observador{

    private final Color colorFondo = new Color(0, 0, 0, 200);
    private final Color colorBorde = new Color(255, 255, 255);
    private final int radio = 15;

    private final JPanel contenedorItems;
    private final JLabel titulo;
    private final List<JLabel> slots = new ArrayList<>();
    private int indiceSeleccionado = 0;

    private final Inventario inventario;

    public VistaInventario(Inventario inventario) {
        this.inventario = inventario;

        setOpaque(false);
        setLayout(new BorderLayout());
        
        titulo = new JLabel("Inventario", SwingConstants.CENTER);
        titulo.setFont(new Font("Monospaced", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        contenedorItems = new JPanel();
        contenedorItems.setOpaque(false);
        contenedorItems.setLayout(new GridLayout(inventario.getCapacidadMaxima(), 1, 0, 5));
        contenedorItems.setBorder(new EmptyBorder(20, 50, 20, 50));
        add(contenedorItems, BorderLayout.CENTER);

        inventario.registrarObservador(this);
        reconstruirInventario();
    }

// 

     private void reconstruirInventario() {
        contenedorItems.removeAll();
        slots.clear();

        final int capacidad = inventario.getCapacidadMaxima();
        final java.util.List<Item> items = inventario.getItems();

        // Para que el indice quede dentro del rango de items
        if (indiceSeleccionado >= capacidad) indiceSeleccionado = capacidad - 1;
        if (indiceSeleccionado < 0) indiceSeleccionado = 0;

        for (int i = 0; i < capacidad; i++) {
            String texto = (i < items.size())
                    ? "> " + items.get(i).getNombre()
                    : "> [Vacío]";

            JLabel slot = new JLabel(texto, SwingConstants.LEFT);
            slot.setFont(new Font("Monospaced", Font.PLAIN, 16));
            slot.setForeground(Color.WHITE);
            slot.setOpaque(false); // lo cambiamos al resaltar
            slot.setBorder(new EmptyBorder(4, 10, 4, 10));

            contenedorItems.add(slot);
            slots.add(slot);
        }

        aplicarEstilosSeleccion();
        contenedorItems.revalidate();
        contenedorItems.repaint();
    }

    private void aplicarEstilosSeleccion() {
        for (int i = 0; i < slots.size(); i++) {
            JLabel slot = slots.get(i);
            if (i == indiceSeleccionado) {
                slot.setOpaque(true);
                slot.setBackground(new Color(60, 60, 60, 180));
                slot.setFont(slot.getFont().deriveFont(Font.BOLD, 18f));
            } else {
                slot.setOpaque(false);
                slot.setBackground(new Color(0,0,0,0));
                slot.setFont(slot.getFont().deriveFont(Font.PLAIN, 16f));
            }
        }
        contenedorItems.repaint();
    }

    public void moverSeleccion(int delta) {
        
            int capacidad = inventario.getCapacidadMaxima();
            // movimiento circular
            indiceSeleccionado = (indiceSeleccionado + delta + capacidad) % capacidad;
            aplicarEstilosSeleccion();
    }

    public int getIndiceSeleccionado() {
        return indiceSeleccionado;
    }

    public Item getItemSeleccionado() {
        java.util.List<Item> items = inventario.getItems();
        return (indiceSeleccionado < items.size()) ? items.get(indiceSeleccionado) : null;
    }


    @Override
    public void actualizar() {
        // Actualizamos en el hilo de Swing
        if (SwingUtilities.isEventDispatchThread()) {
            reconstruirInventario();
        } else {
            SwingUtilities.invokeLater(this::reconstruirInventario);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        Shape fondo = new RoundRectangle2D.Double(0, 0, w, h, radio, radio);
        g2.setColor(colorFondo);
        g2.fill(fondo);

        g2.setStroke(new BasicStroke(3f));
        g2.setColor(colorBorde);
        g2.draw(fondo);

        g2.dispose();
    }

   
}

