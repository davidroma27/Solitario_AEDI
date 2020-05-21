
package solitario.Core;

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

    //Funcion llamada por el metodo Jugar() de la clase Solitario
    //seleccionarCarta() a su vez llama al metodo seleccionarPosicion() de la clase Solitario
    //Pregunta para mover una carta de un monton ORIGEN
    public int[] seleccionarCarta() {
        System.out.println("[?] Qué carta quieres mover del montón interior?");
        return solitario.IU.Solitario.seleccionarPosicion();

    }

    //Funcion llamada por el metodo Jugar() de la clase Solitario
    //seleccionarDestino() a su vez llama al metodo seleccionarPosicion() de la clase Solitario
    //Pregunta para escoger un monton DESTINO
    public int[] seleccionarDestino() {
        System.out.println("[?] Indica la posición de destino de tu carta");
        return solitario.IU.Solitario.seleccionarPosicion();
    }

    public void comprobarMovimientoInterior(int filaOri, int colOri, int filaDest, int colDest) throws Exception {
        //Comprobar si el montón desde donde se quiere mover la carta está vacío
        if (Mesa.montonInterior[filaOri][colOri].empty()) {
            throw new Exception("Movimiento inválido : No se pueden mover cartas desde un espacio vacío");
        }
    
        //Comprobar si el montón al que se quiere mover la carta está vacío
        if (Mesa.montonInterior[filaDest][colDest].empty()) {
            throw new Exception("Movimiento inválido : No se pueden mover cartas a espacios vacíos");
        }
    
        //Una vez listas las comprobaciones previas vemos que cartas queremos coger
        Carta cartaOri = Mesa.montonInterior[filaOri][colOri].peek();

        //Vemos sobre que carta queremos ponerla
        Carta cartaDest = Mesa.montonInterior[filaDest][colDest].peek();

        //Comprobar que la carta que hemos cogido y la carta sobre la cual vamos a poner sean del mismo palo
        if (!cartaOri.getPalo().equals(cartaDest.getPalo())) {
            throw new Exception("Movimiento inválido : No se pueden juntar cartas de distintos palos");
        }

        //Comprobar que el número de la carta origen sea menor que la del destino
        //Encima del 12 no se puede poner nada
        int numDest = cartaDest.getNumero();
        int numOri = cartaOri.getNumero();
        
        if (numOri == 12) {
            throw new Exception("Movimiento inválido : La carta de destino no es una unidad mayor que la de origen");
        }
        
        if (numOri == 7 && numDest != 10) {
            throw new Exception("Movimiento inválido : La carta de destino no es una unidad mayor que la de origen");
        }
        
        if (numOri != 7 && numOri != (numDest-1)) {
            throw new Exception("Movimiento inválido : La carta de destino no es una unidad mayor que la de origen");
        }
    }
    
    public void moverCartaInterior(int filaOri, int colOri, int filaDest, int colDest) throws Exception {
        //!!!Lo primero que hacermos es comprobar si se puede realizar el movimiento!!!
        this.comprobarMovimientoInterior(filaOri, colOri, filaDest, colDest);
        // Una vez listas las comprobaciones podremos mover la carta
        Mesa.montonInterior[filaDest][colDest].push(Mesa.montonInterior[filaOri][colOri].pop()); // Movemos a la posicion de destino la carta situada en posicion origen
    }

    public int comprobarMovimientoExterior(int filaOri, int colOri) throws Exception {
    
        //Comprobar si el montón desde donde se quiere mover la carta está vacío
        if (Mesa.montonInterior[filaOri][colOri].empty()) {
            throw new Exception("Movimiento inválido : No se pueden mover cartas desde un espacio vacío");
        }
    
        //Vemos que carta vamos a mover al montón Exterior
        Carta cartaOri = Mesa.montonInterior[filaOri][colOri].peek();

        //Miramos el palo de la carta que acabamos de coger y le asignamos el montón del mismo Palo
        int montonDest = cartaOri.getPalo().ordinal();

        //Comprobación de que la primera carta escogida para mover sea un AS
        if (Mesa.montonExterior[montonDest].empty()) {
            if (cartaOri.getNumero() != 1) {
                throw new Exception("Movimiento inválido : Si un montón de un palo está vacío la primera carta a poner debe ser un as");
            }
        } else {

            //Vemos que carta vamos a solapar
            Carta cartaDest = Mesa.montonExterior[montonDest].peek();

            //Comprobar cartaOri sea una unidad mayor sobre la carta a solapar.
            if ((cartaOri.getNumero() == 10 && cartaDest.getNumero() != 7)
                    || (cartaOri.getNumero() != 10 && cartaOri.getNumero() - 1 != cartaDest.getNumero())) {
                throw new Exception("Movimiento inválido :La carta de destino no es una unidad menor que la de origen");
            }
        }
        return montonDest; //Si no lanza ninguna excepción, devuelve el monton correspondiente al palo de esa carta
    }
    
    //Funcion que mueve la carta elegida del monton interior a su monton exterior correspondiente
    public void moverCartaExterior(int filaOri, int colOri) throws Exception {
        //!!!Lo primero que hacermos es comprobar si se puede realizar el movimiento!!!
        int montonDest = this.comprobarMovimientoExterior(filaOri,colOri);
        //Una vez listas las comprobaciones movemos la carta al montón exterior
        Mesa.montonExterior[montonDest].push(Mesa.montonInterior[filaOri][colOri].pop());
    }    
        
    //Se llama en cada bucle del metodo Jugar() en Solitario
    public boolean movPosibles() {
        boolean quedanMov = false; //Inicializamos a false la variable que indica si quedan mov.posibles 

        int fila, columna; //Fila y columna que referencian una carta origen 
        int filaC, columnaC; //Fila y columna que referencian una carta destino

        fila = 0;

        //Recorre cada fila del monton interior
        while(!quedanMov && fila < Mesa.montonInterior.length) {
            columna = 0;
            //Recorre las columnas de cada fila del monton interior
            while(!quedanMov && columna < Mesa.montonInterior[fila].length) {

                // --- Comprobaciones del monton interior
                filaC = 0;
                while(!quedanMov && filaC < Mesa.montonInterior.length) {
                    columnaC = 0;
                    while(!quedanMov && columnaC < Mesa.montonInterior[fila].length) {
                            try {
                                //Comprueba el movimiento posible entre la carta origen y carta destino
                                this.comprobarMovimientoInterior(fila, columna, filaC, columnaC);
                                quedanMov = true; //Si el movimiento es posible, devuelve true y continúa la partida
                            } catch(Exception err) {}   
                            
                        columnaC++;
                    }
                    filaC++;
                }

                // --- Comprobaciones del montón exterior
                try {
                    this.comprobarMovimientoExterior(fila, columna);
                    quedanMov = true;
                } catch(Exception err) {}

                columna++;
            }
            fila++;
        }

        return quedanMov;
    }
}
