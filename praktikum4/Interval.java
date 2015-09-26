/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST
 * Place all of your implementation into CounterIntelligence.java.
 */

public class Interval {
	/* In interval includes the range [start, end)
	 * This means start is included, but end is not.
	 * Formally, it includes all x such that 
	 *    start <= x < end
	 */
	public int start;
	public int end;
	
	Interval(int s, int e) {
		start = s;
		end = e;
	}
	
	/* Returns true if this interval equals another.
	 */
	public boolean isEqual(Interval o) {
		return start == o.start && end == o.end;
	}
	
	/* Returns true if this interval overlaps another.
	 * Formally, true if there exists a real x such
	 * that start <= x < end and also o.start <= x < end
	 */
	public boolean overlaps(Interval o) {
		return start < o.end && o.start < end;
	}
	
	/* Returns true if this interval contains o.
	 * Formally, true if for every x in o x is in this.
	 */
	public boolean contains(Interval o) {
		return start <= o.start && o.end <= end;
	}
	
	/* The size of the interval.
	 * Formally, if x is output then x integers are contained in the interval.
	 */
	int size() {
		return end - start;
	}
}
