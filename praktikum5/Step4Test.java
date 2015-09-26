/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST
 * Place all of your implementation into Layoffs.java.
 */

import junit.framework.TestCase;
import java.util.*;

public class Step4Test extends TestCase {
	Layoffs l;
	
	public void setUp() {
		l = new Layoffs();
	}
	
	void sortByAgentName(List<Coordinate> agents) {
		class AgentMatters implements Comparator<Coordinate> {
			public int compare(Coordinate a, Coordinate b) {
				return Integer.parseInt(a.agent) - Integer.parseInt(b.agent);
			}
		}
		Collections.sort(agents, new AgentMatters());
	}
	
	public void testXRandom() {
		Random r = new MTRandom(42);
		
		for (int i = 0; i < 100; ++i) {
			List<Coordinate> agents = new ArrayList<Coordinate>();
			
			for (int j = 0; j < 1000; ++j)
				agents.add(new Coordinate(r.nextDouble(), 0.0, "x"));
			
			/* Find the closest b/c they are sorted linearly */
			l.sortByXCoordinate(agents);
			Layoffs.Result best = null;
			for (int j = 0; j < agents.size()-1; ++j)
				best = l.min(best, l.new Result(agents.get(j), agents.get(j+1)));
			
			/* Slightly perturb the ordering */
			for (int j = 0; j < agents.size(); ++j) {
				int jitter = j + r.nextInt(6);
				agents.get(j).agent = Integer.toString(jitter);
			}
			sortByAgentName(agents);
			
			Layoffs.Result answer = l.findClosestOfNext7(agents); 
			
			assertEquals("Closest agents found", best.distance(), answer.distance());
		}
	}
	
	public void testYRandom() {
		Random r = new MTRandom(42);
		
		for (int i = 0; i < 100; ++i) {
			List<Coordinate> agents = new ArrayList<Coordinate>();
			
			for (int j = 0; j < 1000; ++j)
				agents.add(new Coordinate(0.0, r.nextDouble(), "x"));
			
			/* Find the closest b/c they are sorted linearly */
			l.sortByYCoordinate(agents);
			Layoffs.Result best = null;
			for (int j = 0; j < agents.size()-1; ++j)
				best = l.min(best, l.new Result(agents.get(j), agents.get(j+1)));
			
			/* Slightly perturb the ordering */
			for (int j = 0; j < agents.size(); ++j) {
				int jitter = j + r.nextInt(6);
				agents.get(j).agent = Integer.toString(jitter);
			}
			sortByAgentName(agents);
			
			Layoffs.Result answer = l.findClosestOfNext7(agents); 
			
			assertEquals("Closest agents found", best.distance(), answer.distance());
		}
	}
}
