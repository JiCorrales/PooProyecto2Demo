package Cliente;

import Modelos.Mensaje;
import Modelos.Tablero.Mercado;
import Modelos.Tablero.Tablero;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadCliente extends Thread {

    boolean isRunning = true;
    private Socket socket;
    private Cliente cliente;
    private ObjectInputStream entrada;

    public ThreadCliente(Socket socket, Cliente cliente) {
        try {
            this.socket = socket;
            this.cliente = cliente;
            entrada = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
        }
    }

    public void run() {
        Object receivedObject;
        while (isRunning) {
            try {
                receivedObject = entrada.readObject();
                processReceivedObject(receivedObject);
            } catch (IOException | ClassNotFoundException ex) {

                System.out.println("Error al recibir el objeto");
                System.out.println(ex.getMessage());

            }
        }
    }

    private void processReceivedObject(Object receivedObject) {
        if (receivedObject instanceof Mensaje) {
            recibeMensaje(receivedObject);
        } else if (receivedObject instanceof Tablero) {
            recibeTablero(receivedObject);
        } else {
            System.out.println("Objeto no reconocido");
            System.out.println(receivedObject.toString());
        }
    }



    public void recibeMensaje(Object receivedObject){
        Mensaje mensaje = (Mensaje) receivedObject;
        cliente.getInterfazCliente().write(mensaje.toString());
        switch (mensaje.getContenido().trim()) {
            case "¡Ha iniciado la partida!":
                cliente.getInterfazCliente().agregarPanelInformacionBarco(cliente);
                break;
            case "Es tu turno":
                cliente.getInterfazCliente().crearBotonesMovimiento();
                break;
            default:
                break;
        }
    }
    public void recibeTablero(Object receivedObject) {
        Tablero tablero = (Tablero) receivedObject;
        cliente.crearBarco(cliente.getInterfazCliente(), tablero);
        cliente.getInterfazCliente().crearBotones();
        cliente.getInterfazCliente().crearBotonInteractuar(tablero);
        for (int i = 0; i < tablero.getTableroMapa().length; i++) {
            for (int j = 0; j < tablero.getTableroMapa()[0].length; j++) {
                if (tablero.getTableroMapa()[i][j] instanceof Mercado) {
                    cliente.getInterfazCliente().mostrarCelda(i, j, tablero.getTableroMapa()[i][j]);
                    tablero.getTableroMapa()[i][j].setCeldaVisible(true);
                }
            }
        }
    }
}
