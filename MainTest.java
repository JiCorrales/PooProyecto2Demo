/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danielasuarez
 */
public class MainTest {
    public static void main(String[] args) {
        // Crear un counter de prueba
        Counter counter = new Counter("Counter Central", "123456789", "San José", 10);

        // Crear un cliente
        Cliente cliente1 = new Cliente("C001", "Juan Pérez", "juan.perez@mail.com", "8888-8888", "Calle 123", "M");

        // Agregar el cliente al counter
        counter.agregarCliente(cliente1);

        // Asignar un casillero al cliente
        Casillero casilleroAsignado = counter.asignarCasillero(cliente1);
        if (casilleroAsignado != null) {
            System.out.println("Casillero asignado al cliente " + cliente1.getNombre() + ": Número " + casilleroAsignado.getNumero());
        } else {
            System.out.println("No hay casilleros disponibles para asignar.");
        }

        // Consultar el estado del casillero asignado
        String estadoCasilleroCliente = counter.consultarEstadoCasilleroPorCliente(cliente1.getId());
        System.out.println(estadoCasilleroCliente);

        // Liberar el casillero
        counter.liberarCasillero(casilleroAsignado.getNumero());
        System.out.println("Casillero número " + casilleroAsignado.getNumero() + " ha sido liberado.");

        // Consultar nuevamente el estado del casillero
        String estadoCasilleroLiberado = counter.consultarEstadoCasilleroPorNumero(casilleroAsignado.getNumero());
        System.out.println(estadoCasilleroLiberado);
    }
}

