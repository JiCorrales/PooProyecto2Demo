package Modelos.Barco;

import java.util.List;

import java.io.Serializable;

public class Barco implements Serializable{
    private String capitanBarco;
    private int nivelSalud = 100;
    private int oroDisponible = 2500;
    private int posicionX, posicionY;
    private List<Bala> inventarioBalasCanon;
    private List<Radar> inventarioRadares;

    public Barco(String capitanBarco, int posicionInicialX, int posicionInicialY){
        this.capitanBarco = capitanBarco;
        this.posicionX = posicionInicialX;
        this.posicionY = posicionInicialY;
    }

    public String getCapitanBarco() {
        return capitanBarco;
    }
    public void setCapitanBarco(String capitanBarco) {
        this.capitanBarco = capitanBarco;
    }
    public int getNivelSalud() {
        return nivelSalud;
    }
    public void setNivelSalud(int nivelSalud) {
        this.nivelSalud = nivelSalud;
    }
    public int getOroDisponible() {
        return oroDisponible;
    }
    public void setOroDisponible(int oroDisponible) {
        this.oroDisponible = oroDisponible;
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
    public List<Bala> getInventarioBalasCanon() {
        return inventarioBalasCanon;
    }
    public void setInventarioBalasCanon(List<Bala> inventarioBalasCanon) {
        this.inventarioBalasCanon = inventarioBalasCanon;
    }
    public List<Radar> getInventarioRadares() {
        return inventarioRadares;
    }
    public void setInventarioRadares(List<Radar> inventarioRadares) {
        this.inventarioRadares = inventarioRadares;
    }
}
