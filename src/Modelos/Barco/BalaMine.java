package Modelos.Barco;

import java.io.Serializable;

public class BalaMine implements Bala, Serializable {
    
    private String tipoBala = "Mine";
    private int alcanceBala = 0;
    private int danoBala = 50;
    private int costoBala = 1000;

    public BalaMine() {}

    public String getTipoBala() {
        return tipoBala;
    }

    public int getAlcanceBala() {
        return alcanceBala;
    }

    public int getDanoBala() {
        return danoBala;
    }

    public int getCostoBala() {
        return costoBala;
    }

    @Override
    public void atacar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atacar'");
    }
}
