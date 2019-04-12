import java.util.*;

/*
 * The purpose of this class is to find all primes up to limit, n. 
 *
 * This is done using Erathostenes Sieve. 
 *
 *
 * @author Carl Nyströmer
 * @author Jakob Vyth
 */
public class Primes{
    BitSet primes;
    int n;

    /*
     * Creates an object which holds an array which indicates 
     * whether or not a number lower than an upper limit is prime.
     *
     * @param n The upper limit.
     */
    public Primes(int n){
        this.n = n;
        int rootN = (int) Math.sqrt(n);
        primes = new BitSet(n+1);
        int currPrime = 2;
        primes.set(0);
        primes.set(1);
        int previousPrime = 1;
        while(currPrime <= rootN){
            for(int mult = currPrime*(previousPrime+1); mult <= n; mult += currPrime){
                primes.set(mult);
            }
            previousPrime = currPrime;
            currPrime = primes.nextClearBit(currPrime+1);
        }
    }

    /*
     * Get whether or not x is a prime. 
     *
     * @param x The number to check for primality.
     * @return True if prime, False if not prime.
     */
    public boolean isPrime(int x){
        return !primes.get(x);
    }

    /*
     * @return The number of primes in the interval between 0 and the upper
     * limit specified in the constructor.
     */
    public int numPrimes(){
        return n-primes.cardinality()+1;
    }
}
