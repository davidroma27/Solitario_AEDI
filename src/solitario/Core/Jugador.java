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

   
    public void moverCartaInterior(Mesa mesa,int filaOri, int colOri, int filaDest, int colDest) throws Exception {
        //!!!Lo primero que hacermos es comprobar si se puede realizar el movimiento!!!
        mesa.comprobarMovimientoInterior(filaOri, colOri, filaDest, colDest);
        // Una vez listas las comprobaciones podremos mover la carta
        Mesa.montonInterior[filaDest][colDest].push(Mesa.montonInterior[filaOri][colOri].pop()); // Movemos a la posicion de destino la carta situada en posicion origen
    }

    
    
    //Funcion que mueve la carta elegida del monton interior a su monton exterior correspondiente
    public void moverCartaExterior(Mesa mesa,int filaOri, int colOri) throws Exception {
        //!!!Lo primero que hacermos es comprobar si se puede realizar el movimiento!!!
        int montonDest = mesa.comprobarMovimientoExterior(filaOri,colOri);
        //Una vez listas las comprobaciones movemos la carta al montón exterior
        Mesa.montonExterior[montonDest].push(Mesa.montonInterior[filaOri][colOri].pop());
    }    
        
    
}