/*
 * The purpose of this class is to represent a directed edge between two nodes in a graph. 
 * The edges are supposed to be stored in a data structure keeping track of the "from"
 * node, since the edge only stores the to. 
 *
 * @author Jakob Vyth (vyth@kth.se)
 * @author Carl Nyströmer (carlnys@kth.se)
 */
public class Edge{
    //End node of this edge.
    public int to;
    //Stores the weight/duration/length of the edge.
    public int weight;
    //t0 and tInc are used for the timed implementation of Dijkstra.
    public int t0;
    public int tInc;
    //Capacity in a flow graph.
    public int cap;

    /*
     * @param to The end node
     * @param weight The weight or capacity of the edge
     */
    public Edge(int to, int weight){
        this.to = to;
        this.weight = weight;
        this.cap = weight;
    }

    /*
     * @param to The end node
     * @param weight The weight of the edge
     * @param t0 the first time where the edge is available
     * @param tInc Sets which times the edge will be available 
     *             after the t0. (t0 + p*tInc) 
     */
    public Edge(int to, int weight, int t0, int tInc){
        this.to = to;
        this.weight = weight;
        this.t0 = t0;
        this.tInc = tInc;
    }
}
