/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST.
 * Place all of your implementation into FundingNetwork.java.
 */
import junit.framework.TestCase;
import java.util.*;

public class BFSTest extends TestCase {
	FundingNetwork fn;
	
	public void setUp() throws Exception {
		fn = new FundingNetwork();
	}
	
	public void testEmpty() {
		Graph g = new HashGraph();
		g.addVertex("Foo");
		Map<String, String> bfs = fn.BFS(g, "Foo");
		assertEquals("no tree edges", 1, bfs.size());
		assertNull("no tree root", bfs.get("Foo"));
	}
	
	public void testOneEdge() {
		String[][] in = { { "Foo", "Bar", "2.0" } };
		Graph g = new HashGraph(in);
		Map<String, String> bfs = fn.BFS(g, "Foo");
		assertNull("one edge root", bfs.get("Foo"));
		assertEquals("one edge size", 2, bfs.size());
		assertEquals("one edge link", "Foo", bfs.get("Bar"));
	}
	
	public void testTwoEdges() {
		String[][] in = { { "Foo", "Bar", "2.0" }, { "Bar", "Baz", "1.0" } };
		Graph g = new HashGraph(in);
		Map<String, String> bfs = fn.BFS(g, "Foo");
		assertNull("two edges root", bfs.get("Foo"));
		assertEquals("two edges size", 3, bfs.size());
		assertEquals("two edges link", "Foo", bfs.get("Bar"));
		assertEquals("two edges link", "Bar", bfs.get("Baz"));
	}
	
	public void testNotDFS1() {
		String[][] in = { { "A", "B", "1" }, { "B", "C", "1" }, { "A", "C", "1" } };
		Graph g = new HashGraph(in);
		Map<String, String> bfs = fn.BFS(g, "A");
		assertNull("not dfs1 root", bfs.get("A"));
		assertEquals("not dfs1 size", 3, bfs.size());
		assertEquals("not dfs1 link", "A", bfs.get("B"));
		assertEquals("not dfs1 link", "A", bfs.get("C"));
	}
	
	public void testNotDFS2() {
		String[][] in = { { "C", "A", "1" }, { "B", "A", "1" }, { "C", "B", "1" } };
		Graph g = new HashGraph(in);
		Map<String, String> bfs = fn.BFS(g, "C");
		assertNull("not df21 root", bfs.get("C"));
		assertEquals("not dfs2 size", 3, bfs.size());
		assertEquals("not dfs2 link", "C", bfs.get("B"));
		assertEquals("not dfs2 link", "C", bfs.get("A"));
	}
	
	public void testNotDFS3() {
		String[][] in = { { "A", "B", "1" }, { "B", "C", "1" }, { "C", "D", "1" }, { "A", "E", "1" }, { "E", "D", "1" } };
		Graph g = new HashGraph(in);
		Map<String, String> bfs = fn.BFS(g, "A");
		assertNull("not dfs3 root", bfs.get("A"));
		assertEquals("not dfs3 size", 5, bfs.size());
		assertEquals("not dfs3 link", "A", bfs.get("B"));
		assertEquals("not dfs3 link", "B", bfs.get("C"));
		assertEquals("not dfs3 link", "E", bfs.get("D"));
		assertEquals("not dfs3 link", "A", bfs.get("E"));
	}
	
	public void testTwoPieces() {
		String[][] in = { { "A", "B", "1" }, { "C", "D", "1" } };
		Graph g = new HashGraph(in);
		Map<String, String> bfs = fn.BFS(g, "A");
		assertNull("two pieces root", bfs.get("A"));
		assertEquals("two pieces size", 2, bfs.size());
		assertEquals("two pieces link", "A", bfs.get("B"));		
	}
	
	public void testZeroIgnored() {
		String[][] in = { { "A", "B", "1" }, { "B", "C", "0" } };
		Graph g = new HashGraph(in);
		Map<String, String> bfs = fn.BFS(g, "A");
		assertNull("two pieces root", bfs.get("A"));
		assertEquals("two pieces size", 2, bfs.size());
		assertEquals("two pieces link", "A", bfs.get("B"));		
	}
	
	void DFS(Graph g, String parent, String child, Map<String, String> output) {
		if (output.containsKey(child)) return;
		output.put(child, parent);
		
		for (Map.Entry<String, Double> neighbour : g.get(child).entrySet())
			if (neighbour.getValue() > 0)
				DFS(g, child, neighbour.getKey(), output);
	}
	
	void compareToDFS(Graph g, String root) {
		Map<String, String> dfs = new HashMap<String, String>();
		DFS(g, null, root, dfs);
		Map<String, String> bfs = fn.BFS(g, root);

		// for (String a : dfs.keySet()) System.out.print(a + " "); System.out.print("\n");
		// for (String a : bfs.keySet()) System.out.print(a + " "); System.out.print("\n");
		
		// reachability should be the same
		assertTrue("same reachable set", dfs.keySet().equals(bfs.keySet()));
		
		// the path length in a BFS must be <= the path length in a DFS
		for (String vertex : bfs.keySet()) {
			int bfsLength = 0, dfsLength = 0;
			for (String step = vertex; step != root; step = bfs.get(step)) ++bfsLength;
			for (String step = vertex; step != root; step = dfs.get(step)) ++dfsLength;
			assertTrue("bfs paths are shorter than dfs", bfsLength <= dfsLength);
		}
	}
	
	public void testRandom() {
		Random r = new MTRandom3(42);
		for (int i = 0; i < 1000; ++i) {
			Graph g = new HashGraph();
			g.addVertex("0");
			for (int j = 0; j < 32; ++j)
				g.addEdge(Integer.toString(r.nextInt(32)), Integer.toString(r.nextInt(32)), r.nextInt(4));
			compareToDFS(g, "0");
		}
	}
}
