import jdk.dynalink.linker.LinkerServices;
import vertexcover.Graph;

import javax.script.SimpleBindings;
import java.io.*;
import java.util.*;
import java.util.function.BiConsumer;

public class MyGraph implements vertexcover.Graph {


    public HashMap<Integer, HashSet<Integer>> vertices;

    int edgeCount = 0;


    MyGraph(){
        vertices = new HashMap<>();


    }


    MyGraph(String filename){
        vertices = new HashMap<>();

        BufferedReader readBuffer = null;
        try {
            readBuffer = new BufferedReader(new FileReader(new File(filename)));
            String line = null;
            while((line = readBuffer.readLine()) != null) {

                String[] parts = line.split("//s");
                this.addEdge(Integer.parseInt(parts[0]) , Integer.parseInt(parts[1]));

                }
            } catch(FileNotFoundException e) {
                e.printStackTrace();
            } catch(IOException e) {
                e.printStackTrace();
            } finally {
                if(readBuffer != null) {
                    try {
                        readBuffer.close();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }







    /**
     * Adds a new vertex to the graph
     *
     * @param v Id of the new added vertex
     */
    @Override
    public void addVertex(Integer v) {
        vertices.putIfAbsent(v, new HashSet<>());

    }

    /**
     * Adds a new edge to the graph
     *
     * @param v First Id of incident vertex
     * @param w Second ID of incident vertex
     */
    @Override
    public void addEdge(Integer v, Integer w) {

        if(vertices.putIfAbsent(v,new HashSet<> (w)) != null) {
            (vertices.get(v)).add(w);
        }


        if(vertices.put(w, new HashSet<>(v)) != null){
            (vertices.get(w)).add(v);
        }

        edgeCount++;

        return;

    }

    /**
     * Delets a vertex from the graph
     *
     * @param v Id of the vertex for deleting
     */
    @Override
    public void deleteVertex(Integer v) {

       edgeCount = edgeCount - vertices.get(v).size();
       for(Integer i : vertices.get(v)){ //optimierung da edges bekannt
           this.vertices.get(i).remove(v);

       }
       vertices.remove(v);
       return;
    }





    /**
     * Delets an edge from the graph
     *
     * @param u
     * @param v First Id of incident vertex
     */
    @Override
    public void deleteEdge(Integer u, Integer v) {
        vertices.get(u).remove(v);
        vertices.get(v).remove(u);
        edgeCount--;
        return;

    }

    /**
     * Return whether the given vertex belongs to the graph
     *
     * @param v Vertex ID which will be checked
     * @return True if the ID belongs to the graph, False if not
     */
    @Override
    public boolean contains(Integer v) {

        return vertices.containsKey(v);
    }

    /**
     * Return the degree of a vertex in the graph
     *
     * @param v Vertex ID
     * @return Degree of vertex v if v in the graph, null else
     */
    @Override
    public int degree(Integer v) {
        return (vertices.containsKey(v)) ? vertices.get(v).size() : null;
    }

    /**
     * Returns whether vertices v and w are adjacent
     *
     * @param v ID of first vertex
     * @param w ID of second vertex
     * @return True if v and w are adjacent, False else
     */
    @Override
    public boolean adjacent(Integer v, Integer w) {
        return vertices.get(v).contains(w);

    }

    /**
     * Creats a copy of the graph
     *
     * @return Copy of the graph
     */
    @Override
    public MyGraph getCopy() {
        MyGraph copyGraph = new MyGraph(); // Neuen Graphen erstellen


        for (Integer key : vertices.keySet()) { // Fuer jeden Knoten in der Hash Map
            //copyGraph.addVertex(key); // <- Wir in add Edge durgefuehrt
            for (Integer value : vertices.get(key) ){ // Fuer jeden Partnerknoten im jeweiligen HashSet des Knotens
                copyGraph.addEdge(key, value); //Neue Edge erstellen aus dem Knoten und dem Partnerknoten
            }


        }

        return copyGraph;

    }

    /**
     * Returns the neighbors of a vertex
     *
     * @param v ID of the vertex
     * @return Set of the neighbors of vertex v
     */
    @Override
    public Set<Integer> getNeighbors(Integer v) {
        return vertices.get(v);
    }

    /**
     * Returns number of vertices in the graph
     *
     * @return Number of vertices in the graph
     */
    @Override
    public int size() {
        return vertices.size();
    }

    /**
     * Calculates number of edges in the graph
     *
     * @return number of edges of the graph
     */
    @Override
    public int getEdgeCount() {
        return this.edgeCount;
    }

    /**
     * Returns the vertex set
     *
     * @return Set of the vertices of the graph
     */
    @Override
    public Set<Integer> getVertices() {
        return vertices.keySet();
    }
}
