package controlador;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ControlTeclas implements KeyListener {

     public boolean upPressed, downPressed, leftPressed, rightPressed, escPressed, enterPressed;
     public boolean teclaInventario, teclaRecoger, teclaInteractuar;
     //15/10 - - - para que no siga caminando detras de la batalla 
     public boolean activo = true;

    @Override
    public void keyTyped(KeyEvent e) {
       
    }


    @Override
    public void keyPressed(KeyEvent e) {
       
     int codigo = e.getKeyCode(); 

     if(codigo == KeyEvent.VK_UP){
          upPressed = true;
     }

     if(codigo == KeyEvent.VK_DOWN){
          downPressed = true;
     }

     if(codigo == KeyEvent.VK_LEFT){
          leftPressed = true;
     }

     if(codigo == KeyEvent.VK_RIGHT){
          rightPressed = true;
     }

     if (codigo == KeyEvent.VK_I) {
          teclaInventario = true;
          System.out.println("I PRESSED");
     }

     if (codigo == KeyEvent.VK_E) {
          teclaRecoger = true;
     }

     if (codigo == KeyEvent.VK_ENTER) {
          enterPressed = true;
     }

     if (codigo == KeyEvent.VK_SPACE) {
          teclaInteractuar = true;
     }

     if (codigo == KeyEvent.VK_ESCAPE) {
          escPressed = true;
          System.err.println("ESC");
     }

          /*System.out.println("KEY PRESSED: " + e.getKeyCode()
            + " → U:" + upPressed + " D:" + downPressed
            + " L:" + leftPressed + " R:" + rightPressed);*/
    }

    //este metodo evita que al presionarse las teclas direccionales opuestas al mismo tiempo no deba moverse el personaje
    @Override
    public void keyReleased(KeyEvent e) {
       int codigo = e.getKeyCode();

        if(codigo == KeyEvent.VK_UP){
            upPressed = false;
       }

       if(codigo == KeyEvent.VK_DOWN){
            downPressed = false;
       }

       if(codigo == KeyEvent.VK_LEFT){
            leftPressed = false;
       }

       if(codigo == KeyEvent.VK_RIGHT){
            rightPressed = false;
       }

       if(codigo == KeyEvent.VK_I){
          teclaInventario = false;
          System.out.println("DEJE DE PRESIONAR I");
       }

       if(codigo == KeyEvent.VK_SPACE){
          teclaInteractuar = false;
       }

       if (codigo == KeyEvent.VK_ESCAPE) {
          escPressed = true;
       }
    }

    //15-10
    public void habilitarTeclas(boolean habilitada) {
        this.activo = habilitada;
        if (!habilitada) limpiarTeclas();
    }

    public boolean estaHabilitado() {
        return activo;
    }

    public void limpiarTeclas() {
        upPressed = downPressed = leftPressed = rightPressed = false;
        escPressed = false;
        teclaInventario = false;
        teclaInteractuar = false;
    }


}
