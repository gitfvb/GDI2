/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST
 * Place all of your implementation into Surveillance.java.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import junit.framework.TestCase;

public class SatCoverageTest extends TestCase {
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
		assertEquals("empty", 0, s.satCoverage(makeMap(intervals)).size());
	}
	
	public void testEmptySat() {
		int[][] intervals = { };
		Map<String, Collection<Interval1>> map = makeMap(intervals);
		map.put("Foo", new ArrayList<Interval1>());
		assertEquals("empty sat", 0, s.satCoverage(makeMap(intervals)).size());
	}
	
	public void testOneSat() {
		int[][] intervals = { { 7, 8, 0 } };
		assertEquals("one sat", 0, s.satCoverage(makeMap(intervals)).size());
	}
	
	public void testTwoSat() {
		int[][] intervals = { { 7, 8, 0 }, { 7, 8, 1 } };
		assertEquals("two sat", 0, s.satCoverage(makeMap(intervals)).size());
	}
	
	public void testThreeSat() {
		int[][] intervals = { { 7, 8, 0 }, { 7, 8, 1 }, { 7, 8, 2 } };
		assertTrue("three sat", new Interval1(7, 8).isEqual(s.longestSatCoverage(makeMap(intervals))));
	}
	
	public void testNeverThree() {
		int[][] intervals1 = { { 1, 3, 0 }, { 2, 4, 1 }, { 3, 5, 2 } };
		int[][] intervals2 = { { 3, 5, 0 }, { 2, 4, 1 }, { 1, 3, 2 } };
		assertEquals("never three1", 0, s.satCoverage(makeMap(intervals1)).size());
		assertEquals("never three2", 0, s.satCoverage(makeMap(intervals2)).size());
	}
	
	public void testSmallOverlap() {
		int[][] intervals1 = { { 1, 5, 0 }, { 2, 6, 1 }, { 3, 7, 2 } };
		int[][] intervals2 = { { 3, 7, 0 }, { 2, 6, 1 }, { 1, 5, 2 } };
		assertTrue("small overlap1", new Interval1(3, 5).isEqual(s.longestSatCoverage(makeMap(intervals1))));
		assertTrue("small overlap2", new Interval1(3, 5).isEqual(s.longestSatCoverage(makeMap(intervals2))));
	}
	
	public void testTwoHandover() {
		int[][] intervals = { { 7, 8, 0 }, { 6, 9, 1 }, { 5, 10, 2}, { 8, 10, 4 } };
		assertTrue("two handover", new Interval1(7, 9).isEqual(s.longestSatCoverage(makeMap(intervals))));
	}
	
	public void testThreeHandover() {
		int[][] intervals = { { 1, 5, 0 }, { 4, 8, 1 }, { 0, 3, 2 }, { 3, 7, 4 }, { 2, 6, 5 }, { 5, 9, 6 } };
		assertTrue("three handover", new Interval1(2, 7).isEqual(s.longestSatCoverage(makeMap(intervals))));
	}
	
 	public void testRandom() {
		Random r = new MTRandom1(42);
		int[] answers = { 34, 21, 54, 40, 74, 84, 59, 56, 74, 68, 70, 43, 87, 67, 55, 35, 69, 76, 54, 66, 54, 50, 67, 66, 44, 81, 86, 66, 50, 64, 55, 34, 67, 49, 31, 20, 33, 45, 18, 55, 51, 69, 26, 38, 56, 53, 40, 37, 66, 54, 73, 12, 57, 49, 51, 19, 61, 41, 39, 67, 78, 51, 61, 53, 34, 55, 30, 62, 47, 66, 41, 74, 44, 26, 70, 19, 43, 75, 24, 12, 25, 2, 58, 51, 72, 7, 53, 38, 12, 50, 73, 64, 71, 64, 66, 53, 20, 40, 75, 34, 70, 37, 63, 25, 4, 38, 28, 10, 5, 37, 56, 74, 23, 5, 64, 63, 74, 51, 33, 21, 19, 49, 61, 63, 43, 37, 44, 51, 47, 74, 24, 52, 23, 45, 57, 68, 79, 49, 68, 19, 76, 55, 21, 60, 17, 53, 43, 30, 35, 32, 16, 71, 49, 65, 14, 68, 55, 13, 55, 86, 83, 63, 43, 29, 51, 45, 73, 61, 73, 54, 41, 33, 50, 45, 83, 62, 36, 78, 21, 29, 40, 73, 35, 63, 66, 51, 45, 39, 43, 62, 46, 59, 26, 66, 49, 38, 84, 76, 69, 52, 68, 82, 24, 12, 88, 49, 24, 29, 69, 77, 19, 50, 64, 51, 61, 56, 38, 71, 90, 66, 28, 9, 91, 14, 58, 49, 49, 19, 22, 62, 55, 44, 51, 42, 63, 73, 15, 54, 49, 15, 65, 50, 49, 38, 43, 38, 46, 26, 34, 68, 75, 32, 70, 37, 41, 63, 44, 27, 20, 63, 60, 44, 13, 46, 38, 72, 26, 62, 40, 47, 56, 20, 41, 40, 48, 35, 67, 52, 67, 59, 18, 37, 9, 21, 46, 58, 78, 44, 27, 62, 19, 81, 54, 72, 9, 46, 58, 1, 72, 32, 35, 53, 26, 8, 14, 53, 67, 35, 51, 83, 68, 26, 63, 69, 35, 57, 42, 70, 34, 58, 70, 40, 71, 89, 26, 62, 60, 59, 59, 61, 55, 7, 30, 19, 69, 7, 60, 14, 73, 81, 48, 58, 39, 53, 67, 58, 75, 64, 17, 30, 77, 40, 49, 18, 57, 63, 64, 83, 42, 44, 52, 52, 70, 44, 57, 20, 45, 65, 18, 58, 11, 18, 43, 60, 54, 57, 27, 35, 71, 22, 74, 58, 20, 23, 21, 34, 64, 13, 45, 18, 63, 39, 57, 39, 26, 79, 65, 56, 49, 71, 27, 9, 82, 59, 76, 64, 1, 47, 67, 45, 65, 61, 61, 50, 48, 41, 65, 34, 22, 57, 30, 62, 43, 55, 58, 35, 73, 36, 88, 55, 53, 61, 12, 9, 46, 72, 61, 50, 41, 54, 49, 70, 72, 26, 78, 53, 59, 28, 62, 64, 64, 43, 48, 27, 72, 60, 48, 29, 41, 63, 80, 50, 32, 39, 41, 78, 42, 57, 47, 30, 49, 51, 6, 32, 8, 47, 39, 41, 85, 50, 59, 58, 26, 64, 64, 12, 76, 64, 34, 58, 44, 82, 59, 25, 24, 23, 45, 50, 60, 61, 74, 25, 63, 34, 32, 12, 58, 45, 69, 43, 76, 75, 65, 46, 52, 42, 49, 65, 46, 40, 70, 21, 68, 80, 53, 11, 50, 69, 71, 50, 28, 36, 23, 54, 28, 56, 48, 70, 46, 35, 58, 37, 23, 50, 49, 72, 69, 69, 61, 78, 34, 72, 21, 50, 74, 57, 33, 23, 48, 33, 73, 54, 37, 22, 33, 52, 73, 56, 66, 47, 52, 23, 74, 49, 9, 69, 59, 61, 15, 55, 72, 41, 56, 43, 60, 67, 60, 33, 24, 23, 20, 63, 1, 32, 40, 63, 61, 18, 50, 35, 62, 83, 47, 70, 43, 62, 60, 11, 57, 45, 56, 41, 25, 37, 28, 20, 24, 52, 71, 68, 63, 51, 38, 55, 60, 68, 60, 57, 66, 51, 55, 66, 56, 11, 24, 72, 28, 80, 24, 47, 65, 50, 55, 57, 17, 82, 69, 33, 62, 34, 28, 73, 22, 45, 32, 64, 26, 33, 43, 49, 85, 28, 60, 46, 29, 41, 64, 22, 21, 42, 56, 75, 9, 17, 53, 79, 55, 67, 46, 24, 48, 50, 51, 49, 44, 17, 20, 44, 45, 15, 45, 63, 54, 33, 46, 70, 47, 42, 68, 38, 34, 83, 77, 26, 69, 26, 41, 55, 21, 73, 57, 56, 71, 36, 69, 56, 57, 17, 76, 60, 55, 58, 22, 15, 73, 59, 20, 18, 66, 50, 51, 20, 60, 70, 23, 35, 70, 56, 55, 54, 25, 50, 58, 69, 47, 49, 43, 47, 34, 69, 34, 25, 32, 58, 37, 17, 70, 22, 13, 54, 13, 61, 7, 25, 49, 71, 65, 39, 50, 49, 42, 16, 76, 70, 39, 60, 43, 71, 68, 70, 66, 67, 20, 21, 19, 43, 30, 45, 52, 62, 44, 36, 68, 29, 20, 28, 12, 58, 10, 54, 42, 61, 75, 26, 10, 29, 17, 77, 45, 81, 74, 34, 18, 50, 60, 35, 29, 35, 55, 60, 34, 65, 68, 41, 52, 80, 47, 65, 52, 74, 26, 35, 50, 71, 38, 43, 59, 68, 61, 51, 15, 12, 51, 71, 73, 43, 60, 39, 52, 36, 56, 62, 73, 49, 35, 64, 1, 30, 34, 72, 39, 36, 44, 23, 44, 56, 50, 47, 33, 29, 60, 53, 23, 79, 37, 58, 44, 16, 69, 44, 41, 28, 34, 37, 45, 46, 70, 85, 52, 61, 56, 13, 20, 36, 39, 74, 30, 70, 60, 56, 46, 69, 14, 74, 38, 76, 67, 26, 24, 75, 56, 15, 56, 49, 49, 37, 46, 72, 19, 52, 62, 58, 69, 88, 6, 73, 55, 34, 57, 79, 21, 50, 51, 69, 52, 51, 36, 83, 61, 21, 33, 63, 52, 53, 51, 57, 25, 70, 33, 46, 23, 49, 71, 78, 70, 41, 46, 35, 70, 66, 65, 72, 26, 38, 55, 64, 54, 26, 46, 63, 14, 35, 74, 71, 12, 47, 55, 82, 71, 27, 65, 38, 48, 41, 74, 43, 29, 65, 40, 41, 22, 57, 42, 56, 43, 10, 52, 36, 79, 39 };
 
		for (int i = 0; i < 1000; ++i) {
			int[][] intervals = new int[8][3];
			for (int j = 0; j < intervals.length; ++j) {
				int x = r.nextInt(100), y = r.nextInt(100); 
				intervals[j][0] = Math.min(x, y);
				intervals[j][1] = Math.max(x, y)+1;
				intervals[j][2] = j;
			}
			assertEquals("random", answers[i], s.longestSatCoverage(makeMap(intervals)).size());
		}
	}
}
