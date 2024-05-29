package Cliente;

import Modelos.Barco.Barco;
import Modelos.Mensaje;

public class Cliente {
    private String nombre;
    private String ip;
    private Mensaje mensaje;
    public String salida;
    public Barco barco;
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
}
