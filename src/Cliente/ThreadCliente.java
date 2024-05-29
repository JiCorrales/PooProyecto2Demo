package Cliente;

import Modelos.Mensaje;
import Modelos.Barco.Barco;
import Modelos.Tablero.CeldaAmenaza;
import Modelos.Tablero.CeldaTesoro;
import Modelos.Tablero.CeldaVacia;
import Modelos.Tablero.Mercado;
import Modelos.Tablero.Tablero;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Random;
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
        } catch (IOException ex) {}
    }

    public void run() {
        while (isRunning) {
            try {
                Object receivedObject = entrada.readObject();
                
                if (receivedObject instanceof Mensaje) {
                    Mensaje mensaje = (Mensaje) receivedObject;
                    cliente.getInterfazCliente().write(mensaje.toString());
                    switch (mensaje.getMensaje().trim()) {
                        case "¡Ha iniciado la partida!":
                            cliente.getInterfazCliente().agregarPanelInformacionBarco(cliente);
                            break;
                        case "Es tu turno":
                            cliente.getInterfazCliente().crearBotonesMovimiento();
                            break;
                        default:
                            break;
                    }
                } else if (receivedObject instanceof Tablero) {
                    Tablero tablero = (Tablero) receivedObject;
                    cliente.crearBarco(cliente.getInterfazCliente(), tablero);
                    cliente.getInterfazCliente().crearBotones();
                    cliente.getInterfazCliente().crearBotonMostrarOcultar(tablero);
                     for (int i = 0; i < tablero.getTableroMapa().length; i++) {
                        for (int j = 0; j < tablero.getTableroMapa()[0].length; j++) {
                            if (tablero.getTableroMapa()[i][j] instanceof Mercado){
                                cliente.getInterfazCliente().mostrarCelda(i, j, tablero.getTableroMapa()[i][j]);
                                tablero.getTableroMapa()[i][j].setCeldaVisible(true);
                            }
                        }
                    }
                }

            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
