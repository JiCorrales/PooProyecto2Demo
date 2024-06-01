package Modelos.Barco;

import java.io.Serializable;

public class BalaHeavy implements Bala, Serializable {

    //quiero usar el enumerable de tipo de bala pero no se como hacerlo
    private BalaTipo tipoBala = BalaTipo.HEAVY;

    private int alcanceBala = 3;
    private int danoBala = 20;
    private int costoBala = 250;

    public BalaHeavy() {}

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
    public String toString() {
        return "Bala Heavy";
    }

    @Override
    public void atacar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atacar'");
    }
}
