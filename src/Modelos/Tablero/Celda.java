package Modelos.Tablero;

import Interfaces.InterfazServidor;

public interface Celda {
    void mostrar(InterfazServidor interfazServidor);
    void accionCelda(InterfazServidor interfazServidor);
    String getNombreTipoCelda();
    int getPosicionX();
    void setPosicionX(int posicionX);
    int getPosicionY();
    void setPosicionY(int posicionY);
    boolean isCeldaOcupadaPorBarco();
    void setCeldaOcupadaPorBarco(boolean b);
    boolean isCeldaVisible();
    void setCeldaVisible(boolean isCeldaVisible);
}
