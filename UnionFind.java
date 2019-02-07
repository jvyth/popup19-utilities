/*
 * The puropse of this class is to efficiently unionize sets and to find out
 * whether or not two elements lie in the same set. Each set is identified 
 * by an unique integer in the same range of the number of elements. 
 * The UnionFind data structure makes makes both operations run in O(logn).
 *
 * @author Jakob Vyth (vyth@kth.se) 
 * @author Carl Nyströmer (carlnys@kth.se)
 */
public class UnionFind {
  private int[] parents;
  private int[] size;

  /*
   * Creates a UnionFind data structure.
   * @param n The number of initial disjoint sets. n > 0
   */
  public UnionFind(int n) {
    parents = new int[n];
    size = new int[n];
    for (int i = 0; i < n ; ++i) {
      parents[i] = i;
      size[i] = 1;
    }
  }

  /*
   * Returns whether or not two elements lie in the same set.
   * @param x First element 0<x<n where n is the number of elements
   * @param y Second element 0<y<n
   * @return True if x and y in same set, false otherwise.
   */
  public boolean same(int x, int y) {
    return find(x) == find(y);
  }

  /*
   * Unionize the sets containing x and y. If x and y are already in the same set
   * no union is needed.
   * @param x An element in some set 0<x<n
   * @param y A second element in some set 0<y<n
   * @return The identifier (also an element) of the set containing x and y. 
   */
  public int union(int x, int y) {
    int xRoot = find(x);
    int yRoot = find(y);
    if (xRoot == yRoot) return xRoot;
    if (size[xRoot] > size[yRoot]) {
      parents[yRoot] = xRoot;
      size[xRoot] += size[yRoot];
      return xRoot;
    } else {
      parents[xRoot] = yRoot;
      size[yRoot] += size[xRoot];
      return yRoot;
    }
  }

  private int find(int x) {
    if (parents[x] != x) parents[x] = find(parents[x]);
    return parents[x];
  }
}
