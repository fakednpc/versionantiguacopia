package vista;

import java.awt.*;


import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PanelTexto extends JPanel{
    private JTextArea areaTexto;

    public PanelTexto (){
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        setLayout(new BorderLayout());

        areaTexto = new JTextArea(1,1);
        areaTexto.setEditable(false);
        areaTexto.setForeground(Color.WHITE);
        areaTexto.setBackground(Color.BLACK);
        areaTexto.setFont(new Font("Monospaced",Font.PLAIN, 18));

        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        add(scroll, BorderLayout.CENTER);
        setPreferredSize(new Dimension(400,100));
    }

    public void agregarMensaje (String mensaje){
        areaTexto.append("\n"+ mensaje);
        areaTexto.setCaretPosition(areaTexto.getDocument().getLength());
    }

    public void limpiar() {
        areaTexto.setText("");
        areaTexto.setCaretPosition(0);
    }
}
