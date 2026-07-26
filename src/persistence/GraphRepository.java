package persistence;

import java.io.File;
import java.io.IOException;

import graphs.Graph;
import models.MapPoint;

public interface GraphRepository {

    void guardar (Graph<MapPoint> grafo, File archivo) throws IOException;
    Graph<MapPoint> cargar (File archivo) throws IOException;
    
}
