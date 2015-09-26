/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST.
 * Place all of your implementation into FundingNetwork.java.
 */
import junit.framework.TestCase;
import java.util.*;

public class MinCutTest extends TestCase {
	FundingNetwork fn;
	
	public void setUp() throws Exception {
		fn = new FundingNetwork();
	}
	
	void testReachable(Graph g, List<FundingNetwork.Edge> edges) {
		Graph h = g.clone();
		
		for (FundingNetwork.Edge e : edges) {
			h.get(e.i).remove(e.j);
			h.get(e.j).remove(e.i);
		}
		
		Set<String> reachable = fn.BFS(h, "Source").keySet();
		assertFalse("is a cut", reachable.contains("Sink"));
		assertTrue("has source", reachable.contains("Source"));
	}
	
	void testFlow(Graph g, List<FundingNetwork.Edge> edges) {
		Graph h = g.clone();
		double flow = fn.edmondsKarp(h, "Source", "Sink");
		double capacity = 0;
		for (FundingNetwork.Edge e : edges)
			capacity += e.capacity;
		assertEquals("is a minimum cut", flow, capacity, 0.01);
	}
	
	void testAnswer(String[][] in, List<String> sources, List<String> sinks) {
		Graph g = new HashGraph(in);
		Graph h = g.clone();
		List<FundingNetwork.Edge> edges = fn.minCut(h, sources, sinks);
		fn.addSuperSourceSink(g, sources, sinks);
		testReachable(g, edges);
		testFlow(g, edges);
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
		for (int i = 0; i < 10000; ++i) {
			Graph g = new HashGraph();
			for (int j = 0; j < 36; ++j)
				g.addEdge(node(r.nextInt(36)), node(r.nextInt(36)), r.nextDouble()*1000);
			
			Graph h = g.clone();
			List<FundingNetwork.Edge> edges = 
				fn.minCut(h, Collections.singletonList("A"), Collections.singletonList("B"));
			g.addEdge("Source", "A", Double.POSITIVE_INFINITY);
			g.addEdge("B", "Sink", Double.POSITIVE_INFINITY);
			testReachable(g, edges);
			testFlow(g, edges);
		}
	}
	
	public void testBig() {
		Random r = new MTRandom3(42);
		for (int i = 0; i < 200; ++i) {
			Graph g = new HashGraph();
			for (int j = 0; j < 1000; ++j)
				g.addEdge(Integer.toString(r.nextInt(1000)), Integer.toString(r.nextInt(1000)), r.nextDouble()*1000);
			
			Graph h = g.clone();
			List<FundingNetwork.Edge> edges = 
				fn.minCut(h, Collections.singletonList("0"), Collections.singletonList("1"));
			g.addEdge("Source", "0", Double.POSITIVE_INFINITY);
			g.addEdge("1", "Sink", Double.POSITIVE_INFINITY);
			testReachable(g, edges);
			testFlow(g, edges);
		}
	}
}
