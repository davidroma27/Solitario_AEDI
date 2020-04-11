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
    
    private int numcartas =40;
    Carta[] barajaCartasOrdenadas= new Carta[numcartas];
    Pila<Carta> barajaCartasAleatorias= new EnlazadaPila<>();
    
    
    //CONSTRUCTOR DE LA BARAJA
    public Baraja(){
    crearTodasCartas(barajaCartasOrdenadas);
    rellenarBarajaAleatoriamente(barajaCartasAleatorias, barajaCartasOrdenadas );
}
   
    //METODO PARA CREAR LAS CARTAS ORDENADAS EN ARRAY
    private void crearTodasCartas(Carta[] baraja){
        int contador =0;
        
        //crear copas
        for(int c=1; c<13; c++){
        if(c==8||c==9){
        //Nada
        }else{
            baraja[contador]=new Carta(c, Palos.COPAS);
        contador++;
        }
        }
        
        //crear bastos
        for(int b=1; b<13; b++){
        if(b==8||b==9){
        //Nada
        }else{
            baraja[contador]=new Carta(b, Palos.BASTOS);
        contador++;
        }
        }
        
        //crear espadas
        
        for(int e=1; e<13; e++){
        if(e==8||e==9){
        //Nada
        }else{
            baraja[contador]=new Carta(e, Palos.ESPADAS);
        contador++;
        }
        }
        
        //crear oros
        for(int o=1; o<13; o++){
        if(o==8||o==9){
        //Nada
        }else{
            baraja[contador]=new Carta(o, Palos.OROS);
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
    List<Carta> lista = Arrays.asList(baraja);
    
    //2- aplicamos el metodo shuffle
    Collections.shuffle(lista);
        
    //3- ahora volvemos a convertir la lista en un array
        lista.toArray(baraja);
        
    
    }
    
    
    
    //METODO PARA RELLENAR LA BARAJA DE FORMA ALEATORIA
    private void rellenarBarajaAleatoriamente(Pila<Carta> barajaCartasAleatorias, Carta[] barajaCartasOrdenadas){
        
        for(int i=0; i<numcartas; i++){
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
