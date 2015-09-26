/* Änderungen nur an dieser Datei durchführen. Die vollständige Datei
 * nach https://www.dvs.tu-darmstadt.de/teaching/inf2/2009-prak/ hochladen.
 */
import java.util.*;

public class FundingNetwork {
	/* Breitensuche (BFS).
	 * Eingabe: Ein Residualgraph mit Kapazitäten für gerichtete Kanten.
	 * Ausgabe: Eine Map von Knoten zu ihren BFS-Eltern.
	 * Die Ausgabe beschreibt einen Baum mit den kürzesteten Pfaden der
	 * Knoten bis zur Quelle. Jeder Eintrag mappt einen Knoten zu seinem
	 * Elternknoten im Baum. Kanten mit Kapazität = 0 sind nicht in der
	 * Ausgabe enthalten. Der Quellknoten erhält ein Mapping auf den
	 * Elternknoten null.
	 */
	public Map<String, String> BFS(Graph g, String source) {
		
		// initialisieren
		Map<String, String> result = new HashMap<String, String>();
		LinkedList<String> queue = new LinkedList<String>();
		
		// source-Knoten zur Warteschlange und zum Ergebnis hinzufügen
		queue.add(source);
		result.put(source, null);
		
		// Durchlaufen der Knoten
		while (!queue.isEmpty()) {
			String father = queue.getFirst();
			queue.removeFirst();

			// Durchlaufen der Kinder
			for (String child : g.get(father).keySet()) {
				// Wenn der Knoten bzw. das Kind noch nicht durchlaufen wurde
				// und die Kapazität 0 betrügt, wird dieser der Warteschlange
				// und dem Ergebnis hinzugefügt
				if (!result.containsKey(child) && g.get(father).get(child) > 0) {
					queue.addLast(child);
					result.put(child, father);
				}
			}
		}
		
		return result;
	}

	/* Findet einen flussvergrößerndern Pfad (augmenting path) im
	 * Residualgraphen mithilfe von BFS.
	 * Eingabe: Ein Residualgraph mit Kapazitäten für gerichtete Kanten.
	 * Ausgabe: Ein Pfad mit verfügbarer Kapazität von der Quelle zur Senke
	 * als Liste von Knoten.
	 * Wenn {a, b, c, d } ausgegeben werden, dann ist a=Quelle, d=Senke und
	 * a->b, b->c, c->d sind Kanten im Graph mit einer Kapazität > 0.
	 * Existiert kein flussvergrößernder Pfad, wird null zurück gegeben,
	 */
	public List<String> augmentingPath(Graph g, String source, String sink) {
		
		// Initialisieren
		LinkedList<String> augPath = new LinkedList<String>();
		Map<String, String> bfsSearch = null;
		
		// Breitensuche auf den Graphen anwenden
		bfsSearch = this.BFS(g, source);
		
		// Abbruch, falls flussvergrößernder Pfad nicht möglich (Senke ist nicht enthalten)
		if (!bfsSearch.keySet().contains(sink)) {
			return null;
		}
		
		// Pfad von der Senke zur Quelle suchen und in Liste ergänzen
		String val = sink;
		do {
			augPath.addFirst(val); // Knoten zur Liste hinzufügen
			val = bfsSearch.get(val); // Jetzt den nächsten Elternknoten auslesen
		} while (val != null); // While-Schleife, bis null nach der Quelle erreicht wird
		
		// Rückgabe der Werte
		return augPath;
	}

