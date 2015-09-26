/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST.
 * Place all of your implementation into FundingNetwork.java.
 */
import junit.framework.TestCase;
import java.util.*;

public class AugmentingPathTest extends TestCase {
	FundingNetwork fn;
	
	public void setUp() throws Exception {
		fn = new FundingNetwork();
	}
	
	void pathExists(Graph g, List<String> path) {
		for (int i = 1; i < path.size(); ++i) {
			String from = path.get(i-1);
			String to = path.get(i);
			assertTrue("edge exists", g.get(from).get(to) > 0);
		}
	}
	
	void testAnswer(Graph g) {
		String source = "A", sink = "Z";
		Set<String> reachable = fn.BFS(g, source).keySet();
		List<String> path = fn.augmentingPath(g, source, sink);
		
		if (path == null) {
			assertFalse("reachlable but no path", reachable.contains(sink));
		} else {
			assertTrue("is a path", path.size() > 1);
			assertEquals("source corect", source, path.get(0));
			assertEquals("sink correct", sink, path.get(path.size()-1));
			pathExists(g, path);
		}
	}
	
	public void testEmpty() {
		Graph g = new HashGraph();
		g.addVertex("A");
		testAnswer(g);
	}
	
	public void testOneEdge() {
		String[][] in = { { "A", "Z", "2.0" } };
		Graph g = new HashGraph(in);
		testAnswer(g);
	}
	
	public void testTwoEdges() {
		String[][] in = { { "A", "B", "2.0" }, { "B", "Z", "1.0" } };
		Graph g = new HashGraph(in);
		testAnswer(g);
	}
	
	public void testNotDFS() {
		String[][] in = { { "A", "B", "1" }, { "B", "Z", "1" }, { "A", "Z", "1" } };
		Graph g = new HashGraph(in);
		testAnswer(g);
	}
	
	public void testTwoPieces1() {
		String[][] in = { { "A", "Z", "1" }, { "C", "D", "1" } };
		Graph g = new HashGraph(in);
		testAnswer(g);
	}
	
	public void testTwoPieces2() {
		String[][] in = { { "A", "B", "1" }, { "C", "Z", "1" } };
		Graph g = new HashGraph(in);
		testAnswer(g);
	}
	
	public void testZeroIgnored() {
		String[][] in = { { "A", "B", "1" }, { "B", "Z", "0" } };
		Graph g = new HashGraph(in);
		testAnswer(g);
	}
	
	String node(int x) {
		return Character.toString(Character.toUpperCase(Character.forDigit(x, 36)));
	}
	
	public void testRandom() {
		Random r = new MTRandom3(42);
		for (int i = 0; i < 1000; ++i) {
			Graph g = new  HashGraph();
			g.addVertex("A");
			for (int j = 0; j < 36; ++j)
				g.addEdge(node(r.nextInt(36)), node(r.nextInt(36)), r.nextInt(4));
			testAnswer(g);
		}
	}
}
