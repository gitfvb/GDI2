/* Änderungen nur an dieser Datei durchführen. Die vollständige Datei
 * nach https://www.dvs.tu-darmstadt.de/teaching/inf2/2009-prak/ hochladen.
 */
import java.util.*;

public class CounterIntelligence {
	/* Eine AVL-Baum Implementierung, die Intervalle speichert. */
	public class Tree {
		/* Ein Baumknoten hat linke und rechte Kinder (die null sein könnten!) */
		public Tree left, right;

		/* Das Intervall das in diesem Knoten gespeichert wird.
		 * Das Feld 'start' wird als Primarschlüssel für die 
		 *   Sortierung der Knoten im Baum benutzt.
		 * Das Feld 'end' ist zunächst nur Zusatzinformation.
		 */
		public Interval interval;

		/* Der Agent auf den sich der gespeicherte Zeitintervall dieses
		 * Knotens bezieht. Dies wird als Sekundärschlüssel benutzt.
		 */
		public String agent;

		/* Die AVL-Baum Höhe (Schritt 2).
		 * Ein leerer Baum (null) hat eine Höhe von 0.
		 * Ein Baum mit einem Element hat eine Höhe von 1.
		 */
		public int height;

		/* Der größte 'end' Wert in diesem Teilbaum (Schritt 3).
		 * Diese Information kann benutzt werden, um effizient zu ermitteln,
		 * ob ein Intervall überlappt.
		 */
		public int biggest_end;

		public Tree(Interval x, String y) {
			interval = x;
			agent = y;
			height = 1;
			biggest_end = x.end;
		}
	}
	
	/* Fürge einen neuen Knoten in den Baum ein.
	 * Eingabe: Die Wurzel des Baums und den Intervall + Agent des neuen Knoten.
	 * Ausgabe: Die neue Wurzel des Baums.
	 * In Schritt 1 soll die Funktion sicherstellen, dass die Baumsortierung
	 * durch 'start' und 'agent' eingehalten wird.
	 * In Schritt 2 soll die Funktion die Baumhöhe aktualisieren und die
	 * Baumbalance instand halten.
	 * In Schritt 3 soll die Funktion biggest_end aktuell halten.
	 */
	public Tree add(Tree node, Interval x, String agent) {
		
		// Knoten noch gar nicht besetzt... anlegen!
		if (node == null) {
			node = new Tree(x, agent);
		
		// rekursiver Aufruf zur Besetzung des Baumes
		} else {
			if (x.start < node.interval.start) 
				node.left = this.add(node.left, x, agent);
			else if (x.start > node.interval.start)
				node.right = this.add(node.right, x, agent);
			else if (agent.compareTo(node.agent) < 0)
				node.left = this.add(node.left, x, agent);
			else if (agent.compareTo(node.agent) > 0)
				node.right = this.add(node.right, x, agent);
		}
		
		return this.updateNode(node);
	}

	/* Eine Hilfsfunktion zur Bestimmung der Höhe eines
	 * (möglicherweise leeren) Unterbaums. */
	int height(Tree node) {
		return (node == null) ? 0 : node.height;
	}

	/* Eine Hilfsfunktion, um die Höhe eines Knoten zu aktualisieren.
	 * Setzt die Höhe auf max(left, right)+1.
	 */
	void updateHeight(Tree node) {
		node.height = Math.max(this.height(node.left),
							   this.height(node.right)) + 1;
	}

	/* Dreht den Baum nach links, aktualisiert Unterbäume und Baumhöhe.
	 *
	 *       A                     B
	 *      / \                   / \
	 *     x   B        =>       A   z
	 *        / \               / \
	 *       y   z             x   y
	 */
	public Tree rotateLeft(Tree node) {
		
		// Rotation
		Tree n = node.right; 	// rechten Knoten anheben
		node.right = n.left; 	// Knoten umhängen
		n.left = node; 			// Wurzel nach links absenken
		
		// Aktualisierung der Attribute
		updateHeight(n.left);	// Höhe des linken Knotens aktualisieren
		updateHeight(n);		// Höhe der Wurzel aktualisieren
		updateBiggest(n.left);
		updateBiggest(n);
		
		// Rückgabe
		return n;
	}

