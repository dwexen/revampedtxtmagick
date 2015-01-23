/*
 *
 * HanoiEnumeration.java
 * 
 * (c) 2014 Antonio R. Nicolosi
 */

package hanoi;

import java.util.Enumeration;

/**

 * The HanoiEnumeration class represents an enumeration of the moves
  for the Hanoi Towers problem of height n.  Poles are labelled with
  'A', 'B', and 'C'.  Which poles is used as source/destination/buffer
  is configured via the attributes src/dst/buf, respectively.

  * (Basis) If n=1, the enumeration just consists of a single move,
            namely "src->dst"

  * (Recurse) If n>1, the enumeration can be broken up into three parts
              ** (recursive) enumeration of the moves to bring the
                 top n-1 discs from src to buf, using dst as buffer
              ** single move to bring the size-n disc from src to dst
              ** (recursive) enumeration of the moves to bring the
                 n-1 discs that are now on buf to dst, using src as buffer

  Accordingly, the class keeps track of the current phase, and of an
  enumerator for n-1 (oneSmallerEnum).  Upon initial creation of the
  enumerator for n (i.e., in the constructor), oneSmallerEnum is
  created as per the first recursive enumeration (from src to buf).

  Phase transition happens after getting the next element with
  nextElement(), and works differently for the basis vs recursive
  cases:

  * For the basis case (n==1), the transition is BASIS_CASE ==> DONE 
  * For the recursive case (n > 1), the transition is 
      FIRST_RECURSION ==> IN_BETWEEN ==> SECOND_RECURSION ==> DONE

*/
public class HanoiEnumeration implements Enumeration {

    /* constants representing the possible states */
    public static final byte BASIS_CASE=0;       /* used only for n==1 */
    public static final byte FIRST_RECURSION=1;
    public static final byte SECOND_RECURSION=2;
    public static final byte IN_BETWEEN=3;
    public static final byte DONE=4;

    private byte phase;                /* could have been an enum type */
    private int n;               
    private char src, dst, buf;  

    private HanoiEnumeration oneSmallerEnum;     /* recursive enumeration
						  * for n-1
						  */

    public HanoiEnumeration() { this(3); }

    public HanoiEnumeration(int n) { this(n, 'A', 'C', 'B'); }

    /* visibility is private, because users of this class should not
     * care about the poles labelling */
    private HanoiEnumeration(int n, char src, char dst, char buf) {

	this.n = n;    this.src=src;    this.dst=dst;    this.buf=buf;

	if (1 < n) {
	    /* set up the recursive enumerator (note poles re-ordering) */
	    oneSmallerEnum=new HanoiEnumeration(n-1, src, buf, dst);
	    phase=FIRST_RECURSION;
	} else if (1 == n) {
	    oneSmallerEnum=null;
	    phase=BASIS_CASE;	    
	} else throw new IllegalArgumentException("Only positives, please: " + n);
    }

    public boolean hasMoreElements() {
	boolean ans=false;

	switch (phase) {
	case BASIS_CASE: 
	case FIRST_RECURSION:
	case IN_BETWEEN: 
	case SECOND_RECURSION:
	    ans=true;
	    break;
	case DONE:
	    ans=false;
	    break;
	default:
	    throw new RuntimeException("Unknown phase" + phase);
	}

	return ans;
    }

    public Object nextElement() {
	Object ans=null;

	switch (phase) {
	case BASIS_CASE: 
	    ans=""+src+"->"+dst;
	    phase=DONE;
	    break;
	case FIRST_RECURSION:
	    ans=oneSmallerEnum.nextElement(); 
	    if (!oneSmallerEnum.hasMoreElements()) {
		oneSmallerEnum=null;
		phase=IN_BETWEEN;
	    }
	    break;
	case IN_BETWEEN:
	    ans=""+src+"->"+dst;

	    /* set up the recursive enumerator (note poles ordering) */
	    oneSmallerEnum=new HanoiEnumeration(n-1, buf, dst, src);
	    phase=SECOND_RECURSION;
	    break;
	case SECOND_RECURSION: 
	    ans=oneSmallerEnum.nextElement(); 
	    if (!oneSmallerEnum.hasMoreElements()) {
		oneSmallerEnum=null;
		phase=DONE;
	    }
	    break;
	case DONE:             
	    throw new NoSuchElementException("No more elements!");
	default:
	    throw new RuntimeException("Unknown phase" + phase);
	}
	return ans;
    }
}
