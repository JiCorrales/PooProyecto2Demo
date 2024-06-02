package Modelos.Barco;

import java.util.*;

import java.io.Serializable;

public class Barco implements Serializable {
    private String capitanBarco;
    private int nivelSalud = 100;
    private int oroDisponible = 2500;
    private int posicionX, posicionY;
    private ArrayList<Bala> inventarioBalasCanon = new ArrayList<Bala>() {

    };
    private List<Radar> inventarioRadares;

    public Barco(String capitanBarco, int posicionInicialX, int posicionInicialY) {
        this.capitanBarco = capitanBarco;
        this.posicionX = posicionInicialX;
        this.posicionY = posicionInicialY;
        inventarioRadares = new ArrayList<Radar>() {
        };
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

    public ArrayList<Bala> getInventarioBalasCanon() {
        return inventarioBalasCanon;
    }

    public void setInventarioBalasCanon(ArrayList<Bala> inventarioBalasCanon) {
        this.inventarioBalasCanon = inventarioBalasCanon;
    }

    public List<Radar> getInventarioRadares() {
        return inventarioRadares;
    }

    public void setInventarioRadares(List<Radar> inventarioRadares) {
        this.inventarioRadares = inventarioRadares;
    }

    public int getBalasLong() {
        int contador = 0;
        for (Bala bala : inventarioBalasCanon) {
            if (bala.getTipoBala() == BalaTipo.LONGG) {
                contador++;
            }
        }
        return contador;
    }

    public int getBalasHeavy() {
        int contador = 0;
        for (Bala bala : inventarioBalasCanon) {
            if (bala.getTipoBala() == BalaTipo.HEAVY) {
                contador++;
            }
        }
        return contador;
    }

    public int getBalasMine() {
        int contador = 0;
        for (Bala bala : inventarioBalasCanon) {
            if (bala.getTipoBala() == BalaTipo.MINE) {
                contador++;
            }
        }
        return contador;
    }

    public void agregarBalaLong() {
        inventarioBalasCanon.add(new BalaLong());
    }

    public void agregarBalaHeavy() {
        inventarioBalasCanon.add(new BalaHeavy());
    }

    public void agregarBalaMine() {
        inventarioBalasCanon.add(new BalaMine());
    }

    public int[] getPosicion() {
        int[] posicion = {posicionX, posicionY};
        return posicion;
    }

    public void getBalaSeleccionada(BalaTipo tipoBala) {
        for (Bala bala : inventarioBalasCanon) {
            if (bala.getTipoBala() == tipoBala) {
                inventarioBalasCanon.remove(bala);
                break;
            }
        }
    }
    public void setBalaSeleccionada(BalaTipo tipoBala) {
        switch (tipoBala) {
            case LONGG:
                agregarBalaLong();
                break;
            case HEAVY:
                agregarBalaHeavy();
                break;
            case MINE:
                agregarBalaMine();
                break;
        }
    }

    public int getRadar(RadarTipo radarTipo) {
        int contador = 0;
        for (Radar radar : inventarioRadares) {
            if (radar.getTipoRadar() == radarTipo) {
                contador++;
            }
        }
        return contador;
    }
    public void agregarRadar(RadarTipo radarTipo) {
        switch (radarTipo) {
            case SHORT:
                inventarioRadares.add(new RadarShort());
                break;
            case LONG:
                inventarioRadares.add(new RadarLong());
                break;
            case SPOTS:
                inventarioRadares.add(new RadarSpots());
                break;
        }
    }

    public void setBalasLong(int valor) {
        if (valor < 0) {
            removerBalasLong(valor);
        for (int i = 0; i < valor; i++) {
            agregarBalaLong();
        }
    }

}

    private void removerBalasLong(int valor) {
        for (int i = 0; i < valor; i++) {
            for (Bala bala : inventarioBalasCanon) {
                if (bala.getTipoBala() == BalaTipo.LONGG) {
                    inventarioBalasCanon.remove(bala);
                    break;
                }
            }
        }
    }
    public void setBalasHeavy(int valor) {
        if (valor < 0) {
            removerBalasHeavy(valor);
        }
        for (int i = 0; i < valor; i++) {
            agregarBalaHeavy();
        }
    }

    private void removerBalasHeavy(int valor) {
        for (int i = 0; i < valor; i++) {
            for (Bala bala : inventarioBalasCanon) {
                if (bala.getTipoBala() == BalaTipo.HEAVY) {
                    inventarioBalasCanon.remove(bala);
                    break;
                }
            }
        }
    }
    public void setBalasMine(int valor) {
        if (valor < 0) {
            removerBalasMine(valor);
        }
        for (int i = 0; i < valor; i++) {
            agregarBalaMine();
        }
    }
    private void removerBalasMine(int valor) {
        for (int i = 0; i < valor; i++) {
            for (Bala bala : inventarioBalasCanon) {
                if (bala.getTipoBala() == BalaTipo.MINE) {
                    inventarioBalasCanon.remove(bala);
                    break;
                }
            }
        }
    }

    public int getRadarLong() {
        int contador = 0;
        for (Radar radar : inventarioRadares) {
            if (radar.getTipoRadar() == RadarTipo.LONG) {
                contador++;
            }
        }
       return contador;
    }

    public int getRadarShort() {
        int contador = 0;
        for (Radar radar : inventarioRadares) {
            if (radar.getTipoRadar() == RadarTipo.SHORT) {
                contador++;
            }
        }
        return contador;
    }

    public int getRadarSpots() {
        int contador = 0;
        for (Radar radar : inventarioRadares) {
            if (radar.getTipoRadar() == RadarTipo.SPOTS) {
                contador++;
            }
        }
        return contador;
    }
}
