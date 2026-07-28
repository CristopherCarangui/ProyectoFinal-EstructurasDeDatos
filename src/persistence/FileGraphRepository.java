package persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import graphs.Graph; 
import models.MapPoint;
import node.Node; 

public class FileGraphRepository implements GraphRepository {

    @Override
    public void guardar(Graph<MapPoint> grafo, File archivo) throws IOException {

        if (grafo == null || archivo == null) {
            throw new IllegalArgumentException("El grafo y el archivo no pueden ser nulos.");
        }

        try (BufferedWriter datos = new BufferedWriter(new FileWriter(archivo))) {
            datos.write("# Formato de nodos: NODE;id;x;y | Formato de uniones: EDGE;from;to;bidirectional ");
            datos.newLine();

            for (Node<MapPoint> n : grafo.getNodes()) {
                MapPoint p = n.getValue();
                datos.write("NODE;" + p.getId() + ";" + p.getX() + ";" + p.getY());
                datos.newLine();
            }

            Map<Node<MapPoint>, Set<Node<MapPoint>>> mapa = grafo.getGraph();
            Set<String> visitados = new HashSet<>();
            for (Map.Entry<Node<MapPoint>, Set<Node<MapPoint>>> entry : mapa.entrySet()) {
                MapPoint origen = entry.getKey().getValue();
                for (Node<MapPoint> destinoN : entry.getValue()) {
                    MapPoint destino = destinoN.getValue();

                    String par = origen.getId().compareTo(destino.getId()) <= 0 ? origen.getId() + "::" + destino.getId() : destino.getId() + "::" + origen.getId();

                    if (visitados.contains(par)) {
                        continue;
                    }
                    visitados.add(par);

                    Set<Node<MapPoint>> vueltaSet = mapa.get(new Node<>(destino));
                    boolean bidireccional = vueltaSet != null && vueltaSet.contains(new Node<>(origen));

                    datos.write("EDGE" + ";" + origen.getId() + ";" + destino.getId() + ";" + bidireccional);
                    datos.newLine();
                }
            }
        }
    }

    @Override
    public Graph<MapPoint> cargar(File archivo) throws IOException {
        
        Graph<MapPoint> grafo = new Graph<>();
        Map<String, MapPoint> puntosPorId = new HashMap<>();

        try(BufferedReader leer = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = leer.readLine()) != null ) {
                linea = linea.trim();
                if (linea.isEmpty() || linea.startsWith("#")) {
                    continue;
                }

                String[] partes = linea.split(";");
                if (partes.length < 4) {
                    continue;
                }
                if ("NODE".equals(partes[0])) {
                    String id = partes[1];
                    int x = Integer.parseInt(partes[2]);
                    int y = Integer.parseInt(partes[3]);
                    MapPoint p = new MapPoint(id, x, y);
                    puntosPorId.put(id, p);
                    grafo.add(p);
                } else if ("EDGE".equals(partes[0])) {
                    MapPoint desde = puntosPorId.get(partes[1]);
                    MapPoint hasta = puntosPorId.get(partes[2]);
                    boolean bidireccional = Boolean.parseBoolean(partes[3]);
                    if (desde == null || hasta == null) {
                        continue;
                    }    

                    if (bidireccional) {
                        grafo.addEdge(desde, hasta);
                    }else{
                        grafo.addEdgeUni(desde, hasta);
                    }
                }
            }
        }
        return grafo;
    }

}
