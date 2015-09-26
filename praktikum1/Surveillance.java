/* Änderungen nur an dieser Datei durchführen. Die vollständige Datei
 * nach https://www.dvs.tu-darmstadt.de/teaching/inf2/2009-prak/ hochladen.
 */
import java.util.*;

public class Surveillance {

	private int satLimit = 3;

	/*
	 * Ein Event markiert eine Änderung der Satellitenverfügbarkeit. Wenn
	 * satEntersRange true ist, hat der Satellit das Zielgebiet erreicht. Wenn
	 * der Wert false ist, hat der Satellit das Zielgebiet verlassen.
	 */
	public class Event implements Comparable<Event> {
		boolean satEntersRange;
		int time;

		public Event(int t, boolean s) {
			time = t;
			satEntersRange = s;
		}

		/*
		 * Eine Vergleichsfunktion zur Sortierung der Events. Sollte folgendes
		 * zurück geben: -1 wenn this < o 0 wenn this = o 1 wenn this > o
		 * 
		 * Hinweis: time ist der primäre Sortierschlüssel, aber für zwei
		 * Events mit der selben Zeit, werden Enter-Events vor Leave-Events
		 * einsortiert; sie sind also kleiner. (Das erleichtert die späteren
		 * Schritte.)
		 */
		public int compareTo(Event o) {
			int i = 0;
			if (this.time > o.time)
				i++;
			else if (this.time < o.time)
				i--;
			else {
				if (this.satEntersRange != o.satEntersRange) {
					if (this.satEntersRange)
						i--;
					else
						i++;
				}
			}
			return i;
		}
	}

	/*
	 * Konvertiert eine Satellitenintervallliste in eine Event-Liste. Eingabe:
	 * Eine Map von Satelliten-Transponder-IDs zu einer Gruppe von Intervalls zu
	 * denen sie verfügbar sind. Ausgabe: Eine sortierte Liste von Enter- und
	 * Leave-Events.
	 */
	public List<Event> satEvents(Map<String, Collection<Interval1>> satellites) {
		ArrayList<Event> listofEvents = new ArrayList<Event>();
		for (String strSat : satellites.keySet()) {
			for (Interval1 i : satellites.get(strSat)) {
				listofEvents.add(this.new Event(i.start, true));
				listofEvents.add(this.new Event(i.end, false));
			}
		}
		Collections.sort(listofEvents);
		return listofEvents;
	}

	/*
	 * Bestimmt alle Intervalle mit ausreichender Satellitenabdeckung. Eingabe:
	 * Eine Map von Satelliten-Transponder-IDs zu einer Gruppe von Intervalls zu
	 * denen sie verfügbar sind. Ausgabe: Alle Intervalle in denen >= 3
	 * Satelliten verfügbar sind. Hinweis: { { 5, 6 }, { 6, 7 } } sollte nicht
	 * zurück gegeben werden. { { 5, 7 } } wäre die korrekte Ausgabe. { { 6, 6
	 * } } sollte nicht zurück gegeben werden (leerer Intervall). { } wäre die
	 * korrekte Ausgabe.
	 */
	public List<Interval1> satCoverage(
			Map<String, Collection<Interval1>> satellites) {

		ArrayList<Interval1> listInterval = new ArrayList<Interval1>();
		if (satellites.size() >= this.satLimit) {
			List<Event> listofEvents = this.satEvents(satellites);

			int satCount = 0;
			int satStart = 0;
			int lastEvent = 0;
			for (Event e : listofEvents) {

				// Eine neue Zahl beginnt... checken, ob Start-Event gesetzt
				// wird.
				if (satCount >= this.satLimit && satStart == 0
						&& e.time > lastEvent)
					satStart = lastEvent;

				// hoch- und runterz�hlen der aktuell verf�gbaren Satelliten
				if (e.satEntersRange)
					satCount++;
				else
					satCount--;

				// Ende f�r Interval setzen und in Liste abspeichern
				if (satCount < this.satLimit && satStart > 0
						&& e.time > satStart) {
					listInterval.add(new Interval1(satStart, e.time));
					satStart = 0;
				}

				// Speichern des letzten Events
				lastEvent = e.time;

			}
		}
		return listInterval;
	}

	/*
	 * Bestimmt den längsten Intervall mit ausreichender Satellitenabdeckung.
	 * Eingabe: Eine Map von Satelliten-Transponder-IDs zu einer Gruppe von
	 * Intervalls zu denen sie verfügbar sind. Ausgabe: Der längste Intervall
	 * in dem >= 3 Satelliten verfügbar sind. Gibt null zurück, wenn kein
	 * Intervall gefunden wurde.
	 */
	public Interval1 longestSatCoverage(
			Map<String, Collection<Interval1>> satellites) {
		Interval1 interval = null;
		List<Interval1> listInterval = this.satCoverage(satellites);
		if (listInterval.size() > 0) {
			for (Interval1 i : listInterval) {
				if (interval == null)
					interval = i;
				if (i.size() > interval.size())
					interval = i;
			}
		}
		return interval;
	}

	/*
	 * Prüft, ob der Missionszeitraum ausreichende Satellitenabdeckung besitzt.
	 * Eingabe: Der Missionszeitraum und die verfügbaren Satelliten. Ausgabe:
	 * Ob es möglich ist, den gegebenen Zeitraum mit den gegebenen Satelliten
	 * ausreichend abzudecken.
	 */
	boolean intervalCovered(Interval1 mission,
			Map<String, Collection<Interval1>> satellites) {
		List<Interval1> interval = this.satCoverage(satellites);
		for (Interval1 i : interval) {
			if (i.isEqual(mission) || i.contains(mission)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Berechnet die minimale Satellitenzusammenstellung, die für die Mission
	 * benötigt wird. Eingabe: Der Missionszeitraum und die verfügbaren
	 * Satelliten. Ausgabe: Die Namen der Satelliten der minimalen
	 * Zusammenstellung. Gibt null zurück, wenn keine ausreichende Abdeckung
	 * möglich ist.
	 */
	public Set<String> fewestSatellites(Interval1 mission,
			Map<String, Collection<Interval1>> sats) {

		Set<String> minimal = null;
		Set<String> satellites_temp = null;
		Map<String, Collection<Interval1>> satsTemp = new HashMap<String, Collection<Interval1>>(
				sats);
		int minLength = 0;

		if (sats.size() >= this.satLimit) {
			if (intervalCovered(mission, sats)) {

				minimal = new HashSet<String>(); // erst jetzt initialisieren
				satellites_temp = new HashSet<String>();

				for (String s : sats.keySet()) {

					// Entfernen des aktuellen Satelliten
					satsTemp.remove(s);

					// rekursiver Aufruf zur Suche
					minLength = minimal.size();
					satellites_temp = this.fewestSatellites(mission, satsTemp);

					// L�sung wurde rekursiv gefunden
					if (satellites_temp != null) {
						minimal = satellites_temp;
						// Schleife kann abgebrochen werden, aktuelle L�sung ist
						// eh schon zu gro�
						if (minLength > satellites_temp.size())
							break;
					} else {
						// satellit wird ben�tigt zur Minimall�sung erg�nzen
						minimal.add(s);
					}

					// Satellit wieder erg�nzen
					satsTemp.put(s, sats.get(s));

				}
			}
		}
		return minimal;
	}

}
