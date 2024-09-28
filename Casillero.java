/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danielasuarez
 */
public class Casillero {
    private int numero;
    private String estado; // Libre u Ocupado
    private Cliente cliente;

    public Casillero(int numero, String estado) {
        this.numero = numero;
        this.estado = estado;
    }

    public void asignarCliente(Cliente cliente) {
        this.cliente = cliente;
        this.estado = "Ocupado";
    }

    public void liberarCasillero() {
        this.cliente = null;
        this.estado = "Libre";
    }

    public int getNumero() {
        return numero;
    }

    public String getEstado() {
        return estado;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
