/**
 * Representa el juego del solitario, con sus reglas.
 * Se recomienda una implementación modular.
 */
package solitario.IU;

import pila.PilaVaciaExcepcion;
import solitario.Core.Baraja;
import solitario.Core.Jugador;
import solitario.Core.Mesa;

/**
 *
 * @author AEDI
 */
public class Solitario {

    //Atributos.
    private static Jugador jugador;
    private static Baraja baraja = new Baraja();
    private static Mesa mesa = new Mesa();

    //En vez de introducir todo en inicioPartida() creé otro método llamado jugarTurnos() donde se 
    //desarrolla el solitario.
    //Muestra un error de visibilidad.??
    public static void inicioPartida() {

        String temp = null;

        System.out.println("---------------- JUEGO DEL SOLITARIO ------------------");
        System.out.println("");
        ES.introducirEnter("Bienvenid@ al SOLITARIO, pulsa ENTER para introducir tu nombre y empezar la partida. ");
        System.out.println("");

        jugador = crearJugador();

        do {

            try {

                System.out.println("INICIO DE LA PARTIDA.");

                //Se vacía la mesa.
                mesa.mostrarMesa();

                //Coloca las cartas en la mesa.
                mesa.colocarCartas(baraja);

                //Se muestra la mesa con las cartas.
                mesa.mostrarMesa();

                jugarTurnos();

                //Otra partida.
                temp = ES.pideCadena("Quieres jugar otra partida " + jugador.getNombre() + " (s/n): ");

                //Controlamos algún posible error si no borraremos el try-cath.
            } catch (Exception exc) {
                System.err.println("ERROR: " + exc.getMessage());
            }

            //Fin de juego.
            finDePartida();

        } while (temp.toLowerCase().equals("s"));

    }//Fin inicioPartida().

    private static void jugarTurnos() throws PilaVaciaExcepcion {

        int op;
        boolean hayMovimientos = true;

        System.out.print("----------------------------------- MESA -------------------------------");
        System.out.println(mesa);

        //Fila y Columna ORIGEN.
        int x = 0;
        int y = 0;

        //Fila y Columna DESTINO.
        int n;
        int m;

        //Montón Exterior DESTINO.
        int t = 0;

        do {

            System.out.print("----------------------------------- MESA -------------------------------");
            System.out.println(mesa);

            op = ES.pideNumero("Qué movimineto deseas hacer? "
                    + "\n (1)-->MOVER INTERIOR."
                    + "\n (2)-->MOVER EXTERIOR. "
                    + " ");

            switch (op) {
                case 1:

                    do {

                        x = ES.pideNumero("Dime la filOrigen: ");
                        y = ES.pideNumero("Dime la colOrigen: ");

                        n = ES.pideNumero("Dime la filDestino: ");
                        m = ES.pideNumero("Dime la colDestino: ");

                        t = ES.pideNumero("Dime el monton Destino: ");

                    } while ((x < 0 || x > 3) && ((y < 0 || y > 3)) && ((n < 0 || n > 3)) && ((m < 0 || m > 3)) && ((t < 0 || t > 3)));

                    mesa.moverCartaInterior(x, y, n, m);
                    System.out.println(mesa);

//                        if (Solitario.finDePartida() == true) {
//                        }
                    break;

                case 2:
                    do {

                        x = ES.pideNumero("Dime la filOrigen: ");

                        y = ES.pideNumero("Dime la colOrigen: ");

                        t = ES.pideNumero("Dime el monton Destino: ");

                    } while ((x < 0 || x > 3) && ((y < 0 || y > 3)) && ((t < 0 || t > 3)));

                    mesa.moverCartaExterior(x, y, t);
                    System.out.println(mesa);

//                        if (Solitario.finDePartida() == true) {
//                        }
                    break;
                default:
                    System.err.println("Opción incorrecta.... ");
            }

        } while (hayMovimientos == false);

        finDelJuego();

    }//Fin jugarTurno.

//Crea el jugador.
    private static Jugador crearJugador() {

        Jugador j = new Jugador(ES.pideCadena("Introduce tu nombre: "));

        return j;
    }

//Métodos de fin de partida.   
//Es redundante terner los dos metodos...??? finDelJuego() finDePartida()
    private static void finDelJuego() {

    }

//Nos dice al terminar la partida si se consiguio el objetivo o no.
    private static boolean finDePartida() {

        if (mesa.verificarMontonExterior() == true) {
            System.out.println("Objetivo conseguido....");
        } else {
            System.out.println("Objetivo no conseguido... ");
        }
        return true;
    }

//Comprobación si hay ganador.
//Para ello comprobamos que los montones exteriores estan
//todas las cartas (10 cartas) 1-2-3-4-5-6-7-10-11-12 ordenadas por palos.
    public static boolean hayGanador() {

        return false;
    }

} //Fin clase Solitario.

