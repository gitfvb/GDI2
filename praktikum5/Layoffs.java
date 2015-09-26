/* Änderungen nur an dieser Datei durchführen. Die vollständige Datei
 * nach https://www.dvs.tu-darmstadt.de/teaching/inf2/2009-prak/ hochladen.
 */
import java.util.*;

public class Layoffs {
	
	/**
	 * Aufzählung möglicher Koordinaten
	 */
	public enum Axis {
		  x,y
	}
	
	
	/**
	 * Comparator für Koordinaten einer Liste... könnte auch in zwei einzelnen Comparatoren abgehandelt
	 * werden, aber diese Methode ist ein wenig dynamischer und beherbergt weniger Redundanz...
	 */
	static class axisComparator implements Comparator {
		
		Axis axis;
		
		public axisComparator(Axis axis) {
			this.axis = axis;
		}
		
		private double getAxisValue(Coordinate c) {
			return (axis == Axis.x) ? c.x : c.y;
		}
		
		public int compare(Object arg0, Object arg1) {
			return compare((Coordinate)arg0, (Coordinate)arg1);
		}
		
		public int compare(Coordinate arg0, Coordinate arg1) {
			double c0 = getAxisValue(arg0);	
			double c1 = getAxisValue(arg1);
			if (c0 < c1) return -1;
			else if (c0 > c1) return 1;
			else return 0;
		}
		
	}

	
	/* Sortiert die Agentenliste nach der X-Koordinate.
	 * Eingabe: Eine Liste von Agenten
	 * Ausgabe: Die Liste von Agenten in sortierter Form
	 *   for (i <= j): agents.get(i).x <= agents.get(j).x
	 */
	void sortByXCoordinate(List<Coordinate> agents) {
		Collections.sort(agents, new axisComparator(Axis.x));
	}

	/* Sortiert die Agentenliste nach der Y-Koordinate.
	 * Eingabe: Eine Liste von Agenten
	 * Ausgabe: Die Liste von Agenten in sortierter Form
	 *   for (i <= j): agents.get(i).y <= agents.get(j).y
	 */
	void sortByYCoordinate(List<Coordinate> agents) {
		 Collections.sort(agents, new axisComparator(Axis.y));
	}

	/* Teilt eine Liste von Agenten an einer vertikalen Linie.
	 * Eingabe: Eine Liste von Agenten
	 * Ausgabe: Zwei Listen von Agenten (links/rechts der Linie)
	 *   for (Agent a : left)  a.x <= x
	 *   for (Agent a : right) a.x >= x
	 *   Wenn a.x == x, die Haelfte davon sollen in links
	 *      und die andere in rechts gespeichert werden.
	 *   for (Agent a : agents) left.contains(a) XOR right.contains(a)
	 */
	void partitionByXCoordinate(
			List<Coordinate> agents, double x,
			List<Coordinate> left, List<Coordinate> right) {
		
			for (Coordinate agent : agents) 
				if (agent.x < x)
					left.add(agent);
				else
					right.add(agent);
			
			// Sonderfall: eine Liste hat viele Einträge mit gleicher Achse...
			// daher verändert sich die Listengröße nicht
			// dann wird genau in der Mitte gesplittet
			if (agents.size() > 0 && (agents.size() == left.size() || agents.size() == right.size())) {
				left.clear();
				right.clear();
				for (Coordinate agent : agents)
					if (agents.indexOf(agent) <= agents.size()/2)
						left.add(agent);
					else
						right.add(agent);
			}
			
	}

	/* Filtert eine Liste, so dass sie nur noch Agenten innerhalb
	 * eines vertikalen Streifens beinhaltet.
	 * Eingabe: Eine Liste von Agenten, Start und Breite des Streifens
	 * Ausgabe: Liste der Agenten innerhalb des Streifens
	 *    for (Agent a : agents) x-width <= a.x <= x+width
	 */
	List<Coordinate> keepOnlyInsideStrip(List<Coordinate> agents, double x, double width) {
		List<Coordinate> result = new ArrayList<Coordinate>();
		for (Coordinate agent : agents)
			if (x - width <= agent.x && agent.x <= x + width)
				result.add(agent);
		return result;
	}
	

