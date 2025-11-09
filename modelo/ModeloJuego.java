package modelo;

import controlador.ControlTeclas;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import modelo.item.ItemFactory;
import modelo.item.PocionFactory;


/**
 * Modelo del juego: contiene la lógica de colisiones, movimiento
 * de personaje e inventario, y expone solo getters para la vista.
 */
public class ModeloJuego implements SujetoObservable {

    //SUJETO // Para la puntuacion
    private List<Observador> observadores = new ArrayList<>();

    
    private final Personaje jugador;
    private final IModeloMapa modeloMapa;
    private final ChequearColision colision = new ChequearColision();
    private final int dimensionTile;
    private boolean mostrarInventario = false;
    private int puntajeTotal = 0;
    private String nombreJugador;

    //14/10
    private boolean enPausa = false;
    private int pasosContados;
    private EventoBatallaListener listener;
    private String mazmorraElegida;
    private Random random = new Random();

    //17/10 para implementar bossFinal
    private int batallasGanadas = 0;
    private boolean bossDerrotado;
    private final int umbralBoss = 5;

    // Para los pickups 21/10
    private static final int TILE_POCION = 4;
    private boolean hayPickupCerca = false;
    //Eventos de interacción
    // Estas variables almacenan las coordenadas del tile del pickup que esta cerca del jugador
    private int pickupCol = -1; 
    private int pickupFil = -1;

    // Catalogo de los pickups disponibles, guarda su "numero de tile" y su factory correspondiente
    private final Map<Integer, ItemFactory> catalogoPickups = Map.of(
        4, new PocionFactory("Pocion pequeña", "Cura 10 de HP", 10)
        // Aca se pueden agregar más pickups en el futuro
        // por ejemplo la de pocion grande xd
    );
    
    public ModeloJuego(Personaje jugador, IModeloMapa mapa, int dimensionTile) {
        this.jugador = jugador;
        this.modeloMapa = mapa;
        this.dimensionTile = dimensionTile;

        // Posición inicial desde spawn del mapa
        this.jugador.mundoX = mapa.getSpawnX();
        this.jugador.mundoY = mapa.getSpawnY();

        reiniciarProgreso();
    }

    //-------------------------------------------------------------------
    //  NUEVO!!! METODOS PARA LOS OBSERVADORES
    @Override
    public void registrarObservador (Observador observador) {
        observadores.add(observador);
    }
    
    @Override
    public void removerObservador(Observador observador){
        observadores.remove(observador);
    }

    @Override
    public void notificarObservadores(){
        for (Observador observador : observadores) {
            observador.actualizar(); //Notifica a todos los observadores que el puntaje cambio en este caso
        }
    }
    
    // Sumar puntaje de cada batalla o evento
    public void sumarPuntaje(int puntos) {
        puntajeTotal += puntos;
        notificarObservadores();
    }
    
    public void registrarVictoria(){ 
        batallasGanadas++;
        sumarPuntaje(100); //+100 puntos por cada batalla ganada con los monstruos basicoas
    } 

    public void registrarVictoriaBossFinal(){
        batallasGanadas++; // Incrementar contador
        bossDerrotado = true; // Marcar como derrotado
        sumarPuntaje(500); //+500 puntos por matar al final boss 
        System.out.println("Boss derrotado - (+500) puntos. Total batallas: " + batallasGanadas);
    }

    //-------------------------------------------------------------------

    

    //14/10
     public void setListener (EventoBatallaListener listener){
        this.listener = listener;
     }

     public void setMazmorraElegida(String mazmorra) {
        this.mazmorraElegida = mazmorra;
    }
    /**
     * Actualiza la lógica del modelo: colisiones, movimiento y toggle de inventario.
     * Se llama cada frame desde ControladorJuego.
     */
    public void update(ControlTeclas input) {
        // Resetear estado de colisión
        jugador.enColision = false;

        // Chequear colisiones contra el mapa lógico
        jugador.enColision = false;
        colision.chequearTile(jugador, modeloMapa.getMapaTileNum(), modeloMapa.getDimensionTile());

        // Mover y animar al jugador
        jugador.actualizar(modeloMapa.getMapaTileNum(),dimensionTile);

        actualizarPickupCercano();

        if (input.teclaRecoger) {
            if (hayPickupCerca) {
                recogerPickupSiCorresponde();
            }
            input.teclaRecoger = false;
        }

        // Alternar visibilidad de inventario
        if (input.teclaInventario) {
            mostrarInventario = !mostrarInventario;
            input.teclaInventario = false;
        }
         // Detectar batalla
        detectarBatalla();

        //Eventos del mapa
        if (input.teclaInteractuar){
           if (siJugadorCercaJefe() && debeActivarBoss()) {
        pausarJuego();
        listener.alIniciarBatalla(jugador, mazmorraElegida);
         input.teclaInteractuar = false;
         System.out.println("Evento de jefe activado");
            }
        }

    }
    //para congelar el hilo
    public void pausarJuego (){
        enPausa = true;
        System.out.println("Juego pausado");
    }