	/* Dreht den Baum nach rechts, aktualisiert Unterbäume und Baumhöhe.
	 *
	 *       B                     A
	 *      / \                   / \
	 *     A   z        =>       x   B
	 *    / \                       / \
	 *   x   y                     y   z
	 */
	public Tree rotateRight(Tree node) {
		
		// Rotation
		Tree n = node.left; 	// linken Knoten anheben
		node.left = n.right;	// Knoten umhängen
		n.right = node;			// Wurzel nach rechts absenken
		
		// Aktualisierung der Attribute
		updateHeight(n.right);	// Höhe des rechten Knotens aktualisieren
		updateHeight(n);		// Höhe der Wurzel aktualisieren
		updateBiggest(n.right);
		updateBiggest(n);
		
		// Rückgabe
		return n;
	}

	/* Eine Hilfsfunktion um die Baumbalance in einem Knoten zu berechnen.
	 * Liefert die Höhe des rechten Unterbaums minus der Höhe des linken Unterbaums.
	 */
	int balance(Tree node) {
		return height(node.right) - height(node.left);
	}

	/* Eine Hilfsfunktion um den Baum zu balancieren.
	 * Eingabe: Ein möglicherweise unbalancierter Unterbaum.
	 * Ausgabe: Ein Unterbaum mit balance(node) gleich -1, 0, oder 1. 
	 */
	Tree rebalance(Tree node) {
		/*
		 * balance = rechts - links
		 * wenn = -2 -> rechtsrotation
		 * wenn = +2 -> linksrotation
		 * Fall sich Vorzeichen des Knotens und der Kinder unterscheiden,
		 * ist eine Doppelrotation notwendig.
		 * 
		 * Ist die Balance < -2 oder > +2, wird rekursiv runtergewandert,
		 * bis -2 oder +2 erreicht wird.
		 * 
		 */
		
		// Rotationen (Einzel- und Doppelrotationen)
		switch (balance(node)) {
		case -2:
			if (balance(node.left) == 1) // falls sich Vorzeichen unterscheiden, wird hier eine Doppelrotation durchgeführt
				node.left = this.rotateLeft(node.left);
			node = this.rotateRight(node);
			break;
		
		case 2:
			if (balance(node.right) == -1)  // falls sich Vorzeichen unterscheiden, wird hier eine Doppelrotation durchgeführt
				node.right = this.rotateRight(node.right);
			node = this.rotateLeft(node);
			break;
		}
		
		
		// Rekursiver Aufruf
		if (balance(node) < -2)
			if (node.right != null) // nur, wenn der rechte Knoten auch existiert
				node.right = this.rebalance(node.right);
		
		if (balance(node) > 2)	
			if (node.left != null) // nur, wenn der linke Knoten auch existiert
				node.left = this.rebalance(node.left);
		
		// Rückgabe
		return node;
	}
	
	/* Eine Hilfsfunktion um biggest_end zu aktualisieren.
	 * Eingabe: Ein Baumknoten mit aktualisierten Kindern.
	 * Ausgabe: biggest_end im Knoten hat den korrekten Wert.
	 */
	public void updateBiggest(Tree node) {
		
		// maximal-Wert der Kinder bilden
		int biggestChild = Math.max((node.left == null) ? 0 : node.left.biggest_end, 
								    (node.right == null) ? 0 : node.right.biggest_end);
		// jetzt den maximal-Wert der Kinder mit dem eigenen Interval-Ende vergleichen
		node.biggest_end = Math.max(biggestChild, node.interval.end);
	}

	/* Java kann keine zwei Werte zurückliefern. Deswegen diese Klasse. */ 
	class RemoveResult {
		public Tree removed;
		public Tree node;
		
		public RemoveResult(Tree a, Tree b) {
			removed = a;
			node = b;
		}
	}
	
