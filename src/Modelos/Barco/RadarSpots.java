package Modelos.Barco;

import java.io.Serializable;

public class RadarSpots implements Radar, Serializable {
    private RadarTipo tipoRadar = RadarTipo.SPOTS;
    private int turnosRestantes = 3;
    private int radioDisponible = 3;

    public RadarTipo getTipoRadar() {
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
    @Override
    public String toString() {
        return "SPOTS";
    }
}
