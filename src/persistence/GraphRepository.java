package persistence;

import java.io.File;
import java.io.IOException;

import models.MapPoint;
import structures.graphs.Graph;

public interface GraphRepository {
    void guardar(Graph<MapPoint> grafo, File archivo) throws IOException;
    Graph<MapPoint> cargar(File archivo) throws IOException;
}