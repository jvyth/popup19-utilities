import java.util.*;

/*
 * The purpose of this class is to find the shortest path
 * from a fixed starting node to any other node in the graph. 
 *
 * This is done by using the Bellman-Ford algorithm.
 *
 * @author Jakob Vyth (vyth@kth.se)
 * @author Carl Nyströmer (carlnys@kth.se)
 */
public class BellmanFord{
    int[] parent;
    long[] dist;
    ArrayList<LinkedList<Edge>> edges;
    int s;


    /*
     * The constructor will apply the Bellman-Ford algorithm to the graph
     * and save the shortest path between s and every other node. 
     *
     * @param nb A datastructure which keeps the list of neighbors for each
     *             node in the graph, edge weight can be negative.
     * @param source The index of the starting node.   
     */    
    public BellmanFord(ArrayList<LinkedList<Edge>> edges, int source){
        this.s = source;
        this.edges = edges;
        int n = edges.size();
        parent = new int[n];
        dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);        
        dist[source] = 0;
        Edge e;
        long potentialBetterDist;
        for(int i = 0; i < n; ++i){
            for(int from = 0; from < n; ++from){
                Iterator<Edge> it = edges.get(from).iterator();
                while(it.hasNext()){
                    e = it.next();      
                    potentialBetterDist = dist[from] + e.weight;
                    if(dist[from] == Long.MAX_VALUE){
                        continue;
                    }
                    if(potentialBetterDist < dist[e.to]){
                        dist[e.to] = potentialBetterDist;
                        parent[e.to] = from;
                    } 
                }
            }
        }
        
        for(int from = 0; from < n; ++from){
            Iterator<Edge> it = edges.get(from).iterator();
            while(it.hasNext()){
                e = it.next();      
                if(dist[from] == Long.MIN_VALUE || dist[from] == Long.MAX_VALUE){
                    continue;
                } else { 
                    potentialBetterDist = dist[from] + e.weight;
                }
                if(potentialBetterDist < dist[e.to]){
                    dist[from] = Long.MIN_VALUE;
                    markInf(from);
                } 
            }
        }
    } 

    private void markInf(int from){
        boolean[] visited = new boolean[edges.size()];
        Stack<Integer> stack = new Stack<>(); 
        stack.push(from);
        int current;
        int neigh;
        while(!stack.empty()){
            current = stack.pop();    
            visited[current] = true;
            LinkedList<Edge> neighs = edges.get(current);
            Iterator<Edge> it = neighs.iterator();
            while(it.hasNext()){
                neigh = it.next().to;
                if(!visited[neigh]){
                    dist[neigh] = Long.MIN_VALUE;
                    stack.push(neigh);
                }
            }
        }
    }

    /*
     * Return parent array. To find the path s and t, backtrack
     * through the parent array from t up until you find s. 
     *
     * @return The shortest paths between s and every other node 
     *         as a parent array. 
     */
    public int[] shortestPaths() {
        return parent;
    }

    /*
     * @param t The target node. 
     * @return The shortest path between s and t. 
     *         If no such path exists, return null.
     */
    public LinkedList<Integer> shortestPath(int t){
        LinkedList<Integer> path = new LinkedList<>();
        if(dist[t] == Long.MAX_VALUE){
            return null;
        }
        while(t != s){
            path.push(t);
            t = parent[t]; 
        }
        path.push(s);
        return path;
    }

    public long getMinDistTo(int target){
        return dist[target];
    }
}
