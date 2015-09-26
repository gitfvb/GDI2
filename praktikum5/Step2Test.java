/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST
 * Place all of your implementation into Layoffs.java.
 */

import junit.framework.TestCase;
import java.util.*;

public class Step2Test extends TestCase {
	Layoffs l;
	
	public void setUp() {
		l = new Layoffs();
	}

	Coordinate make(Random r, int i) {
		return new Coordinate(r.nextDouble(), r.nextDouble(), Integer.toString(i));
	}

	public void testEmpty() {
		List<Coordinate> agents = new ArrayList<Coordinate>();
		List<Coordinate> L = new ArrayList<Coordinate>();
		List<Coordinate> R = new ArrayList<Coordinate>();
		l.partitionByXCoordinate(agents, 0.5, L, R);
		assertEquals("left emprty", 0, L.size());
		assertEquals("right emprty", 0, L.size());
	}
	
	public void testRandom() {
		Random r = new MTRandom(42);
		for (int i = 0; i < 100; ++i) {
			List<Coordinate> agents = new ArrayList<Coordinate>();
			for (int j = 0; j < 10000; ++j) agents.add(make(r, j));
			List<Coordinate> copy = new ArrayList<Coordinate>(agents);
			
			List<Coordinate> L = new ArrayList<Coordinate>();
			List<Coordinate> R = new ArrayList<Coordinate>();
			l.partitionByXCoordinate(agents, 0.5, L, R);
			
			for (Coordinate agent : L)
				assertTrue("Agent exists", copy.get(Integer.parseInt(agent.agent)) == agent);
			for (Coordinate agent : R)
				assertTrue("Agent exists", copy.get(Integer.parseInt(agent.agent)) == agent);
			
			assertEquals("All agents present", 10000, L.size() + R.size());
			
			for (Coordinate agent : L)
				assertTrue("Left nodes are left", agent.x <= 0.5);
			for (Coordinate agent : R)
				assertTrue("Right nodes are right", agent.x >= 0.5);
			
			for (int j = 0; j < L.size()-1; ++j)
				assertTrue("Agent order unchanged", 
						Integer.parseInt(L.get(j).agent) < Integer.parseInt(L.get(j+1).agent));
		}
	}
}
