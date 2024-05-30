package Cliente;

import java.net.Socket;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;

import java.util.Random;


import Interfaces.InterfazCliente;
import Modelos.Mensaje;
import Modelos.Barco.Barco;
import Modelos.Tablero.CeldaVacia;
import Modelos.Tablero.Tablero;

public class Cliente {
    private final String IP = "localhost";
    private final int PUERTO = 8089;
    private Socket socket;
    private DataOutputStream salidaDatos;
    private InterfazCliente interfazCliente;
    private ThreadCliente threadCliente;
    private String nombre;
    private Barco barco;
    public ObjectOutputStream salida;

    public Cliente(InterfazCliente interfazCliente) {
        this.interfazCliente = interfazCliente;
        conectar();
    }


    public synchronized void conectar() {
        try {
            socket = new Socket(IP, PUERTO);
            salida = new ObjectOutputStream(socket.getOutputStream());
            salidaDatos = new DataOutputStream(socket.getOutputStream());
            threadCliente = new ThreadCliente(socket, this);
            threadCliente.start();
            // Pedir el nombre
            this.nombre = JOptionPane.showInputDialog("Nombre:");
            salidaDatos.writeUTF(this.nombre);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar con el servidor. Se debería de iniciar el servidor antes de iniciar con el cliente.");
        }
    }

    public synchronized void enviarMensaje(Mensaje mensaje) {
        if (salida != null) {
            try {
                salida.writeObject(mensaje);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tienes que escribir algo para poder enviarlo.");
        }
    }

    public void crearBarco(InterfazCliente interfazCliente, Tablero tablero){
        Random random = new Random();
        int selectorEstatico = random.nextInt(3);
        //Caso 0: Inicia con 0 fijo en X
        //Caso 1: Inicia con 14 fijo en X
        //Caso 2: Inicia con 0 fijo en Y
        //Caso 3: Inicia con 14 fijo en Y
        int posicionBarcoX = 0;
        int posicionBarcoY = 0;

        switch(selectorEstatico){
            case 0:
                posicionBarcoX = 0;
                posicionBarcoY = random.nextInt(14);
                break;
            case 1:
                posicionBarcoX = 14;
                posicionBarcoY = random.nextInt(14);
                break;
            case 2:
                posicionBarcoX = random.nextInt(14);
                posicionBarcoY = 0;
                break;
            case 3:
                posicionBarcoX = random.nextInt(14);
                posicionBarcoY = 14;
                break;
        }
        barco = new Barco(nombre, posicionBarcoX, posicionBarcoY);
        tablero.getTableroMapa()[posicionBarcoX][posicionBarcoY] = new CeldaVacia(posicionBarcoX, posicionBarcoY);
        tablero.getTableroMapa()[posicionBarcoX][posicionBarcoY].setCeldaVisible(true);
        interfazCliente.mostrarCelda(posicionBarcoX, posicionBarcoY, tablero.getTableroMapa()[posicionBarcoX][posicionBarcoY]);
        interfazCliente.moverBarcoCelda(posicionBarcoX, posicionBarcoY);
        interfazCliente.setVida(barco.getNivelSalud());
        interfazCliente.setOro(barco.getOroDisponible());
    }

    public void moverBarco(int posicionX, int posicionY){
        barco.setPosicionX(posicionX);
        barco.setPosicionY(posicionY);
    }

    public Barco getBarco() {
        return barco;
    }

    public void setBarco(Barco barco) {
        this.barco = barco;
    }

    public InterfazCliente getInterfazCliente() {
        return interfazCliente;
    }

    public String getNombre() {
        return nombre;
    }

    //Quiero una funcion que me sume un entero y un double y me retorne el valor de la suma
    
}