	/* Ein Paar von Agenten */
	class Result {
		Coordinate a, b;

		double distance() { return a.distance(b); }
		Result(Coordinate a, Coordinate b) {
			this.a = a;
			this.b = b;
		}
	}

	/* Eine Hilfsfunktion um von zwei Paaren, das mit dem
	 * geringsten Abstand zueinander zu bestimmen.
	 */
	Result min(Result a, Result b) {
		if (a == null) return b;
		if (b == null) return a;
		return (a.distance() < b.distance()) ? a : b;
	}

	/* Findet die beiden Agenten mit dem geringsten Abstand
	 * zueinander in einer sortierten Liste.
	 * Eingabe: Eine sortierte Liste mit der Garantie, dass
	 *          das Paar mit dem geringsten Abstand nicht mehr
	 *          als sieben Schritte voneinander entfernt sind.
	 *          y.indexOf(output.a) - y.indexOf(output.b) <= 7
	 * Ausgabe: Die beiden Agenten mit dem geringsten Abstand zueinander
	 *
	 * HINWEIS: Dieser Algorithmus muss in O(n) Zeitschritten ablaufen.
	 */
	Result findClosestOfNext7(List<Coordinate> Y) {
		Result best = null;
		for (int i = 0; i < Y.size(); i++)
			for (int j = 1; j <= 7 && i+j < Y.size(); j++)
				best = min(best, new Result(Y.get(i), Y.get(i+j)));
		return best;
	}
	
	/* Findet die beiden Agenten mit dem geringsten Abstand zueinander.
	 * Eingabe: Eine Liste von Agenten, sortiert nach der X-Koordinate
	 *          Eine Liste derselben Agenten, sortiert nach der Y-Koordinate.
	 * Ausgabe: Die beiden Agenten mit dem geringsten Abstand zueinander
	 *
	 * HINWEIS: Dieser Algorithmus muss in O(n log(n)) Zeitschritten ablaufen.
	 */
	Result findClosest(List<Coordinate> X, List<Coordinate> Y) {
		
		// Rekursionsanker: Wenn 7+1 Punkte vorhanden sind, darf horizontal und vertikal geprüft werden
		if (X.size() <= 8) 			
			return findClosestOfNext7(Y);
		
		// Deklaration
		List<Coordinate> xL = new ArrayList<Coordinate>();
		List<Coordinate> xR = new ArrayList<Coordinate>();
		List<Coordinate> yL = new ArrayList<Coordinate>();
		List<Coordinate> yR = new ArrayList<Coordinate>();
		
		// Divide: Aufteilen des Systems in zwei Hälften
		double mid = X.get(X.size()/2).x; // Bestimmung des Medians
		partitionByXCoordinate(X, mid, xL, xR); // Trennen der X-Koordinaten am vertikalen Median
		partitionByXCoordinate(Y, mid, yL, yR); // Trennen der Y-Koordinaten am vertikalen Median
	
		// Conquer: minimalen Abstand aus linken und rechtem Ergebnis rekursiv ermitteln
		Result minDeltaPartition = min(findClosest(xL, yL),
				           findClosest(xR, yR));

		// Es könnte sein, dass an der vertikalen Linie noch ein dichteres Paar vorhanden ist
		Result minDeltaStrip = findClosestOfNext7(keepOnlyInsideStrip(Y, mid, minDeltaPartition.distance()));
		
		// Rückgabe
		return min(minDeltaStrip, minDeltaPartition);
		
	}

	/* Findet die beiden Agenten mit dem geringsten Abstand
	 * zueinander in einer beliebigen Liste.
	 * Eingabe: Eine Liste von Agenten
	 * Ausgabe: Die beiden Agenten mit der geringsten .distance()
	 *
	 * HINWEIS: Dieser Algorithmus muss in O(n log(n)) Zeitschritten ablaufen.
	 */
	Result findClosest(List<Coordinate> agents) {
				
		// Listen kopieren
		List<Coordinate> x = new ArrayList<Coordinate>(agents);
		List<Coordinate> y = new ArrayList<Coordinate>(agents);
		
		// Listen sortieren
		sortByXCoordinate(x);		
		sortByYCoordinate(y);
		
		// Berechnung des Ergebnisses
		return findClosest(x, y);
		
	}


}
