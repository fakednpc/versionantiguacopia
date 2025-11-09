package modelo; 

import javax.sound.sampled.*;
import java.net.URL;

public class Sonido {

    private static Sonido instancia; //Nos garantiza una unica instancia de Singleton 

    //Para gestionar el sonido se uso un array que contiene la ruta de las canciones, una para el menu principal y la otra para el juego
    //mas los efectos de sonido 

    private Clip clip;
    private URL sonidoUrl[] = new URL[30]; //Usamos un Array + el path de las canciones
    private int volumenEscala = 1; //por default lo dejo en escala 1  (VOLUMEN MEDIO)  0/1/2/3/4/5
    private int volumenEfectoEscala = 1; //Esto es para el Efecto, lo dejo en escala 3 por default
    private float volumen; //voluemn actual
    private FloatControl controlarSonido;
    private boolean estaSonando = false;

    private Sonido() { //Sin constructor y clase privada 
        sonidoUrl [0] = getClass().getResource("/assets/sounds/music/Shadowfell.wav");
        sonidoUrl [1] = getClass().getResource("/assets/sounds/music/Lessmournful.wav");
        sonidoUrl [2] = getClass().getResource("/assets/sounds/audio/select_options.wav"); //sonido de eleccion del menu
        sonidoUrl [3] = getClass().getResource("/assets/sounds/audio/magical-level-up.wav");
        sonidoUrl [4] = getClass().getResource("/assets/sounds/audio/Battle_Grunt8.wav");
        sonidoUrl [5] = getClass().getResource("/assets/sounds/audio/select_option_in_game.wav");
        sonidoUrl [6] = getClass().getResource("/assets/sounds/audio/monstruo_final_boss.wav");
        sonidoUrl [7] = getClass().getResource("/assets/sounds/audio/level-up-02.wav");
        sonidoUrl [8] = getClass().getResource("/assets/sounds/audio/curar.wav");
        sonidoUrl [9] = getClass().getResource("/assets/sounds/audio/absorve2.wav");
        // setSonido(0); 
    }

    //Instancia del singleton 
    public static Sonido getInstancia() { 
        if (instancia == null){
            instancia = new Sonido(); //Creo la instancia si no esta creada
        }
        return instancia; // Devuelvo siempre la instancia original
    }


    //Seteas el sonido mediante el indice; 
    public void setSonido(int i) {
        try {  
            AudioInputStream ais = AudioSystem.getAudioInputStream(sonidoUrl [i]);  //Utiliza el indice del array
            clip = AudioSystem.getClip();  
            clip.open(ais); //Se reproduce
            controlarSonido = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN); //Se puede pasar una variable a el Clip para cambiar de volumen
            chequearVolumen(); //aplica el volumen actual ni bien se carga el sonido
        } catch (Exception e) {
            System.err.println("Error no se puedo cargar la cancion" + i + ": " + e.getMessage());
        }
    }

    public void play() {
        clip.start();
        estaSonando = true;
    }


    public void loop () {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }


    public void stop () {
        clip.stop(); 
        estaSonando = false;
    }
      
    public int getVolumenActual() {
        return volumenEscala;
    }

    public void subirVolumen() {
        if (volumenEscala < 5) {
            volumenEscala ++;
            chequearVolumen();
        }
    }

    public void bajarVolumen() {
        if (volumenEscala > 0) {
            volumenEscala --;
            chequearVolumen();
        }
    }

    public int getVolumenEfectoActual() {
        return volumenEfectoEscala;
    }

    public void subirVolumenEfecto() {
        if (volumenEfectoEscala < 5) {
            volumenEfectoEscala++;
        }
    }

    public void bajarVolumenEfecto() {
        if (volumenEfectoEscala > 0) {
            volumenEfectoEscala--;
        }
    }

    public void chequearVolumen() {
        switch (volumenEscala) {
            case 0: volumen = -80f; break; //Volumen minimo 
            case 1: volumen = -20f; break;
            case 2: volumen = -12f; break;
            case 3: volumen = -5f; break;
            case 4: volumen = 1f; break;
            case 5: volumen = 6f; break; //Volumen maximoo
        }
        controlarSonido.setValue(volumen);
    }
    
    public boolean getSonido () {
        return estaSonando;
    }
    
    //Creamos un clip temporal para que los botones tengan sonido
    public void playEfecto(int i) { 
        try {
            Clip efecto = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(sonidoUrl[i]);
            efecto.open(ais);

            //Ajustar volumen del efecto
            FloatControl controlEfecto = (FloatControl) efecto.getControl(FloatControl.Type.MASTER_GAIN);
            float volumenEfecto = switch (volumenEfectoEscala) {
                case 0 -> -80f;
                case 1 -> -20f;
                case 2 -> -12f;
                case 3 -> -5f;
                case 4 -> 1f;
                case 5 -> 6f;
                default -> -5f;
            };

            controlEfecto.setValue(volumenEfecto);
            efecto.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    } //en los controladores seria setPlayEfecto(2);
}