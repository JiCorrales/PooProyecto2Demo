package Modelos.Barco;

public interface Radar {

    public RadarTipo getTipoRadar();
    public int getTurnosRestantes();
    public int getRadioDisponible();
    public void usarRadar();
}