	/* Findet den maximalen Fluss eines Graphen.
	 * Eingabe: Ein Graph mit symmetrischen Kanten (Kapazität(a->b) = Kapazität(b->a))
	 * Ausgabe: Modifiziert den übergebenen Graphen, so dass kein
	 * flussvergrößernder Pfad mehr von der Quelle zur Senke existiert.
	 * Im Ergebnis muss (Kapazität(a->b) + Kapazität(b->a))/2 gleich
	 * der Originalkapazität der Kante a-b entsprechen. Die Summe der
	 * Kapazität der beiden Richtungen bleibt also unverändert.
	 */
	public double edmondsKarp(Graph g, String source, String sink) {
		
		// Initialisierung
		List<String> augPath = this.augmentingPath(g, source, sink);
		double result = 0.0;
		double cap = Double.POSITIVE_INFINITY;
		
		// Solange flußvergrößernde Pfade existieren
		while (augPath != null) {
			
			// minimale Kapazität auslesen
			cap = this.getMinimalCapacity(g, augPath);
			
			// für alle Knoten des Pfades
			for (int i = 1; i < augPath.size(); i++) {
				
				// Kanten merken
				String edge1 = augPath.get(i-1);
				String edge2 = augPath.get(i);
				
				// Werte der Kanten merken
				double forwEdge = g.get(edge1).get(edge2).doubleValue();
				double backEdge = g.get(edge2).get(edge1).doubleValue();
				
				// Vorkante verringern, Rückkante erhöhen
				g.get(edge1).put(edge2, forwEdge - cap);
				g.get(edge2).put(edge1, backEdge + cap);
				
			}
			
			// neuen flußvergrößernden Pfad suchen
			augPath = this.augmentingPath(g, source, sink);
			
			// Ergebnis erhöhen (maximaler Fluß)
			result += cap;
			
		}
		
		return result;
		
	}
	
	
	/*
	 * Gibt die minimale Kapazität eines Pfades zurück
	 */
	public Double getMinimalCapacity(Graph g, List<String> p) {
		
		// größtmöglichen Werte für Kapazität setzen
		double min = Double.MAX_VALUE;
		
		// über den gesamten Pfad laufen und minimale Kapazität auslesen
		for (int i = 1; i < p.size(); i++) {
			
			// aktuellen wert mit i, letzten wert mit i-1 rausholen
			String edge1 = p.get(i-1);
			String edge2 = p.get(i);
			
			// minimale Kapazität speichern, falls kleiner als der bisherige gespeicherte Wert 
			if (g.get(edge1).get(edge2).doubleValue() < min) min = g.get(edge1).get(edge2).doubleValue();
			
		}
		
		// Rückgabe
		return min;
	}
	

	/* Modifiziert einen Flussgraphen, so dass er mehrere Quellen und Senken
	 * unterstützt.
	 * Eingabe: Ein beliebiger Graph, Listen von Quellen und Senken.
	 * Ausgabe: Modifizierter Graph mit den Knoten "Source" und "Sink",
	 * welche mit allen Quellen bzw. Senken mit unendlicher Kapazität
	 * verbunden sind.
	 */
	public void addSuperSourceSink(Graph g, List<String> sources, List<String> sinks) {
		
		// Ergänzen der Quelle
		for (String s : sources) {
			g.addEdge("Source", s, Double.POSITIVE_INFINITY);
			g.addEdge(s, "Source", Double.POSITIVE_INFINITY);
		}
		// Ergänzen der Senke
		for (String s : sinks) {
			g.addEdge("Sink", s, Double.POSITIVE_INFINITY);
			g.addEdge(s, "Sink", Double.POSITIVE_INFINITY);
		}
		
	}

	/* Findet alle Knoten, welche über Kapazität > 0 Kanten erreicht werden
	 * können.
	 * Eingabe: Ein beliebiger Graph und eine Quelle.
	 * Ausgabe: Alle Knoten, welche von der Quelle erreicht werden können.
	 * Kanten mit Kapazität = 0 dürfen nicht verwendet werden, um Knoten
	 * zu erreichen.
	 */
	public Set<String> reachableSet(Graph g, String source) {
		// Breitensuche ausführen und Keyset (und damit alle verfügbaren Knoten) zurückgeben
		return this.BFS(g, source).keySet();
	}
	

	class Edge {
		public String i, j;
		double capacity;

		Edge(String i, String j, double c) {
			this.i = i; this.j = j; capacity = c;
		}
	}

	/* Findet die Mengen von Kanten, welche den Netzwerkfluss vollständig
	 * unterbinden, wenn sie entfernt werden.
	 * Eingabe: Ein Graph mit symmetrischen Kanten.
	 * Ausgabe: Kanten, deren Entfernung sicher stellen, dass keine Senke
	 * von keiner Quelle erreicht werden können.
	 * Die Gesamtkapazität der entfernten Kanten muss minimal sein.
	 */
	public List<Edge> minCut(Graph g, List<String> sources, List<String> sinks) {
		
		// Initialisierung
		List<Edge> edges = new ArrayList<Edge>();
		
		// Graphen korrekt erstellen
		this.addSuperSourceSink(g, sources, sinks);
		
		// Edmondskarp ausführen
		this.edmondsKarp(g, "Source", "Sink");
		
		// Finden aller erreichbaren Knoten
		Set<String> reachableVertexes = this.reachableSet(g, "Source");
		
		// minimalen Schnitt ermitteln: Kanten von erreichbaren Knoten zu nicht erreichbaren Knoten
		// diese bilden dann den minimalen Schnitt
		for (String s : reachableVertexes) {
			Map<String, Double> followingVertexes = g.get(s);
			for (String v : followingVertexes.keySet()) {
				if (!reachableVertexes.contains(v)) {
					Edge edge = new Edge(v, s, g.get(v).get(s).doubleValue() / 2);
					edges.add(edge);
				}
			}
		}
		
		// Rückgabe der Kanten
		return edges;
		
	}
}
