
package prefixspan.utils;

/**
 *
 * @author Raúl Moya Reyes <raulmoya.es>
 * @author Agustín Ruiz Linares <agustruiz.es>
 * 
 * @param <A> First param
 * @param <B> Second param
 */
public class Pair<A, B> {
    public A first;
    public B second;

    /**
     * Class constructor
     * 
     * @param first First item of the pair
     * @param second Second item of the pair
     */
    public Pair(A first, B second) {
    	super();
    	this.first = first;
    	this.second = second;
    }

    /**
     * Method for create the hash code of the items
     * 
     * @return Quick hash of the pair.
     */
    @Override
    public int hashCode() {
    	int hashFirst = this.first != null ? this.first.hashCode() : 0;
    	int hashSecond = this.second != null ? this.second.hashCode() : 0;

    	return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

    /**
     * Method for comparation between this pair with other pair
     * 
     * @param other The pair to compare with.
     * @return True, if this pair is equals to the other pair. Otherwise, false.
     */
    @Override
    public boolean equals(Object other) {
    	if (other instanceof Pair) {
    		Pair otherPair = (Pair) other;
    		return 
    		((  this.first == otherPair.first ||
    			( this.first != null && otherPair.first != null &&
    			  this.first.equals(otherPair.first))) &&
    		 (	this.second == otherPair.second ||
    			( this.second != null && otherPair.second != null &&
    			  this.second.equals(otherPair.second))) );
    	}

    	return false;
    }

    /**
     * Method for transform this pair into string
     * 
     * @return String representation of this pair
     */
    @Override
    public String toString()
    { 
           return "(" + this.first + ", " + this.second + ")"; 
    }

}
