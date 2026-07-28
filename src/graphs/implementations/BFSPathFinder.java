package graphs.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
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
        Set<T> visitados = new LinkedHashSet<>();
        Map<Node<T>,Node<T>> parent = new LinkedHashMap<>(); 
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
                    visitados.add(vecino.getValue());
                    parent.put(vecino, new Node<>(current));
                    queue.add(vecino.getValue());
                }
            }
        }
       return new PathResult<>(viseted, new LinkedHashSet<>());
    }

    private Set<T> buildPath(Map<Node<T>, Node<T>> parent, T end) {
        List<T> pathList = new ArrayList<>();
        Node<T> nEnd = new Node<>(end);
        for(Node<T> at = nEnd ; at != null ; at = parent.get(at)){
            pathList.add(at.getValue());

        }
        Collections.reverse(pathList);;
        return new LinkedHashSet<>(pathList);
    }
    
}
