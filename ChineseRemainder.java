import java.util.*;
import java.util.Random;
import java.math.*;

/*
 * The purpose of this class is to solve the general chinese remainder theorem
 * i.e find x for 
 *      x = a (mod n)
 *      x = b (mod m) 
 *
 * @author Carl Nyströmer
 * @author Jakob Vyth
 */
public class ChineseRemainder{

    /*
     * Does the extended euclidean algorithm for a and b.
     *
     * @param a Some long value
     * @param b Some long value
     * @return The algorithm will solve the equation ax + by = gcd(a,b) 
     *         and return [x, y, gcd(a,b)]
     */
    public static long[] extEuc(long a, long b){
        if(b == 0) {
            long[] arr = {1, 0, a};
            return arr;
        }
        long c = a/b; 
        long r = a%b;
        long[] arr = extEuc(b,r); 
        long x1 = arr[1];
        arr[1] = arr[0] - c*arr[1];
        arr[0] = x1;
        return arr; 
    }

    /*
     * Returns the solution to the equation
     *      x = a (mod n)
     *      x = b (mod m) 
     *
     * @return Returns x in the above algorithm.
     */
    public static long[] solve(long a, long n, long b, long m){
        long[] arr = extEuc(n,m); 
        BigInteger g = BigInteger.valueOf(arr[2]);
        BigInteger v = BigInteger.valueOf(arr[0]);
        BigInteger u = BigInteger.valueOf(arr[1]);
        BigInteger nb = BigInteger.valueOf(n);
        BigInteger mb = BigInteger.valueOf(m);
        BigInteger ab = BigInteger.valueOf(a);
        BigInteger bb = BigInteger.valueOf(b);
        if(ab.mod(g).equals(bb.mod(g))){
            BigInteger x = ab.multiply(u).multiply(mb).divide(g).add(bb.multiply(v).multiply(nb).divide(g));
            BigInteger k = mb.multiply(nb).divide(g); 
            x = x.mod(k);
            long[] res = {x.longValue(), k.longValue()};
            return res;
        } else {
            long[] res = {-1, -1};
            return res;
        }
    }

    public static long[] solve(long[] a, long[] m){
        long x = a[0];
        long k = m[0];
        long[] xk = {x,k};
        for(int i = 1; i < a.length; ++i){
            xk = solve(a[i], m[i], x, k);
            x = xk[0];
            k = xk[1];
            if(x == -1){
                return new long[]{-1,-1};
            }
        }
        return xk;
    }
}        
