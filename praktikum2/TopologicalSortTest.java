/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST.
 * Place all of your implementation into StrikeForce.java.
 */
import junit.framework.TestCase;
import java.util.*;

public class TopologicalSortTest extends TestCase {
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
	
	String testTopsort(StrikeForce.Graph graph) {
		List<String> sort = sf.topologicalSort(graph);
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < sort.size(); ++i) {
			assertFalse("vertex listed twice", map.containsKey(sort.get(i)));
			map.put(sort.get(i), i);
		}
		return allVertexesPresent(map, graph);
	}
	
	public void testEmpty() {
		int in[][] = { };
		assertEquals("empty graph", "", testTopsort(makeGraph(in)));
	}
	
	public void testOneVertex() {
		int in[][] = { {} };
		assertEquals("one vertex", "", testTopsort(makeGraph(in)));
	}
	
	public void testTwoVertexes() {
		int in[][] = { {}, {} };
		assertEquals("two vertexes", "", testTopsort(makeGraph(in)));
	}
	
	public void testDirectedEdge() {
		int in[][] = { { 1 }, {} };
		assertEquals("directed edge", "", testTopsort(makeGraph(in)));
	}
	
	public void testReverseEdge() {
		int in[][] = { {}, { 0 } };
		assertEquals("reverse edge", "", testTopsort(makeGraph(in)));
	}
	
	public void testChain() {
		int in[][] = { {1}, {3}, {4}, {2}, {} };
		assertEquals("chain", "", testTopsort(makeGraph(in)));
	}
	
	public void testCompleteEdges() {
		int in[][] = { {1, 2, 3, 4}, {2, 3, 4}, {3, 4}, {4}, {} };
		assertEquals("complete edges", "", testTopsort(makeGraph(in)));
	}
	
	public void testReverseComplete() {
		int in[][] = { {}, {0}, {0,1}, {0,1,2}, {0,1,2,3} };
		assertEquals("reverse complete", "", testTopsort(makeGraph(in)));
	}
	
	public void testTwoComponents() {
		int in[][] = { {1}, {}, {3}, {} };
		assertEquals("two components", "", testTopsort(makeGraph(in)));
	}
	
	public void testTree() {
		int in[][] = { {}, {}, {3,4}, {5,6}, {0, 1}, {}, {} };
		assertEquals("tree", "", testTopsort(makeGraph(in)));
	}
	
	public void testDAG() {
			int in[][] = { {1}, {}, {3,4}, {5,6}, {0, 1}, {}, {5} };
			assertEquals("dag", "", testTopsort(makeGraph(in)));
	}
}
