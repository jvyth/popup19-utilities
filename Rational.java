/*
 *  The purpose of this class is to represent rational numbers and 
 *  provide a way to do the fundamental operations addition, subtraction
 *  multiplication, and division. Each operations returns a new rational number,
 *  this way provides flexibility to perform several operations in a row
 *  over the same rational number.
 *
 *  @author Jakob Vyth (vyth@kth.se)
 *  @author Carl Nyströmer (carlnys@kth.se)
 */
public class Rational {
    private long numer;
    private long denom;

    /*
     * Create a rational number.
     *
     * @param numer The numerator of the rational number. 
     * @param denom The denominator of the rational number. 
     */
    public Rational(long numer, long denom) {
        this.numer = numer; 
        this.denom = denom; 
    }

    /*
     * Perform addition.
     * @param r The rational number to add to this one.
     * @return The result as a rational number. 
     */
    public Rational add(Rational r) {
        long lcm = lcm(this.denom, r.denom);
        long num1, num2;
        num1 = this.numer * (lcm/this.denom);  
        num2 = r.numer * (lcm/r.denom);

        return new Rational(num1 + num2, lcm);
    }

    /*
     * Perform subtraction.
     * @param r The rational number to subtract from this one.
     * @return The result as a rational number. 
     */
    public Rational sub(Rational r) {
        //find least common denominator = lcm
        long lcm = lcm(this.denom, r.denom);
        long num1, num2;
        num1 = this.numer * (lcm/this.denom);  
        num2 = r.numer * (lcm/r.denom);

        return new Rational(num1 - num2, lcm);
    }

    /*
     * Perform multiplication.
     * @param r The rational number to multiply with this one.
     * @return The result as a rational number. 
     */
    public Rational mul(Rational r) {
        // 3/5 * 2/3 = 6/15 = 2/5 
        return new Rational(this.numer*r.numer, this.denom*r.denom);
    }

    /*
     * Perform division.
     * @param r The rational number to divide with this one.
     * @return The result as a rational number. 
     */
    public Rational div(Rational r) {
        //find greatest common divider between n3 and d3
        return new Rational(this.numer*r.denom, this.denom*r.numer);
    }

    /*
     * @return A string representation of the rational number. 
     *     E.g "1 / 5" 
     */
    public String toString() {
        if (denom < 0) {
            return new StringBuilder().append(-numer).append(" / ").append(-denom).toString();
        }
        return new StringBuilder().append(numer).append(" / ").append(denom).toString();
    }

    /*
     * 
     * @return The reduced form of the rational number. 
     *     E.g 2/10 => 1/5 
     */
    public Rational reduced(){
        long gcd = gcd(this.numer, this.denom);
        return new Rational(this.numer/gcd, this.denom/gcd);
    }

    private long lcm(long a, long b){
        return Math.abs(a*b)/gcd(a,b);
    }

    private long gcd(long a, long b) {
        if (a == 0) {
            return b;
        } else if (b == 0){
            return a;
        }
        long big = Math.abs(a);
        long small = Math.abs(b);  
        if (big < small) {
            long tmp = small;
            small = big;
            big = tmp;

        }
        long rest = big%small; 
        while (rest != 0) {
            big = small;
            small = rest;
            rest = big%small;
        }
        return small;
    }
}
