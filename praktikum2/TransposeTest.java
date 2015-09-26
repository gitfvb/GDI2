/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST.
 * Place all of your implementation into StrikeForce.java.
 */
import junit.framework.TestCase;
import java.util.*;

public class TransposeTest extends TestCase {
	StrikeForce sf;
	
	public void setUp() {
		sf = new StrikeForce();
		Agents.setUp();
	}
	
	String graphToString(StrikeForce.Graph graph) {
		StringBuilder output = new StringBuilder();
		
		// Sort the vertexes so all student graphs compare the same
		List<String> vertexes = new ArrayList<String>(graph.keySet());
		Collections.sort(vertexes);
		for (String i : vertexes) {
			output.append(i + ";\n");
			List<String> edges = new ArrayList<String>(graph.get(i));
			Collections.sort(edges);
			for (String j : edges)
				output.append(i + " -> " + j + ";\n");
		}
		
		return output.toString();
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
	
	public void testEmpty() {
		int in[][] = { };
		int out[][] = { };
		assertEquals("empty graph", 
				graphToString(makeGraph(out)), 
				graphToString(sf.transpose(makeGraph(in))));
	}
	
	public void testOneVertexOnly() {
		int in[][] = { { } };
		int out[][] = { { } };
		assertEquals("one vertex, no edges", 
				graphToString(makeGraph(out)), 
				graphToString(sf.transpose(makeGraph(in))));
	}
	
	public void testTwoVertexOnly() {
		int in[][] = { { }, { } };
		int out[][] = { { }, { } };
		assertEquals("two vertexes, no edges", 
				graphToString(makeGraph(out)), 
				graphToString(sf.transpose(makeGraph(in))));
	}
	
	public void testOneVertexThreeSelf() {
		int in[][] = { { 0, 0, 0 } };
		int out[][] = { { 0, 0, 0 } };
		assertEquals("one vertex, three self loops", 
				graphToString(makeGraph(out)), 
				graphToString(sf.transpose(makeGraph(in))));
	}
	
	public void testOneDirectedEdge() {
		int in[][] = { { 1 }, { } };
		int out[][] = { { }, { 0 } };
		assertEquals("two vertexes, one directed edge", 
				graphToString(makeGraph(out)),
				graphToString(sf.transpose(makeGraph(in))));
	}
	
	public void testOneUndirectedEdge() {
		int in[][] = { { 1 }, { 0 } };
		int out[][] = { { 1 }, { 0 } };
		assertEquals("two vertexes, one undirected edge", 
				graphToString(makeGraph(out)), 
				graphToString(sf.transpose(makeGraph(in))));
	}
	
	public void testTriangle() {
		int in[][] = { { 1 }, { 2 }, { 0 } };
		int out[][] = { { 2 }, { 0 }, { 1} };
		assertEquals("triangle", 
				graphToString(makeGraph(out)), 
				graphToString(sf.transpose(makeGraph(in))));
	}
}