	/* Entfernt den kleinsten Knoten aus dem angegebenen Unterbaum.
	 * Eingabe: Ein nicht-null-Knoten.
	 * Ausgabe: out.removed war das kleinste Kind des Unterbaums.
	 *          out.node ist der neue Unterbaum ohne dieses Kind.
	 */
	RemoveResult removeSmallest(Tree node) {
		RemoveResult rr = null;
		
		// Links ist kein Knoten vorhanden, es kann keinen kleineren Knoten als den Aktuellen.
		if (node.left == null) {
			return new RemoveResult(node, node.right);
		
		// Links ist ein Knoten vorhanden -> rekursiv nach links runterwandern
		} else {
			rr = this.removeSmallest(node.left);
			rr.node = node;
			if (rr.node == null) 	
				rr.node.left = null;
		}
		
		return rr;
	}

	/* Entfernt das Alibi eines Agenten.
	 * Eingabe: Ein Unterbaum, der genau ein Knoten mit
	 *          node.interval.start == x.start und node.agent == agent hat.
	 * Ausgabe: Ein Unterbaum ohne dieses Alibi.
	 */
	public Tree remove(Tree node, Interval x, String agent) {
		
		// gewünschten Knoten raussuchen (dieser wird gelöscht)
		Tree z = this.find(node, x, agent);
		
		/*
		 * Der Knoten a ist entweder der Eingabeknoten z (falls z höchstens ein Kind hat)
		 * oder der Nachfolger von z (falls z zwei Kinder hat).
		 */
		Tree a;
		if (z.left == null || z.right == null)
			a = z;
		else
			a = this.removeSmallest(z.right).removed;
		
		
		/*
		 * b wird auf das Kind von a gesetzt, das nicht null ist, oder auf null,
		 * falls a keine Kinder hat. 
		 */
		Tree b = null;
		if (a.left != null)
			b = a.left;
		else
			b = a.right;
		
		/*
		 * Nun wird der Knoten ausgeschnitten und an die Position des alten Knotens gesetzt.
		 */
		Tree e = this.getParent(node, a.interval, a.agent);
		if (e == null) {
			node = b;
		} else {
			if (a == e.left)
				e.left = b;
			else
				e.right = b;
		}
		
		/*
		 * Abschließend werden die Schlüssel von a nach z kopiert,
		 * falls a der ausgeschnittene Nachfolger von z war.
		 */
		if (a != z) {
			z.agent = a.agent;
			z.interval = a.interval;
		}
		
		// Attribute des Suchbaumes aktualisieren (auf Grundlage des vorher ermittelten Elternknotens)
		if (e != null)
			node = this.updateTree(node, e.interval, e.agent);
		
		// Rückgabe des Baumes ohne den gelöschten Knoten
		return node;
	
	}
	
