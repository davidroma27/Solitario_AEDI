package solitario.IU;

import java.util.Scanner;

/**
 *
 * @author AEDI
 */
public class ES {

    public static Scanner leer = new Scanner(System.in);

    public static String pideCadena(String mensaje) {
        // Poner el mensaje
        System.out.print(mensaje);

        // Pedir
        return leer.nextLine();

    }

    public static int pideNumero(String mensaje) { // Correccion error si no se introduce un int 
        int numero = -1;
        boolean error;

        do {
            error = false;
            System.out.print(mensaje);
            try {
                numero = Integer.parseInt(leer.nextLine());
            } catch (NumberFormatException err) {
                error = true;
            }
        } while (error);

        return numero;

    }
}
