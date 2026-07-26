package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import controllers.MapController;

public class MainFrame extends JFrame {
    private MapPanel mapPanel;
    private JPanel panelButton;
    private JPanel panelResult;
    private JTextArea mResultados;
    private JButton btnCrear, btnUnid, btnBid, btnInicio,btnFin,btnEliminar;
    private JButton btnBFS , btnDFS;
    private JButton btnGuardar, btnCargar, btnLimpiar;
    private JCheckBox chkRecorrido;
    public MainFrame(){
        setTitle("Mapa de calles BFS y DFS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setMinimumSize(new Dimension(900, 600));
        setResizable(true);
        setLocationRelativeTo(null);

        JPanel tamanios = new JPanel(null){
            @Override
            public void doLayout(){
                acomodarTamanio(this);
            }    
        };
        setContentPane(tamanios);
        mapPanel = new MapPanel();
        panelButton = aplicarBotones();
        panelResult = panelResultados();    
        tamanios.add(mapPanel);
        tamanios.add(panelButton);
        tamanios.add(panelResult);
        tamanios.setComponentZOrder(mapPanel, tamanios.getComponentCount()-1);
        new MapController(mapPanel,this);
        
        cargarMapa();

        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    private void cargarMapa() {
        try{
            File archivo = new File("/resources/maps/MapaED.png");
            Image imagen;
            if(archivo.exists()){
                imagen = ImageIO.read(archivo);
            }else{
                imagen = ImageIO.read(getClass().getResource("/resources/maps/MapaED.png"));
            }
            mapPanel.setImagen(imagen);
        }catch(Exception e){
            System.out.println("Error no se encontro la imagen" + e.getMessage());
        }
        
    }
    private JPanel panelResultados() {
        JPanel panelR = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                Graphics2D gtx = (Graphics2D) g;
                gtx.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                gtx.setColor(new Color(43, 45, 66, 220));
                gtx.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponents(g); 
            };
        };
        panelR.setOpaque(false);
        panelR.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        mResultados = new JTextArea("Ejecuta BFS o DFS para ver el resultado aqui.");
        mResultados.setEditable(false);
        mResultados.setLineWrap(true);
        mResultados.setWrapStyleWord(true);
        mResultados.setOpaque(false);
        mResultados.setForeground(Color.WHITE);
        mResultados.setFont(new Font("Consolas", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(mResultados);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        panelR.add(scroll, BorderLayout.CENTER);
        return panelR;

    }
    private void acomodarTamanio(JPanel tamanio) {
        int ancho = tamanio.getWidth();
        int alto = tamanio.getHeight();
        if(ancho<=0 || alto<=0) return;
        mapPanel.setBounds(0,0,ancho,alto);
        panelButton.setBounds(20,20,200,480);
        panelResult.setBounds(20,510,320,190);
    }
    
    private JPanel aplicarBotones() {
        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                Graphics2D gtx = (Graphics2D) g;
                gtx.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                gtx.setColor(new Color(43, 45, 66, 220));
                gtx.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponents(g); 
            };
        };
        panel.setOpaque(false);
        panel.setLayout(new GridLayout(0, 1, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // seccion de edicion
        panel.add(crearEtiqueta("EDICION"));
        btnCrear = crearBoton("Crear Nodo", "CREAR_NODO");
        btnBid = crearBoton("Conectar Doble", "CONECTAR_BIDIRECCIONAL");
        btnUnid = crearBoton("Conectar Uno", "CONECTAR_UNIDIRECCIONAL");
        btnEliminar = crearBoton("Eliminar", "ELIMINAR_NODO");

        panel.add(btnCrear);
        panel.add(btnBid);
        panel.add(btnUnid);
        panel.add(btnEliminar);


        panel.add(createSeparador());
        panel.add(crearEtiqueta("DEFINIR RUTA"));
        btnInicio = crearBoton("Inicio (A)", "MARCAR_INICIO");
        btnFin = crearBoton("Destino (B)", "MARCAR_FIN");
        panel.add(btnInicio);
        panel.add(btnFin);

        panel.add(createSeparador());
        panel.add(crearEtiqueta("ALGORITMOS"));

        JPanel panelRecorridos = new JPanel(new GridLayout(1, 2, 5, 0));
        panelRecorridos.setOpaque(false);
        btnBFS = new JButton("BFS");
        btnDFS = new JButton("DFS");
        btnBFS.setActionCommand("BFS");
        btnDFS.setActionCommand("DFS");
        estilizarAccion(btnBFS);
        estilizarAccion(btnDFS);
        panelRecorridos.add(btnBFS);
        panelRecorridos.add(btnDFS);
        panel.add(panelRecorridos);

        chkRecorrido = new JCheckBox("Ver Recorrido");
        chkRecorrido.setForeground(Color.WHITE);
        chkRecorrido.setOpaque(false);
        chkRecorrido.setActionCommand("TOGGLE_EXPLORACION");
        panel.add(chkRecorrido);

        panel.add(createSeparador());
        panel.add(crearEtiqueta("ARCHIVO"));
        btnGuardar = crearBoton("Guardar", "GUARDAR");
        btnCargar = crearBoton("Cargar", "CARGAR");
        btnLimpiar = crearBoton("Limpiar Todo", "LIMPIAR");

        panel.add(btnGuardar);
        panel.add(btnCargar);
        panel.add(btnLimpiar);
        return panel;     

    }
     private JLabel crearEtiqueta(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setForeground(new Color(200, 200, 200));
        label.setFont(new Font("Segoe UI", Font.BOLD, 10));
        return label;
    }

    private JSeparator createSeparador() {
        JSeparator separa = new JSeparator();
        separa.setForeground(new Color(100, 100, 100));
        return separa;
    }

    private JButton crearBoton(String texto, String comando) {
        JButton btn = new JButton(texto);
        btn.setActionCommand(comando);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(240, 240, 240));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    private void estilizarAccion(JButton btn) {
        btn.setFocusPainted(false);
        btn.setBackground(new Color(60, 120, 200));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    public JButton getBtnCrear() { return btnCrear; }
    public JButton getBtnBid() { return btnBid; }
    public JButton getBtnUnid() { return btnUnid; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnInicio() { return btnInicio; }
    public JButton getBtnFin() { return btnFin; }
    public JButton getBtnBFS() { return btnBFS; }
    public JButton getBtnDFS() { return btnDFS; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnCargar() { return btnCargar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JCheckBox getChkRecorrido() { return chkRecorrido; }

    
}
