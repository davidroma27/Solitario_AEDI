
package solitario.Core;

import java.util.Collections;
import java.util.Stack; // Uso librería nativa java para no tener que implementar nada externo

public class Baraja {

    //Definimos baraja
    private final Stack<Carta> baraja; // La pongo como final porque no se modifica

    //CONSTRUCTOR DE LA BARAJA
    public Baraja() {
   
        //Creamos la baraja vacía
        baraja = new Stack<>();

        //Rellenamos la baraja 
        for (int palo = 0; palo < Palos.values().length; palo++) { // Recorre palos
            for (int numero = 1; numero <= 12; numero++) { // Inserta los números 
                if (numero != 8 && numero != 9) { // Salta el 8 y el 9
                    baraja.push(new Carta(numero, Palos.values()[palo])); // Si no es ni 8 ni 9 crea una carta con su palo correspondiente
                }
            }
        }
        //Barajamos
        Collections.shuffle(baraja); //Mezcla las cartas que se han metido en la baraja

    }

    //Coger carta
    public Carta sacarCarta() throws Exception {

        if (baraja.empty()) { // Si la baraja está vacía salta excepción
            throw new Exception("La baraja esta vacía");
        } 
        
        return baraja.pop(); // Coge la última carta de la baraja
    }

}
