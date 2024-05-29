package Cliente;

import Modelos.Barco.Barco;
import Modelos.Mensaje;

import java.io.DataOutputStream;
import java.io.ObjectOutputStream;

public class Cliente {
    private String nombre;
    private final String ip = "localhost";
    private final int puerto = getPuerto();
    private DataOutputStream salidaDatos;


    private Mensaje mensaje;
    public ObjectOutputStream salida;
    public Barco barco;


    //Métodos
    public String getNombre() {
        return nombre;
    }
    public String enviarMensaje(Mensaje mensaje) {
        System.out.println(mensaje);
        return "Mensaje enviado: " + mensaje.toString();
    }

    public Barco getBarco() {
        return barco;
    }
    private int getPuerto() {
        return puerto;
    }
}
