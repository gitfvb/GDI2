/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST.
 * Place all of your implementation into StrikeForce.java.
 */
import junit.framework.TestCase;
import java.util.*;

public class ContraintsToGraphTest extends TestCase {
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
	
	public void testEmpty() {
		String[] constraints = { };
		StrikeForce.Graph g = sf.emptyGraph();
		assertEquals("empty", 
				graphToString(g),
				graphToString(sf.constraintsToGraph(Arrays.asList(constraints))));
	}
	
	public void testOneOf() {
		String[] constraints = { "Bob", "Jim" };
		StrikeForce.Graph g = sf.emptyGraph();
		g.addEdge("!Jim", "Bob");
		g.addEdge("!Bob", "Jim");
		assertEquals("one of", 
				graphToString(g),
				graphToString(sf.constraintsToGraph(Arrays.asList(constraints))));
	}
	
	public void testOneAway() {
		String[] constraints = { "!Bob", "!Jim" };
		StrikeForce.Graph g = sf.emptyGraph();
		g.addEdge("Jim", "!Bob");
		g.addEdge("Bob", "!Jim");
		assertEquals("one away", 
				graphToString(g),
				graphToString(sf.constraintsToGraph(Arrays.asList(constraints))));
	}
	
	public void testImplication1() {
		String[] constraints = { "!Bob", "Jim" };
		StrikeForce.Graph g = sf.emptyGraph();
		g.addEdge("!Jim", "!Bob");
		g.addEdge("Bob", "Jim");
		assertEquals("implication1", 
				graphToString(g),
				graphToString(sf.constraintsToGraph(Arrays.asList(constraints))));
	}
	
	public void testImplication2() {
		String[] constraints = { "Bob", "!Jim" };
		StrikeForce.Graph g = sf.emptyGraph();
		g.addEdge("Jim", "Bob");
		g.addEdge("!Bob", "!Jim");
		assertEquals("implication2", 
				graphToString(g),
				graphToString(sf.constraintsToGraph(Arrays.asList(constraints))));
	}
	
	public void testTautology() {
		String[] constraints = { "Bob", "!Bob" };
		StrikeForce.Graph g = sf.emptyGraph();
		g.addEdge("Bob", "Bob");
		g.addEdge("!Bob", "!Bob");
		assertEquals("tautology", 
				graphToString(g),
				graphToString(sf.constraintsToGraph(Arrays.asList(constraints))));
	}
	
	public void testImpossible1() {
		String[] constraints = { "Bob", "Bob", "!Bob", "!Bob" };
		StrikeForce.Graph g = sf.emptyGraph();
		g.addEdge("Bob", "!Bob");
		g.addEdge("Bob", "!Bob");
		g.addEdge("!Bob", "Bob");
		g.addEdge("!Bob", "Bob");
		assertEquals("impossible1", 
				graphToString(g),
				graphToString(sf.constraintsToGraph(Arrays.asList(constraints))));
	}
	
	public void testImpossible2() {
		String[] constraints = { "Bob", "Jim", "Bob", "!Jim", "!Bob", "Jim", "!Bob", "!Jim" };
		StrikeForce.Graph g = sf.emptyGraph();
		g.addEdge("Bob", "Jim");
		g.addEdge("Bob", "!Jim");
		g.addEdge("!Bob", "Jim");
		g.addEdge("!Bob", "!Jim");
		g.addEdge("Jim", "Bob");
		g.addEdge("Jim", "!Bob");
		g.addEdge("!Jim", "Bob");
		g.addEdge("!Jim", "!Bob");
		assertEquals("impossible2", 
				graphToString(g),
				graphToString(sf.constraintsToGraph(Arrays.asList(constraints))));
	}
}
