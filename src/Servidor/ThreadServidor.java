package Servidor;

import Modelos.Mensaje;
import Servidor.Servidor;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadServidor extends Thread {
    public Socket socket;
    private Servidor server;
    private ObjectInputStream entrada;
    private DataInputStream entradaDatos;
    ObjectOutputStream salida;
    String nombre;
    private boolean isRunning = true;

    public ThreadServidor(Socket socket, Servidor server) {
        this.socket = socket;
        this.server = server;
        try {
            entrada = new ObjectInputStream(socket.getInputStream());
            salida = new ObjectOutputStream(socket.getOutputStream());
            entradaDatos = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {

        }
    }

    public void enviarMensaje(Mensaje mensaje) {
        try {
            salida.writeObject(mensaje);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Object receivedObject;

        try {
            this.nombre = entradaDatos.readUTF();
            server.getInterfazServidor().agregarAlHistorial("Recibido el nombre: " + nombre, Color.RED);
        } catch (IOException ex) {
            // do nothing
        }

        while (isRunning) {
            try {
                receivedObject = entrada.readObject();
                if (receivedObject instanceof Mensaje) {
                    Mensaje mensaje = (Mensaje) receivedObject;
                    if (mensaje.getPosicion() != null) {
                        server.actualizarPosicionBarco(mensaje.getPosicion(), nombre, mensaje.getBarco(), mensaje.getPosicionAnterior());
                    } else {
                        server.enviarMensaje(mensaje);
                        server.getInterfazServidor().agregarAlHistorial("Recibido: " + mensaje, Color.BLACK);
                    }

                }
            } catch (IOException | ClassNotFoundException ex) {
                // do nothing
            }
        }

    }
}