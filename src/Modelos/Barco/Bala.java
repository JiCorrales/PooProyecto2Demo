package Modelos.Barco;

import java.io.Serializable;

public interface Bala {

    public String getTipoBala();
    public int getAlcanceBala();
    public int getDanoBala();
    public int getCostoBala();
    public void atacar();
}
