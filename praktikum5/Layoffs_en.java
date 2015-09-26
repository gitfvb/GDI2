/* Make your changes only to this file. Once complete, upload
 * it to https://www.dvs.tu-darmstadt.de/teaching/inf2/2009-prak/
 */
import java.util.*;

public class Layoffs_en {
	/* Sort the agent list by the x coordinate.
	 * Input: A list of agents
	 * Output: A sorted list of agents
	 *   for (i <= j): agents.get(i).x <= agents.get(j).x
	 */
	void sortByXCoordinate(List<Coordinate> agents) {
		// unimplemented
	}

	/* Sort the agent list by the y coordinate.
	 * Input: A list of agents
	 * Output: A sorted list of agents
	 *   for (i <= j): agents.get(i).y <= agents.get(j).y
	 */
	void sortByYCoordinate(List<Coordinate> agents) {
		// unimplemented
	}

	/* Split a list of agents by a vertical line.
	 * Input: A list of agents.
	 * Output: Two lists of agents: those left/right of the line
	 *   for (Agent a : left)  a.x <= x
	 *   for (Agent a : right) a.x >= x
	 *   If a.x == x, half should go into left and half into right.
	 *   for (Agent a : agents) left.contains(a) XOR right.contains(a)
	 */
	void partitionByXCoordinate(
			List<Coordinate> agents, double x,
			List<Coordinate> left, List<Coordinate> right) {
		// unimplemented
	}

	/* Filter the agent list to contain only those agents within a vertical strip.
	 * Input: A list of agents, start and width of the strip
	 * Output: A list of agents within the strip.
	 *    for (Agent a : agents) x-width <= a.x <= x+width
	 */
	List<Coordinate> keepOnlyInsideStrip(List<Coordinate> agents, double x, double width) {
		return null; // unimplemented
	}

	/* A pair of agents */
	class Result {
		Coordinate a, b;

		double distance() { return a.distance(b); }
		Result(Coordinate a, Coordinate b) {
			this.a = a;
			this.b = b;
		}
	}

	/* A helper function to keep the closest pair of agents */
	Result min(Result a, Result b) {
		if (a == null) return b;
		if (b == null) return a;
		return (a.distance() < b.distance()) ? a : b;
	}

	/* Find the closest two agents in a sorted list.
	 * Input: A list of agents where it is guaranteed that the closest
	 *        two are no more than 7 indexes apart.
	 *        Result output = findClosestOfNext7(y);
	 *        y.indexOf(output.a) - y.indexOf(output.b) <= 7
	 * Output: The closest two agents.
	 *
	 * NOTE: This algorithm must execute in O(n) time.
	 */
	Result findClosestOfNext7(List<Coordinate> Y) {
		return null; // unimplemented
	}

	/* Find the closest two agents.
	 * Input: A list of agents sorted by their X coordinate.
	 *        A list of (the same) agents sorted by Y.
	 * Output: The closest two agents.
	 *
	 * NOTE: This algorithm must execute in O(n log(n)) time.
	 */
	Result findClosest(List<Coordinate> X, List<Coordinate> Y) {
		return null; // unimplemented
	}

	/* Find the two closet agents in the list.
	 * Input: A list of agents
	 * Output: The two agents with smallest .distance()
	 *
	 * NOTE: This algorithm must be O(nlog(n)) time to run fast enough.
	 */
	Result findClosest(List<Coordinate> agents) {
		return null; // unimplemented
	}
}
