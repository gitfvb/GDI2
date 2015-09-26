/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST.
 * Place all of your implementation into FundingNetwork.java.
 */
import java.util.*;

public class HashGraph extends HashMap<String, Map<String, Double>> implements Graph {
	public void addVertex(String i) {
		if (!containsKey(i))
			put(i, new HashMap<String, Double>());
	}
	
	public void addEdge(String i, String j, double capacity) {
		addVertex(i);
		addVertex(j);
		
		get(i).put(j, capacity);
		get(j).put(i, capacity);
	}
	
	public String toString() {
		StringBuilder output = new StringBuilder();
		
		// Sort the vertexes so all student graphs compare the same
		List<String> vertexes = new ArrayList<String>(keySet());
		Collections.sort(vertexes);
		for (String i : vertexes) {
			output.append(i + ";\n");
			Map<String, Double> adjacency = get(i);
			List<String> edges = new ArrayList<String>(adjacency.keySet());
			Collections.sort(edges);
			for (String j : edges)
				output.append(i + " -> " + j + ": " + adjacency.get(j) + ";\n");
		}
		
		return output.toString();		
	}
	
	public HashGraph() { }
	
	public HashGraph(String[][] edges) {
		for (int i = 0; i < edges.length; ++i)
			addEdge(edges[i][0], edges[i][1], Double.parseDouble(edges[i][2]));
	}
	
	public Graph clone() {
		Graph output = new HashGraph();
		for (Map.Entry<String, Map<String, Double>> i : entrySet()) {
			addVertex(i.getKey());
			for (Map.Entry<String, Double> j : i.getValue().entrySet()) {
				output.addEdge(i.getKey(), j.getKey(), j.getValue());
			}
		}
		return output;
	}
	
	static final long serialVersionUID = 85858585; 
}
