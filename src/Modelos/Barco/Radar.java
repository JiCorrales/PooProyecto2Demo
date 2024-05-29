package Modelos.Barco;

import java.io.Serializable;

public interface Radar {

    public String getTipoRadar();
    public int getTurnosRestantes();
    public int getRadioDisponible();
    public void usarRadar();
}
