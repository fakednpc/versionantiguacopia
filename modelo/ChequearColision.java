package modelo;

import tile.CargadorTiles;
import tile.Tile;


public class ChequearColision {

    public static final int TILE_POCION = 4;

    /**
     * Chequea si la entidad colisiona con tiles colisionables.
     * Corrige su posición para evitar que se quede pegada.
     */
    private Tile[] tiles;

    public ChequearColision() {
        tiles = CargadorTiles.cargarTiles("/res/tiles/mazmorra_1/"); 
    }

    public void chequearTile(Entidad entidad, int[][] mapaTileNum, int dimensionTile) {
        entidad.enColision = false;

        // Coordenadas del área sólida en el mundo
        int entidadIzq = entidad.mundoX + entidad.areaSolida.x;
        int entidadDer = entidad.mundoX + entidad.areaSolida.x + entidad.areaSolida.width;
        int entidadArr = entidad.mundoY + entidad.areaSolida.y;
        int entidadAbj = entidad.mundoY + entidad.areaSolida.y + entidad.areaSolida.height;

        // Determinar las filas y columnas que ocupa la entidad
        int colIzq = entidadIzq / dimensionTile;
        int colDer = entidadDer / dimensionTile;
        int filArr = entidadArr / dimensionTile;
        int filAbj = entidadAbj / dimensionTile;

        int tile1, tile2;

        switch (entidad.direccion) {
            case "arriba":
                filArr = (entidadArr - entidad.velocidad) / dimensionTile;
                tile1 = mapaTileNum[colIzq][filArr];
                tile2 = mapaTileNum[colDer][filArr];
                if (esColision(tile1) || esColision(tile2)) {
                    entidad.enColision = true;
                    // Corrección: lo ubico justo debajo del tile sólido
                    entidad.mundoY = (filArr + 1) * dimensionTile - entidad.areaSolida.y;
                }
                break;

            case "abajo":
                filAbj = (entidadAbj + entidad.velocidad) / dimensionTile;
                tile1 = mapaTileNum[colIzq][filAbj];
                tile2 = mapaTileNum[colDer][filAbj];
                if (esColision(tile1) || esColision(tile2)) {
                    entidad.enColision = true;
                    // Corrección: justo arriba del tile sólido
                    entidad.mundoY = filAbj * dimensionTile - entidad.areaSolida.y - entidad.areaSolida.height - 1;
                }
                break;

            case "izquierda":
                colIzq = (entidadIzq - entidad.velocidad) / dimensionTile;
                tile1 = mapaTileNum[colIzq][filArr];
                tile2 = mapaTileNum[colIzq][filAbj];
                if (esColision(tile1) || esColision(tile2)) {
                    entidad.enColision = true;
                    // Corrección: justo a la derecha del tile sólido
                    entidad.mundoX = (colIzq + 1) * dimensionTile - entidad.areaSolida.x;
                }
                break;

            case "derecha":
                colDer = (entidadDer + entidad.velocidad) / dimensionTile;
                tile1 = mapaTileNum[colDer][filArr];
                tile2 = mapaTileNum[colDer][filAbj];
                if (esColision(tile1) || esColision(tile2)) {
                    entidad.enColision = true;
                    // Corrección: justo a la izquierda del tile sólido
                    entidad.mundoX = colDer * dimensionTile - entidad.areaSolida.x - entidad.areaSolida.width - 1;
                }
                break;
        }
    }

    // Devuelve col y fil del pickup cercano, o null si no hay ninguno cerca
    public int[] pickupCercano(Entidad entidad, int[][] mapaTileNum, int dimensionTile) {
        
        int cx = entidad.mundoX + entidad.areaSolida.x + entidad.areaSolida.width  / 2;
        int cy = entidad.mundoY + entidad.areaSolida.y + entidad.areaSolida.height / 2;

        int col = cx / dimensionTile;
        int fil = cy / dimensionTile;

        // Chequea la celda actual y las 4 adyacentes
        int[][] vecinos = {
            { col,     fil     },
            { col - 1, fil     },
            { col + 1, fil     },
            { col,     fil - 1 },
            { col,     fil + 1 }
        };

        for (int[] v : vecinos) {
            int c = v[0], f = v[1];
            if (c >= 0 && c < mapaTileNum.length &&
                f >= 0 && f < mapaTileNum[0].length &&
                mapaTileNum[c][f] == TILE_POCION) {
                return new int[]{ c, f };
            }
        }
        return null;
    }


     /** Devuelve true si el tile bloquea al jugador */
    private boolean esColision(int tileNum) {
    if (tileNum < 0 || tileNum >= tiles.length) return false;
    return tiles[tileNum].tieneColision();
}
 

}
