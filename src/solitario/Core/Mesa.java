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

    private Stack<Carta>[] montonExterior; // 4 montones ( los 4 palos) 
    private Stack<Carta>[][] montonInterior;  // Una matriz

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

    //Coloca las cartas en el monton interior: 2 cartas por stack y 3 cartas por stack
    //en las diagonales
    public void colocarCartas(Baraja b) throws Exception { // Puede lanzar excepción si no hay cartas ( ver clase baraja)

        // 1º Paso : Repartir 16 primeras cartas boca abajo
        //(repartiendo una carta a cada stack)
        for (int fila = 0; fila < montonInterior.length; fila++) { // Recorre fila
            for (int col = 0; col < montonInterior[fila].length; col++) { //Recorre columna de fila actual
                montonInterior[fila][col].push(b.sacarCarta()); // Coloca una carta sacada de la baraja en cada stack
            }
        }
        //2º Paso : Repartir cartas en las 2 diagonales
        //Diagonal principal
        for (int posicion = 0; posicion < montonInterior.length; posicion++) {
            montonInterior[posicion][posicion].push(b.sacarCarta()); // La diagonal principal coincide en (0,0) (1,1) (2,2) (3,3)
            montonInterior[posicion][montonInterior.length - posicion - 1].push(b.sacarCarta()); // La diagonal secundaria coincide en (0,3) (1,2) (2,1) (3,0)
        }

        //3º Paso : Repartir 16 últimas cartas boca arriba
        //(de nuevo añadir una carta a cada stack)
        for (int fila = 0; fila < montonInterior.length; fila++) { // Recorre fila
            for (int col = 0; col < montonInterior[fila].length; col++) { //Recorre columna de fila actual
                montonInterior[fila][col].push(b.sacarCarta()); // Colocar en posición carta
            }
        }
    }

    //Muestra por pantalla las cartas que hay visibles en la mesa
    @Override
    public String toString() {

        // Creamos una representación para carta inexistente
        String cartaInexistente = "[--|-]"; //Cuando el stack está vacío

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

        // Mostramos la última carta de los montones interiores    
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

        return "";
    }

    //Comprueba si los montones exteriores han sido completados
    public boolean MontonExteriorCompleto() {
        boolean flag = true;
        int i = 0;
        //comprueba cada stack del monton
        while (i < montonExterior.length) {
            if (montonExterior[i].size() != 10) {
                flag = false; // Si hay algún montón que no tenga 10 cartas significa que los montones exteriores no están llenos
            }
            i++;
        }
        return flag;
    }

    public void colocarCartaInterior(int filaOri, int colOri, int filaDest, int colDest,boolean soloComprobar) throws Exception {
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
        //Si pasa las comprobaciones realizamos movimiento
        if(!soloComprobar)
        montonInterior[filaDest][colDest].push(montonInterior[filaOri][colOri].pop());
    }

    public void colocarCartaExterior(int filaOri, int colOri,boolean soloComprobar) throws Exception {
        
        //Comprobar si el montón desde donde se quiere mover la carta está vacío
        if (montonInterior[filaOri][colOri].empty()) {
            throw new Exception("Movimiento inválido : No se pueden mover cartas desde un espacio vacío");
        }

        //Vemos que carta vamos a mover al montón Exterior
        Carta cartaOri = montonInterior[filaOri][colOri].peek();

        //Miramos el palo de la carta que acabamos de coger y le asignamos el montón del mismo Palo
        int montonDest = cartaOri.getPalo().ordinal();

        //Comprobación de que la primera carta escogida para mover sea un AS
        if (montonExterior[montonDest].empty()) {
            if (cartaOri.getNumero() != 1) {
                throw new Exception("Movimiento inválido : Si un montón de un palo está vacío la primera carta a poner debe ser un as");
            }
        } else {

            //Vemos que carta vamos a solapar
            Carta cartaDest = montonExterior[montonDest].peek();

            //Comprobar cartaOri sea una unidad mayor sobre la carta a solapar.
            if ((cartaOri.getNumero() == 10 && cartaDest.getNumero() != 7)
                    || (cartaOri.getNumero() != 10 && cartaOri.getNumero() - 1 != cartaDest.getNumero())) {
                throw new Exception("Movimiento inválido :La carta de destino no es una unidad menor que la de origen");
            }
        }
        if(!soloComprobar)
        montonExterior[montonDest].push(montonInterior[filaOri][colOri].pop());
        
    }

    //Se llama en cada bucle del metodo Jugar() en Solitario
    public boolean movPosibles() {
        boolean quedanMov = false; //Inicializamos a false la variable que indica si quedan mov.posibles 

        int fila, columna; //Fila y columna que referencian una carta origen 
        int filaC, columnaC; //Fila y columna que referencian una carta destino

        fila = 0;

        //Recorre cada fila del monton interior
        while (!quedanMov && fila < montonInterior.length) {
            columna = 0;
            //Recorre las columnas de cada fila del monton interior
            while (!quedanMov && columna < montonInterior[fila].length) {

                // --- Comprobaciones del monton interior
                filaC = 0;
                while (!quedanMov && filaC < montonInterior.length) {
                    columnaC = 0;
                    while (!quedanMov && columnaC < montonInterior[fila].length) {
                        try {
                            //Comprueba el movimiento posible entre la carta origen y carta destino
                            this.colocarCartaInterior(fila, columna, filaC, columnaC,true);
                            quedanMov = true; //Si el movimiento es posible, devuelve true y continúa la partida
                        } catch (Exception err) {
                        }

                        columnaC++;
                    }
                    filaC++;
                }

                // --- Comprobaciones del montón exterior
                try {
                    this.colocarCartaExterior(fila, columna,true);
                    quedanMov = true;
                } catch (Exception err) {
                }

                columna++;
            }
            fila++;
        }

        return quedanMov;
    }
}
    
    
    
    
    
    
    

