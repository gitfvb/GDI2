/* Änderungen nur an dieser Datei durchführen. Die vollständige Datei
 * nach https://www.dvs.tu-darmstadt.de/teaching/inf2/2009-prak/ hochladen.
 */
import java.util.*;

public class StrikeForce {
	/*
	 * Das Graph-Interface stellt Knoten (Vertex) als Strings dar. Für jeden
	 * Knoten wird eine Sammlung aller Knoten gespeichert, zu denen von diesem
	 * Knoten aus eine Kante führt.
	 */
	interface Graph extends Map<String, Collection<String>> {
		public void addEdge(String i, String j);

		public void addVertex(String i);
	}

	/*
	 * Eine Factory-Methode zur Erzeugung eines leeren Graphen. Dieser Graph
	 * enthält weder Knoten noch Kanten.
	 */
	public Graph emptyGraph() {
		class MyGraph extends HashMap<String, Collection<String>> implements
				Graph {
			static final long serialVersionUID = 83838383;

			public void addVertex(String i) {
				if (!containsKey(i))
					put(i, new ArrayList<String>());
			}

			public void addEdge(String i, String j) {
				addVertex(i);
				addVertex(j);
				get(i).add(j);
			}
		}

		return new MyGraph();
	}

	/*
	 * Eingabe: Ein gerichteter Graph. Ausgabe: Ein neuer Graph mit umgekehrten
	 * Kantenrichtungen. z.B.: wenn a->b im Graph ist, dann ist b->a im neuen
	 * Graphen.
	 */
	public Graph transpose(Graph graph) {
		Graph output = this.emptyGraph();
		if (!graph.isEmpty()) {
			for (String s : graph.keySet()) {
				output.addVertex(s); // zuerst Knoten ergänzen
				for (String sc : graph.get(s)) {
					output.addEdge(sc, s); // jetzt alle Kanten umgekehrt
					// ergänzen
				}
			}
		}
		return output;
	}

	/*
	 * Tiefensuche ausgehend vom Knoten 'vertex'. Die 'visited' Map wird
	 * benutzt, um festzustellen, ob ein Knoten bereits besucht wurde (Farbe
	 * grau/schwarz). Besuchte Knoten sollten zu finishOrder .add()iert werden,
	 * wenn der Besuch abgeschlossen ist.
	 */
	public void dfsVisit(Graph graph, List<String> finishOrder,
			Set<String> visited, String vertex) {

		// Knoten als "besucht" ergänzen
		visited.add(vertex);

		// Rekursive Tiefensuche
		for (String s : graph.get(vertex)) {
			if (!visited.contains(s)) {
				dfsVisit(graph, finishOrder, visited, s);
			}
		}

		// Knoten endgültig ergänzen
		finishOrder.add(vertex);
	}

	/*
	 * Sortiert den Graphen topologisch. Eingabe: Ein gerichteter azyklischer
	 * Graph Ausgabe: Eine Liste von Knoten, so dass wenn die Kante a->b im
	 * Graphen enthalten ist, a vor b in der Liste steht.
	 */
	public List<String> topologicalSort(Graph graph) {
		List<String> finishOrder = new ArrayList<String>();
		HashSet<String> visited = new HashSet<String>();

		// Prüfung auf jeden Knoten im Graph
		for (String s : graph.keySet()) {

			// Wenn der Knoten bereits besucht wurde, ist er bereits einsortiert
			if (!visited.contains(s)) {
				dfsVisit(graph, finishOrder, visited, s);
			}

		}

		// Reihenfolge laut Hinweis noch umkehren
		Collections.reverse(finishOrder);
		return finishOrder;
	}

