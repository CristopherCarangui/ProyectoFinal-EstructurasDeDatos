package controllers;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import graphs.Graph;
import graphs.PathFinder;
import graphs.PathResult;
import graphs.implementations.BFSPathFinder;
import graphs.implementations.DFSPathFinder;
import models.MapPoint;
import persistence.FileGraphRepository;
import persistence.GraphRepository;
import views.MainFrame;
import views.MapPanel;

public class MapController {
    
    private MapPanel mapPanel;
    private MainFrame mainFrame;
    private Graph<MapPoint> grafo = new Graph<>();
    private GraphRepository repositorio = new FileGraphRepository(); 
    private PathFinder<MapPoint> bfs =  new BFSPathFinder<>();
    private PathFinder<MapPoint> dfs = new DFSPathFinder<>();
    private MapPoint inicio;
    private MapPoint finalA;
    private int contadorPuntos =0;
    private MapPoint seleccionado;

    public MapController(MapPanel mapPanel, MainFrame mainFrame) {
        this.mapPanel = mapPanel;
        this.mainFrame = mainFrame;
        this.mapPanel.setGraph(grafo);
        configurarCrearNodo();
        configurarConexionBidireccional();
        configurarConexionUnidireccional();
        configurarEliminarNodo();
        configurarMarcarInicio();
        configurarMarcarFin();
        configurarBFS();
        configurarDFS();
        configurarGuardar();
        configurarCargar();
        configurarLimpiar();
        mapPanel.setClickListener(this::manejarClic);
        
    }


    private void manejarClic(int x, int y, MapPoint cercano) {
    switch (mapPanel.getModo()) {
            case "CREAR":
                if (cercano == null) {
                    contadorPuntos++;
                    MapPoint nuevo = new MapPoint("P" + contadorPuntos, x, y);
                    grafo.add(nuevo);
                    mapPanel.repaint();
                }
                break;
            case "BIDIRECCIONAL":
                if(cercano != null){
                    if(seleccionado == null){
                        seleccionado = cercano;
                        mapPanel.setSeleccionado(seleccionado);
                    }else{
                        if(seleccionado.equals(cercano)){
                            JOptionPane.showMessageDialog(mapPanel,"No puedes conectar un nodo consigo mismo");
                            return;
                        }
                        grafo.addEdge(seleccionado, cercano);
                        seleccionado = null;
                        mapPanel.setSeleccionado(null);
                        mapPanel.repaint(); 
                    }
                }
                break;
            case "UNIDIRECCIONAL":
                if(cercano != null){
                    if(seleccionado == null){
                        seleccionado = cercano;
                        mapPanel.setSeleccionado(seleccionado);
                    }else{
                        if(seleccionado.equals(cercano)){
                            JOptionPane.showMessageDialog(mapPanel,"No puedes conectar un nodo consigo mismo");
                            return;
                        }
                        grafo.addEdgeUni(seleccionado, cercano);
                        seleccionado = null;
                        mapPanel.setSeleccionado(null);
                        mapPanel.repaint(); 
                    }
                }
                break;
            case "INICIO":
                if (cercano != null) {
                    inicio = cercano;
                    mapPanel.setInicio(inicio);
                    mapPanel.repaint();
                }
                break;
            case "FIN":
                if (cercano != null) {
                    finalA = cercano;
                    mapPanel.setFinal(finalA);
                    mapPanel.repaint();
                }
                break;
            case "ELIMINAR":
                if (cercano != null) {
                    grafo.removeNode(cercano);

                    if (cercano.equals(inicio)) {
                        inicio = null;
                        mapPanel.setInicio(null);
                    }

                    if (cercano.equals(finalA)) {
                        finalA = null;
                        mapPanel.setFinal(null);
                    }

                    if(cercano.equals(seleccionado)){
                        seleccionado = null;
                        mapPanel.setSeleccionado(null);
                    }

                    mapPanel.repaint();
                }
                break;
            default:
                break;
        }
    }

    

