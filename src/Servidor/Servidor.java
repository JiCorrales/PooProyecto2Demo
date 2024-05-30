package Servidor;

import Interfaces.InterfazServidor;
import Modelos.Mensaje;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Servidor{
    private final int port = 8084;
    ServerSocket serverSocket;
    InterfazServidor pantallaServidor;
    ArrayList<ThreadServidor> clientesConectados;
    ServerConnectionsThread conexionsThread;

    public Servidor(InterfazServidor pantallaServidor){
        this.pantallaServidor = pantallaServidor;
        this.initServer();
        clientesConectados = new ArrayList<ThreadServidor>();
        conexionsThread = new ServerConnectionsThread(this);
        conexionsThread.start();
    }

    private void initServer() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Servidor iniciado en el puerto " + port);
        } catch (IOException ex) {
            System.out.println("Error al iniciar el servidor");   }
    }

    public void broadcast(Mensaje mensaje) {
        for (ThreadServidor tsDelCliente : clientesConectados) {
            try {
                tsDelCliente.salida.writeObject(mensaje);
            } catch (IOException ex) {
                System.out.println("Error al enviar mensaje a todos los clientes");
            }
        }
    }

    public void iniciarPartida() {
        System.out.println("Partida iniciada");
    }
}
