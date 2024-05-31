package Servidor;

import Interfaces.InterfazServidor;
import Modelos.Mensaje;
import Modelos.Tablero.*;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor{
    private int turnosRestantes = 1;
    private int turnoActual;
    private final int port = 8084;
    private ServerSocket serverSocket;
    private InterfazServidor interfazServidor;
    private ArrayList<ThreadServidor> clientesConectados;
    private ServerConnectionsThread conexionsThread;

    public Servidor(InterfazServidor pantallaServidor){
        this.interfazServidor = pantallaServidor;
        this.initServer();
        clientesConectados = new ArrayList<ThreadServidor>();
        conexionsThread = new ServerConnectionsThread(this);
        conexionsThread.start();
    }

    private void initServer() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            System.out.println("Error al iniciar el servidor");   }
    }

    public void iniciarPartida() {
        Tablero tablero = new Tablero();
        if (clientesConectados.size() >= 2 && clientesConectados.size() <= 4){
            if (turnosRestantes == 1){
                enviarMensaje(new Mensaje("Servidor", "¡Ha iniciado la partida!"));
                interfazServidor.configurarCeldasInterfaz(configurarTablero(tablero));
                manejarTurno();
                turnosRestantes = turnosRestantes - 1;
            } else {
                interfazServidor.cambiarNombreBoton();
                manejarTurno();
            }
            JOptionPane.showMessageDialog(interfazServidor.getPantallaServidor(), "¡La partida ha comenzado!", "Iniciar Partida", JOptionPane.INFORMATION_MESSAGE);
        } else if (clientesConectados.size() < 2) {
            JOptionPane.showMessageDialog(interfazServidor.getPantallaServidor(), "No se puede iniciar la partida con menos de 2 jugadores.", "Iniciar Partida", JOptionPane.INFORMATION_MESSAGE);
        } else if (clientesConectados.size() > 4){
            JOptionPane.showMessageDialog(interfazServidor.getPantallaServidor(), "¡Hay demasiados jugadores! Solo pueden jugar de 2 a 4 jugadores.", "Iniciar Partida", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public void enviarMensaje(Mensaje mensaje){
        for (ThreadServidor threadServidor : clientesConectados) {
            try {
                threadServidor.salida.writeObject(mensaje);
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public Tablero configurarTablero(Tablero tablero) {
        Random random = new Random();
        int numeroTipoCelda;
        List<String> tiposCeldas = new ArrayList<>();
        tiposCeldas.add("Vacia");
        tiposCeldas.add("Tesoro");
        tiposCeldas.add("Amenaza");
        tiposCeldas.add("Mercado");
        for (int i = 0; i < tablero.getTableroMapa().length; i++) {
            for (int j = 0; j < tablero.getTableroMapa()[0].length; j++) {
                numeroTipoCelda = random.nextInt(4);
                switch (tiposCeldas.get(numeroTipoCelda)) {
                    case "Vacia":
                        tablero.getTableroMapa()[i][j] = new CeldaVacia(i, j);
                        break;
                    case "Tesoro":
                        tablero.getTableroMapa()[i][j] = new CeldaTesoro(i, j);
                        break;
                    case "Amenaza":
                        tablero.getTableroMapa()[i][j] = new CeldaAmenaza(i, j);
                        break;
                    case "Mercado":
                        tablero.getTableroMapa()[i][j] = new Mercado(i, j);
                        break;
                    default:
                        System.out.println("Ese tipo de celda no existe");
                        break;
                }
            }
        }
        for (ThreadServidor threadServidor : clientesConectados) {
            try {
                threadServidor.salida.writeObject(tablero);
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tablero;
    }
    public void manejarTurno(){
        ThreadServidor clienteActual = clientesConectados.get(turnoActual);
        clienteActual.enviarMensaje(new Mensaje("Servidor", "Es tu turno"));
        turnoActual = (turnoActual + 1) % clientesConectados.size();
    }
    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public ArrayList<ThreadServidor> getClientesConectados() {
        return clientesConectados;
    }

    public InterfazServidor getInterfazServidor() {
        return interfazServidor;
    }
}
