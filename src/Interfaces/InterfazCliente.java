package Interfaces;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.Icon;

import Cliente.Cliente;
import Modelos.Barco.BalaTipo;
import Modelos.Mensaje;
import Modelos.Tablero.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.logging.*;


public class InterfazCliente {

    Cliente cliente;
    private JFrame pantallaCliente;
    private JPanel tableroMatriz;
    private JTextPane historialChat;
    private JTextArea areaEscribirChat;
    private JLayeredPane jLayeredPane;
    private JScrollPane jScrollPane;
    private JLabel[][] celdasMatriz;
    private JLabel imagenLabel;
    private Tablero tablero;
    private JPanel cuadro;
    private int anchoCeldaMatriz = 30;
    private int altoCeldaMatriz = 30;
    private int posicionAnteriorX = -1;
    private int posicionAnteriorY = -1;
    private ImageIcon fondoCeldaVaciaTableroScaledIcon;
    private ImageIcon fondoCeldaTesoroTableroScaledIcon;
    private ImageIcon fondoCeldaAmenazaTableroScaledIcon;
    private ImageIcon fondoCeldaMercadoTableroScaledIcon;
    private ImageIcon barcoTableroScaledIcon;
    private JLabel oroLabel = new JLabel();
    private JLabel vidaLabel = new JLabel();
    private JLabel lblBalasLong = new JLabel();
    private JLabel lblBalasHeavy = new JLabel();
    private JLabel lblBalasMine = new JLabel();
    private JLabel lblRadarCorto = new JLabel();
    private JLabel lblRadarMedio = new JLabel();
    private JLabel lblRadarLargo = new JLabel();
    private JPanel panelMercado = new JPanel();
    JButton botonAtacar = new JButton("Atacar");
    public InterfazCliente(Tablero tablero) {
        this.tablero = tablero;

        cliente = new Cliente(this);

        configurarComponentesInterfazClientes(cliente.getNombre());
        configurarTablero(tablero);
        cargarImagenesCeldas();
    }
    public static void main(String[] args) {
        Tablero tablero = new Tablero();
        InterfazCliente interfazCliente = new InterfazCliente(tablero);
        SwingUtilities.invokeLater(interfazCliente::mostrar);
    }

