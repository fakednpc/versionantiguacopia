package modelo;

public class Dialogo {
    
    private String dialogos[];

    public Dialogo() {
        dialogos = new String[5]; 
        setDialogo();

    }

    public void setDialogo(){
        dialogos[0] = "¡Bienvenido al mundo, Arquero!"; //aca se agregaria el nombre del personaje que se eligio 
        dialogos[1] = "Tu misión es explorar la mazmorra. Durante el mapa, podras utilizar los consumibles que te encuentres y utilizarlos en las batallas";
        dialogos[2] = "Pero cuidado... hay peligros ocultos.";
        dialogos[3] = "Cuando veas necesario podras luchar con monstruos para poder derrotar al monstruo final y salvar la mazmorra";
        dialogos[4] = "¡Buena suerte!";
    }

    public String getDialogo(int i) { //obtener el dialogo i del array

        if (i>=0 && i< dialogos.length) { //para que no se pase del rango
            return dialogos[i];
        }
        else {
            return null;
        }
    }

    public int cantidadDialogos(){
        return dialogos.length;
    }

}
