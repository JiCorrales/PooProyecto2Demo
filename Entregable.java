/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danielasuarez
 */
public class Entregable {
    private String numeroReferencia;
    private String descripcion;
    private String remitente;

    public Entregable(String numeroReferencia, String descripcion, String remitente) {
        this.numeroReferencia = numeroReferencia;
        this.descripcion = descripcion;
        this.remitente = remitente;
    }

    public String getNumeroReferencia() {
        return numeroReferencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getRemitente() {
        return remitente;
    }
}
