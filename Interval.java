/*
 *  The purpose of this class is to represent a real number interval.
 *  
 *  @author Jakob Vyth (vyth@kth.se)
 *  @author Carl Nyströmer (carlnys@kth.se)
 */
class Interval implements Comparable<Interval> {
  //A way to identify an interval even after a collection of intervals has been sorted.
  public final int id;
  //Lower bound of interval
  public final double a;
  //Higher bound of interval
  public final double b;

  /*
   * Creates an interval. 
   * @param a Lower bound of interval. a must be less than or equal to b,
   *     to avoid undefined behaviour.
   * @param b Higher bound of interval
   * @param id An id for the interval 
   */
  public Interval(double a, double b, int id) {
    this.a = a;
    this.b = b;
    this.id = id;
  }
  /*
   * Compares the lower bound of the intervals to be compared.
   * A lower bound compared to another interval means that it's
   * less than the other interval. 
   *
   * @param x The interval to compare against
   * @return 0 if equal lower bound, -1 if x has a higher lower bound,
   *     1 if x has lower lower bound.
   */
  @Override
  public int compareTo(Interval x) {
    if (this.a == x.a) return 0;
    if (this.a < x.a) return -1;
    return 1;
  }

  /*
   * @return A string representation of an interval.
   *     E.g "[1,4]" 
   */
  public String toString() {
    return "[" + a + "," + b + "]";
  }
}
