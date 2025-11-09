package tile;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class CargadorTiles {

    public static Tile[] cargarTiles(String basePath) {
        try {
            Tile[] tiles = new Tile[6];

            // Tiles normales (1 frame cada uno)
            tiles[0] = new Tile(new BufferedImage[]{ ImageIO.read(CargadorTiles.class.getResourceAsStream(basePath + "floor_1.png")) }, false);
            tiles[1] = new Tile(new BufferedImage[]{ ImageIO.read(CargadorTiles.class.getResourceAsStream(basePath + "floor_2.png")) }, false);
            tiles[2] = new Tile(new BufferedImage[]{ ImageIO.read(CargadorTiles.class.getResourceAsStream(basePath + "wall_mid.png")) }, true);
            tiles[3] = new Tile(new BufferedImage[]{ ImageIO.read(CargadorTiles.class.getResourceAsStream(basePath + "floor_3.png")) }, false);
            tiles[4] = new Tile(new BufferedImage[]{ ImageIO.read(CargadorTiles.class.getResourceAsStream(basePath + "tile_pocion_hp_pequeña.png")) }, false);

            // Tile animado: estrella (spritesheet 16x16 por frame, horizontal)
            BufferedImage spriteSheet = ImageIO.read(CargadorTiles.class.getResourceAsStream(basePath + "estrella.png"));
            int numFrames = spriteSheet.getWidth() / 16; // cantidad de columnas = frames
            BufferedImage[] frames = new BufferedImage[numFrames];

            for (int i = 0; i < numFrames; i++) {
                frames[i] = spriteSheet.getSubimage(i * 16, 0, 16, 16);
            }

            tiles[5] = new Tile(frames, true);  

            return tiles;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar tiles desde: " + basePath, e);
        }
    }
}