	/**
	 * Eine kleine Hilfsfunktion, um bei einem einzelnen Knoten die Attribute zu aktualisieren:
	 * - Höhe
	 * - Balance
	 * - Biggest-End
	 * 
	 * @param node 		Knoten, der aktualisiert werden soll
	 * @return			Aktualisierter Knoten, versorgt mit frischen Attributen
	 */
	public Tree updateNode(Tree node) {
		if (node != null) {
			updateHeight(node); 			// Knotenhöhe aktualisieren
			node = this.rebalance(node); 	// Balance wiederherstellen
			updateBiggest(node);			// Biggest aktualisieren
		}
		return node;
	}
	
	
	/**
	 * Selbst geschriebene Hilfsmethode, um bei einen ganzen Baum bis zu einem gewünschten Interval/Agenten
	 * die Attribute zu aktualisieren. Ruft sich selbst rekursiv auf, bis der gewünschte Knoten gefunden ist
	 * und benutzt dann beim Rückweg vom Knoten bis zur Wurzel die Methode updateNode.
	 * 
	 * @param node 		binärer Suchbaum
	 * @param x			gesuchtes Interval
	 * @param agent		gesuchter Agent
	 * @return			gibt den gesamten Suchbaum wieder mit aktualisierten Attributen zurück
	 */
	public Tree updateTree(Tree node, Interval x, String agent) {
		
		// Null-Knoten wird sofort zurückgegeben
		if (node == null) {
			return node;
		}
			
		// rekursives durchlaufen durch den Baum
		if (x.start < node.interval.start) 
			node.left = this.updateTree(node.left, x, agent);
		else if (x.start > node.interval.start)
			node.right = this.updateTree(node.right, x, agent);
		else if (agent.compareTo(node.agent) < 0)
			node.left = this.updateTree(node.left, x, agent);
		else if (agent.compareTo(node.agent) > 0)
			node.right = this.updateTree(node.right, x, agent);	
		
		// Rückgabe
		return this.updateNode(node);		
	}

	
	/**
	 * Selbst geschriebene Hilfsfunktion, um einen Knoten zu finden. Um Redundanzen zu vermeiden,
	 * wird zuerst das Elternelement mit einer anderen Methode gesucht und dann das entsprechende
	 * Kind-Element zurückgegeben.
	 * 
	 * @param node 		binärer Suchbaum
	 * @param x			gesuchtes Interval
	 * @param agent		gesuchter Agent
	 * @return			gesuchter Knoten als Suchbaum
	 */
	public Tree find(Tree node, Interval x, String agent) {
		
		// Vater des gesuchten Knotens finden (aus Zwecken der Code-Reduzierung)
		Tree n = this.getParent(node, x, agent);
		
		if (n != null) {
		
			// gesuchter Knoten ist das linke Kind		
			if (n.left != null)  
				if (n.left.interval.start == x.start && n.left.agent.equals(agent))
					return n.left;
			
			// gesuchter Knoten ist das rechte Kind
			if (n.right != null) 
				if (n.right.interval.start == x.start && n.right.agent.equals(agent))
					return n.right;
			}
		
		return node; // gesuchter Knoten ist die Wurzel

	}
	
	/**
	 * Selbst geschriebene Hilfsfunktion, um den Elternknoten eines gesuchten Knotens zu finden
	 * 
	 * @param node 		binärer Suchbaum
	 * @param x			gesuchtes Interval
	 * @param agent		gesuchter Agent
	 * @return			Elternknoten des gesuchten Kindes als Suchbaum
	 */
	public Tree getParent(Tree node, Interval x, String agent) {
		
		// wenn eins der Kinder dem gesuchten Wert entspricht
		if (node.left != null)
			if (node.left.interval.start == x.start && node.left.agent.equals(agent))
				return node;
		if (node.right != null)
			if (node.right.interval.start == x.start && node.right.agent.equals(agent))
				return node;
	
		// rekursive Suche nach dem gewünschten Knoten
		if (x.start < node.interval.start) 
			return this.getParent(node.left, x, agent);
		else if (x.start > node.interval.start)
			return this.getParent(node.right, x, agent);
		else if (agent.compareTo(node.agent) < 0)
			return this.getParent(node.left, x, agent);
		else if (agent.compareTo(node.agent) > 0)
			return this.getParent(node.right, x, agent);
		else
			return null;

	}
	
	/* Bestimmt die Agenten, die ein Alibi für einen Sicherheitsvorfall (breach) haben.
	 * Eingabe: Die Wurzel des Baums und der Zeitraums des Vorfalls.
	 * Ausgabe: Die Namen der Agenten, deren Alibi-Zeiträume sich mit dem Vorfall überlappen.
	 */
	public void innocentAgents(Tree node, Interval breach, Set<String> output) {
	
		// Knoten ist null -> gar nichts machen
		if (node != null) {
			
			// Knoten überlappt -> einfügen
			if (node.interval.overlaps(breach)) 
				output.add(node.agent);
			
			// Interval-Ende ist bereits kleiner als breach -> Rekursion beenden
			if (node.biggest_end >= breach.start)
				this.innocentAgents(node.left, breach, output);
			// Interval-Start is bereits größer als breach -> Rekursion beenden
			if (node.interval.start <= breach.end)
				this.innocentAgents(node.right, breach, output);
			
		}
	}
	
	
}
