package graphs.implementations;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import graphs.Graph;
import graphs.PathFinder;
import graphs.PathResult;
import node.Node;

public class DFSPathFinder<T> implements PathFinder<T> {

    @Override
    public PathResult<T> find(Graph<T> graph, T start, T end) {
        List<T> visited = new ArrayList<>();
        List<T> path = new ArrayList<>();
        boolean encontrado = dfs(graph, start ,end , visited , path);
        if(!encontrado){
            path.clear();
        }
        Set<T> pathSet = new LinkedHashSet<>();
        return new PathResult<>(visited, pathSet);

    }

    private boolean dfs(Graph<T> graph, T currente, T end, List<T> visited, List<T> path) {
        visited.add(currente);
        path.add(currente);
        Node<T> nC = new Node<>(currente);
        Node<T> nE = new Node<>(end);
        if(nC.equals(nE)){
            return true;
        }
        for(Node<T> vecino : graph.getVecinos(currente)){
            if(!visited.contains(vecino.getValue())){
                boolean encontrado = dfs(graph, vecino.getValue(), end, visited, path);
                if(encontrado){
                    return true;
                }
            }
        }
        path.remove(path.size() -1);
        return false;
    }
    
}