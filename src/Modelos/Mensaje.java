package Modelos;

import java.io.Serializable;

public class Mensaje implements Serializable{

    private String enviador;
    private String mensaje;

    public Mensaje(String enviador, String mensaje) {
        this.enviador = enviador;
        this.mensaje = mensaje;
    }

    public String getEnviador() {
        return enviador;
    }

    public void setEnviador(String enviador) {
        this.enviador = enviador;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Mensaje de " + enviador + ": \"" + mensaje;
    }
}