package Modelos.Tablero;

import java.io.Serializable;

public class Tablero implements Serializable {
    private Celda tableroMapa[][] = new Celda[15][15];

    public Tablero() {
    }

    public Celda[][] getTableroMapa() {
        return tableroMapa;
    }
    
}
