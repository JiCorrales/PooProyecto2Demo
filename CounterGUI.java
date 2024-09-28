/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danielasuarez
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel; // Importación para DefaultTableModel
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CounterGUI extends JFrame {
    private JTextField nombreField;
    private JTextField cedulaField;
    private JTextField direccionField;
    private JTextField casillerosField;
    private JTable casillerosTable;
    private DefaultTableModel casillerosTableModel;

    public CounterGUI() {
        setTitle("Creación de Counter");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        // Campos de texto para los datos del counter
        add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        add(nombreField);

        add(new JLabel("Cédula Jurídica:"));
        cedulaField = new JTextField();
        add(cedulaField);

        add(new JLabel("Dirección:"));
        direccionField = new JTextField();
        add(direccionField);

        add(new JLabel("Cantidad de Casilleros:"));
        casillerosField = new JTextField();
        add(casillerosField);

        // Botón para crear el counter
        JButton crearButton = new JButton("Crear Counter");
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearCounter();
            }
        });
        add(crearButton);

        // Tabla para mostrar los casilleros
        casillerosTableModel = new DefaultTableModel();
        casillerosTableModel.addColumn("Número de Casillero");
        casillerosTableModel.addColumn("Estado");
        casillerosTable = new JTable(casillerosTableModel);
        JScrollPane scrollPane = new JScrollPane(casillerosTable);
        add(scrollPane);
    }

    private void crearCounter() {
        String nombre = nombreField.getText().trim();
        String cedula = cedulaField.getText().trim();
        String direccion = direccionField.getText().trim();
        String casillerosText = casillerosField.getText().trim();

        // Validación de campos vacíos
        if (nombre.isEmpty() || cedula.isEmpty() || direccion.isEmpty() || casillerosText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben ser llenados", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validación de cantidad de casilleros
        int cantidadCasilleros;
        try {
            cantidadCasilleros = Integer.parseInt(casillerosText);
            if (cantidadCasilleros <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad de casilleros debe ser un número entero positivo", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear el Counter
        Counter counter = new Counter(nombre, cedula, direccion, cantidadCasilleros);

        // Mostrar mensaje de éxito
        JOptionPane.showMessageDialog(this, "Counter creado:\n" + counter);

        // Actualizar la tabla con los casilleros
        actualizarTablaCasilleros(counter.getCasilleros());
    }

    private void actualizarTablaCasilleros(List<Casillero> casilleros) {
        // Limpiar la tabla antes de agregar los nuevos casilleros
        casillerosTableModel.setRowCount(0);
        for (Casillero casillero : casilleros) {
            casillerosTableModel.addRow(new Object[]{casillero.getNumero(), casillero.getEstado()});
        }
    }

    public static void main(String[] args) {
        CounterGUI app = new CounterGUI();
        app.setVisible(true);
    }
}


