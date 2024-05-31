package Servidor;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;


public class ServerConnectionsThread extends Thread{
    private boolean isRunning = true;
    Servidor server;

    public ServerConnectionsThread(Servidor server) {
        this.server = server;
    }

    public void run(){
        while (isRunning) {
            try {
                server.getInterfazServidor().agregarAlHistorial("Esperando nuevo cliente...", Color.RED);
                Socket nuevoSocket = server.getServerSocket().accept();
                ThreadServidor ts = new ThreadServidor(nuevoSocket, server);
                ts.start();
                server.getClientesConectados().add(ts);
                server.getInterfazServidor().agregarAlHistorial("Cliente " + server.getClientesConectados().size() + " aceptado", Color.RED);
                if (server.getClientesConectados().size() == 4){
                    isRunning = false;
                    JOptionPane.showMessageDialog(null, "Se han conectado 4 jugadores, ya no se aceptarán más conexiones");
                }
            } catch (IOException _) {

            }
        }
    }

}