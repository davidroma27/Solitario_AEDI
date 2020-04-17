/*
* Representa la baraja española, 40 cartas, 4 palos, valores de las cartas de 1 a 12 (excepto 8 y 9). 
* Estructura: se utilizará un TAD adecuado
* Funcionalidad: estando la baraja desordenada, devolverá la carta situada encima del montón de cartas
 */
package solitario.Core;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import pila.*;


public class Baraja {
    
    
    Carta[] barajaOrdenadas= new Carta[40];
    Pila<Carta> barajaCartasAleatorias= new EnlazadaPila<>();
    
    
    //CONSTRUCTOR DE LA BARAJA
    public Baraja(){
        
    crearTodasCartas();
    rellenarBarajaAleatoriamente(barajaCartasAleatorias, barajaOrdenadas );
}
   
    //METODO PARA CREAR LAS CARTAS ORDENADAS EN ARRAY
    private void crearTodasCartas(){
        int contador =0;
        
        
        for(int p=0; p<Palos.values().length; p++){
            
            for(int j=1; j<8; j++){
                
            barajaOrdenadas[contador]= new Carta(j,Palos.values()[p]);
            contador++;
            
            }
             
            for (int j=10; j<13; j++){
                
            barajaOrdenadas[contador]= new Carta(j,Palos.values()[p]);
            contador++;
            
            }
            
        }
        //Comprobar
        if(contador==40){
            System.out.println("La baraja se ha creado correctamente");
        }else{
            System.out.println("La baraja no se ha creado correctamente");}
        
        //MEZCLAMOS LAS CARTAS
    
    //1- convertimos el array en una lista de cartas
    List<Carta> lista = Arrays.asList(barajaOrdenadas);
    
    //2- aplicamos el metodo shuffle
    Collections.shuffle(lista);
        
    //3- ahora volvemos a convertir la lista en un array
        lista.toArray(barajaOrdenadas);
        
    }

    //METODO PARA RELLENAR LA BARAJA DE FORMA ALEATORIA
    private void rellenarBarajaAleatoriamente(Pila<Carta> barajaCartasAleatorias, Carta[] barajaCartasOrdenadas){
        
        for(int i=0; i<40; i++){
        barajaCartasAleatorias.push(barajaCartasOrdenadas[i]);
        }
   
    }
    
    //METODO QUE DEVUELVE LA CARTA DEL TOPE DE LA BARAJA
    
    public Carta sacarCarta()throws PilaVaciaExcepcion{
        
        if(barajaCartasAleatorias.esVacio()){
            throw new PilaVaciaExcepcion("La pila esta vacia");
        }else
        
        
     return barajaCartasAleatorias.pop();  
    }
    
    
}
