/*
 * Representa al único jugador de la partida, identificado por el nombre 
 * Funcionalidad: le da la vuelta a una carta que está boca abajo, escoge una carta para moverla o al montón de descarte
 *                o la mueve encima de otra carta del interior
 */
package solitario.Core;

import java.util.Scanner;

public class Jugador {

    Scanner entrada = new Scanner(System.in);

    private String nombre;
    private Carta carta;
    private final Mesa mesa;

    public Jugador(String name, Carta card, Mesa table) {

        nombre = name;
        carta = card;
        mesa = table;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String name) {
        nombre = name;
    }

    public Carta getCarta() {
        return carta;
    }

    public void setCarta(Carta card) {
        carta = card;
    }

    public void seleccionarCarta() {

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
            
            mesa.moverCartaInterior(xOrigen,yOrigen, xDest, yDest);
            
        } else if (opcion == 'e') {
            System.out.println("Introduce las nuevas coordenadas de la carta: ");
            System.out.println("Coordenada x: ");
            int xDest = entrada.nextInt();
            
            mesa.moverCartaExterior(xOrigen, yOrigen, xDest);

        }

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

}
