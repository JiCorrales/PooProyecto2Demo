package Interfaces;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import Modelos.Barco.Barco;
import Modelos.Tablero.Tablero;
import Modelos.Tablero.Celda;
import Modelos.Tablero.CeldaAmenaza;
import Modelos.Tablero.CeldaTesoro;
import Modelos.Tablero.CeldaVacia;
import Modelos.Tablero.Mercado;
import Servidor.Servidor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazServidor {
    private Servidor servidor;
    private Tablero tablero;
    private JFrame pantallaServidor;
    private JPanel tableroMatriz;
    private JTextPane historialChat;
    private JLayeredPane jLayeredPane;
    private JLabel[][] celdasMatriz; // Matriz para almacenar las referencias de cada celda
    private JLabel imagenLabel;
    private JPanel cuadro;
    private JScrollPane jScrollPane;
    private int anchoCeldaMatriz = 20;
    private int altoCeldaMatriz = 20;
    private JButton botonIniciarPartida;
    private ImageIcon fondoCeldaVaciaTableroScaledIcon;
    private ImageIcon fondoCeldaTesoroTableroScaledIcon;
    private ImageIcon fondoCeldaAmenazaTableroScaledIcon;
    private ImageIcon fondoCeldaMercadoTableroScaledIcon;
    public InterfazServidor(Tablero tablero) {
        this.tablero = tablero;
        configurarComponentesInterfazServidor();
        configurarTableroInterfaz(tablero);
        cargarImagenesCeldas();
        configurarCeldasInterfaz(tablero);
        servidor = new Servidor(this);

    }

    public static void main(String[] args) {
        InterfazServidor interfazServidor = new InterfazServidor(new Tablero());
        SwingUtilities.invokeLater(interfazServidor::mostrarInterfaz);
    }

    private void configurarComponentesInterfazServidor(){
        pantallaServidor = new JFrame("Servidor | Caza del Tesoro");
        jLayeredPane = new JLayeredPane();
        historialChat = new JTextPane();
        
        pantallaServidor.setSize(800, 600);
        pantallaServidor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pantallaServidor.setLocationRelativeTo(null);
        jLayeredPane.setPreferredSize(new Dimension(pantallaServidor.getWidth(), pantallaServidor.getHeight()));

        historialChat.setEditable(false);
        historialChat.setOpaque(false);
        historialChat.setForeground(Color.BLACK);
        historialChat.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        jScrollPane = new JScrollPane(historialChat);
        jScrollPane.setOpaque(false);
        jScrollPane.getViewport().setOpaque(false);
        jScrollPane.setBounds(50, pantallaServidor.getHeight() - 210, pantallaServidor.getWidth() - 120, 130);
        jLayeredPane.add(jScrollPane, JLayeredPane.PALETTE_LAYER);

        botonIniciarPartida = new JButton("Iniciar Partida");
        botonIniciarPartida.setBounds(jScrollPane.getX() + jScrollPane.getWidth() - 305, jScrollPane.getY() - 75, 300, 60);
        jLayeredPane.add(botonIniciarPartida, JLayeredPane.PALETTE_LAYER);
        botonIniciarPartida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                servidor.iniciarPartida();

            }
        });

        pantallaServidor.setContentPane(jLayeredPane);
        pantallaServidor.setVisible(true);
    }

    public void cambiarNombreBoton(){
        botonIniciarPartida.setText("Siguiente turno");
    }
    public void configurarTableroInterfaz(Tablero tablero){
        Celda[][] mapaTablero = tablero.getTableroMapa();
        tableroMatriz = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon fondoTableroIcon = new ImageIcon("src/Interfaces/fondoTablero.jpeg");
                Image fondoTableroImage = fondoTableroIcon.getImage();
                g.drawImage(fondoTableroImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        tableroMatriz.setLayout(new GridLayout(mapaTablero.length, mapaTablero[0].length));
        celdasMatriz = new JLabel[mapaTablero.length][mapaTablero[0].length];
    
        JLabel fondoTablero = new JLabel();
        ImageIcon fondoTableroIcon = new ImageIcon("src/Interfaces/fondoTablero.jpeg");
        fondoTablero.setIcon(fondoTableroIcon);
        jLayeredPane.add(fondoTablero, JLayeredPane.DEFAULT_LAYER);

        for (int i = 0; i < mapaTablero.length; i++) {
            for (int j = 0; j < mapaTablero[0].length; j++) {
                cuadro = new JPanel();
                cuadro.setOpaque(false);
                cuadro.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                cuadro.setPreferredSize(new Dimension(anchoCeldaMatriz, altoCeldaMatriz));
                cuadro.setLayout(new BorderLayout());

                imagenLabel = new JLabel();
                cuadro.add(imagenLabel, BorderLayout.CENTER);
                tableroMatriz.add(cuadro);
                celdasMatriz[i][j] = imagenLabel;
            }
        }

        int anchoFondo = (anchoCeldaMatriz * mapaTablero[0].length) + 50;
        int altoFondo = (altoCeldaMatriz * mapaTablero.length) + 50;
    
        tableroMatriz.setPreferredSize(new Dimension(anchoFondo, altoFondo));
    
        int x = 50;
        int y = (pantallaServidor.getHeight() - tableroMatriz.getPreferredSize().height) / 8;
        tableroMatriz.setBounds(x, y, tableroMatriz.getPreferredSize().width, tableroMatriz.getPreferredSize().height);
    
        jLayeredPane.add(imagenLabel, JLayeredPane.PALETTE_LAYER);
        jLayeredPane.add(tableroMatriz, JLayeredPane.DEFAULT_LAYER);
    }

    private void cargarImagenesCeldas() {
        ImageIcon celdaVaciaIcon = new ImageIcon("src/Modelos/Tablero/celda_vacia.png");
        Image fondoCeldaVaciaImage = celdaVaciaIcon.getImage();
        Image fondoCeldaVaciaScaledImage = fondoCeldaVaciaImage.getScaledInstance(anchoCeldaMatriz, altoCeldaMatriz, Image.SCALE_SMOOTH);
        fondoCeldaVaciaTableroScaledIcon = new ImageIcon(fondoCeldaVaciaScaledImage);

        ImageIcon celdaTesoroIcon = new ImageIcon("src/Modelos/Tablero/celda_tesoro.png");
        Image fondoCeldaTesoroImage = celdaTesoroIcon.getImage();
        Image fondoCeldaTesoroScaledImage = fondoCeldaTesoroImage.getScaledInstance(anchoCeldaMatriz, altoCeldaMatriz, Image.SCALE_SMOOTH);
        fondoCeldaTesoroTableroScaledIcon = new ImageIcon(fondoCeldaTesoroScaledImage);

        ImageIcon celdaAmenazaIcon = new ImageIcon("src/Modelos/Tablero/celda_amenaza.png");
        Image fondoCeldaAmenazaImage = celdaAmenazaIcon.getImage();
        Image fondoCeldaAmenazaScaledImage = fondoCeldaAmenazaImage.getScaledInstance(anchoCeldaMatriz, altoCeldaMatriz, Image.SCALE_SMOOTH);
        fondoCeldaAmenazaTableroScaledIcon = new ImageIcon(fondoCeldaAmenazaScaledImage);

        ImageIcon celdaMercadoIcon = new ImageIcon("src\\Modelos\\Tablero\\celda_mercado.png");
        Image fondoCeldaMercadoImage = celdaMercadoIcon.getImage();
        Image fondoCeldaMercadoScaledImage = fondoCeldaMercadoImage.getScaledInstance(anchoCeldaMatriz, altoCeldaMatriz, Image.SCALE_SMOOTH);
        fondoCeldaMercadoTableroScaledIcon = new ImageIcon(fondoCeldaMercadoScaledImage);
    }
    public void cargarImagenesBarcos(int i, int j) {
        ImageIcon barcoIcon = new ImageIcon("src/Modelos/Barco/barco.png");
        Image barcoImage = barcoIcon.getImage();
        Image barcoScaledImage = barcoImage.getScaledInstance(anchoCeldaMatriz, altoCeldaMatriz, Image.SCALE_SMOOTH);
        ImageIcon barcoScaledIcon = new ImageIcon(barcoScaledImage);
        celdasMatriz[i][j].setIcon(barcoScaledIcon);
    }
    public void configurarCeldasInterfaz(Tablero tablero) {
        Celda[][] mapaTablero = tablero.getTableroMapa();
        for (int i = 0; i < mapaTablero.length; i++) {
            for (int j = 0; j < mapaTablero[0].length; j++) {
                if (mapaTablero[i][j] instanceof CeldaVacia) {
                    celdasMatriz[i][j].setIcon(fondoCeldaVaciaTableroScaledIcon);
                } else if (mapaTablero[i][j] instanceof CeldaTesoro) {
                    celdasMatriz[i][j].setIcon(fondoCeldaTesoroTableroScaledIcon);
                } else if (mapaTablero[i][j] instanceof CeldaAmenaza) {
                    celdasMatriz[i][j].setIcon(fondoCeldaAmenazaTableroScaledIcon);
                } else if (mapaTablero[i][j] instanceof Mercado) {
                    celdasMatriz[i][j].setIcon(fondoCeldaMercadoTableroScaledIcon);
                } 
            }
        }
    }

    public void agregarAlHistorial(String texto, Color color) {
        StyledDocument doc = historialChat.getStyledDocument();
        Style style = historialChat.addStyle("Style", null);
        StyleConstants.setForeground(style, color);
        try {
            doc.insertString(doc.getLength(), texto + "\n", style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        historialChat.setCaretPosition(historialChat.getDocument().getLength());
    }

    public void actualizarPosicionBarco(int[] posicion, String nombre) {
        int x = posicion[0];
        int y = posicion[1];

        // Limpiar la posición anterior del barco (asumiendo que solo hay un barco por jugador)
        for (int i = 0; i < celdasMatriz.length; i++) {
            for (int j = 0; j < celdasMatriz[0].length; j++) {
                if (celdasMatriz[i][j].getIcon() != null && celdasMatriz[i][j].getIcon().equals(nombre)) {
                    celdasMatriz[i][j].setIcon(null);
                }
            }
        }

        // Establecer la nueva posición del barco
        celdasMatriz[x][y].setIcon(new ImageIcon("src/Modelos/Barco/barco.png")); // Asegúrate de que este es el icono correcto para el barco
        agregarAlHistorial("Barco de " + nombre + " movido a (" + x + ", " + y + ")", Color.BLUE);
    }
    public void mostrarInterfaz() {
        pantallaServidor.setVisible(true);
    }

    public JFrame getPantallaServidor() {
        return pantallaServidor;
    }
    public void moverBarcoCelda(int x, int y, Barco barco, int posicionAnteriorX, int posicionAnteriorY) {
        Icon icon = celdasMatriz[x][y].getIcon(); // Guardar el icono de la celda actual
        vaciarIconoCelda(x, y); // Vaciar la celda actual
        // Establecer la nueva posición del barco
        celdasMatriz[x][y].setIcon(new ImageIcon("src/Modelos/Barco/barco.png"));
        // Agregar al historial
        agregarAlHistorial("Barco de " + barco.getCapitanBarco() + " movido a (" + x + ", " + y + ")", Color.BLUE);
        // Cargar las imágenes de los barcos en las celdas
        cargarImagenesBarcos(x, y);
        // Quitar la imagen del barco de la celda anterior
        quitarImagenBarco(posicionAnteriorX, posicionAnteriorY, icon);

    }
    public void quitarImagenBarco(int x, int y, Icon icon) {
        celdasMatriz[x][y].setIcon(icon);
    }
    public void vaciarIconoCelda(int x, int y) {
        celdasMatriz[x][y].setIcon(null);
    }
}