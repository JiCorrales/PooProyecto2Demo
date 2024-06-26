import Interfaces.InterfazCliente;
import Interfaces.InterfazServidor;
import Modelos.Tablero.Tablero;

public class App {
    public static void main(String[] args) throws Exception {
        Tablero tablero = new Tablero();
        InterfazServidor interfazServidor = new InterfazServidor(tablero);
        interfazServidor.mostrarInterfaz();
        InterfazCliente interfazCliente = new InterfazCliente(tablero);
        interfazCliente.mostrarInterfaz();
        InterfazCliente interfazCliente2 = new InterfazCliente(tablero);
        interfazCliente2.mostrarInterfaz();
        InterfazCliente interfazCliente3 = new InterfazCliente(tablero);
        interfazCliente3.mostrarInterfaz();
        InterfazCliente interfazCliente4 = new InterfazCliente(tablero);
        interfazCliente4.mostrarInterfaz();

    }
}
