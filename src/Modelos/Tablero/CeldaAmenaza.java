package Modelos.Tablero;

import java.io.Serializable;

import Interfaces.InterfazServidor;

public class CeldaAmenaza implements Celda, Serializable {

    String nombreTipoCelda = "Celda Amenaza";
    int posicionX, posicionY;
    boolean isCeldaOcupadaPorBarco = false;
    boolean isCeldaVisible = false;

    public CeldaAmenaza(int posicionX, int posicionY){}

    @Override
    public void mostrar(InterfazServidor interfazServidor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrar'");
    }

    @Override
    public void accionCelda(InterfazServidor interfazServidor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accionCelda'");
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
