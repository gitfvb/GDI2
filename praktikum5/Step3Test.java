/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST
 * Place all of your implementation into Layoffs.java.
 */

import junit.framework.TestCase;
import java.util.*;

public class Step3Test extends TestCase {
	Layoffs l;
	
	public void setUp() {
		l = new Layoffs();
	}

	Coordinate make(Random r, int i) {
		return new Coordinate(r.nextDouble(), r.nextDouble(), Integer.toString(i));
	}
	
	void checkYOrder(List<Coordinate> y) {
		for (int i = 0; i < y.size()-1; ++i)
			assertTrue("Y ordered", y.get(i).y <= y.get(i+1).y);
	}

	public void testRandom() {
		Random r = new MTRandom(42);
		for (int i = 0; i < 100; ++i) {
			List<Coordinate> agents = new ArrayList<Coordinate>();
			for (int j = 0; j < 10000; ++j) agents.add(make(r, j));
			List<Coordinate> copy = new ArrayList<Coordinate>(agents);
			
			l.sortByYCoordinate(agents);
			List<Coordinate> output = l.keepOnlyInsideStrip(agents, 0.5, 0.2);
			
			for (Coordinate agent : output)
				assertTrue("Agent exists", copy.get(Integer.parseInt(agent.agent)) == agent);
			
			for (Coordinate agent : output)
				assertTrue("Output coordinates are contained", agent.x <= 0.7 && agent.x >= 0.3);
		
			checkYOrder(output);
		}
	}
}
