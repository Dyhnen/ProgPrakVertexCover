import java.util.Iterator;

public class SearchTree {


    /**
     * Ueberpruerft, ob eine Instanz aus einem Graphen und einem Vertex cover der groesse k eine Ja-Instanz ist
     * @param i Instanz des VertexCover-Problems
     * @return true fuer eine Ja-Instanz und ansonsten false
     */

    private boolean solve(Instance i){
        if(i.k < 0){
            return false;
        }
        if(i.graph.getEdgeCount() == 0){
            return true;
        }


        Instance newInstance = new Instance(i.graph.getCopy(), i.k - 1); //Kopieinstanz

        Iterator<Integer> iterator = newInstance.graph.getVertices().iterator(); //Iterator für einen Key, der entfernt wird

        newInstance.graph.deleteVertex(iterator.next()); //Entfernen eines Elements (Rekursionsbedingung)

        return solve(newInstance); //Rekursiver Aufruf für u und v versuchen





    }

    /**
     * solve, loest ein Vertexcover und gibt die benoetigte Zeit und das ergebnis aus
     * @param g Graph
     * @return k fuer minimales VertexCover
     */
    public int solve(MyGraph g){

    long startTime = System.currentTimeMillis(); //Zeitmarke setzen
    int count = 0;


    while(!solve(new Instance(g, count))){ //Solange wiederholen, bis solve ein Ergebnis hat
        count += 1;
    }



    System.out.println(System.currentTimeMillis() - startTime); //Zeitmarke vergleichen
    System.out.println("k :" + count);
    return count;

    }




}
