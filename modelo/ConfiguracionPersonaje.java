package modelo;

import controlador.ControlTeclas;

//Para disminución de parámetros en constructor Personaje
//  Agrupa todos los parámetros necesarios para instanciar un personaje, asi se pasa la configuración
// como un solo objeto entre clases
public class ConfiguracionPersonaje {
    public int pantallaAncho, pantallaAltura, dimensionTile;
    public ControlTeclas control;
    public ChequearColision colision; 
    public int spawnY, spawnX; //coordenadas de spawn personaje en mapa (ubicación del 9)
}
