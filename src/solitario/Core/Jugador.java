/*
 * Representa al único jugador de la partida, identificado por el nombre 
 * Funcionalidad: le da la vuelta a una carta que está boca abajo, escoge una carta para moverla o al montón de descarte
 *                o la mueve encima de otra carta del interior
 */
package solitario.Core;

public class Jugador {

    private String nombre;

    public Jugador(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void moverCartaInterior(Mesa mesa, int filaOri, int colOri, int filaDest, int colDest) throws Exception {
        mesa.colocarCartaInterior(filaOri, colOri, filaDest, colDest,false);       
    }

    //Funcion que mueve la carta elegida del monton interior a su monton exterior correspondiente
    public void moverCartaExterior(Mesa mesa, int filaOri, int colOri) throws Exception {
        mesa.colocarCartaExterior(filaOri, colOri,false);
      
    }

}
