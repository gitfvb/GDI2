/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST
 * Place all of your implementation into Layoffs.java.
 */

import junit.framework.TestCase;
import java.util.*;

public class Step1Test extends TestCase {
	Layoffs l;
	
	public void setUp() {
		l = new Layoffs();
	}
	
	void checkXOrder(List<Coordinate> x) {
		for (int i = 0; i < x.size()-1; ++i)
			assertTrue("X ordered", x.get(i).x <= x.get(i+1).x);
	}
	
	void checkYOrder(List<Coordinate> y) {
		for (int i = 0; i < y.size()-1; ++i)
			assertTrue("Y ordered", y.get(i).y <= y.get(i+1).y);
	}
	
	Coordinate make(Random r, int i) {
		return new Coordinate(r.nextDouble(), r.nextDouble(), Integer.toString(i));
	}

	public void testEmpty() {
		List<Coordinate> agents = new ArrayList<Coordinate>();
		l.sortByXCoordinate(agents);
		l.sortByYCoordinate(agents);
	}
	
	public void testXRandom() {
		Random r = new MTRandom(42);
		for (int i = 0; i < 25; ++i) {
			List<Coordinate> agents = new ArrayList<Coordinate>();
			for (int j = 0; j < 10000; ++j) agents.add(make(r, j));
			List<Coordinate> copy = new ArrayList<Coordinate>(agents);
			
			l.sortByXCoordinate(agents);
			
			for (Coordinate agent : agents)
				assertTrue("Agent exists", copy.get(Integer.parseInt(agent.agent)) == agent);
			assertEquals("All agents present", 10000, agents.size());
			checkXOrder(agents);
		}
	}

	public void testYRandom() {
		Random r = new MTRandom(42);
		for (int i = 0; i < 25; ++i) {
			List<Coordinate> agents = new ArrayList<Coordinate>();
			for (int j = 0; j < 10000; ++j) agents.add(make(r, j));
			List<Coordinate> copy = new ArrayList<Coordinate>(agents);
			
			l.sortByYCoordinate(agents);
			
			for (Coordinate agent : agents)
				assertTrue("Agent exists", copy.get(Integer.parseInt(agent.agent)) == agent);
			assertEquals("All agents present", 10000, agents.size());
			checkYOrder(agents);
		}
	}
}
