package modelo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import tile.Tile;

/**
 * ModeloMapa representa la información lógica de un mapa del juego.
 * No dibuja ni carga imágenes; solo almacena datos.
 */
public class ModeloMapa implements IModeloMapa {

    private final int maxCol;
    private final int maxFil;
    private final int dimensionTile;
    private final int[][] mapaTileNum;
    private Tile[] tiles;
    public int spawnX;
    public int spawnY;

     // Crea el modelo del mapa leyendo un archivo de texto con los índices de tiles.
     //rutaMapa ruta al archivo dentro de los recursos
     //maxCol cantidad máxima de columnas
     //maxFil cantidad máxima de filas
     //dimensionTile tamaño en píxeles de cada tile
     
    public ModeloMapa(String rutaMapa, int maxCol, int maxFil, int dimensionTile) {
        this.maxCol = maxCol;
        this.maxFil = maxFil;
        this.dimensionTile = dimensionTile;
        this.mapaTileNum = new int[maxCol][maxFil];
        cargarMapa(rutaMapa);
    }

    /** Setter seguro para cada celda */
    public void setTileNum(int col, int fil, int num) {
        if (col >= 0 && col < maxCol && fil >= 0 && fil < maxFil) {
            mapaTileNum[col][fil] = num;
        } else {
            throw new IndexOutOfBoundsException(
                "Col o Fil fuera de rango: col=" + col + ", fil=" + fil
            );
        }
    }

    public void cargarMapa(String rutaMapa) {
        try {
            InputStream is = getClass().getResourceAsStream(rutaMapa);
            if (is == null) {
                throw new RuntimeException("No se encontró el mapa: " + rutaMapa);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int fil = 0;

            while (fil < maxFil) {
                String linea = br.readLine();
                if (linea == null) break;

                String[] numeros = linea.split(" ");
                for (int col = 0; col < maxCol; col++) {
                    int num = Integer.parseInt(numeros[col]);

                    // Detectar spawn (número 9)
                    if (num == 9 ) {
                        spawnX = col * dimensionTile;
                        spawnY = fil * dimensionTile;
                        num = 0; // reemplaza el 9 por piso
                    }

                    mapaTileNum[col][fil] = num;
                }
                fil++;
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar mapa desde: " + rutaMapa, e);
        }

        
    }

    

    // ——— Getters ———
     public Tile[] getTiles() { 
        return tiles; 
    }

    // ——— Setters ———

    public void setSpawn(int spawnX, int spawnY) {
        this.spawnX = spawnX;
        this.spawnY = spawnY;
    }

    // —— Implementación de IModeloMapa
    @Override public int[][] getMapaTileNum() { return mapaTileNum; }
    @Override public int getMaxCol() { return maxCol; }
    @Override public int getMaxFil() { return maxFil; }
    @Override public int getDimensionTile() { return dimensionTile; }
    @Override public int getSpawnX() { return spawnX; }
    @Override public int getSpawnY() { return spawnY; }

}
