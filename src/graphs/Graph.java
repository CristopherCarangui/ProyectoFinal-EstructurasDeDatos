package graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import node.Node;

public class Graph<T> {
    Map<Node<T>, Set<Node<T>>> nodes;
    public Graph(){
        this.nodes = new HashMap<>();
    }
    public void add(T value){
        Node<T> node = new Node<>(value);
        nodes.putIfAbsent(node, new HashSet<>());
    }

    public void addEdge(T v1, T v2){
        Node<T> nV1 = new Node<>(v1);
        Node<T> nV2 = new Node<>(v2);
        add(v1);
        add(v2);
        nodes.get(nV1).add(nV2);
        nodes.get(nV2).add(nV1);

    }
    public void addEdgeUni(T v1 , T v2){
        Node<T> nV1 = new Node<>(v1);
        Node<T> nV2 = new Node<>(v2);
        add(v1);
        add(v2);
        nodes.get(nV1).add(nV2);

    }

   public void print(){
        int totalN = nodes.size();
        Set<String> cnexC = new HashSet<>();
        for(Map.Entry<Node<T>, Set<Node<T>>> entry: nodes.entrySet()){
            String u = entry.getKey().getValue().toString();
            System.out.println(entry.getKey() + " -> ");
            for(Node<T> connect : entry.getValue()){
                String v = connect.getValue().toString();
                System.out.println("N[" + v + "]");
                if((u.compareTo(v))<0){
                    cnexC.add(u+ "-"+ v);
                }else{
                    cnexC.add(v +"-"+ u );
                }
            }
            System.out.println();
        }
        System.out.println("Total de conexiones: " + cnexC.size());
        System.out.println("Total de direcciones: " + totalN);
        
    }


    public void removeAddEdge(T v1, T v2 ){
        Node<T> nV1 = new Node<>(v1);
        Node<T> nV2 = new Node<>(v2);
        nodes.get(nV1).remove(nV2);
        nodes.get(nV2).remove(nV1);
        
    }
    public void removeEdgeUni(T v1 , T v2){
        Node<T> nV1 = new Node<>(v1);
        Node<T> nV2 = new Node<>(v2);
        add(v1);
        add(v2);
        nodes.get(nV1).remove(nV2);

    }

    public void removeNode(T value){
        Node<T> nodeRemove = new Node<>(value);
        nodes.remove(nodeRemove);
        for(Set<Node<T>> conexiones : nodes.values()){
            conexiones.remove(nodeRemove);
        }

    }
    public Set<Node<T>> getVecinos(T currente) {
        Node<T> vecinos = new Node<>(currente);
        return nodes.getOrDefault(vecinos, new HashSet<>());
        
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
