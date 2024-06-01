package Modelos.Barco;

import java.io.Serializable;

public class BalaLong implements Bala, Serializable {
    
    private BalaTipo tipoBala = BalaTipo.LONG;
    private int alcanceBala = 8;
    private int danoBala = 10;
    private int costoBala = 250;

    public BalaLong() {}

    public BalaTipo getTipoBala() {
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