	/*
	 * Findet die starken Zusammenhangskomponenten in topologischer Sortierung.
	 * Eingabe: Ein beliebiger Graph Ausgabe: Eine Liste von Collections von
	 * Knoten. Jede Collection ist eine starke Zusammenhangskomponente (= jeder
	 * Knoten kann alle anderen in er Collection erreichen). Die Collections
	 * sind so sortiert, dass wenn eine Kante a->b mit Knoten a in Komponente A
	 * und Knoten b in Komponente B existiert, A vor B in der Liste steht.
	 */
	public List<Collection<String>> components(Graph graph) {
		List<Collection<String>> output = new ArrayList<Collection<String>>();
		List<String> fOrder = new ArrayList<String>();
		HashSet<String> visited = new HashSet<String>();

		// Topologische Sortierung erstellen
		List<String> topsort = topologicalSort(graph);

		// Graphen umkehren (jetzt schon, da sehr rechenintensiv)
		Graph transposed = transpose(graph);

		// Auf jedem Knoten aus top. Sortierung Tiefensuche ausführen,
		// wenn er noch nicht besucht wurde. Danach diese Komponente
		// dem output hinzufügen.
		for (String s : topsort) {
			fOrder.clear();
			if (!visited.contains(s)) {
				dfsVisit(transposed, fOrder, visited, s);
				output.add(new ArrayList<String>(fOrder));
				if (fOrder.size() == graph.size())
					break; // nicht unbedingt nötig, ist eine kleine Abkürzung
			}
		}

		return output;
	}

	/*
	 * Konvertiert eine Menge von Constraints in einen Implikationsgraphen.
	 * Eingabe: Eine Liste von Paaren von Agentennamen { "Bob", "Sally" }
	 * bedeutet "Bob OR Sally" { "Tom", "Tina", "!Bob", "Sally", "Joe", "!Geoff"
	 * } bedeutet:
	 * "(Tom OR Tina) AND ((NOT Bob) OR Sally) AND (Joe OR (NOT Geoff))"
	 * Ausgabe: Ein Graph, der die Implikationen der Eingabeconstraints wieder
	 * gibt, z.B. "Bob OR Sally" bedeutet, dass !Bob -> Sally und !Sally -> Bob
	 * Kanten im Graph sind. Jeder Agent erscheint zweimal im Graphen; wenn
	 * "Bob" ein Knoten ist, muss auch "!Bob" ein Knoten sein.
	 */
	public Graph constraintsToGraph(List<String> constraints) {
		Graph output = emptyGraph();
		
		int i = 0;
		String t = null;
		for (String s : constraints) {
			switch (i % 2) {
			
			// zuerst den ersten agenten merken
			case 0:
				t = s;
				i++;
				break;
			
			// dann beim zweiten Agent die Kanten einfügen
			// und den Zähler wieder zurückzählen lassen
			case 1:
				output.addEdge(negateAgent(t), s);
				output.addEdge(negateAgent(s), t);
				i--;
				break;
			}
		}
		return output;
	}

	/**
	 * 
	 * Negiert einen Agenten-String
	 * 
	 * @param agent
	 *            Ein String, der mit einem "!" negiert werden soll bzw. dieses
	 *            entfernt werden soll
	 * @return Ein negierter String, je nach Eingabe mit oder ohne "!"
	 */
	public String negateAgent(String agent) {
		String output;
		if (agent.charAt(0) == '!')
			output = agent.substring(1);
		else
			output = "!" + agent;
		return output;
	}

	/*
	 * Findet eine zulässige Zusammenstellung für das Missionsteam. Eingabe:
	 * Eine Liste von Constraints Ausgabe: Teamzugehörigkeit der Agenten Wenn
	 * "Bob" in der Ausgabe wahr ist, dann ist er Teammitglied. Wenn er falsch
	 * ist, dann ist er kein Teammitglied. Z.B. würde { "Bob", "Bob" } zu
	 * "Bob"=true führen. Wenn keine Lösung möglich ist, wird null zurück
	 * gegeben.
	 */
	public Map<String, Boolean> solve(List<String> constraints) {
		Map<String, Boolean> result = new HashMap<String, Boolean>();

		// Implikationsgraph erstellen
		Graph constraint = this.constraintsToGraph(constraints);

		// Zusammenhangskomponenten ermitteln
		List<Collection<String>> components = this.components(constraint);

		// Beste Zusammenstellung ermitteln
		boolean b;
		String a;
		HashSet<String> visited = new HashSet<String>();
		for (Collection<String> component : components) {

			visited.clear();
			for (String agent : component) {

				// Parameter zusammenstellen
				if (agent.charAt(0) == '!') {
					a = agent.substring(1);
					b = false;
				} else {
					a = agent;
					b = true;
				}

				// Prüfung, ob Kombination überhaupt möglich ist, ansonsten
				// sofort abbrechen
				if (visited.contains(negateAgent(agent)))
					return null;

				// Kombination scheint möglich zu sein -> als besucht markieren
				visited.add(agent);

				// Scheint in Ordnung zu sein -> hinzufügen
				result.put(a, b);

			}
		}
		return result;
	}
}
