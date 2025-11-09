package vista;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import modelo.Limpiable;
import modelo.ModeloBatalla;
import modelo.ObservadorBatalla;

public class VistaBatalla extends JPanel implements ObservadorBatalla, Limpiable {
    
    private ModeloBatalla modelo;

    private JLabel labelAventurero = new JLabel();
    private JLabel labelEnemigo = new JLabel();
   
    private PanelTexto panelConTexto;
    private Panel panelConFondo; // se podría quitar y reutilizar clase panel de menu y seleccion
    private PanelSprites panelSprites;
    private PanelBotones panelBotones;

    private final JPanel capaFlotante = new JPanel(null); // layout absoluto
    private final JLayeredPane capas = new JLayeredPane();
    private VistaInventario vistaInventario;

    public VistaBatalla (){
        setLayout(null);
        setPreferredSize(new Dimension(720, 672));

        capas.setBounds(0, 0, getWidth(), getHeight());
        capas.setLayout(null);
        add(capas);

        panelConFondo = new Panel();
        panelConFondo.setLayout(new GridBagLayout()); // importante fijar el layout antes de usar GridBagConstraints
        panelConFondo.setSize(720, 672);
        capas.add(panelConFondo, Integer.valueOf(0)); // fondo

        panelSprites = new PanelSprites();
        panelBotones = new PanelBotones();
        panelConTexto = new PanelTexto();

        GridBagConstraints gbc = new GridBagConstraints();

        // PanelSprites
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weighty = 3;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        
        panelConFondo.add(panelSprites, gbc);

        // PanelTexto
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 60, 0, 60);
        panelConFondo.add(panelConTexto, gbc);

        // Barra info (labels)
        JPanel panelInfo = new JPanel(new GridLayout(1, 2));
        labelAventurero.setOpaque(true);
        labelAventurero.setBackground(Color.BLACK);
        labelAventurero.setForeground(Color.WHITE);
        labelEnemigo.setOpaque(true);
        labelEnemigo.setBackground(Color.BLACK);
        labelEnemigo.setForeground(Color.WHITE);
        panelInfo.setOpaque(false);
        panelInfo.add(labelAventurero);
        panelInfo.add(labelEnemigo);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 60, 0, 60);
        panelConFondo.add(panelInfo, gbc);

        // PanelBotones
        gbc.gridy = 3;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.fill = GridBagConstraints.BOTH;
        panelConFondo.add(panelBotones, gbc);


        getBotonInventario().setFocusable(false);
        


        }

        public void setVistaInventario(VistaInventario vistaInventario) {
        if (this.vistaInventario == vistaInventario) return; // ya está integrada

        if (this.vistaInventario != null) {
            capas.remove(this.vistaInventario); // quita la anterior si existía
        }

        this.vistaInventario = vistaInventario;
        vistaInventario.setVisible(false);
        vistaInventario.setBounds((720 - 400) / 2,(672 - 300) / 2, 400, 300 );
        capas.add(vistaInventario, Integer.valueOf(1)); // encima del fondo
        vistaInventario.setFocusable(false);
        capas.revalidate();
        capas.repaint();
        }

        public void setModelo(ModeloBatalla modeloBatalla){
            if(this.modelo == modeloBatalla) return;
            if(this.modelo != null) {
                this.modelo.quitarObservador(this);
            }

            this.modelo = modeloBatalla;
            if(this.modelo != null){
                this.modelo.agregarObservador(this);
                SwingUtilities.invokeLater(() -> {
                panelSprites.setImagen(modelo.getEnemigo().getSpriteReposo());
                actualizarEstado();
                revalidate();
                repaint();
            });
        } else {
            SwingUtilities.invokeLater(() -> {
                labelAventurero.setText("");
                labelEnemigo.setText("");
                panelConTexto.limpiar();
                panelSprites.setImagen(null);
                revalidate();
                repaint();
            });
        }


    }
            

        
    public void actualizarEstado (){
        if(modelo == null) return;
        labelAventurero.setText("Aventurero: " + modelo.getAventurero().getNombre()+"           HP: " + modelo.getAventurero().getVida()+ "/" + modelo.getAventurero().getVidaMax());
       labelEnemigo.setText("Enemigo: " + modelo.getEnemigo().getNombre() + "           HP: " + modelo.getEnemigo().getVida()+"/"+modelo.getEnemigo().getVidaMax());
    }

    public void reiniciarVista() {
    labelAventurero.setText("");
    labelEnemigo.setText("");
    panelConTexto.limpiar();
    panelSprites.setImagen(null);
    panelBotones.habilitarBotones(true); // asegurás que estén activos
    revalidate();
    repaint();
    }

    
    // Getters para que el controlador añada y quite listeners
    public PanelBotones getPanelBotones(){ return panelBotones;}
    public PanelSprites getPanelSprites() { return panelSprites; }
    public PanelTexto getPanelTexto() { return panelConTexto; }
    public JButton getBotonAtacar() { return panelBotones.getBotonAtacar(); }
    public JButton getBotonDefender() { return panelBotones.getBotonDefender(); }
    public JButton getBotonHuir() { return panelBotones.getBotonHuir(); }
    public JButton getBotonInventario(){ return panelBotones.getBotonInventario();};
    public JLabel getVidaAventurero() { return labelAventurero; }
    public JLabel getVidaEnemigo() { return labelEnemigo; }
    public VistaInventario getVistaInventario(){ return vistaInventario;}
    public JLayeredPane getCapas() {
    return capas;
    }

    
    @Override
    public void alTerminarBatalla (){
        if(modelo == null) return;
        actualizarEstado();
        
    if (!modelo.getEnemigo().estaVivo()){
            JOptionPane.showMessageDialog(this, "Victoria. Ganó el aventurero");

    }
    
        
    }
    
    @Override
    public void alCambiarEstado (){
        if(modelo == null) return;
        actualizarEstado();

    }

    @Override
    public void alElegirAccion (int accion, String nombre, int puntos){
        if(modelo == null) return;
        switch (accion) {
            case 1:
                panelConTexto.agregarMensaje(nombre + " ATACA con " + puntos + " puntos de ataque");
                break;
        
            case 2:
                panelConTexto.agregarMensaje(nombre + " se DEFIENDE con " + puntos + " puntos de defensa");
                break;
        }
    }

    @Override
    public void alTurnoEnemigo(){
        if(modelo == null) return;
        panelBotones.habilitarBotones(false);
        panelSprites.setImagen(modelo.getEnemigo().getSpriteAtaque());
        Timer timer = new Timer(1500, e ->{
            
            modelo.procesarTurnoEnemigo();
            panelSprites.setImagen(modelo.getEnemigo().getSpriteReposo());
            if (modelo.getAventurero().estaVivo()){
                panelBotones.habilitarBotones(true);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public void limpiar() {
    if (modelo != null) {
        modelo.quitarObservador(this);
    }
    modelo = null;

    reiniciarVista(); //reutilizada porque es una limpieza visual
    }     

    @Override
    public void doLayout() {
    super.doLayout();
    capas.setBounds(0, 0, getWidth(), getHeight());

    int w = panelConFondo.getWidth();
    int h = panelConFondo.getHeight();
    int x = (getWidth() - w) / 2;
    int y = (getHeight() - h) / 2;

    panelConFondo.setBounds(x, y, w, h);
    }
    
}

