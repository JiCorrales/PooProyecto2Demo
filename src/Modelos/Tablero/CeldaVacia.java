package Modelos.Tablero;

import java.io.Serializable;

import javax.swing.JOptionPane;

import Interfaces.InterfazServidor;

public class CeldaVacia implements Celda, Serializable {

    String nombreTipoCelda = "Celda Vacia";
    int posicionX, posicionY;
    boolean isCeldaOcupadaPorBarco = false;
    boolean isCeldaVisible = false;

    public CeldaVacia(int posicionX, int posicionY){}

    @Override
    public void mostrar(InterfazServidor interfazServidor) {
        accionCelda(interfazServidor);
    }

    @Override
    public void accionCelda(InterfazServidor interfazServidor) {
        JOptionPane.showMessageDialog(interfazServidor.getPantallaServidor(), "¡La celda esta vacia!", "Has encontrado una celda", JOptionPane.INFORMATION_MESSAGE);
    }

    public String getNombreTipoCelda() {
        return nombreTipoCelda;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }

    public boolean isCeldaOcupadaPorBarco() {
        return isCeldaOcupadaPorBarco;
    }

    public void setCeldaOcupadaPorBarco(boolean isCeldaOcupadaPorBarco) {
        this.isCeldaOcupadaPorBarco = isCeldaOcupadaPorBarco;
    }

    public boolean isCeldaVisible() {
        return isCeldaVisible;
    }

    public void setCeldaVisible(boolean isCeldaVisible) {
        this.isCeldaVisible = isCeldaVisible;
    }
}
