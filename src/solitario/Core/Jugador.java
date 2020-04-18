/*
 * Representa al único jugador de la partida, identificado por el nombre 
 * Funcionalidad: le da la vuelta a una carta que está boca abajo, escoge una carta para moverla o al montón de descarte
 *                o la mueve encima de otra carta del interior
 */
package solitario.Core;

import solitario.IU.ES;

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

    private int[] seleccionarPosicion() {
        int[] posicion = {-1, -1};
        do {
            posicion[0] = ES.pideNumero("[*]Selecciona la fila deseada [0-3]: ");
        } while (posicion[0] < 0 || posicion[0] > 3);
        do {
            posicion[1] = ES.pideNumero("[*]Selecciona la columna deseada [0-3]: ");
        } while (posicion[1] < 0 || posicion[1] > 3);
        return posicion;

    }
    
       public int[] seleccionarCarta() {
        System.out.println("[?] Qué carta quieres mover del montón interior?");
        return seleccionarPosicion();

    }

    public int[] seleccionarDestino() {
        System.out.println("[?] Indica la posición de destino de tu carta");
        return seleccionarPosicion();
    }

    /*public void seleccionarCarta() {

        int x, y;

        System.out.println("Introduce las coordenadas de la carta que quieras girar o mover: ");
        System.out.println("Coordenada x: ");
        x = entrada.nextInt();
        System.out.println("Coordenada y: ");
        y = entrada.nextInt();

        if (cartaEsVisible()) {

            moverCarta(x, y);

        } else {

            girarCarta(x, y);

        }

    }

    public void moverCarta(int xOrigen, int yOrigen) {

        char opcion;

        do {

            System.out.println("Si quieres mover la carta a un montón interior, pulsa i: ");
            System.out.println("Si quieres mover la carta a su montón exterior, pulsa e: ");

            opcion = entrada.next().charAt(0);

        } while (opcion != 'i' && opcion != 'e');

        if (opcion == 'i') {
            System.out.println("Introduce las nuevas coordenadas de la carta: ");
            System.out.println("Coordenada x: ");
            int xDest = entrada.nextInt();
            System.out.println("Coordenada y: ");
            int yDest = entrada.nextInt();

            mesa.moverCartaInterior(xOrigen, yOrigen, xDest, yDest);

        } else if (opcion == 'e') {
            System.out.println("Introduce las nuevas coordenadas de la carta: ");
            System.out.println("Coordenada x: ");
            int xDest = entrada.nextInt();

            mesa.moverCartaExterior(xOrigen, yOrigen, xDest);

        }*/

    }

    //Estos metodos sobran, se pueden hacer ambos movimientos en el metodo
//    public void moverCartaPorInterior(Carta card) {
//
//        int nuevaX;
//        int nuevaY;
//
//        System.out.println("Introduce las nuevas coordenadas de la carta: ");
//        System.out.println("Coordenada x: ");
//        nuevaX = entrada.nextInt();
//        System.out.println("Coordenada y: ");
//        nuevaY = entrada.nextInt();
//
//        moverCarta(nuevaX, nuevaY);
//
//    }
//
//    public void moverCartaAlExterior(Carta card) {
//
//        /* 
//    Aquí no sé si debo indicar al montón que se desplaza, al ser solo posible mover unas cartas de un palo al montón de su
//    mismo palo 
//         */
//    }
