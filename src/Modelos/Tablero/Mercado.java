package Modelos.Tablero;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Interfaces.InterfazServidor;
import Modelos.Barco.Bala;
import Modelos.Barco.BalaHeavy;
import Modelos.Barco.BalaLong;
import Modelos.Barco.BalaMine;
import Modelos.Barco.Radar;
import Modelos.Barco.RadarLong;
import Modelos.Barco.RadarShort;
import Modelos.Barco.RadarSpots;

public class Mercado implements Celda, Serializable {

    String nombreTipoCelda = "Mercado";
    int posicionX, posicionY;
    boolean isCeldaOcupadaPorBarco = false;
    boolean isCeldaVisible = false;
    
    List<Bala> balasDisponibles;
    List<Radar> radaresDisponibles;

    public Mercado(int posicionX, int posicionY) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        balasDisponibles = new ArrayList<>();
        radaresDisponibles = new ArrayList<>();
    
        for (int i = 0; i <= 3; i++){
            balasDisponibles.add(new BalaHeavy());
            balasDisponibles.add(new BalaLong());
            balasDisponibles.add(new BalaMine());
            radaresDisponibles.add(new RadarShort());
            radaresDisponibles.add(new RadarLong());
            radaresDisponibles.add(new RadarSpots());
        }
    }

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

    public List<Bala> getBalasDisponibles() {
        return balasDisponibles;
    }
    public void setBalasDisponibles(List<Bala> balasDisponibles) {
        this.balasDisponibles = balasDisponibles;
    }
    public List<Radar> getRadaresDisponibles() {
        return radaresDisponibles;
    }
    public void setRadaresDisponibles(List<Radar> radaresDisponibles) {
        this.radaresDisponibles = radaresDisponibles;
    }

    public String getNombreTipoCelda() {
        return nombreTipoCelda;
    }

    public void setNombreTipoCelda(String nombreTipoCelda) {
        this.nombreTipoCelda = nombreTipoCelda;
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
