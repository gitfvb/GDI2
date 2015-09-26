/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST.
 * Place all of your implementation into StrikeForce.java.
 */
import java.util.ArrayList;
import java.util.List;

public class Agents {	
	static List<String> agents;
	
	static public String name(String agentWithValue) {
		if (agentWithValue.charAt(0) == '!')
			return agentWithValue.substring(1);
		else
			return agentWithValue;
	}
	
	static public Boolean value(String agentWithValue) {
		return agentWithValue.charAt(0) != '!';
	}
	
	static public String get(int i) {
		return agents.get(i);
	}
	
	static public void setUp() {
		agents = new ArrayList<String>();
		agents.add("Amy");
		agents.add("Barney");
		agents.add("Charles");
		agents.add("Dolores");
		agents.add("Ethan");
		agents.add("Fred");
		agents.add("George");
		agents.add("Hugo");
		agents.add("Irene");
		agents.add("Joline");
		agents.add("Keith");
		agents.add("Lara");
		agents.add("Mike");
		agents.add("Nathan");
		agents.add("Opera");
		agents.add("Peter");
		agents.add("Q"); // Of Star Trek fame.
		agents.add("Roger");
		agents.add("Samuel");
		agents.add("Tamy");
		agents.add("Uther");
		agents.add("Victoria");
		agents.add("Wesley");
		agents.add("Xavier");
		agents.add("Young");
		agents.add("Zeus");
	}
}
