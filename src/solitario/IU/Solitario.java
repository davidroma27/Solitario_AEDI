/**
 * Representa el juego del solitario, con sus reglas.
 * Se recomienda una implementación modular.
 */
package solitario.IU;

import solitario.Core.Baraja;
import solitario.Core.Jugador;
import solitario.Core.Mesa;

/**
 *
 * @author AEDI
 */
public class Solitario {

    private static Jugador jugador;
    private static Mesa mesa;

    private static void mostrarMenuInicio() {
        System.out.println("[*] Bienvenid@ al juego del Solitario [*]\n[1] Comenzar partida\n[0] Salir");
    }

    //Método que crea un jugador al principio de la partida
    private static Jugador crearJugador() {
        String nombre;
        //Mientras que no se introduce un nombre, o se introduce vacío, se pide introducir un nombre
        do {
            nombre = ES.pideCadena("[?] Introduce tu nombre para comenzar a jugar: ");
        } while (nombre.trim().equals("")); // Comprueba que el nombre no esté vacío
        System.out.print("[+]Registrando al jugador ");
        System.out.println(nombre);
        return new Jugador(nombre); //Al final se crea una instancia del Jugador con su nombre
    }

    //Método que crea una mesa al principio de la partida
    private static Mesa crearJuego() throws Exception {
        System.out.println("[*] Generando un nuevo juego ...");
        Baraja baraja = new Baraja(); //Se crea una instancia de la baraja
        Mesa _mesa = new Mesa(); //Se crea una instancia de la mesa
        _mesa.colocarCartas(baraja); //Se colocan las cartas en la mesa pasandole la baraja
        return _mesa;

    }

    //Despues de crear Jugador y mesa:
    //Mostramos la informacion del jugador y las cartas visibles (mostrarMesa())
    private static void mostrarInterfaz() {
        System.out.print("[+] Jugador : ");
        System.out.println(jugador.getNombre());
        System.out.println("[+] Mesa : \n");
        mesa.mostrarMesa(); //Se llama a la funcion mostrarMesa de la clase mesa
        System.out.println("\n[%] Opciones: \n| 1) Mover carta del montón interior al montón exterior\n| 2) Mover carta del montón interior al montón interior\n| 0) Salir");
    }

    
    private static int mostrarVictoria() {
        int opcion;
        System.out.println("[+]¡Enorabuena, has ganado!");
        do {
            opcion = ES.pideNumero("[?] ¿Quieres jugar de nuevo?\n| 1)Si\n| 0)No\n");
        } while (opcion != 1 && opcion != 0);
        return opcion;
    }

    //Es llamado en el metodo Jugar() debido a que no quedan mas movPosibles
    private static int mostrarDerrota() {
        int opcion;
        System.out.println("[+]¡Lo siento, has perdido!");
        do {
            opcion = ES.pideNumero("[?] ¿Quieres jugar de nuevo?\n| 1)Si\n| 0)No\n");
        } while (opcion != 1 && opcion != 0);
        return opcion;
    }

    private static void salir() {
        System.out.println("[*] Gracias por jugar al Solitario, esperamos verte pronto [*]");
        System.exit(0);
    }

    //Metodo que se ejecuta durante la partida
    private static int jugar() {
        int opcion;
        while (!mesa.MontonExteriorCompleto() && jugador.movPosibles()) { // Si no ha finalizado el juego sigue mostrando interfaz
            do {
                mostrarInterfaz();
                opcion = ES.pideNumero("[?] Selecciona una opción: ");
            } while (opcion < 0 || opcion > 2);
            switch (opcion) {
                case 1: //Opcion 1: Mover una carta al monton exterior
                    int[] posicion = jugador.seleccionarCarta(); //Almacenamos las posiciones de la carta origen elegida
                    try {
                        //Una vez obtenidas fila y columna, se realiza el movimiento
                        jugador.moverCartaExterior(posicion[0], posicion[1]); //Realizamos el movimiento con la carta elegida
                    } catch (Exception err) { 
                        //Se captura la excepción lanzada desde el método comprobarMovimientoExterior() en Jugador
                        System.out.print("[!] ");
                        System.out.println(err.getMessage());
                    }
                    break;
                case 2: //Opcion 2: Mover una carta entre montones interiores
                    int[] posOri = jugador.seleccionarCarta(); 
                    int[] posDest = jugador.seleccionarDestino();
                    try {
                        jugador.moverCartaInterior(posOri[0], posOri[1], posDest[0], posDest[1]);
                    } catch (Exception err) {
                        //Se captura la excepción lanzada desde el método comprobarMovimientoInterior() en Jugador
                        System.out.print("[!] ");
                        System.out.println(err.getMessage());
                    }
                    break;
                case 0:
                    salir();
            }
        }

        //Si el monton exterior esta completo, mostramos victoria. Si no, mostramos derrota (porque no quedan movPosibles)
        //mostrarVictoria() y mostrarDerrota() devuelven un int(0,1) correspondiente a la opción elegida por el usuario
        //1-Jugar de nuevo
        //0-Salir
        return mesa.MontonExteriorCompleto() ? mostrarVictoria() : mostrarDerrota();
    }

    //La clase main llama a este método y comienza la partida
    public static void inicioPartida() {

        int opcion;
        //Mostrar menu de inicio
        do {
            mostrarMenuInicio(); //Muestra las opciones de Jugar o Salir
            opcion = ES.pideNumero("[?] Selecciona una opción: ");
        } while (opcion != 1 && opcion != 0);

        if (opcion == 0) {
            salir();
        }

        //Crear jugador
        jugador = crearJugador();

        //Crear partida
        do {
            try {
                mesa = crearJuego();
            } catch (Exception err) {
                System.err.print("[!] No se ha podido crear el juego: ");
                System.err.println(err.getMessage());
                System.exit(1); // Sale con error
            }

            //Jugar
            opcion = jugar(); //Almacena el int devuelto correspondiente a la opcion elegida por el usuario
                              //Jugar de nuevo(1) o salir(0)
        } while (opcion != 0);
        salir();
    }

    //Funcion llamada por la clase Jugador
    //Pregunta la fila y columna correspondientes a la carta que se va a mover
    //Esto se realiza llamando a la clase ES con el metodo pideNumero()
    public static int[] seleccionarPosicion() {
        int[] posicion = {-1, -1}; //Variable (array) que almacena la posicion
        do {
            posicion[0] = ES.pideNumero("[*]Selecciona la fila deseada [0-3]: ");
        } while (posicion[0] < 0 || posicion[0] > 3); //Se controla que el numero a introducir corresponda a una fila (0,1,2 ó 3)
        do {
            posicion[1] = ES.pideNumero("[*]Selecciona la columna deseada [0-3]: ");
        } while (posicion[1] < 0 || posicion[1] > 3); //Se controla que el numero a introducir corresponda a una columna(0,1,2 ó 3)
        return posicion;
    }

}
