import java.util.ArrayList;
import java.util.Collections;


/*
 *  The purpose of this class is to solve the interval cover problem by utilizing a greedy algorithm.
 *  
 *  @author Jakob Vyth (vyth@kth.se)
 *  @author Carl Nyströmer (carlnys@kth.se)
 */
class Intervalcover {

    /*
     * Solves the interval cover problem.
     * @param L The low end of the interval to cover
     * @param R The high end of the interval to cover
     * @param unsortedIntervals The intervals available for covering the interval
     * @return An array specifying the id's of the intervals chosen to cover the interval.
     *     The id's will be ordered in the array, in the same order as they're chosen.  
     *     If there's no solution, null will be returned.
     */
    public static ArrayList<Integer> cover(double L, double R, 
                                           ArrayList<Interval> unsortedIntervals) {

        ArrayList<Interval> intervals = new ArrayList<>(unsortedIntervals);
        Collections.sort(intervals);
        int n = intervals.size();
        ArrayList<Integer> S = new ArrayList<>(n);
        double maxB = Double.NEGATIVE_INFINITY;
        int maxIndex = 0;
        boolean foundMax;
        do {
            foundMax = false;
            for (int i = 0; i < intervals.size() && intervals.get(i).a <= L; i++) {
                Interval I = intervals.get(i);
                if (I.a <= L) {
                    if (I.b > maxB) {
                        maxB = I.b;
                        maxIndex = I.id;
                        foundMax = true;
                    }
                }
            }
            if (!foundMax) {
                return null;
            }
            S.add(maxIndex);
            L = maxB;
        } while (L < R);
        return S;
    }
}
