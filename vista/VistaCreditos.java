package vista;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class VistaCreditos extends JPanel {
    private Boton botonVolver;
    private Fondo fondo;

    public VistaCreditos () {
        setLayout(new BorderLayout());
        fondo = new Fondo("/assets/img/fondoCreditos2.png"); //Fondo 
        fondo.setLayout(null);
        add(fondo,BorderLayout.CENTER);

        this.botonVolver = new Boton("Volver"); //Creo el boton
        this.botonVolver.setBounds(500, 600, 150, 40); 
        fondo.add(botonVolver); //
    }

    public JButton getBotonVolver() {
        return this.botonVolver;
    }
}
