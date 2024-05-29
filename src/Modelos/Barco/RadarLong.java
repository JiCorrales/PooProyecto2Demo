package Modelos.Barco;

import java.io.Serializable;

public class RadarLong implements Radar, Serializable {
    private String tipoRadar = "Long";
    private int turnosRestantes = 3;
    private int radioDisponible = 8;

    public String getTipoRadar() {
        return tipoRadar;
    }
    public int getTurnosRestantes() {
        return turnosRestantes;
    }
    public int getRadioDisponible() {
        return radioDisponible;
    }
    @Override
    public void usarRadar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'usarRadar'");
    }
}
