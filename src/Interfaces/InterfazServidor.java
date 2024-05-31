package Interfaces;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

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

    public void mostrarInterfaz() {
        pantallaServidor.setVisible(true);
    }

    public JFrame getPantallaServidor() {
        return pantallaServidor;
    }
}