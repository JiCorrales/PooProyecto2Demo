package Modelos;

import Cliente.Cliente;
import Modelos.Barco.Barco;

import java.io.Serializable;

public class Mensaje implements Serializable{
    Cliente cliente;
    private String remitente;
    private String contenido;
    private int[] posicion;
    private int[] posicionAnterior;
    private Barco barco;
    public Mensaje(String enviador, String mensaje) {
        this.remitente = enviador;
        this.contenido = mensaje;
    }
    public Mensaje(String enviador, String mensaje, int[] posicion, Barco barco, int[] posicionAnterior) {
        this.remitente = enviador;
        this.contenido = mensaje;
        this.posicion = posicion;
        this.barco = barco;
        this.posicionAnterior = posicionAnterior;
    }
    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }


    public int[] getPosicion() {
        return posicion;
    }
    public int[] getPosicionAnterior() {
        return posicionAnterior;
    }
    public void setPosicion(int[] posicion) {
        this.posicion = posicion;
    }
    @Override
    public String toString() {
        return "Mensaje de " + remitente + ": \"" + contenido;
    }

    public Barco getBarco() {
        return barco;
    }

    public void setBarco(Barco barco) {
        this.barco = barco;
    }
}