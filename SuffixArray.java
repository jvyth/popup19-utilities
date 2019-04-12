import java.util.*;

/*
 * The purpose of this class is to create a suffix array of a string. 
 *
 * This is done using a "prefix doubling" algorithm.
 *
 * @author Carl Nyströmer
 * @author Jakob Vyth
 */
public class SuffixArray {

    private ArrayList<Suffix> sa;

    private class Suffix implements Comparable<Suffix> {
        int index;
        int r1;
        int r2;

        public Suffix(int index, int r1, int r2) {
            this.index = index;
            this.r1 = r1;
            this.r2 = r2;
        }

        public int compareTo(Suffix other) {
            if(r1 == other.r1){
                return r2 - other.r2;
            } else {
                return r1 - other.r1;
            }
        }

        public String toString(){
            return "(" + index + "," + r1 + "," + r2 + ")";
        }
    }

    /*
     * Create the suffix array. 
     *
     * @param str The string to construct the suffix array from.
     */
    public SuffixArray(String str) {
        int n = str.length();
        sa = new ArrayList<Suffix>(n);
        ArrayList<Suffix> suffix = new ArrayList<Suffix>(n);
        for (int i = 0; i < n-1; ++i) {
                Suffix s = new Suffix(i, str.charAt(i), str.charAt(i+1));
                suffix.add(s);
                sa.add(s);
        }
        Suffix last = new Suffix(n-1, str.charAt(n-1), -1);
        suffix.add(last);
        sa.add(last);

        Collections.sort(sa);

        for (int k = 4; k < 2*n; k*=2) {
            int rank = 0;
            int previousRank = sa.get(0).r1;
            sa.get(0).r1 = rank;

            for (int i = 1; i < n; ++i){
                Suffix s = sa.get(i);
                if (s.r1 == previousRank && s.r2 == sa.get(i-1).r2){
                    s.r1 = rank;
                } else {
                    ++rank;
                    previousRank = s.r1;
                    s.r1 = rank;
               }
            }


            for(int i = 0; i < n; ++i) {
                int r = i + k/2;
                Suffix s = suffix.get(i);
                if (r < n) {
                    s.r2 = suffix.get(r).r1;
                } else {
                    s.r2 = -1;
                }
            }

            Collections.sort(sa);
        }
    }

    public String toString(){
        return sa.toString();
    }

    /*
     * @param i Index in the suffix array
     * @return The i'th suffix in lexographical order.
     */
    public int getSuffix(int i) {
        return sa.get(i).index;
    }
}
