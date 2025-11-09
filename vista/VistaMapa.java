package vista;

import java.awt.Graphics2D;
import modelo.IModeloMapa;
import tile.Tile;

public class VistaMapa {

    private final IModeloMapa modelo;  // Solo depende de la interfaz
    private final Tile[] tiles;
    private final int dimensionTile;

    public VistaMapa(IModeloMapa modelo, Tile[] tiles, int dimensionTile) {
        this.modelo = modelo;
        this.dimensionTile = dimensionTile;
        this.tiles = tiles;
    }

    public IModeloMapa getModelo() {
        return modelo;
    }

    public void dibujar(Graphics2D g2, int jugadorMundoX, int jugadorMundoY, int jugadorPantallaX, int jugadorPantallaY) {
        int[][] mapa = modelo.getMapaTileNum();

        //  Dibujamos todos los tiles normalmente
        for (int fil = 0; fil < modelo.getMaxFil(); fil++) {
            for (int col = 0; col < modelo.getMaxCol(); col++) {
                int tileNum = mapa[col][fil];
                int mundoX = col * dimensionTile;
                int mundoY = fil * dimensionTile;
                int pantallaX = mundoX - jugadorMundoX + jugadorPantallaX;
                int pantallaY = mundoY - jugadorMundoY + jugadorPantallaY;

                if (mundoX + dimensionTile > jugadorMundoX - jugadorPantallaX &&
                    mundoX - dimensionTile < jugadorMundoX + jugadorPantallaX &&
                    mundoY + dimensionTile > jugadorMundoY - jugadorPantallaY &&
                    mundoY - dimensionTile < jugadorMundoY + jugadorPantallaY) {

                    if (tileNum >= 0 && tileNum < tiles.length && tiles[tileNum].getImagen() != null) {
                        g2.drawImage(tiles[tileNum].getImagen(), pantallaX, pantallaY, dimensionTile, dimensionTile, null);
                    }
                }
            }
        }

        // Dibujamos la estrella animada encima del tile correspondiente (tileNum == 5)
            outer:
            for (int fil = 0; fil < modelo.getMaxFil(); fil++) {
                for (int col = 0; col < modelo.getMaxCol(); col++) {
                    if (mapa[col][fil] == 5) { // Tile de la estrella
                        int mundoX = col * dimensionTile;
                        int mundoY = fil * dimensionTile;
                        int pantallaX = mundoX - jugadorMundoX + jugadorPantallaX;
                        int pantallaY = mundoY - jugadorMundoY + jugadorPantallaY;

                        // 👇 1. Dibujar el fondo de piso primero (por ejemplo, floor_1)
                        Tile pisoBase = tiles[0]; // tile de piso, podés cambiarlo por otro
                        g2.drawImage(pisoBase.getImagen(), pantallaX, pantallaY, dimensionTile, dimensionTile, null);

                        // 👇 2. Luego dibujar la estrella animada encima
                        Tile estrella = tiles[5];
                        estrella.actualizarAnimacion();
                        g2.drawImage(estrella.getImagen(), pantallaX, pantallaY, dimensionTile, dimensionTile, null);

                        

                        break outer; // Solo una vez
                    }
                }
            }

    }
}
