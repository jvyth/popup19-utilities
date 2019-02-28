import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.ListIterator;

/*
 * The purpose of this class is to find the shortest path
 * from a fixed starting node to any other node in the graph. 
 *
 * This algorithm is a variant of the Dijkstras algorithm where
 * one also have to consider that edges can be "occupied" at certain
 * time (weight) intervals. 
 *
 * @author Jakob Vyth (vyth@kth.se)
 * @author Carl Nyströmer (carlnys@kth.se)
 */
public class DijkTimed {
    PriorityQueue<Node> tentative;
    int[] parents;
    Node[] nodes;
    int s;

    /*
     * The constructor will apply the modified Dijkstra algorithm to the graph
     * and save the shortest path between s and every other node. 
     *
     * @param nb A datastructure which keeps the list of neighbors for each
     *             node in the graph. 
     * @param s The index of the starting node.   
     */    
    public DijkTimed(ArrayList<LinkedList<Edge>> nb, int s) {
        this.s = s;
        tentative = new PriorityQueue<>();
        parents = new int[nb.size()];
        nodes = new Node[nb.size()];
        for (int i = 0; i < nb.size(); i++) {
            nodes[i] = new Node(i);
        }
        nodes[s].accWeight = 0;
        Node current = nodes[s];
        Node neigh;
        Node parent;
        Edge edge;
        LinkedList <Edge> nbs;
        ListIterator <Edge> it;
        tentative.add(nodes[s]);

        long waitTime = Long.MAX_VALUE;
        long currTime;
        long arrivalTime; 
        int traversalTime;
        int firstTimeSlot;

        while (!tentative.isEmpty()) {
            parent = current;
            current = tentative.poll();
            parents[current.index] = parent.index;
            current.visited = true;
            currTime = current.accWeight;
            it = nb.get(current.index).listIterator();

            while (it.hasNext()) {
                edge = it.next();
                traversalTime = edge.weight;
                firstTimeSlot = edge.t0;
                neigh = nodes[edge.to];
                arrivalTime = neigh.accWeight;
                /*       -------------------Cases----------------------
                 * 1) currTime <= firstTimeSlot -> We wait for firstTimeSlot
                 * 2) currTime > firstTimeSlot -> We wait for next available slot
                 * 3) currTime > firstTimeSlot && no increment -> Can't reach! 
                 */
                if (!neigh.visited) { 
                    if(currTime <= firstTimeSlot){ 
                        waitTime = firstTimeSlot - currTime;
                    } else if ((edge.tInc != 0) && (currTime > firstTimeSlot)) { 
                        if(((currTime - firstTimeSlot) % edge.tInc) == 0){
                            waitTime = 0;
                        } else {
                            waitTime = edge.tInc - ((currTime - firstTimeSlot) % edge.tInc); 
                        }
                    } else { 
                        continue;
                    }
                    if (arrivalTime > currTime + traversalTime + waitTime) {
                        if (tentative.contains(neigh)) {
                            tentative.remove(neigh);
                        }
                        arrivalTime = currTime + traversalTime + waitTime;
                        neigh.accWeight = arrivalTime;
                        tentative.add(neigh);
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
    public long getAccWeight(int index) {
        return nodes[index].accWeight;
    }

    private class Node implements Comparable <Node> {
        public int index;
        public long accWeight;
        public boolean visited;

        public Node(int index) {
            this.index = index;
            this.accWeight = Long.MAX_VALUE;
            visited = false;
        }

        public int compareTo(Node node) {
            return (int) accWeight - (int) node.accWeight;
        }
    }
}