    public void configurarCrearNodo() {
        mainFrame.getBtnCrear().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.setModo("CREAR");
            }
        });
    }

    public void configurarConexionBidireccional() {
        mainFrame.getBtnBid().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.setModo("BIDIRECCIONAL");
            }
        });
    }

    public void configurarConexionUnidireccional() {
        mainFrame.getBtnUnid().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.setModo("UNIDIRECCIONAL");
            }
        });
    }

    public void configurarEliminarNodo() {
        mainFrame.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.setModo("ELIMINAR");
            }
        });
    }

    public void configurarMarcarInicio() {
        mainFrame.getBtnInicio().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.setModo("INICIO");
            }
        });
    }

    public void configurarMarcarFin() {
        mainFrame.getBtnFin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.setModo("FIN");
            }
        });
    }

    public void configurarBFS() {
        mainFrame.getBtnBFS().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarAlgoritmo(bfs, "BFS");
            }
        });
    }

    public void configurarDFS() {
        mainFrame.getBtnDFS().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarAlgoritmo(dfs, "DFS");
            }
        });
    }
    public void configurarGuardar() {
        mainFrame.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardar();
            }
        });
    }
    public void configurarCargar() {
        mainFrame.getBtnCargar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargar();
            }
        });
    }

    private void ejecutarAlgoritmo(PathFinder<MapPoint> algoritmo, String nombreAlgoritmo) {

        if (inicio == null || finalA == null) {
            JOptionPane.showMessageDialog(mapPanel,"Debes marcar un punto de Inicio y un Destino primero");
            return;
        }

        long t0 = System.nanoTime();
        PathResult<MapPoint> resultado = algoritmo.find(grafo, inicio, finalA);
        long t1 = System.nanoTime();

        double milisegundos = (t1 - t0) / 1_000_000.0;

        List<MapPoint> ruta = new ArrayList<>(resultado.getPath());
        List<MapPoint> visitadosOrden = new ArrayList<>(resultado.getVisitados());

        if (ruta.isEmpty()) {
            JOptionPane.showMessageDialog(mapPanel,"No hay camino entre Inicio y Destino");
            mainFrame.mostrarResultado(String.format("Algoritmo: %s%nTiempo: %.3f ms%nVisitados: %d%nOrden visitados: %s%nNo se encontró una ruta.",
                nombreAlgoritmo,
                milisegundos,
                visitadosOrden.size(),
                visitadosOrden));

            mapPanel.limpiarResultados();
            mapPanel.repaint();
            return;
        }

        mainFrame.mostrarResultado(String.format("Algoritmo: %s%nTiempo: %.3f ms%nVisitados: %d%nOrden visitados: %s%nRuta encontrada (Longitud=%d): %s",
            nombreAlgoritmo,
            milisegundos,
            visitadosOrden.size(),
            visitadosOrden,
            ruta.size(),
            ruta));

        mapPanel.setResultado(ruta, visitadosOrden);

        if (mainFrame.getChkRecorrido().isSelected()) {
            mapPanel.recorridoAnimacion(visitadosOrden, ruta);
        } else {
            mapPanel.repaint();
        }
    }

    private void guardar() {
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(mapPanel) == JFileChooser.APPROVE_OPTION) {
            try {
                File archivo = fc.getSelectedFile();
                if (!archivo.getName().endsWith(".txt")) {
                    archivo = new File(archivo.getAbsolutePath() + ".txt");
                }
                repositorio.guardar(grafo, archivo);
                JOptionPane.showMessageDialog(mapPanel, "Mapa guardado correctamente");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mapPanel, "Error al guardar: " + ex.getMessage());
            }
        }
    }

    private void cargar() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(mapPanel) == JFileChooser.APPROVE_OPTION) {
            try {
                Graph<MapPoint> cargado = repositorio.cargar(fc.getSelectedFile());
                grafo = cargado;
                mapPanel.setGraph(grafo);
                contadorPuntos = grafo.getNodes().size();
                inicio = null;
                finalA = null;
                seleccionado = null;
                mapPanel.setSeleccionado(null);
                mapPanel.setInicio(null);
                mapPanel.setFinal(null);
                limpiarResultados();
                mapPanel.repaint();
                JOptionPane.showMessageDialog(mapPanel, "Mapa cargado correctamente");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mapPanel, "Error al cargar: " + ex.getMessage());
            }
        }
    }
    public void configurarLimpiar() {
        mainFrame.getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarTodo();
            }
        });
    }

    private void limpiarResultados() {
        mapPanel.limpiarResultados();
        mainFrame.limpiarResultados();
    }

    private void limpiarTodo() {
        grafo = new Graph<>();
        mapPanel.setGraph(grafo);
        inicio = null;
        finalA = null;
        contadorPuntos = 0;

        mapPanel.setInicio(null);
        mapPanel.setFinal(null);
        mapPanel.setSeleccionado(null);

        limpiarResultados();
        mapPanel.repaint();
    }
    
}