    public void reanudarJuego (){
        enPausa = false;
        System.out.println("Juego reanudado");
    }

    public void detectarBatalla (){
            if (jugador.seMovioTile)  {
            pasosContados++;
            jugador.seMovioTile = false;

            if(pasosContados >= 20){
                double prob = random.nextDouble();
                if (prob < 0.1) { //10% de chance
                        pausarJuego();
                        listener.alIniciarBatalla(jugador, mazmorraElegida);
                        pasosContados = 0;
                        
                }
            }
        }
    }
    
    public int getBatallasGanadas(){
        return batallasGanadas;
    }

    // Chequea si hay un pickup cerca del jugador
    public void actualizarPickupCercano() {
        int[][] mapa = modeloMapa.getMapaTileNum();

        int[] pf = colision.pickupCercano(jugador, mapa, dimensionTile);
        if (pf != null) {
            hayPickupCerca = true;
            pickupCol = pf[0];
            pickupFil = pf[1];
        } else {
            hayPickupCerca = false;
            pickupCol = -1;
            pickupFil = -1;
        }
    }

    private void recogerPickupSiCorresponde() {
    
        if (pickupCol < 0 || pickupFil < 0) return;

        int[][] mapa = modeloMapa.getMapaTileNum();
        if (mapa == null) return;

        if (mapa[pickupCol][pickupFil] == TILE_POCION) {

            var fabrica = catalogoPickups.get(TILE_POCION);
            if (fabrica == null) return;

            var inv = jugador.getInventario();
            var item = fabrica.fabricar();

            if (!inv.agregarItem(item)) {
                // Inventario lleno
                return;
            }

            // Reemplaza la pocion por un tile de suelo
            mapa[pickupCol][pickupFil] = 1;

            hayPickupCerca = false;
            pickupCol = -1;
            pickupFil = -1;

            System.out.println("Poción recogida");
        }
       
    }

    public boolean siJugadorCercaJefe() {
        int jugadorCol = (jugador.mundoX + jugador.areaSolida.width / 2) / dimensionTile;
        int jugadorFil = (jugador.mundoY + jugador.areaSolida.height / 2) / dimensionTile;

        int[][] mapa = modeloMapa.getMapaTileNum();

        for (int col = 0; col < mapa.length; col++) {
            for (int fil = 0; fil < mapa[0].length; fil++) {
                if (mapa[col][fil] == 5) { // jefe = tile 5
                    if (Math.abs(jugadorCol - col) <= 1 && Math.abs(jugadorFil - fil) <= 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    //Lanza el evento de Jefe
    public boolean hayEventoJefeCerca() {
    return siJugadorCercaJefe();
    }   
    
    public boolean debeActivarBoss(){
        return (batallasGanadas >= umbralBoss && !bossDerrotado);
    }

    public void marcarBossDerrotado(){
        bossDerrotado = true;
    }

    // —— Getters que la vista y el controlador usan al pintar ——
    public Personaje getJugador() {return jugador;}

    public IModeloMapa getModeloMapa() {return modeloMapa;}

    public int getDimensionTile() {return dimensionTile;}

    public boolean isMostrarInventario() {return mostrarInventario;}

    public boolean EstaPausado (){return enPausa;}

    public boolean isHayPickupCerca() { return hayPickupCerca; }
    public int getPickupCol() { return pickupCol; }
    public int getPickupFil() { return pickupFil; }


    // Obtener puntaje final
    public int getPuntajeTotal() {return puntajeTotal;}

    //Pedir el nombre del jugador
    public String getNombreJugador() {return nombreJugador;}

    // Setear el nombre del jugador
    public void setNombreJugador(String nombreJugador) {this.nombreJugador =  nombreJugador;}


     // Reiniciar puntaje (si querés reiniciar partida)
    public void reiniciarPuntaje() {
        puntajeTotal = 0;
        notificarObservadores();
    }

    //NUevo
    public void reiniciarProgreso() {
    this.batallasGanadas = 0;
    this.bossDerrotado = false;
    this.pasosContados = 0;
    this.enPausa = false;
    this.mostrarInventario = false;
    this.listener = null;
}
}
