package structures.graphs;

import java.util.List;
import java.util.Set;

public class PathResult<T> {
    private final List<T> visitados;
    private final Set<T> path;
    public PathResult(List<T> visitados, Set<T> path) {
        this.visitados = visitados;
        this.path = path;
    }
    public List<T> getVisitados() {
        return visitados;
    }
    public Set<T> getPath() {
        return path;
    }
    @Override
    public String toString() {
        if(path.isEmpty()){
            return "visitados= " + visitados + "\n No se encontro un camino entre los nodos" ;
        }
        return "visitados= " + visitados + "\n path= " + path ;
    }
    
    
}
