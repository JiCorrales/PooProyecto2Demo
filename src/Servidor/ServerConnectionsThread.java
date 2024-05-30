package Servidor;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerConnectionsThread extends Thread{
    private boolean isRunning = true;
    Servidor server;

    public ServerConnectionsThread(Servidor server) {
        this.server = server;
    }

    public void run(){
        while (isRunning) {
            try {
                server.pantallaServidor.write("Esperando nuevo cliente...");
                Socket nuevoSocket = server.serverSocket.accept();
                ThreadServidor ts = new ThreadServidor(nuevoSocket, server);
                ts.start();
                server.clientesConectados.add(ts);
                server.pantallaServidor.write("Cliente " + server.clientesConectados.size() + " aceptado");
                if (server.clientesConectados.size() == 4){
                    isRunning = false;
                }
            } catch (IOException ex) {
                //Logger.getLogger(ServerConnectionsThread.class.getName()).log(Level.SEVERE, null, ex);
            }


        }
    }

}