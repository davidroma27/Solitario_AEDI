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

    private final Stack<Carta>[] montonExterior; // 4 montones ( los 4 palos) 
    private final Stack<Carta>[][] montonInterior;  // Una matriz

    public Mesa() {
        //Inicializa el monton exterior
        montonExterior = new Stack[4];

        //En el tablero del jugador (Exterior) máximo 10 cartas por Stack/Palo 
        for (int i = 0; i < montonExterior.length; i++) { // No se ponen números directamente, se ponen de donde salen ( por si se cambia el código en un futuro o loquesea)
            montonExterior[i] = new Stack<>(); //Almacena los stacks en el array de montonExterior
        }

        //Inicializa el monton interior
        montonInterior = new Stack[4][4];
        for (int fila = 0; fila < montonInterior.length; fila++) { // Recorre fila
            for (int col = 0; col < montonInterior[fila].length; col++) { //Recorre columna de fila actual
                montonInterior[fila][col] = new Stack<>(); // Crea espacio en la mesa
            }
        }
    }

    //Coloca las cartas en el monton interior: 2 cartas por stack y los stacks
    //de las diagonales 3 cartas
    public void colocarCartas(Baraja b) throws Exception { // Puede lanzar excepción si no hay cartas ( ver clase baraja)
        // Método especificado por Rosalía 

        // 1º Paso : Repartir 16 primeras cartas boca abajo
        for (int fila = 0; fila < montonInterior.length; fila++) { // Recorre fila
            for (int col = 0; col < montonInterior[fila].length; col++) { //Recorre columna de fila actual
                montonInterior[fila][col].push(b.sacarCarta()); // Colocar en posición carta
            }
        }
        //2º Paso : Repartir cartas en las 2 diagonales
        //Diagonal principal
        for (int posicion = 0; posicion < montonInterior.length; posicion++) {
            montonInterior[posicion][posicion].push(b.sacarCarta()); // La diagonal principal coincide en (0,0) (1,1) (2,2) (3,3)
            montonInterior[posicion][montonInterior.length - posicion - 1].push(b.sacarCarta()); // La diagonal secundaria coincide en (0,3) (1,2) (2,1) (3,0)
        }

        //3º Paso : Repartir 16 últimas cartas boca arriba
        for (int fila = 0; fila < montonInterior.length; fila++) { // Recorre fila
            for (int col = 0; col < montonInterior[fila].length; col++) { //Recorre columna de fila actual
                montonInterior[fila][col].push(b.sacarCarta()); // Colocar en posición carta
            }
        }
    }

    //Mueve una carta de un stack a otro del monton interior
    //La carta que se oculta en el stack destino tiene que ser 1 unidad > y del mismo palo que la que se mueve
    //Encima de un 10 tiene que colocarse un 7
    //No se puede mover una carta a un stack vacío
    public void moverCartaInterior(int filaOri, int colOri, int filaDest, int colDest) throws Exception {
        //Comprobar si el montón desde donde se quiere mover la carta está vacío
        if (montonInterior[filaOri][colOri].empty()) {
            throw new Exception("Movimiento inválido : No se pueden mover cartas desde un espacio vacío");
        }

        //Comprobar si el montón al que se quiere mover la carta está vacío
        if (montonInterior[filaDest][colDest].empty()) {
            throw new Exception("Movimiento inválido : No se pueden mover cartas a espacios vacíos");
        }

        //Una vez listas las comprobaciones previas vemos que cartas queremos coger
        Carta cartaOri = montonInterior[filaOri][colOri].peek();

        //Vemos sobre que carta queremos ponerla
        Carta cartaDest = montonInterior[filaDest][colDest].peek();

        //Comprobar que la carta que hemos cogido y la carta sobre la cual vamos a poner sean del mismo palo
        if (!cartaOri.getPalo().equals(cartaDest.getPalo())) {
            throw new Exception("Movimiento inválido : No se pueden juntar cartas de distintos palos");
        }

        //Comprobar que el número de la carta origen sea menor que la del destino
        //Encima del 12 no se puede poner nada
        if (cartaOri.getNumero() == 12
                || (cartaOri.getNumero() == 7 && cartaDest.getNumero() != 10)
                || (cartaOri.getNumero() != 7 && cartaOri.getNumero() != cartaDest.getNumero() - 1)) {
            throw new Exception("Movimiento inválido : La carta de destino no es una unidad mayor que la de origen");
        }

        // Una vez listas las comprobaciones podremos mover la carta
        montonInterior[filaDest][colDest].push(montonInterior[filaOri][colOri].pop()); // Movemos a la posicion de destino la carta situada en posicion origen

        /*if (!montonInterior[filaDest][colDest].empty()) {  
            //Mientras el nº de la carta origen sea MENOR que el nº de la carta que se oculta
            while (montonInterior[filaOri][colOri].peek().getNumero() < montonInterior[filaDest][colDest].peek().getNumero()) { ES UN BUCLE INFINITO QUE BLOQUEA EL PROGRAMA SI SE MUEVE CARTA ORIGEN>DESTINO

                //Si se oculta un 10 pero la carta de encima es distinta de 7...
                if (montonInterior[filaOri][colOri].peek().getNumero() != 7
                        && montonInterior[filaDest][colDest].peek().getNumero() == 10) {
                    System.out.println("Movimiento inválido!");
                } else {
                    //Se pone en el stack destino la carta que sacamos del stack origen
                    montonInterior[filaDest][colDest].push(montonInterior[filaOri][colOri].pop());
                }
            }
        } else {
            System.out.println("Movimiento inválido!");
        }
    
        ERRORES :
        -NO SE COMPRUEBA SI COGES DE UNA POSICIÓN VACÍA
        -NO SE COMPRUEBA SI LA CARTA ORIGEN Y DESTINO SON DEL MISMO PALO
        -SI LA CARTA ORIGEN ES MENOR EN MAS DE UNA UNIDAD QUE LA CARTA DESTINO SE EJECUTA UN BUCLE IFNINITO QUE MUEVE TODO EL STACK
        -SI LA CARTA ORIGEN ES MAYOR O IGUAL QUE LA CARTA DESTINO NO HACE NADA NI MUESTRA MENSAJE
        -NO SE COMPRUEBA SI LA CARTA ORIGEN ES UNA UNIDAD MENOR QUE LA CARTA DESTINO
         */
    }

    //Colocar carta en un monton exterior
    //Se saca una carta de un stack del monton interior y se mete en el stack del 
    //palo correpondiente en el monton exterior
    //La carta que se pone en el monton exterior tiene que ser mayor que la carta que queda oculta
    //Si la carta que se oculta es un 7, la carta que se coloca encima tiene que ser un 10
    //Solo se puede colocar un AS(1) como primera carta en un stack exterior vacío
    public void moverCartaExterior(int filaOri, int colOri) throws Exception {

        //Comprobar si el montón desde donde se quiere mover la carta está vacío
        if (montonInterior[filaOri][colOri].empty()) {
            throw new Exception("Movimiento inválido : No se pueden mover cartas desde un espacio vacío");
        }

        //Vemos que carta vamos a mover al montón Exterior
        Carta cartaOri = montonInterior[filaOri][colOri].peek();

        //Miramos el palo de la carta que acabamos de coger y le asignamos el montón del mismo Palo
        int montonDest = cartaOri.getPalo().ordinal();

        //Comprobación de que la primera carta escogida para mover sea un AS
        if (montonExterior[montonDest].empty()){
            if(cartaOri.getNumero() != 1){
                throw new Exception("Movimiento inválido : Si un montón de un palo está vacío la primera carta a poner debe ser un as");
            }
        }else{

        //Vemos que carta vamos a solapar
        Carta cartaDest = montonExterior[montonDest].peek();

        //Comprobar cartaOri sea una unidad mayor sobre la carta a solapar.
        if ((cartaOri.getNumero() == 10 && cartaDest.getNumero() != 7)
                || (cartaOri.getNumero() != 10 && cartaOri.getNumero() - 1 != cartaDest.getNumero()) ){
            throw new Exception("Movimiento inválido :La carta de destino no es una unidad menor que la de origen");
        }
        }
        //Una vez listas las comprobaciones movemos la carta al montón exterior
        montonExterior[montonDest].push(montonInterior[filaOri][colOri].pop());

        /*//Si el monton exterior esta vacío
        if (montonExterior[montonDest].empty()) {
            //Solo puede ponerse un AS
            if (montonInterior[filaOri][colOri].peek().getNumero() == 1) {
                //Se hace el movimiento del AS
                montonExterior[montonDest].push(montonInterior[filaOri][colOri].pop());
            } else { //Si no es un AS
                System.out.println("Movimiento inválido!");
            }
        } else { //Si hay elementos en el monton exterior
            //Mientras la carta que se coloca sea mayor que la carta que se oculta
            while (montonInterior[filaOri][colOri].peek().getNumero() > montonExterior[montonDest].peek().getNumero()) {
                //Si la carta que se coloca encima del 7 es distinta de 10
                if (montonExterior[montonDest].peek().getNumero() == 7 && montonInterior[filaOri][colOri].peek().getNumero() != 10) {
                    System.out.println("Movimiento inválido!");
                } else { //Cualquier otro caso es correcto y se realiza el movimiento
                    montonExterior[montonDest].push(montonInterior[filaOri][colOri].pop());
                }
            }
        }
        ERRORES :
        - NO SE COMPRUEBA SI EL MONTÓN INTERIOR ESTÁ VACÍO
        - NO SE COMPRUEBA QUE SEAN DEL MISMO PALO
        - BUCLE INFINITO SI LA CARTA ORIGEN ES MAYOR QUE LA DE DESTINO , QUE MUEVE TODO UN STACK HACIA FUERA O MUESTRA DE FORMA INDEFINIDA EL MENSAJE "MOVIMIENTO INVÁLIDO"
        - NO SE COMPRUEBA QUE LA CARTA ORIGEN SEA UNA UNIDAD MAYOR QUE LA CARTA DESTINO
        - SI LA CARTA ORIGEN ES MENOR O IGUAL A LA DE DESTINO, NO HACE NADA NI MUESTRA NINGÚN MENSAJE
         */
    }

    //Muestra por pantalla las cartas que hay visibles en la mesa
    public void mostrarMesa() {
        // Creamos una representación para carta inexistente
        String cartaInexistente = "[--|-]";

        // Mostramos la última carta de los montones exteriores     
        System.out.println("Montón Exterior:\n");
        for (Stack<Carta> monton : montonExterior) {// Recorremos los palos
            if (monton.empty()) { // Si un montón está vacio , se muestra representación por defecto carta inexistente
                System.out.print(cartaInexistente);
            } else {
                System.out.print(monton.peek().toString()); // Mostramos la representación de la carta (clase carta método toString)
            }
            System.out.print(" "); // Separacion horizontal
        }

        // Mostramos la última carta de los montones exteriores     
        System.out.println("\n\nMontón Interior:\n");
        for (Stack[] posicion : montonInterior) {// Recorremos posiciones de montón Interior
            for (Stack<Carta> monton : posicion) { // Accedemos a las cartas de cada posición
                if (monton.empty()) {
                    System.out.print(cartaInexistente);
                } else {
                    System.out.print(monton.peek().toString()); // Mostramos la representación de la carta (clase carta método toString)
                }
                System.out.print(" "); // Separacion horizontal
            }
            System.out.println(); // Separación vertical
        }
    }

    //Comprueba si los montones exteriores han sido completados
    public boolean MontonExteriorCompleto() {
        boolean flag = true;
        //comprueba cada stack del monton
        for (Stack<Carta> stack : montonExterior) {
            if (stack.size() != 10) {
                flag = false; // Si hay algún montón que no tenga 10 cartas significa que los montones exteriores no están llenos
            }
        }
        return flag;
    }
}
