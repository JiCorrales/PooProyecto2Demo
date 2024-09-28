/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danielasuarez
 */
import java.util.ArrayList;
import java.util.List;

public class Counter {
    private String nombre;
    private String cedulaJuridica;
    private String direccion;
    private int cantidadCasilleros;
    private List<Casillero> casilleros;
    private List<Cliente> clientes;

    public Counter(String nombre, String cedulaJuridica, String direccion, int cantidadCasilleros) {
        this.nombre = nombre;
        this.cedulaJuridica = cedulaJuridica;
        this.direccion = direccion;
        this.cantidadCasilleros = cantidadCasilleros;
        this.casilleros = new ArrayList<>();
        this.clientes = new ArrayList<>();
        crearCasilleros();
    }

    private void crearCasilleros() {
        for (int i = 1000; i < 1000 + cantidadCasilleros; i++) {
            casilleros.add(new Casillero(i, "Libre"));
        }
    }

    public Casillero asignarCasillero(Cliente cliente) {
        for (Casillero casillero : casilleros) {
            if (casillero.getEstado().equals("Libre")) {
                casillero.asignarCliente(cliente);
                cliente.setCasillero(casillero);
                return casillero;
            }
        }
        return null; // Si no hay casilleros disponibles
    }

    public void liberarCasillero(int numero) {
        for (Casillero casillero : casilleros) {
            if (casillero.getNumero() == numero) {
                casillero.liberarCasillero();
                break;
            }
        }
    }

    public Casillero buscarCasilleroDisponible() {
        for (Casillero casillero : casilleros) {
            if (casillero.getEstado().equals("Libre")) {
                return casillero;
            }
        }
        return null;
    }

    public String consultarEstadoCasilleroPorCliente(String clienteId) {
        for (Cliente cliente : clientes) {
            if (cliente.getId().equals(clienteId)) {
                return "Casillero del cliente " + clienteId + ": " + cliente.getCasillero().getNumero();
            }
        }
        return "Cliente no encontrado";
    }

    public String consultarEstadoCasilleroPorNumero(int casilleroNumero) {
        for (Casillero casillero : casilleros) {
            if (casillero.getNumero() == casilleroNumero) {
                return "Casillero número " + casilleroNumero + " está " + casillero.getEstado();
            }
        }
        return "Casillero no encontrado";
    }

    public void notificarClienteRecepcion(Cliente cliente, Entregable entregable) {
        // Simulación de notificación por correo
        System.out.println("Notificación enviada a " + cliente.getCorreo() + " sobre el entregable " + entregable.getNumeroReferencia());
    }

    public List<Casillero> getCasilleros() {
        return casilleros;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void agregarCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }
}

