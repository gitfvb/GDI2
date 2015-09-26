/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST.
 * Place all of your implementation into StrikeForce.java.
 */
import junit.framework.TestCase;
import java.util.*;

public class ComponentsTest extends TestCase {
	StrikeForce sf;
	
	public void setUp() {
		sf = new StrikeForce();
		Agents.setUp();
	}
	
	StrikeForce.Graph makeGraph(int[][] graph) {
		StrikeForce.Graph output = sf.emptyGraph();
		for (int i = 0; i < graph.length; ++i) {
			output.addVertex(Agents.get(i));
			for (int j = 0; j < graph[i].length; ++j) {
				output.addEdge(Agents.get(i), Agents.get(graph[i][j]));
			}
		}
		return output;
	}
	
	String edgesAgree(Map<String, Integer> sort, StrikeForce.Graph graph) {
		StringBuilder output = new StringBuilder();
		for (String i : graph.keySet())
			for (String j : graph.get(i))
				if (sort.get(i) > sort.get(j))
					output.append(i + "->" + j + ": violates sort order;\n");
		return output.toString();
	}
	
	String allVertexesPresent(Map<String, Integer> sort, StrikeForce.Graph graph) {
		for (String i : graph.keySet())
			if (sort.get(i) == null)
					return i + " not in topological sort;\n";
		
		for (String i : sort.keySet())
			if (graph.get(i) == null)
					return i + " not in graph;\n";
					
		return edgesAgree(sort, graph);
	}
	
	String testComponents(StrikeForce.Graph graph, List<Collection<String>> sort) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < sort.size(); ++i) {
			for (String j : sort.get(i)) {
				assertFalse("vertex listed twice", map.containsKey(j));
				map.put(j, i);
			}
		}
		return allVertexesPresent(map, graph);
	}
	
	public void testEmpty() {
		int in[][] = { };
		StrikeForce.Graph g = makeGraph(in);
		List<Collection<String>> c = sf.components(g);
		assertEquals("empty graph", "", testComponents(g, c));
		assertEquals("empty graph count", 0, c.size());
	}
	
	public void testOneVertex() {
		int in[][] = { {} };
		StrikeForce.Graph g = makeGraph(in);
		List<Collection<String>> c = sf.components(g);
		assertEquals("one vertex", "", testComponents(g, c));
		assertEquals("one vertex count", 1, c.size());
	}
	
	public void testTwoVertexes() {
		int in[][] = { {}, {} };
		StrikeForce.Graph g = makeGraph(in);
		List<Collection<String>> c = sf.components(g);
		assertEquals("two vertex", "", testComponents(g, c));
		assertEquals("two vertex count", 2, c.size());
	}
	
	public void testDirectedEdge() {
		int in[][] = { { 1 }, {} };
		StrikeForce.Graph g = makeGraph(in);
		List<Collection<String>> c = sf.components(g);
		assertEquals("directed edge", "", testComponents(g, c));
		assertEquals("directed edge count", 2, c.size());
	}
	
	public void testReverseEdge() {
		int in[][] = { {}, { 0 } };
		StrikeForce.Graph g = makeGraph(in);
		List<Collection<String>> c = sf.components(g);
		assertEquals("reverse edge", "", testComponents(g, c));
		assertEquals("reverse edge count", 2, c.size());
	}
	
	public void testChain() {
		int in[][] = { {1}, {3}, {4}, {2}, {} };
		StrikeForce.Graph g = makeGraph(in);
		List<Collection<String>> c = sf.components(g);
		assertEquals("chain", "", testComponents(g, c));
		assertEquals("chain count", 5, c.size());
	}
	
	public void testCompleteEdges() {
		int in[][] = { {1, 2, 3, 4}, {2, 3, 4}, {3, 4}, {4}, {} };
		StrikeForce.Graph g = makeGraph(in);
		List<Collection<String>> c = sf.components(g);
		assertEquals("complete edges", "", testComponents(g, c));
		assertEquals("complete edges count", 5, c.size());
	}
	
	public void testReverseComplete() {
		int in[][] = { {}, {0}, {0,1}, {0,1,2}, {0,1,2,3} };
		StrikeForce.Graph g = makeGraph(in);
		List<Collection<String>> c = sf.components(g);
		assertEquals("reverse complete edges", "", testComponents(g, c));
		assertEquals("reverse complete edges count", 5, c.size());
	}
	
	public void testTree() {
		int in[][] = { {}, {}, {3,4}, {5,6}, {0, 1}, {}, {} };
		StrikeForce.Graph g = makeGraph(in);
		List<Collection<String>> c = sf.components(g);
		assertEquals("tree", "", testComponents(g, c));
		assertEquals("tree count", 7, c.size());
	}
	
	public void testDAG() {
		int in[][] = { {1}, {}, {3,4}, {5,6}, {0, 1}, {}, {5} };
		StrikeForce.Graph g = makeGraph(in);
		List<Collection<String>> c = sf.components(g);
		assertEquals("dag", "", testComponents(g, c));
		assertEquals("dag count", 7, c.size());
	}

	public void testOneComponentRing() {
		int in[][] = { {1}, {2}, {3}, {0} };
		StrikeForce.Graph g = makeGraph(in);
		List<Collection<String>> c = sf.components(g);
		assertEquals("one ring", "", testComponents(g, c));
		assertEquals("one ring count", 1, c.size());
	}
	
	public void testTwoComponents() {
		int in[][] = { {1}, {0}, {3}, {2} };
		StrikeForce.Graph g = makeGraph(in);
		List<Collection<String>> c = sf.components(g);
		assertEquals("two components", "", testComponents(g, c));
		assertEquals("two components count", 2, c.size());
	}
	
	public void testComplete() {
		int in[][] = { {1,2,3}, {0,2,3}, {0,1,3}, {0,1,2} };
		StrikeForce.Graph g = makeGraph(in);
		List<Collection<String>> c = sf.components(g);
		assertEquals("complete", "", testComponents(g, c));
		assertEquals("complete count", 1, c.size());
	}
}

