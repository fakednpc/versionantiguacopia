package modelo;
//fabrica concreta
public class CuartelArquero extends EntidadFactory {
    private final ConfiguracionPersonaje configuracion;
    
    public CuartelArquero (ConfiguracionPersonaje configuracion){
        this.configuracion = configuracion;
    }

    @Override
    public Personaje crearPersonaje(){
        return new Arquero(configuracion);
    }
}
