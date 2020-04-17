/*
* Representa la mesa de juego, donde estarán todas las cartas.
* Tendrá dos partes diferenciadas:
* - la parte interior, donde inicialmente estarán colocadas las cartas correctamente para jugar al solitario
* - los montones exteriores, donde estarán colocadas las cartas por palo ordenadas de menor a mayor
* Estructura: Se utilizarán TADs adecuados para su respresentación. En concreto:
* - Una matriz de Pilas para representar la parte o montón interior 
* - Un array de Pilas para representar los montones exteriores.
* Funcionalidad: colocar las cartas para iniciar el juego, quitar una carta de la parte interior, colocar una carta en el interior,
* colocar una carta en el montón exterior correspondiente, visualizar cartas en la mesa, etc

La Pila es una estructura de datos que existe en Java y que se corresponde con la clase Stack. Por lo tanto debereis hacer uso de dicha
clase para representar la mesa de juego, y en particular de los métodos que se indican a continuación (de ser necesarios):

        public boolean empty();
        // Produce: Si la pila está vacía devuelve true, sino false.
        public Carta peek();
        // Produce: Devuelve la Carta del tope de la pila, sin eliminarla de ella.
        public Carta pop();
        // Produce: Elimina la Carta del tope de la pila y la devuelve.
        public void push(Carta item);
        // Produce: Introduce la Carta en el tope de la pila.
 */

package solitario.Core;
import java.util.Stack;

/**
 *
 * @author AEDI
 */
public class Mesa {
    private Stack<Carta> [] montonExterior;
    private Stack<Carta> [][] montonInterior;
   
    public Mesa(){
        //Inicializa el monton exterior
        montonExterior = new Stack<Carta>[4];
        for(int i=0; i<4; i++){ //Crea 4 stacks (montones) vacíos, cada uno de tamaño 10
            Stack<Carta> s = new Stack<>();
            s.setSize(10);
            montonExterior[i] = s; //Almacena los stacks en el array de montonExterior
        }
        
        //Inicializa el monton interior
        for(int fila=0; fila<4; fila++){
            for(int col=0; col<4; col++){
                Stack<Carta> s = new Stack<>();
                montonInterior[fila][col] = s;
            }
        }
    }
    
    //Coloca las cartas en el monton interior: 2 cartas por stack y los stacks
    //de las diagonales 3 cartas
    public void colocarCartas(Baraja b){
        //Reparte 2 cartas en cada stack
        for(int i=0; i<2; i++){
            for(int stack=0; stack < montonInterior.length; stack++){
                Stack<Carta> s = new Stack<>();
                s.push(b.sacarCarta()); //Obtenemos la carta tope de la baraja y la metemos en la pila de cada posicion
            }
        } 
        
        //Añadir 1 carta mas en las diagonales
        montonInterior[0][0].push(b.sacarCarta());
        montonInterior[0][3].push(b.sacarCarta());
        montonInterior[1][1].push(b.sacarCarta());
        montonInterior[1][2].push(b.sacarCarta());
        montonInterior[2][1].push(b.sacarCarta());
        montonInterior[2][2].push(b.sacarCarta());
        montonInterior[3][0].push(b.sacarCarta());
        montonInterior[3][3].push(b.sacarCarta());
    }
    
    
    //Mueve una carta de un stack a otro del monton interior
    //La carta que se oculta en el stack destino tiene que ser 1 unidad > y del mismo palo que la que se mueve
    //Encima de un 10 tiene que colocarse un 7
    //No se puede mover una carta a un stack vacío
    public void moverCartaInterior(int filaOri, int colOri, int filaDest, int colDest){
        //Si el stack destino no está vacío
        if(!montonInterior[filaDest][colDest].empty()){
            //Mientras el nº de la carta origen sea MENOR que el nº de la carta que se oculta
            while(montonInterior[filaOri][colOri].peek().getNumero() <  montonInterior[filaDest][colDest].peek().getNumero()){

                //Si se oculta un 10 pero la carta de encima es distinta de 7...
                if(montonInterior[filaOri][colOri].peek().getNumero() != 7 && 
                   montonInterior[filaDest][colDest].peek().getNumero()==10){
                    System.out.println("Movimiento inválido!");
                }
                else{
                    //Se pone en el stack destino la carta que sacamos del stack origen
                    montonInterior[filaDest][colDest].push(montonInterior[filaOri][colOri].pop());
                }
            }
        }
        else{
            System.out.println("Movimiento inválido!");
        }
    }
    
    //Colocar carta en un monton exterior
    //Se saca una carta de un stack del monton interior y se mete en el stack del 
    //palo correpondiente en el monton exterior
    //La carta que se pone en el monton exterior tiene que ser mayor que la carta que queda oculta
    //Si la carta que se oculta es un 7, la carta que se coloca encima tiene que ser un 10
    //Solo se puede colocar un AS(1) como primera carta en un stack exterior vacío
    public void moverCartaExterior(int filaOri, int colOri, int montonDest){
        //Si el monton exterior esta vacío
        if(montonExterior[montonDest].empty()){
            //Solo puede ponerse un AS
            if(montonInterior[filaOri][colOri].peek().getNumero() == 1){
                //Se hace el movimiento del AS
                montonExterior[montonDest].push(montonInterior[filaOri][colOri].pop());
            }
            else{ //Si no es un AS
                System.out.println("Movimiento inválido!");
            }
        }
        else{ //Si hay elementos en el monton exterior
            //Mientras la carta que se coloca sea mayor que la carta que se oculta
            while(montonInterior[filaOri][colOri].peek().getNumero() > montonExterior[montonDest].peek().getNumero()){
                //Si la carta que se coloca encima del 7 es distinta de 10
                if(montonExterior[montonDest].peek().getNumero() == 7 && montonInterior[filaOri][colOri].peek().getNumero() != 10){
                    System.out.println("Movimiento inválido!");
                }
                else{ //Cualquier otro caso es correcto y se realiza el movimiento
                    montonExterior[montonDest].push(montonInterior[filaOri][colOri].pop());
                }
            }
        }
    }
    
    //Muestra por pantalla las cartas que hay visibles en la mesa
    public void mostrarMesa(){
        int cont=0;
        
        System.out.println("Monton Exterior:\n");
        for(int i=0; i<montonExterior.length; i++){
            
            //Si el stack esta vacio
            if(montonExterior[i].empty()){
                System.out.println(" [ ] ");
            }
            else{
                System.out.print(" [" + montonExterior[i].peek().getNumero() + "|" + montonExterior[i].peek().getPaloCarta()+ "] ");
            }
        }
        
        System.out.print("Monton Interior:\n");
        for(int fila=0; fila<4; fila++){
            for(int col=0; col<4; col++){
                
                //Si el stack actual está vacío
                if(montonInterior[fila][col].empty()){
                    System.out.println(" [ ] ");
                }
                else {
                    System.out.println(" [" + montonInterior[fila][col].peek().getNumero()
                            + "|" + montonInterior[fila][col].peek().getPaloCarta() + "] ");                
                }
                cont++;
                //Cada 4 stacks hace un salto de linea
                if (cont == 4) {
                    System.out.println("");
                }
            }
        }
    }//Fin mostrarMesa()
    
    //Comprueba si los montones exteriores han sido completados
    public boolean verificarMontonExterior(){
        boolean lleno = false;
        //comprueba cada stack del monton
        for (Stack<Carta> stack : montonExterior) {
            if(stack.size() == 10) lleno = true;
        }
        return lleno;
    }
}
