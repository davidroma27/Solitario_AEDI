

package solitario.Core;

import java.util.Stack;


public class Mesa {

    public static Stack<Carta>[] montonExterior; // 4 montones ( los 4 palos) 
    public static Stack<Carta>[][] montonInterior;  // Una matriz

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
    public void mostrarMesa() {
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
