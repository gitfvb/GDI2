/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST
 * Place all of your implementation into Surveillance.java.
 */
import junit.framework.TestCase;
import java.util.*;

public class SatEventsTest extends TestCase {
	Surveillance s;
	
	public void setUp() {
		s = new Surveillance();
	}
	
	public void testCompare() {
		Surveillance.Event tmf = s.new Event(-5, false);
		Surveillance.Event tmt = s.new Event(-5, true);
		Surveillance.Event t0f = s.new Event(0, false);
		Surveillance.Event t0t = s.new Event(0, true);
		Surveillance.Event tpf = s.new Event(7, false);
		Surveillance.Event tpt = s.new Event(7, true);
		Surveillance.Event tlf = s.new Event(8, false);
		Surveillance.Event tlt = s.new Event(8, true);
		assertEquals("identity", 0, tmf.compareTo(tmf));
		assertEquals("identity", 0, tmt.compareTo(tmt));
		assertEquals("identity", 0, t0f.compareTo(t0f));
		assertEquals("identity", 0, t0t.compareTo(t0t));
		assertEquals("identity", 0, tpf.compareTo(tpf));
		assertEquals("identity", 0, tpt.compareTo(tpt));
		assertEquals("identity", 0, tlf.compareTo(tlf));
		assertEquals("identity", 0, tlt.compareTo(tlt));
		assertEquals("true less", -1, tmt.compareTo(tmf));
		assertEquals("true less", -1, t0t.compareTo(t0f));
		assertEquals("true less", -1, tpt.compareTo(tpf));
		assertEquals("true less", -1, tlt.compareTo(tlf));
		assertEquals("false greater", 1, tmf.compareTo(tmt));
		assertEquals("false greater", 1, t0f.compareTo(t0t));
		assertEquals("false greater", 1, tpf.compareTo(tpt));
		assertEquals("false greater", 1, tlf.compareTo(tlt));
		assertEquals("time less", -1, tmt.compareTo(t0t));
		assertEquals("time less", -1, tmf.compareTo(t0f));
		assertEquals("time less", -1, t0t.compareTo(tpt));
		assertEquals("time less", -1, t0f.compareTo(tpf));
		assertEquals("time less", -1, tpt.compareTo(tlt));
		assertEquals("time less", -1, tpf.compareTo(tlf));
		assertEquals("time greater", 1, t0t.compareTo(tmt));
		assertEquals("time greater", 1, t0f.compareTo(tmf));
		assertEquals("time greater", 1, tpt.compareTo(t0t));
		assertEquals("time greater", 1, tpf.compareTo(t0f));
		assertEquals("time greater", 1, tlt.compareTo(tpt));
		assertEquals("time greater", 1, tlf.compareTo(tpf));
		assertEquals("time primary less", -1, tmf.compareTo(t0t));
		assertEquals("time primary less", -1, tmt.compareTo(t0f));
		assertEquals("time primary less", -1, t0f.compareTo(tpt));
		assertEquals("time primary less", -1, t0t.compareTo(tpf));
		assertEquals("time primary less", -1, tpf.compareTo(tlt));
		assertEquals("time primary less", -1, tpt.compareTo(tlf));
		assertEquals("time primary greater", 1, t0f.compareTo(tmt));
		assertEquals("time primary greater", 1, t0t.compareTo(tmf));
		assertEquals("time primary greater", 1, tpf.compareTo(t0t));
		assertEquals("time primary greater", 1, tpt.compareTo(t0f));
		assertEquals("time primary greater", 1, tlf.compareTo(tpt));
		assertEquals("time primary greater", 1, tlt.compareTo(tpf));
	}
	
	String eventToString(Surveillance.Event e) {
		return Integer.toString(e.time) + e.satEntersRange + ";";
	}
	
	String listToString(List<Surveillance.Event> l) {
		StringBuilder b = new StringBuilder();
		for (Surveillance.Event e : l)
			b.append(eventToString(e));
		return b.toString();
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
	
	List<Surveillance.Event> makeEvents(int[][] intervals) {
		List<Surveillance.Event> output = new ArrayList<Surveillance.Event>();
		for (int i = 0; i < intervals.length; ++i) {
			output.add(s.new Event(intervals[i][0], true));
			output.add(s.new Event(intervals[i][1], false));
		}
		Collections.sort(output);
		return output;
	}
	
	void autoCheck(String reason, int[][] intervals) {
		String student = listToString(s.satEvents(makeMap(intervals)));
		String answer = listToString(makeEvents(intervals));
		assertEquals(reason, answer, student);
	}
	
	public void testEmpty() {
		int[][] intervals = { };
		autoCheck("empty", intervals);
	}
	
	public void testEmptySat() {
		int[][] intervals = { };
		Map<String, Collection<Interval1>> map = makeMap(intervals);
		map.put("Foo", new ArrayList<Interval1>());
		assertEquals("empty sat", "", listToString(s.satEvents(makeMap(intervals))));
	}
	
	public void testOneSat() {
		int[][] intervals = { { 7, 8, 0 } };
		autoCheck("one sat", intervals);
	}
	
	public void testTwoNonOverlap() {
		int[][] intervals1 = { { 7, 8, 0 }, { 9, 10, 1 } };
		int[][] intervals2 = { { 9, 10, 0 }, { 7, 8, 1 } };
		autoCheck("two non-overlapping1", intervals1);
		autoCheck("two non-overlapping2", intervals2);
	}
	
	public void testTwoOverlap() {
		int[][] intervals1 = { { 7, 8, 0 }, { 6, 10, 1 } };
		int[][] intervals2 = { { 6, 10, 0 }, { 7, 8, 1 } };
		autoCheck("two overlapping1", intervals1);
		autoCheck("two overlapping2", intervals2);
	}
	
	public void testTwoHandover() {
		int[][] intervals1 = { { 7, 8, 0 }, { 8, 9, 1 } };
		int[][] intervals2 = { { 8, 9, 0 }, { 7, 8, 1 } };
		autoCheck("two handover1", intervals1);
		autoCheck("two handover2", intervals2);
	}
	
	public void testOneSatTwice() {
		int[][] intervals1 = { { 6, 7, 0 }, { 8, 9, 0 } };
		int[][] intervals2 = { { 8, 9, 0 }, { 6, 7, 0 } };
		autoCheck("one sat twice1", intervals1);
		autoCheck("one sat twice2", intervals2);
	}
	
	public void testRandom() {
		Random r = new MTRandom1(42);
		for (int i = 0; i < 1000; ++i) {
			int[][] intervals = new int[100][3];
			for (int j = 0; j < 100; ++j) {
				int x = r.nextInt(100), y = r.nextInt(100); 
				intervals[j][0] = Math.min(x, y);
				intervals[j][1] = Math.max(x, y)+1;
				intervals[j][2] = j;
			}
			autoCheck("random", intervals);
		}
	}
}