    private void configurarComponentesInterfazClientes(String nombreJugador) {
        pantallaCliente = new JFrame("Cliente del jugador " + nombreJugador + " | Caza del Tesoro");
        jLayeredPane = new JLayeredPane();
        historialChat = new JTextPane();
        areaEscribirChat = new JTextArea();

        // Configuración de la pantalla del cliente
        pantallaCliente.setSize(1200, 800);
        pantallaCliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pantallaCliente.setLocationRelativeTo(null);
        jLayeredPane.setPreferredSize(new Dimension(pantallaCliente.getWidth(), pantallaCliente.getHeight()));

        // Configuración del fondo
        JLabel fondo = new JLabel();
        ImageIcon fondoIcon = new ImageIcon("src\\Interfaces\\fondoJuego.png");
        fondo.setIcon(fondoIcon);
        fondo.setBounds(0, 0, pantallaCliente.getWidth(), pantallaCliente.getHeight());
        jLayeredPane.add(fondo, JLayeredPane.DEFAULT_LAYER);

        // Configuración del historial del chat
        historialChat.setEditable(false);
        historialChat.setOpaque(false);
        historialChat.setForeground(Color.WHITE);
        historialChat.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        jScrollPane = new JScrollPane(historialChat);
        jScrollPane.setOpaque(false);
        jScrollPane.getViewport().setOpaque(false);
        jScrollPane.setBounds(50, pantallaCliente.getHeight() - 210, pantallaCliente.getWidth() - 120, 85);

        // Configuración del área de escritura de mensajes en el chat
        areaEscribirChat.setOpaque(false);
        areaEscribirChat.setForeground(Color.WHITE);
        areaEscribirChat.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        areaEscribirChat.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String mensaje = areaEscribirChat.getText().trim();
                    if (!mensaje.isEmpty()) {
                        enviarMensaje(mensaje, Color.BLACK);
                        areaEscribirChat.setText("");
                    }
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {}
        });
        areaEscribirChat.setBounds(50, pantallaCliente.getHeight() - 120, pantallaCliente.getWidth() - 120, 50);

        jLayeredPane.add(areaEscribirChat, JLayeredPane.PALETTE_LAYER);
        jLayeredPane.add(jScrollPane, JLayeredPane.PALETTE_LAYER);
        pantallaCliente.setContentPane(jLayeredPane);
    }

    public void mostrar() {
        pantallaCliente.setVisible(true);
    }

    public void configurarTablero(Tablero tablero) {
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

        // Configuración de la matriz del tablero
        JLabel fondoTablero = new JLabel();
        ImageIcon fondoTableroIcon = new ImageIcon("src/Interfaces/fondoTablero.jpeg");
        fondoTablero.setIcon(fondoTableroIcon);
        jLayeredPane.add(fondoTablero, JLayeredPane.DEFAULT_LAYER);

        for (int i = 0; i < mapaTablero.length; i++) {
            for (int j = 0; j < mapaTablero[0].length; j++) {
                cuadro = new JPanel(new BorderLayout());
                cuadro.setOpaque(false);
                cuadro.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                cuadro.setPreferredSize(new Dimension(anchoCeldaMatriz, altoCeldaMatriz));

                imagenLabel = new JLabel();

                cuadro.add(imagenLabel, BorderLayout.CENTER);
                tableroMatriz.add(cuadro);

                celdasMatriz[i][j] = imagenLabel;
            }
        }

        int anchoFondo = anchoCeldaMatriz * tablero.getTableroMapa()[0].length;
        int altoFondo = altoCeldaMatriz * tablero.getTableroMapa().length;

        tableroMatriz.setPreferredSize(new Dimension(anchoFondo + 50, altoFondo + 50));

        int x = (pantallaCliente.getWidth() - tableroMatriz.getPreferredSize().width) / 2;
        int y = (pantallaCliente.getHeight() - tableroMatriz.getPreferredSize().height) / 8;
        tableroMatriz.setBounds(x, y, tableroMatriz.getPreferredSize().width, tableroMatriz.getPreferredSize().height);
        jLayeredPane.add(tableroMatriz, JLayeredPane.PALETTE_LAYER);
    }

    public void agregarPanelInformacionBarco(Cliente cliente) {
        JPanel rectangulo = new JPanel();
        rectangulo.setLayout(new BorderLayout());
        rectangulo.setPreferredSize(new Dimension(250, 100));

        JPanel panelBarco = new JPanel();
        panelBarco.setLayout(new BorderLayout());
        panelBarco.setPreferredSize(new Dimension(135, 95));
        panelBarco.setOpaque(false);

        try {
            BufferedImage img = ImageIO.read(new File("src\\Modelos\\Barco\\barco.png"));
            ImageIcon iconoBarco = new ImageIcon(img.getScaledInstance(135, 95, Image.SCALE_SMOOTH));
            JLabel labelIconoBarco = new JLabel(iconoBarco);
            panelBarco.add(labelIconoBarco, BorderLayout.WEST);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel texto = new JLabel(cliente.getNombre());
        texto.setHorizontalAlignment(JLabel.CENTER);
        texto.setForeground(Color.WHITE);
        texto.setFont(new Font("Arial", Font.BOLD, 20));
        panelBarco.add(texto, BorderLayout.CENTER);

        rectangulo.add(panelBarco, BorderLayout.CENTER);
        rectangulo.setOpaque(false);
        jLayeredPane.add(rectangulo, JLayeredPane.PALETTE_LAYER);
        rectangulo.setBounds(10, 10, 250, 100);
    }

    public void enviarMensaje(String texto, Color color) {
        if (cliente.salida != null) {
            try {
                Mensaje mensaje = new Mensaje(cliente.getNombre(), texto);
                cliente.enviarMensaje(mensaje);
            } catch (Exception ex) {
                Logger.getLogger(InterfazCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Logger.getLogger(InterfazCliente.class.getName()).log(Level.SEVERE, "El flujo de salida no está inicializado");
        }
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

        ImageIcon barcoIcon = new ImageIcon("src\\Modelos\\Barco\\barco.png");
        Image barcoImage = barcoIcon.getImage();
        Image barcoScaledImage = barcoImage.getScaledInstance(anchoCeldaMatriz, altoCeldaMatriz, Image.SCALE_SMOOTH);
        barcoTableroScaledIcon = new ImageIcon(barcoScaledImage);
    }

    public void moverBarcoCelda(int x, int y) {
        // Mover el barco a la nueva celda
        int indexNuevo = x * tablero.getTableroMapa()[0].length + y;
        // Obtener el panel de la nueva celda
        JPanel cuadroNuevo = (JPanel) tableroMatriz.getComponent(indexNuevo);
        // Si la celda está ocupada, no se puede mover el barco
        if (posicionAnteriorX != -1 && posicionAnteriorY != -1) {

            int indexAnterior = posicionAnteriorX * tablero.getTableroMapa()[0].length + posicionAnteriorY;
            // Obtener el panel de la celda anterior
            JPanel cuadroAnterior = (JPanel) tableroMatriz.getComponent(indexAnterior);
            // Remover el barco de la celda anterior
            for (Component componente : cuadroAnterior.getComponents()) {
                if (componente instanceof JLabel) {
                    Icon icono = ((JLabel) componente).getIcon();
                    if (icono == barcoTableroScaledIcon) {
                        cuadroAnterior.remove(componente);
                        cuadroAnterior.revalidate();
                        cuadroAnterior.repaint();
                        break;
                    }
                }
            }
        }

        posicionAnteriorX = x;
        posicionAnteriorY = y;

        JLabel iconoBarcoLabel = new JLabel(barcoTableroScaledIcon);
        cuadroNuevo.add(iconoBarcoLabel);
        jLayeredPane.moveToFront(cuadroNuevo);
    }

    public void establecerPosicionInicialBarco(int x, int y) {
        posicionAnteriorX = x;
        posicionAnteriorY = y;
    }

    public void mostrarCelda(int x, int y, Celda celda) {
        if (celda instanceof CeldaVacia) {
            celdasMatriz[x][y].setIcon(fondoCeldaVaciaTableroScaledIcon);
        } else if (celda instanceof CeldaTesoro) {
            celdasMatriz[x][y].setIcon(fondoCeldaTesoroTableroScaledIcon);
        } else if (celda instanceof CeldaAmenaza) {
            celdasMatriz[x][y].setIcon(fondoCeldaAmenazaTableroScaledIcon);
        } else if (celda instanceof Mercado) {
            celdasMatriz[x][y].setIcon(fondoCeldaMercadoTableroScaledIcon);
        }
    }

    public void write(String text) {
        historialChat.setText(historialChat.getText() + text + "\n");
    }
    public void setVida(int valor) {
        vidaLabel.setText("Vida: " + valor);
        vidaLabel.setForeground(Color.WHITE);
        vidaLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ImageIcon corazonIcono = new ImageIcon("src\\Modelos\\Barco\\corazonIcono.png");
        Image corazonImage = corazonIcono.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        vidaLabel.setIcon(new ImageIcon(corazonImage));
        agregarPanelInformacion(vidaLabel, 10, 120);
    }

    public void setOro(int valor) {
        oroLabel.setText("Oro: " + valor);
        oroLabel.setForeground(Color.WHITE);
        oroLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ImageIcon oroIcono = new ImageIcon("src\\Modelos\\Barco\\oroIcono.png");
        Image oroImage = oroIcono.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        oroLabel.setIcon(new ImageIcon(oroImage));
        agregarPanelInformacion(oroLabel, 10, 230);
    }

    public void setBalasLong(int valor){
        lblBalasLong.setText("Balas Long: " + valor);
        lblBalasLong.setForeground(Color.WHITE);
        lblBalasLong.setFont(new Font("Arial", Font.BOLD, 14));
        ImageIcon balaIcono = new ImageIcon("src\\Modelos\\Barco\\balaLong.png");
        Image balaImage = balaIcono.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        lblBalasLong.setIcon(new ImageIcon(balaImage));
        agregarPanelInformacion(lblBalasLong, 10, 340);
    }

    public void setBalasHeavy(int valor){
        lblBalasHeavy.setText("Balas Heavy: " + valor);
        lblBalasHeavy.setForeground(Color.WHITE);
        lblBalasHeavy.setFont(new Font("Arial", Font.BOLD, 14));
        ImageIcon balaIcono = new ImageIcon("src\\Modelos\\Barco\\balaHeavy.jpg");
        Image balaImage = balaIcono.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        lblBalasHeavy.setIcon(new ImageIcon(balaImage));
        agregarPanelInformacion(lblBalasHeavy, 10, 370);
    }

    public void setBalasMine(int valor){
        lblBalasMine.setText("Balas Mine: " + valor);
        lblBalasMine.setForeground(Color.WHITE);
        lblBalasMine.setFont(new Font("Arial", Font.BOLD, 14));
        ImageIcon balaIcono = new ImageIcon("src\\Modelos\\Barco\\balaMine.jpg");
        Image balaImage = balaIcono.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        lblBalasMine.setIcon(new ImageIcon(balaImage));
        agregarPanelInformacion(lblBalasMine, 10, 400);
    }

    public void mostrarMercado() {
        JFrame mercadoFrame = new JFrame("Mercado");
        mercadoFrame.setSize(400, 400);
        mercadoFrame.setLocationRelativeTo(null);

        JPanel panelMercado = new JPanel(new GridBagLayout());
        panelMercado.setPreferredSize(new Dimension(250, 350));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        // Display player's gold
        JLabel oroLabel = new JLabel("Oro: " + cliente.getBarco().getOroDisponible());
        oroLabel.setForeground(Color.BLACK);
        oroLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panelMercado.add(oroLabel, gbc);

        // Reset grid width
        gbc.gridwidth = 1;

        // Display products and their prices
        ArrayList<BalaTipo> productos = new ArrayList<>(Arrays.asList(BalaTipo.LONG, BalaTipo.HEAVY, BalaTipo.MINE));
        String[] productosStrings = {BalaTipo.LONG.toString(), BalaTipo.HEAVY.toString(),
                              BalaTipo.MINE.toString(), "Radar Corto", "Radar Medio", "Radar Largo"};
        int[] precios = {100, 200, 300, 400, 500, 600}; // Example prices

        for (int i = 0; i < productosStrings.length; i++) {
            String producto = productosStrings[i];
            int precio = precios[i];

            JLabel productoLabel = new JLabel(producto + ": " + precio + " oro");
            productoLabel.setForeground(Color.BLACK);
            productoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.anchor = GridBagConstraints.WEST;
            panelMercado.add(productoLabel, gbc);

            JButton comprarButton = getComprarButton(productos, producto,precio, mercadoFrame);
            gbc.gridx = 1;
            gbc.gridy = i + 1;
            gbc.anchor = GridBagConstraints.EAST;
            panelMercado.add(comprarButton, gbc);
        }

        mercadoFrame.add(panelMercado);
        mercadoFrame.setVisible(true);
    }

//    private JButton getComprarButton(ArrayList<BalaTipo> productos,String producto, int precio, JFrame mercadoFrame) {
//        JButton comprarButton = new JButton("Comprar " + producto);
//        comprarButton.addActionListener(e -> {
//            if (cliente.getBarco().getOroDisponible() >= precio) {
//                cliente.getBarco().setOroDisponible(cliente.getBarco().getOroDisponible() - precio);
//                setOro(cliente.getBarco().getOroDisponible());
//                switch (productos){
//                    case BalaTipo.LONG:
//                        cliente.getBarco().getInventarioBalasCanon().add(new BalaLong());
//                        break;
//                    case BalaTipo.HEAVY:
//                        cliente.getBarco().getInventarioBalasCanon().add(new BalaHeavy());
//                        break;
//                    case BalaTipo.MINE:
//                        cliente.getBarco().getInventarioBalasCanon().add(new BalaMine());
//                        break;
//
//                }
//            } else {
//                JOptionPane.showMessageDialog(mercadoFrame, "No tienes suficiente oro para comprar " + producto, "Mercado", JOptionPane.INFORMATION_MESSAGE);
//            }
//        });
//        return comprarButton;
//    }

    public void setInventarioRadares(List<String> radares) {
        agregarPanelInventario(radares, "Inventario de Radares", 10, 450);
    }

    private void agregarPanelInformacion(JLabel label, int x, int y) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(250, 40));

        panel.add(label, BorderLayout.CENTER);
        panel.setOpaque(false);
        jLayeredPane.add(panel, JLayeredPane.PALETTE_LAYER);
        panel.setBounds(x, y, 250, 40);
    }

    private void agregarPanelInventario(List<String> elementos, String titulo, int x, int y) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(250, 100));

        JLabel tituloLabel = new JLabel(titulo);
        tituloLabel.setHorizontalAlignment(JLabel.CENTER);
        tituloLabel.setForeground(Color.WHITE);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(tituloLabel, BorderLayout.NORTH);

        JTextArea inventarioArea = new JTextArea();
        inventarioArea.setOpaque(false);
        inventarioArea.setForeground(Color.WHITE);
        inventarioArea.setFont(new Font("Arial", Font.PLAIN, 14));

        for (String elemento : elementos) {
            inventarioArea.append(elemento + "\n");
        }

        JScrollPane scrollPane = new JScrollPane(inventarioArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        panel.add(scrollPane, BorderLayout.CENTER);

        panel.setOpaque(false);
        jLayeredPane.add(panel, JLayeredPane.PALETTE_LAYER);
        panel.setBounds(x, y, 250, 100);
    }

    public void moverBarco(String direccion) {
        int actualPosicionX = cliente.getBarco().getPosicionX();
        int actualPosicionY = cliente.getBarco().getPosicionY();
        switch (direccion) {
            case "arriba":
                cliente.getBarco().setPosicionX(actualPosicionX - 1);
                cliente.getBarco().setPosicionY(actualPosicionY);
                moverBarcoCelda(cliente.getBarco().getPosicionX(), cliente.getBarco().getPosicionY());
                break;
            case "abajo":
                cliente.getBarco().setPosicionX(actualPosicionX + 1);
                cliente.getBarco().setPosicionY(actualPosicionY);
                moverBarcoCelda(cliente.getBarco().getPosicionX(), cliente.getBarco().getPosicionY());
                break;
            case "derecha":
                cliente.getBarco().setPosicionX(actualPosicionX);
                cliente.getBarco().setPosicionY(actualPosicionY + 1);
                moverBarcoCelda(cliente.getBarco().getPosicionX(), cliente.getBarco().getPosicionY());
                break;
            case "izquierda":
                cliente.getBarco().setPosicionX(actualPosicionX);
                cliente.getBarco().setPosicionY(actualPosicionY - 1);
                moverBarcoCelda(cliente.getBarco().getPosicionX(), cliente.getBarco().getPosicionY());
                break;
        }
    }

    public void crearBotones() {
        crearBotonAtacar();
    }
    private         JButton botonMostrarOcultar = new JButton("Interactuar celda");

    public void crearBotonMostrarOcultar(Tablero tablero) {

        botonMostrarOcultar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonMostrarOcultar.addActionListener(e -> {
            mostrarCelda(cliente.getBarco().getPosicionX(), cliente.getBarco().getPosicionY(), tablero.getTableroMapa()[cliente.getBarco().getPosicionX()][cliente.getBarco().getPosicionY()]);
            if (tablero.getTableroMapa()[cliente.getBarco().getPosicionX()][cliente.getBarco().getPosicionY()] instanceof CeldaTesoro){
                cliente.getBarco().setOroDisponible(cliente.getBarco().getOroDisponible() + 500);
                setOro(cliente.getBarco().getOroDisponible());
            } else if (tablero.getTableroMapa()[cliente.getBarco().getPosicionX()][cliente.getBarco().getPosicionY()] instanceof CeldaAmenaza) {
                Random random = new Random();
                cliente.getBarco().setNivelSalud(cliente.getBarco().getNivelSalud() - random.nextInt(30));
                setVida(cliente.getBarco().getNivelSalud());
            } else if (tablero.getTableroMapa()[cliente.getBarco().getPosicionX()][cliente.getBarco().getPosicionY()] instanceof Mercado) {
                mostrarMercado();
            }
        });

        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new BorderLayout());
        panelBoton.add(botonMostrarOcultar, BorderLayout.CENTER);
        panelBoton.setBounds(950, 20, 150, 50);

        jLayeredPane.add(panelBoton, JLayeredPane.MODAL_LAYER);
    }

    private void crearBotonAtacar() {
        botonAtacar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonAtacar.addActionListener(e -> {
        });

        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new BorderLayout());
        panelBoton.add(botonAtacar, BorderLayout.CENTER);
        panelBoton.setBounds(950, 80, 150, 50);

        jLayeredPane.add(panelBoton, JLayeredPane.MODAL_LAYER);
    }

    public void crearBotonesMovimiento() {
        final int[] turnosRestantes = {3};

        JPanel panelMovimiento = new JPanel();
        panelMovimiento.setLayout(new GridLayout(2, 2));
        panelMovimiento.setPreferredSize(new Dimension(150, 150));
        panelMovimiento.setBounds(950, 200, 150, 150);

        JButton botonArriba = new JButton("\u2191");
        JButton botonAbajo = new JButton("\u2193");
        JButton botonIzquierda = new JButton("\u2190");
        JButton botonDerecha = new JButton("\u2192");

        botonArriba.addActionListener(e -> {
            if (turnosRestantes[0] > 0) {
                moverBarco("arriba");
                turnosRestantes[0]--;
                JOptionPane.showMessageDialog(pantallaCliente, "Te has movido por lo que te quedan " + turnosRestantes[0] + " turnos.", "Mover Barco", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(pantallaCliente, "¡Ya no tienes turnos restantes!", "Mover Barco", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        botonAbajo.addActionListener(e -> {
            if (turnosRestantes[0] > 0) {
                moverBarco("abajo");
                turnosRestantes[0]--;
                JOptionPane.showMessageDialog(pantallaCliente, "Te has movido por lo que te quedan " + turnosRestantes[0] + " turnos.", "Mover Barco", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(pantallaCliente, "¡Ya no tienes turnos restantes!", "Mover Barco", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        botonIzquierda.addActionListener(e -> {
            if (turnosRestantes[0] > 0) {
                moverBarco("izquierda");
                turnosRestantes[0]--;
                JOptionPane.showMessageDialog(pantallaCliente, "Te has movido por lo que te quedan " + turnosRestantes[0] + " turnos.", "Mover Barco", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(pantallaCliente, "¡Ya no tienes turnos restantes!", "Mover Barco", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        botonDerecha.addActionListener(e -> {
            if (turnosRestantes[0] > 0) {
                moverBarco("derecha");
                turnosRestantes[0]--;
                JOptionPane.showMessageDialog(pantallaCliente, "Te has movido por lo que te quedan " + turnosRestantes[0] + " turnos.", "Mover Barco", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(pantallaCliente, "¡Ya no tienes turnos restantes!", "Mover Barco", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panelMovimiento.add(botonArriba);
        panelMovimiento.add(botonAbajo);
        panelMovimiento.add(botonIzquierda);
        panelMovimiento.add(botonDerecha);

        jLayeredPane.add(panelMovimiento, JLayeredPane.MODAL_LAYER);
    }

    //En esta pantalla se tienen que mostrar los productos que se pueden comprar.
    //O sea,
    //Mostrar los productos que se pueden comprar
    //los tres tipos de balas y los tres tipos de radares.
    public void agregarImagenesProductos(JFrame pantallaCompra){

    }
}