package graphs.implementations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import graphs.Graph;
import graphs.PathFinder;
import graphs.PathResult;
import node.Node;

public class BFSPathFinder<T> implements PathFinder<T> {

    @Override
    public PathResult<T> find(Graph<T> graph, T start, T end) {
        Queue<T> queue = new LinkedList<>();
        Set<T> visitados = new HashSet<>();
        Map<Node<T>,Node<T>> parent = new HashMap<>(); 
        List<T> viseted = new ArrayList<>(); 
        queue.add(start);
        visitados.add(start);
        parent.put(new Node<>(start), null);
        while(!queue.isEmpty()){
            T current = queue.poll();

            viseted.add(current);

            if(current.equals(end)){
                return new PathResult<>(viseted, buildPath(parent, end));
            }

            for(Node<T> vecino : graph.getVecinos(current)){
                if(!visitados.contains(vecino.getValue())){
                    visitados.add(current);
                    parent.put(vecino, new Node<>(current));
                    queue.add(vecino.getValue());
                }
            }
        }
       return new PathResult<>(viseted, new HashSet<>());
    }

    private Set<T> buildPath(Map<Node<T>, Node<T>> parent, T end) {
        Set<T> path = new LinkedHashSet<>();
        Node<T> nEnd = new Node<>(end);
        for(Node<T> at = nEnd ; at != null ; at = parent.get(at)){
            path.add(at.getValue());

        }
        return path;
    }
    
}
