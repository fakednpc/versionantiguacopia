package modelo;

import tile.CargadorTiles;
import tile.Tile;
import vista.VistaMapa;


public class MazmorraFactory {
    
    public VistaMapa crearMazmorra(String tipoMazmorra, int dimensionTile) {
        int maxCol = 60;
        int maxFil = 60;
        String rutaMapa = "";
        String basePath = "";
        
         //Seleccionamos la ruta del mapa según el tipo
        switch (tipoMazmorra.toLowerCase()) {
            case "ruinas":
                rutaMapa = "/res/mapas/mapa1.txt";
                basePath = "/res/tiles/mazmorra_1/";
                break;
        }
        
        // Crear modelo limpio (solo lógica)
        IModeloMapa modeloMapa = new ModeloMapa(rutaMapa, maxCol, maxFil, dimensionTile);
         // Cargar tiles para la vista
        Tile[] tiles = CargadorTiles.cargarTiles(basePath);
        return new VistaMapa(modeloMapa, tiles, dimensionTile);

    }
}



