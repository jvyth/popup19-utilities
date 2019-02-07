/*
  The purpose of this class is to calculate prefix sums of an 1-indexed array containing integers
  in an efficient manner, using a Fenwick Tree data structure. Updating a value in the
  underlying array and calculating a prefix sum takes O(logn) time.
  
  @author Jakob Vyth (vyth@kth.se) 
  @author Carl Nyströmer (carlnys@kth.se)
*/
class FenwickTree {
  private long[] sums;
  private int size;

  /*
   * Creates a Fenwick Tree.
   * @param n The number of elements in the underlying array. Must be > -1
   */
  public FenwickTree(int n) {
    this.size = n+1;
    this.sums = new long[n+1];
  }

  /*
   * Updates a value in the underlying array.
   * @param i Index of the value to update
   * @param d The value to add (for subtraction, add a negative value).
   */
  public void add(int i, long d) {
    for(; i < size; i += i&(-i)) {
      sums[i] += d;
    }
  }

  /*
   * Calculates the prefix sum of the i first elements in the underlying array.
   * @param i Number of elements where 0<i<n 
   * @return The prefix sum specified by i. If i =< 0, returns 0.
   */
  public long sum(int i) {
    long sum = 0;
    for(; i > 0; i -= i&(-i)){
      sum += sums[i];
    }
    return sum;
  }
}
