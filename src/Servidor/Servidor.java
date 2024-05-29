package Servidor;

import Interfaces.InterfazServidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Servidor{
    private final int port = 8000;
    ServerSocket serverSocket;
    InterfazServidor pantallaServidor;
    ArrayList<ThreadServidor> clientesConectados;
    ServerConnectionsThread conexionsThread;

    public Servidor(InterfazServidor pantallaServidor){
        this.pantallaServidor = pantallaServidor;
        this.connect();
        clientesConectados = new ArrayList<ThreadServidor>();
        conexionsThread = new ServerConnectionsThread(this);
        conexionsThread.start();
    }
    private void connect() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            System.out.println("Error al crear el servidor");
        }
    }




    public void iniciarPartida(){
        System.out.println("Partida iniciada");
    }
}
