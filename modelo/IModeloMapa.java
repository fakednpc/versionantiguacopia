package modelo;

/**
 * Interfaz que expone solo lo necesario para que la vista pueda dibujar el mapa.
 * La vista no necesita saber nada más del modelo.
 */
public interface IModeloMapa {

    int[][] getMapaTileNum();
    int getMaxCol();
    int getMaxFil();
    int getDimensionTile();
    int getSpawnX();
    int getSpawnY();
}