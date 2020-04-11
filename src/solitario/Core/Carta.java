/*
 * Representa una carta, formada por un número y su palo correspondiente
 */
package solitario.Core;

/**
 *
 * @author AEDI
 */
public class Carta {
    
    private int numero;
    private Palos paloCarta;

    public Carta(int numero, Palos paloCarta) {
        this.numero = numero;
        this.paloCarta = paloCarta;
    }

    public int getNumero() {
        return numero;
    }


    public Palos getPaloCarta() {
        return paloCarta;
    }
    
    public String toString(){
    
        StringBuilder toret=new StringBuilder();
        
        toret.append("[").append(getNumero()).append("|").append(getPaloCarta()).append("]").append("\n");
    
    return toret.toString();
    }
		


}
