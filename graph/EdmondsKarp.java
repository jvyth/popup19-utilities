import java.util.*;

/*
 * The purpose of this class is to find the maximum amount of flow
 * that can be pushed through a directed graph.
 *
 * This is done by using Edmonds-Karp algorithm.
 *
 * @author Jakob Vyth (vyth@kth.se)
 * @author Carl Nyströmer (carlnys@kth.se)
 */
public class EdmondsKarp{
    ArrayList<LinkedList<Edge>> graph;
    int[][] flowing;
    int flow;
    int n;

    /*
     * The constructor will apply Edmonds-Karp algorithm
     * and save how the "information" flows through the edges.
     *
     * @param graph A datastructure which keeps the list of neighbors for each
     *             node in the graph, edge capacity has to be non-negative.
     *             Back-edges (which will store back-flow capacity) have to be
     *             included in the grapg as well with an initial capacity of 0.
     * @param s The index of the tap. (Start) 
     * @param t The index of the sink. (Target) Hmmm....
     */    
    public EdmondsKarp(ArrayList<LinkedList<Edge>> graph, int s, int t){
        this.graph = graph;
        flow = 0;
        n = graph.size();
        flowing = new int[n][n];
        int[] parent = new int[n];
        int pathFlow;
        int curr, prev;
        while((pathFlow = bfs(s,t, parent)) > 0){
            flow += pathFlow;
            curr = t;
            while(curr != s){
                prev = parent[curr]; 
                flowing[prev][curr] += pathFlow;
                flowing[curr][prev] -= pathFlow;
                curr = prev;
            } 
            parent = new int[n];
        }            
    }

    /*
     * @return A matrix containing the flow through each edge.
     */
    public int[][] getFlowGraph(){
        return flowing.clone();
    }

    /*
     * @return The maximum flow to the sink. 
     */
    public int getMaxFlow(){
        return flow;
    }

    /*
     * @return The edges which have flow through them when the algorithm is done. 
     */
    public LinkedList<Edge> getFlowingEdges(){
        LinkedList<Edge> edges = new LinkedList<>();
        for(int i = 0; i < n; ++i){
            for(Edge e : graph.get(i)){
                if(e.cap != 0 && flowing[i][e.to] > 0){
                    edges.add(e);
                } 
            }
        }
        return edges;
    }

    private int bfs(int s, int t, int[] parent){
        boolean[] visited = new boolean[n];
        int[] minCapInPath = new int[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        minCapInPath[s] = Integer.MAX_VALUE;
        int current;
        int resFlow;
        while(!queue.isEmpty()){
            current = queue.poll(); 
            for(Edge e : graph.get(current)){
                resFlow = e.cap - flowing[current][e.to];
                if(!visited[e.to] && (resFlow > 0)){
                    parent[e.to] = current; 
                    minCapInPath[e.to] = Math.min(minCapInPath[current], resFlow); 
                    if(e.to != t){
                        queue.add(e.to); 
                        visited[e.to] = true;
                    } else {
                        return minCapInPath[t];
                    }
                }
            } 
        }
        return 0; 
    }
}
