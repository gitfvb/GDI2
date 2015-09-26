/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST.
 * Place all of your implementation into FundingNetwork.java.
 */
import junit.framework.TestCase;
import java.util.*;

public class EdmondsKarpTest extends TestCase {
	FundingNetwork fn;
	
	public void setUp() throws Exception {
		fn = new FundingNetwork();
	}
	
	void testConservation(Graph g) {
		for (String i : g.keySet()) {
			double inCapacity = 0, outCapacity = 0;
			for (String j : g.get(i).keySet()) {
				inCapacity += g.get(j).get(i);
				outCapacity += g.get(i).get(j);
			}
			assertEquals("input flow = output flow", inCapacity, outCapacity);
		}
	}
	
	void testCapacity(Graph g, Graph orig) {
		for (String i : g.keySet()) {
			for (String j : g.get(i).keySet()) {
				double origCapacity = orig.get(i).get(j) + orig.get(j).get(i);
				double capacity = g.get(i).get(j) + g.get(j).get(i);
				assertEquals("capacity unchanged", origCapacity, capacity);
			}
		}
	}
	
	double testFlow(Graph g) {
		double outFlow = 0, inFlow = 0;
		for (String i : g.keySet()) {
			double inCapacity = 0, outCapacity = 0;
			for (String j : g.get(i).keySet()) {
				if (g.get(j).get(i).isInfinite()) continue;
				inCapacity += g.get(j).get(i);
				outCapacity += g.get(i).get(j);
			}
			if (inCapacity < outCapacity)
				outFlow += (outCapacity - inCapacity) / 2.0;
			else
				inFlow += (inCapacity - outCapacity) / 2.0;
		}
		
		assertEquals("sink flow = source flow", outFlow, inFlow);
		return outFlow;
	}
	
	void testFinished(Graph g) {
		Set<String> reachable = fn.BFS(g, "Source").keySet();
		assertFalse("sink unreachable", reachable.contains("Sink"));
		assertTrue("source reachable", reachable.contains("Source"));
	}
	
	void testAnswer(String[][] in, List<String> sources, List<String> sinks) {
		Graph g = new HashGraph(in);
		fn.addSuperSourceSink(g, sources, sinks);
		Graph orig = g.clone();
		double flow = fn.edmondsKarp(g, "Source", "Sink");
		testConservation(g);
		testFinished(g);
		testCapacity(g, orig);
		assertEquals("correct flow", testFlow(g), flow);
	}
	
	public void testOneEdge() {
		String[][] edges = { { "A", "B", "5" } };
		testAnswer(edges, Collections.singletonList("A"), Collections.singletonList("B"));
	}
	
	public void testTwoEdges() {
		String[][] edges = { { "A", "B", "5" }, { "B", "C", "5" } };
		testAnswer(edges, Collections.singletonList("A"), Collections.singletonList("C"));
	}
	
	public void testTwoUnequalEdges() {
		String[][] edges = { { "A", "B", "10" }, { "B", "C", "5" } };
		testAnswer(edges, Collections.singletonList("A"), Collections.singletonList("C"));
	}
	
	public void testNoFlow() {
		String[][] edges = { { "A", "B", "10" }, { "B", "C", "0" } };
		testAnswer(edges, Collections.singletonList("A"), Collections.singletonList("C"));
	}
	
	public void testNoConnection() {
		String[][] edges = { { "A", "B", "10" }, { "C", "D", "10" } };
		testAnswer(edges, Collections.singletonList("A"), Collections.singletonList("D"));
	}
	
	public void testMultipath() {
		String[][] edges = { { "A", "B", "10" }, { "A", "C", "10" }, { "B", "D", "10" }, { "C", "D", "10" } };
		testAnswer(edges, Collections.singletonList("A"), Collections.singletonList("D"));
	}
	
	public void testHangFordFulkerson1() {
		String[][] edges = { { "s", "u", "100" }, { "s", "v", "100" }, { "v", "t", "100" }, { "u", "t", "100" }, { "u", "v", "0.0001" } };
		testAnswer(edges, Collections.singletonList("s"), Collections.singletonList("t"));
	}
	
	public void testHangFordFulkerson2() {
		String[][] edges = { { "s", "u", "100" }, { "s", "v", "100" }, { "v", "z", "100" }, { "u", "z", "100" }, { "u", "v", "0.0001" } };
		testAnswer(edges, Collections.singletonList("s"), Collections.singletonList("z"));
	}
	
	String node(int x) {
		return Character.toString(Character.toUpperCase(Character.forDigit(x, 36)));
	}

	public void testRandom() {
		Random r = new MTRandom3(42);
		for (int i = 0; i < 1000; ++i) {
			Graph g = new HashGraph();
			for (int j = 0; j < 36; ++j)
				g.addEdge(node(r.nextInt(36)), node(r.nextInt(36)), r.nextInt(4));
			g.addEdge("Source", "A", Double.POSITIVE_INFINITY);
			g.addEdge("B", "Sink", Double.POSITIVE_INFINITY);
			
			Graph orig = g.clone();
			
			double flow = fn.edmondsKarp(g, "Source", "Sink");
			testConservation(g);
			testFinished(g);
			testCapacity(g, orig);
			assertEquals("correct flow", testFlow(g), flow);
		}
	}
}
