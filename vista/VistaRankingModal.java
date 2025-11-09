package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

public class VistaRankingModal extends JDialog {

    private JTextArea areaTexto;
    private JButton botonVolver;

    private final Color COLOR_FONDO     = new Color(65, 65, 70);
    private final Color COLOR_PANEL     = new Color(95, 95, 100);
    private final Color COLOR_TEXTO     = new Color(240, 235, 220);
    private final Color COLOR_BOTON     = new Color(130, 120, 110);

    public VistaRankingModal(JFrame padre, List<String> top5) {
        super(padre, true);
        setUndecorated(true);
        setSize(500, 290);
        setLocationRelativeTo(padre);
        setLayout(new BorderLayout());
        setBackground(new Color(0, 0, 0, 0)); // Fondo transparente

        // Esquinas redondeadas
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 40, 40));

        // Panel principal con color de fondo
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(COLOR_FONDO);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título
        JLabel titulo = new JLabel("TOP 5 JUGADORES", SwingConstants.CENTER);
        titulo.setFont(new Font("Monospaced", Font.BOLD, 24));
        titulo.setForeground(COLOR_TEXTO);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // Área de texto
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 16));
        areaTexto.setBackground(COLOR_PANEL);
        areaTexto.setForeground(COLOR_TEXTO);
        areaTexto.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        areaTexto.setCaretColor(COLOR_TEXTO);
        panelPrincipal.add(new JScrollPane(areaTexto), BorderLayout.CENTER);

        // Botón volver
        botonVolver = new JButton("Volver");
        botonVolver.setFont(new Font("Serif", Font.BOLD, 16));
        botonVolver.setBackground(COLOR_BOTON);
        botonVolver.setForeground(COLOR_TEXTO);
        botonVolver.setFocusPainted(false);
        botonVolver.setBorderPainted(false);
        botonVolver.setPreferredSize(new Dimension(100, 35));
        botonVolver.addActionListener(e -> dispose());

        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(COLOR_FONDO);
        panelBoton.add(botonVolver);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        // Agregar el panel principal
        add(panelPrincipal);

        // Cargar los datos del ranking
        setRanking(top5);
    }

    public void setRanking(List<String> top5) {
        if (top5 == null || top5.isEmpty()) {
            areaTexto.setText("No hay puntajes registrados aún.");
            return;
        }

        StringBuilder texto = new StringBuilder();
        int pos = 1;
        for (String linea : top5) {
            texto.append(pos).append(". ").append(linea).append("\n");
            pos++;
        }
        areaTexto.setText(texto.toString());
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo redondeado
        Shape forma = new RoundRectangle2D.Double(3, 3, getWidth() - 6, getHeight() - 6, 40, 40);

        // Fondo interno
        g2.setColor(COLOR_FONDO);
        g2.fill(forma);

        g2.dispose();
        super.paint(g);
    }
}
