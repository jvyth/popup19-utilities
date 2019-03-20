import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.ListIterator;

/*
 * The purpose of this class is to find the shortest path
 * from a fixed starting node to any other node in the graph.
 *
 * This is done by using Dijkstras algorithm.
 *
 * @author Jakob Vyth (vyth@kth.se)
 * @author Carl Nyströmer (carlnys@kth.se)
 */
public class Dijkstra{
    PriorityQueue<Node> tentative;
    int[] parents;
    Node[] nodes;
    int s;

    /*
     * The constructor will apply the Dijkstra algorithm to the graph
     * and save the shortest path between s and every other node.
     *
     * @param nb A datastructure which keeps the list of neighbors for each
     *             node in the graph, edge weight have to be non-negative.
     * @param s The index of the starting node.
     */
     public Dijkstra(ArrayList<LinkedList<Edge>> nb, int s){
         this.s = s;
         tentative = new PriorityQueue<>();
         parents = new int[nb.size()];
         nodes = new Node[nb.size()];
         for(int i = 0; i < nb.size(); i++){
             nodes[i] = new Node(i);
         }
         nodes[s].accWeight = 0;
         Node current = nodes[s];
         Node neigh;
         Node parent;
         Edge edge;
         LinkedList<Edge> nbs;
         ListIterator<Edge> it;
         tentative.add(nodes[s]);
         boolean[] visited = new boolean[nb.size()];
         while(!tentative.isEmpty()){
             parent = current;
             current = tentative.poll();
             parents[current.index] = parent.index;
             if(visited[current.index])
                 continue;
             visited[current.index] = true;
             it = nb.get(current.index).listIterator();
             while(it.hasNext()){
                 edge = it.next();
                 neigh =  nodes[edge.to];
                 if(!visited[neigh.index]){
                     if(neigh.accWeight > current.accWeight + edge.weight){
                         neigh.accWeight = current.accWeight + edge.weight;
                         tentative.add(new Node(neigh));
                     }
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
        return parents;
    }

    /*
     * @param t The target node.
     * @return The shortest path between s and t.
     *         If no such path exists, return null.
     */
    public LinkedList<Integer> shortestPath(int t){
        LinkedList<Integer> path = new LinkedList<>();
        if(nodes[t].accWeight == Long.MAX_VALUE){
            return null;
        }
        while(t != s){
            path.push(t);
            t = parents[t];
        }
        path.push(s);
        return path;
    }

    /*
     * @param index The target node.
     * @return The total weight of the path between s and t.
     */
    public int getAccWeight(int index){
        return nodes[index].accWeight;
    }

    private class Node implements Comparable<Node>{
        public int index;
        public int accWeight;

        public Node(int index){
            this.index = index;
            this.accWeight = Integer.MAX_VALUE;
        }

         public Node(Node node){
            this.index = node.index;
            this.accWeight = node.accWeight;
        }

        public int compareTo(Node node){
            if(this.accWeight < node.accWeight) {
                return -1;
            } else if (this.accWeight > node.accWeight) {
                return 1;
            } else  {
                return 0;
            }
        }
    }
}
