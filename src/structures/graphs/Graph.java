package structures.graphs;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import structures.Node;

public class Graph<T>{
    Map<Node<T>, Set<Node<T>>> graph;
    public Graph(){
        this.graph = new LinkedHashMap<>();
    }
    public void add(T value){
        Node<T> node = new Node<>(value);
        graph.putIfAbsent(node, new LinkedHashSet<>());
    }

    public void addEdge(T v1, T v2){
        Node<T> nV1 = new Node<>(v1);
        Node<T> nV2 = new Node<>(v2);
        add(v1);
        add(v2);
        graph.get(nV1).add(nV2);
        graph.get(nV2).add(nV1);

    }
    public void addEdgeUni(T v1 , T v2){
        Node<T> nV1 = new Node<>(v1);
        Node<T> nV2 = new Node<>(v2);
        add(v1);
        add(v2);
        graph.get(nV1).add(nV2);

    }

   


    public void removeEdge(T v1, T v2 ){
        Node<T> nV1 = new Node<>(v1);
        Node<T> nV2 = new Node<>(v2);
        if(graph.containsKey(nV1)){
            graph.get(nV1).remove(nV2);
        }
        if(graph.containsKey(nV2)){
            graph.get(nV2).remove(nV1);
        }   
    }
    public void removeEdgeUni(T v1 , T v2){
        Node<T> nV1 = new Node<>(v1);
        Node<T> nV2 = new Node<>(v2);
        graph.get(nV1).remove(nV2);

    }

    public void removeNode(T value){
        Node<T> nodeRemove = new Node<>(value);
        graph.remove(nodeRemove);
        for(Set<Node<T>> conexiones : graph.values()){
            conexiones.remove(nodeRemove);
        }

    }
    public Set<Node<T>> getVecinos(T currente) {
        Node<T> vecinos = new Node<>(currente);
        return graph.getOrDefault(vecinos, new LinkedHashSet<>());
        
    }
    public Set<Node<T>> getNodes(){
        return graph.keySet();
    }

    public Map<Node<T>,Set<Node<T>>> getGraph(){
        return graph;
    }

    public boolean contains(T data){
        return graph.containsKey(new Node<T>(data));
    }



    
    
}
