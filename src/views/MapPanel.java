package views;

import javax.swing.JPanel;
import graphs.Graph;
import models.MapPoint;
import models.VisualizationMode;
import node.Node;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.Timer;
import java.util.ArrayList;

public class MapPanel extends JPanel implements MouseListener{

    public interface ClickListener{
        void onClic(int x, int y, MapPoint cercano);
    }

    private Image image;
    private Graph<MapPoint> graph;
    private ClickListener clickListener;
    private MapPoint inicio;
    private MapPoint fin;
    private MapPoint seleccionado;
    private List<MapPoint> visitados = new ArrayList<>();
    private List<MapPoint> ruta = new ArrayList<>();
    private VisualizationMode exploracion = VisualizationMode.EXPLORATION;
    private VisualizationMode visualPath = VisualizationMode.FINAL_PATH;
    private Timer tiempoRecorrido;
    private int pasoAnimacion = 0;
    private int segmentoRuta = 0;
    private double progresoRecorrido = 0.0;
    private String modo = "";

    public MapPanel(){
        setLayout(null);
        addMouseListener(this);
    }

    // Al repintar el mapa, tambien repintamos el contenedor padre para que
    // los paneles semitransparentes que quedan encima (botones/resultados)
    // se redibujen correctamente durante la animacion.
    @Override
    public void repaint() {
        super.repaint();
        if (getParent() != null) {
            getParent().repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(tiempoRecorrido != null && tiempoRecorrido.isRunning())
            return;
        if(clickListener != null){
            clickListener.onClic(e.getX(), e.getY(), buscarVecino(e.getX(), e.getY()));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    int radioD = 15;

    public MapPoint buscarVecino(int x,int y){
        if(graph == null)
            return null;
        for(Node<MapPoint> node: graph.getNodes()){
            MapPoint punto = node.getValue();
            double distancia = Math.hypot(x - punto.getX(), y - punto.getY());
            if(distancia<= radioD)
                return punto;
        }
        return null;
    }

    public void recorridoAnimacion(List<MapPoint>visitaOrdenada, List<MapPoint> rutaFinal ){
        if(visitaOrdenada == null){
            visitaOrdenada = new ArrayList<>();
        }
        if(rutaFinal == null){
            rutaFinal = new ArrayList<>();
        }
        this.visitados = visitaOrdenada;
        this.ruta = rutaFinal;
        pasoAnimacion = 0;
        segmentoRuta = 0;
        progresoRecorrido = 0.0;

        if(tiempoRecorrido != null){
            tiempoRecorrido.stop();
        }

        tiempoRecorrido = new Timer(40, e->{
            boolean recorridoTerminado = pasoAnimacion >= visitados.size();
            if(!recorridoTerminado){
                pasoAnimacion++;
            }else if(ruta.size()>1){
                progresoRecorrido += 0.06;
                if(progresoRecorrido>=1.0){
                    progresoRecorrido = 0.0;
                    segmentoRuta++;
                    if(segmentoRuta >= ruta.size()-1){
                        ((Timer) e.getSource()).stop();
                    }
                }
            }else{
                ((Timer) e.getSource()).stop();
            }
            repaint();
        });
        tiempoRecorrido.start();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D gtx = (Graphics2D) g;
        gtx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gtx.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        if(image != null){
            gtx.drawImage(image, 0, 0,getWidth(),getHeight(),this);
        }

        if(graph == null)
            return;

        //direccion flecha
        gtx.setStroke(new BasicStroke(2));
        gtx.setColor(new Color(30, 60, 200, 230));
        Map<Node<MapPoint>,Set<Node<MapPoint>>> mapaGraph = graph.getGraph();
        for(Map.Entry<Node<MapPoint>,Set<Node<MapPoint>>> entry : mapaGraph.entrySet()){
            MapPoint origen = entry.getKey().getValue();
            for(Node<MapPoint> destinoF : entry.getValue()){
                dFlecha(gtx, origen, destinoF.getValue());    
            }
        } 

        //exploracion
        boolean animacion = tiempoRecorrido != null  && tiempoRecorrido.isRunning();
        if(exploracion == VisualizationMode.EXPLORATION && animacion){
            gtx.setColor(new Color(255, 0, 50));
            for(int i =0; i<Math.min(pasoAnimacion, visitados.size());i++){
                MapPoint pExplorado = visitados.get(i);
                gtx.fillOval(pExplorado.getX()-12, pExplorado.getY()-12, 24, 24);
            }
        }

        //ruta final (con animacion de segmento y punto en movimiento)
        if(ruta != null && ruta.size()>1){
            gtx.setColor(new Color(57, 255, 20));
            gtx.setStroke(new BasicStroke(4));
            if(!animacion){
                for(int i =0; i< ruta.size()-1;i++){
                    MapPoint pInicial = ruta.get(i);
                    MapPoint pFinal = ruta.get(i+1);
                    gtx.drawLine(pInicial.getX(), pInicial.getY(), pFinal.getX(), pFinal.getY());
                }
            }else if(pasoAnimacion>= visitados.size()){
                for(int i =0; i< ruta.size()-1;i++){
                    MapPoint pInicial = ruta.get(i);
                    MapPoint pFinal = ruta.get(i+1);
                    gtx.drawLine(pInicial.getX(), pInicial.getY(), pFinal.getX(), pFinal.getY());
                }
                if(segmentoRuta<ruta.size()-1){
                    MapPoint rutaInicial = ruta.get(segmentoRuta);
                    MapPoint rutaFinal = ruta.get(segmentoRuta+1);
                    int x = (int) (rutaInicial.getX() +(rutaFinal.getX()-rutaInicial.getX()) *progresoRecorrido);
                    int y = (int) (rutaInicial.getY() +(rutaFinal.getY()-rutaInicial.getY()) *progresoRecorrido);
                    gtx.drawLine(rutaInicial.getX(), rutaInicial.getY(), x, y);
                    gtx.setColor(Color.ORANGE);
                    gtx.fillOval(x-8, y-8, 16, 16);
                }
            }
        }

        // nodos
        int radio = 16;
        for(Node<MapPoint> node : graph.getNodes()){
            MapPoint pNodo = node.getValue();
            gtx.setColor(Color.BLUE);

            if(pNodo.equals(inicio))
                gtx.setColor(new Color(46, 204, 113));
            else if(pNodo.equals(fin))
                gtx.setColor(new Color(255, 0, 100));
            else if(pNodo.equals(seleccionado))
                gtx.setColor(new Color(255, 215, 0));

            gtx.fillOval(pNodo.getX()-radio/2, pNodo.getY()-2, radio, radio);
            gtx.setColor(Color.BLACK);
            gtx.setStroke(new BasicStroke(1));
            gtx.drawOval(pNodo.getX()-radio/2, pNodo.getY()-2, radio, radio);
            gtx.drawString(pNodo.getId(), pNodo.getX() + 10, pNodo.getY() - 10);
        }
    }

    private void dFlecha( Graphics2D gtx, MapPoint pI, MapPoint pF){
        int x1 = pI.getX();
        int y1 = pI.getY();
        int x2 = pF.getX();
        int y2 = pF.getY();
        gtx.drawLine(x1, y1, x2, y2);

        double angulo = Math.atan2(y2-y1, x2-x1);

        int fSize = 6;
        int offsetX = (int) (x2-15*Math.cos(angulo));
        int offsetY = (int) (y2-15*Math.sin(angulo));
        AffineTransform aq = gtx.getTransform();
        gtx.translate(offsetX, offsetY);
        gtx.rotate(angulo-Math.PI/2.0);
        gtx.fillPolygon(new int[]{0, -fSize, fSize}, new int[]{0, -fSize, -fSize}, 3);
        gtx.setTransform(aq);
    }

    public void setImagen(Image image){
        this.image = image;
        repaint();
    }

    public void setGraph(Graph<MapPoint> graph) {
        this.graph = graph;
        repaint();
    }

    public void setClickListener(ClickListener e){
        this.clickListener = e;
    }

    public void setModoVisualizacion(VisualizationMode mode){
        exploracion = mode;
        visualPath = mode;
        repaint();
    }

    public void setInicio(MapPoint inicio){
        this.inicio = inicio;
        repaint();
    }

    public void setFinal(MapPoint finalp){
        this.fin = finalp;
        repaint();
    }

    public void setSeleccionado(MapPoint selec){
        this.seleccionado = selec;
        repaint();
    }

    public MapPoint getSeleccionado() {
        return seleccionado;
    }

    public void setResultado(List<MapPoint> ruta,List<MapPoint> visitados){
        this.ruta = ruta;
        this.visitados = visitados;
        repaint();
    }

    public void limpiarResultados(){
        this.visitados = new ArrayList<>();
        this.ruta = new ArrayList<>();
        if(tiempoRecorrido != null){
            tiempoRecorrido.stop();
        }
        pasoAnimacion = 0;
        segmentoRuta = 0;
        progresoRecorrido = 0.0;
        repaint();
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public String getModo() {
        return modo;
    }

    public Graph<MapPoint> getGraph() {
        return graph;
    }

    public VisualizationMode getModoVisualizacion() {
        return exploracion;
    }
}