/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST
 * Place all of your implementation into Surveillance.java.
 */
import java.util.*;
import junit.framework.TestCase;

public class FewestSatellitesTest extends TestCase {
	Surveillance s;
	
	public void setUp() {
		s = new Surveillance();
	}
	
	Map<String, Collection<Interval1>> makeMap(int[][] intervals) {
		Map<String, Collection<Interval1>> output = new HashMap<String, Collection<Interval1>>();
		for (int i = 0; i < intervals.length; ++i) {
			String satname = "Sat" + Integer.toString(intervals[i][2]);
			Collection<Interval1> sat = output.get(satname);
			if (sat == null) {
				sat = new ArrayList<Interval1>();
				output.put(satname, sat);
			}
			sat.add(new Interval1(intervals[i][0], intervals[i][1]));
		}		
		return output;
	}
	
	public void testEmpty() {
		int[][] intervals = { };
		Interval1 mission = new Interval1(9, 12);
		assertNull("empty", s.fewestSatellites(mission, makeMap(intervals)));
	}
	
	public void testEmptySat() {
		int[][] intervals = { };
		Map<String, Collection<Interval1>> map = makeMap(intervals);
		map.put("Foo", new ArrayList<Interval1>());
		map.put("Bar", new ArrayList<Interval1>());
		map.put("Baz", new ArrayList<Interval1>());
		Interval1 mission = new Interval1(9, 12);
		assertNull("empty sats", s.fewestSatellites(mission, makeMap(intervals)));
	}
	
	public void testNeverThree() {
		int[][] intervals1 = { { 1, 3, 0 }, { 2, 4, 1 }, { 3, 5, 2 } };
		int[][] intervals2 = { { 3, 5, 0 }, { 2, 4, 1 }, { 1, 3, 2 } };
		Interval1 mission = new Interval1(3, 5);
		assertNull("never three1", s.fewestSatellites(mission, makeMap(intervals1)));
		assertNull("never three2", s.fewestSatellites(mission, makeMap(intervals2)));
	}
	
	public void testSmallOverlap() {
		int[][] intervals1 = { { 1, 5, 0 }, { 2, 6, 1 }, { 3, 7, 2 } };
		int[][] intervals2 = { { 3, 7, 0 }, { 2, 6, 1 }, { 1, 5, 2 } };
		Interval1 mission = new Interval1(3, 5);
		assertEquals("small overlap1", 3, s.fewestSatellites(mission, makeMap(intervals1)).size());
		assertEquals("small overlap2", 3, s.fewestSatellites(mission, makeMap(intervals2)).size());
	}
	
	public void testTwoHandover() {
		int[][] intervals = { { 7, 8, 0 }, { 6, 9, 1 }, { 5, 10, 2}, { 8, 10, 4 } };
		Interval1 mission1 = new Interval1(7, 9);
		Interval1 mission2 = new Interval1(7, 8);
		Set<String> x1 = s.fewestSatellites(mission1, makeMap(intervals));
		Set<String> x2 = s.fewestSatellites(mission2, makeMap(intervals));
		assertTrue("two handover has1", x1.contains("Sat0"));
		assertTrue("two handover has2", x1.contains("Sat1"));
		assertTrue("two handover has3", x1.contains("Sat2"));
		assertEquals("two handover1", 4, x1.size());
		assertEquals("two handover2", 3, x2.size());
	}
	
	public void testThreeHandover() {
		int[][] intervals = { { 1, 5, 0 }, { 4, 8, 1 }, { 0, 3, 2 }, { 3, 7, 4 }, { 2, 6, 5 }, { 5, 9, 6 } };
		Interval1 mission = new Interval1(3, 6);
		assertEquals("three handover", 4, s.fewestSatellites(mission, makeMap(intervals)).size());
	}
	
 	public void testRandom() {
		Random r = new MTRandom1(42);
		int[] answers = { 6, 3, 4, 6, 6, 5, 6, 5, 5, 5, 6, 5, 6, 4, 5, 5, 5, 6, 6, 5, 5, 4, 5, 7, 5, 7, 6, 6, 5, 5, 6, 4, 4, 5, 6, 3, 5, 5, 3, 4, 6, 4, 5, 5, 4, 6, 4, 4, 5, 4, 6, 4, 5, 5, 4, 3, 7, 5, 5, 5, 5, 6, 6, 3, 5, 4, 4, 5, 5, 5, 5, 5, 4, 4, 6, 3, 6, 5, 5, 3, 4, 3, 6, 6, 6, 4, 5, 5, 3, 6, 7, 5, 8, 6, 4, 5, 6, 5, 4, 4, 7, 4, 6, 4, 3, 4, 4, 4, 3, 4, 6, 5, 5, 3, 5, 7, 5, 5, 3, 5, 4, 5, 6, 5, 4, 5, 5, 6, 5, 5, 4, 4, 3, 7, 6, 5, 5, 6, 6, 3, 6, 5, 4, 7, 3, 4, 4, 4, 5, 4 }; 
		for (int i = 0; i < 150; ++i) {
			int[][] intervals = new int[8][3];
			for (int j = 0; j < intervals.length; ++j) {
				int x = r.nextInt(100), y = r.nextInt(100); 
				intervals[j][0] = Math.min(x, y);
				intervals[j][1] = Math.max(x, y)+1;
				intervals[j][2] = j;
			}
			Map<String, Collection<Interval1>> map = makeMap(intervals);
			Interval1 mission = s.longestSatCoverage(map);
			assertEquals("random", answers[i], s.fewestSatellites(mission, map).size());
		}
	}
}
