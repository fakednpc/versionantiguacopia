package vista;

import javax.swing.*;

import modelo.Limpiable;

import java.awt.*;

public class VistaUI extends JPanel implements Limpiable {

    private JProgressBar barraVida;
    private JProgressBar barraMonstruo;
    private JLabel textoMonstruos;
    private JButton botonInventario;
    private JButton botonOpciones;
    private ImageIcon iconoCalavera;
    private JLabel textoPuntaje;

    public VistaUI() {
        setOpaque(false);
        setLayout(new GridLayout(3, 3)); // Dividimos la pantalla en 3x3
        
        // Creamos las 9 celdas
        JPanel[] celdas = new JPanel[9];
        for (int i = 0; i < 9; i++) {
            celdas[i] = new JPanel();
            celdas[i].setOpaque(false);
            celdas[i].setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
            
            add(celdas[i]);
        }

        //--BARRA DE LA VIDA ---------------------------------
        barraVida = new JProgressBar(0, 14);
        barraVida.setValue(10); // Valor inicial
        barraVida.setForeground(Color.RED); 
        barraVida.setBackground(new Color(40, 40, 40));
        barraVida.setPreferredSize(new Dimension(200, 30));
        barraVida.setStringPainted(true); 

        // PANEL INTERMEDIO PARA ALINEAR A LA IZQUIERDA
        JPanel panelVida = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelVida.setOpaque(false);
        panelVida.add(barraVida);

        // CONFIGURAR LA CELDA6 CON BORDERLAYOUT
        celdas[6].setLayout(new BorderLayout()); 
        celdas[6].setOpaque(false);
        celdas[6].setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0)); 

        // AGREGARLO
        celdas[6].add(panelVida, BorderLayout.SOUTH);


        // ------- BARRA DEL MONSTRUO--------------------------------------------------
        barraMonstruo = new BarraMonstruo("/res/monstruos/icono_calavera.png", 0, 5);
        barraMonstruo.setValue(0);
        barraMonstruo.setForeground(Color.RED);
        barraMonstruo.setBackground(new Color(40, 40, 40));
        barraMonstruo.setStringPainted(false);
        barraMonstruo.setPreferredSize(new Dimension(220, 30)); // 220x30

        textoMonstruos = new JLabel("0/5 BATALLAS");
        textoMonstruos.setForeground(Color.WHITE);
        textoMonstruos.setFont(new Font("Monospaced", Font.BOLD, 16));

        textoPuntaje = new JLabel("PUNTAJE: 0");
        textoPuntaje.setForeground(Color.WHITE);
        textoPuntaje.setFont(new Font("Monospaced", Font.BOLD, 14));

        //PANEL PUNTAJE
        JPanel panelPuntaje = new JPanel(new FlowLayout(FlowLayout.TRAILING, 10, 0));
        panelPuntaje.setOpaque(false);
        panelPuntaje.setBorder(BorderFactory.createEmptyBorder(12, 10, 0, 0));  // margen superior e izquierdo
        panelPuntaje.add(textoPuntaje);
        celdas[3].add(panelPuntaje);

        //PANEL BATALLAS
        JPanel panelTexto = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0)); // margen horizontal, sin margen vertical
        panelTexto.setOpaque(false);
        panelTexto.setBorder(BorderFactory.createEmptyBorder(12, 10, 0, 0));  // margen superior e izquierdo
        panelTexto.add(textoMonstruos);

        celdas[1].add(panelTexto);


        JPanel panelMonstruo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelMonstruo.setOpaque(false);
        panelMonstruo.setPreferredSize(new Dimension(400, 45));
        panelMonstruo.setMaximumSize(new Dimension(380, 45)); 
        panelMonstruo.add(barraMonstruo);
        //panelMonstruo.add(textoMonstruos);

        celdas[0].add(panelMonstruo);

        // --- BOTONES ---
        botonOpciones = new JButton("Opciones");
        botonInventario = new JButton("Inventario");

        celdas[2].setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10)); // arriba derecha
        celdas[5].setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10)); // centro derecha
        // --ICONO DE CALAVERA ---
        iconoCalavera = new ImageIcon(getClass().getResource("/res/monstruos/icono_calavera.png"));
    }

    // ---- GETTERS Y SETTERS
    public void cambiarColorBarra(int nivel) {
        if (nivel <= 6)
            barraVida.setForeground(Color.RED);
        else if (nivel <= 9)
            barraVida.setForeground(Color.ORANGE);
        else
            barraVida.setForeground(new Color(0, 200, 0));
    }

    
    public void setTextoPuntaje(int puntaje) {
        textoPuntaje.setText("PUNTAJE: " + puntaje);
    }

    public void setNivelBarra(int nivel) {
        barraVida.setValue(nivel);
        barraVida.setString("Vida: " + nivel + "/15");
        cambiarColorBarra(nivel);
    }

    public void actualizarTextoFinalBoss() {
        textoMonstruos.setText("FINAL BOSS!");
        barraMonstruo.setValue(5);
    }

    public void setBatallasRestantes(int cantidad) {
        barraMonstruo.setValue(cantidad);
        textoMonstruos.setText(cantidad + "/5 BATALLAS");
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (iconoCalavera != null) {
            Image img = iconoCalavera.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            int x = barraMonstruo.getX() + (barraMonstruo.getWidth() - 24) / 2;
            int y = barraMonstruo.getY() + (barraMonstruo.getHeight() - 24) / 2;
            g.drawImage(img, x, y, this);
        }
    }

    @Override
    public void limpiar() {
    barraMonstruo.setValue(0);
    textoMonstruos.setText("0/5 BATALLAS");
    textoPuntaje.setText("Puntaje = 0");
    repaint();
    }


}
