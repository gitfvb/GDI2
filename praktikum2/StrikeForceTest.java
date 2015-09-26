/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST.
 * Place all of your implementation into StrikeForce.java.
 */
import junit.framework.TestCase;
import java.util.*;

public class StrikeForceTest extends TestCase {
	StrikeForce sf;	
	
	public void setUp() {
		sf = new StrikeForce();
		Agents.setUp();
	}
	
	void debugSolution(Map<String, Boolean> solution) {
		for (String agent : solution.keySet())
			System.out.print(agent + " = " + solution.get(agent) + "\n");
		System.out.print("---\n");
	}
	
	String constraintsMet(Map<String, Boolean> solution, List<String> constraints) {
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < constraints.size(); i += 2) {
			String agentAWithValue = constraints.get(i);
			String agentBWithValue = constraints.get(i+1);
			String agentA = Agents.name(agentAWithValue);
			String agentB = Agents.name(agentBWithValue);
			Boolean truthA = Agents.value(agentAWithValue);
			Boolean truthB = Agents.value(agentBWithValue);
			if (solution.get(agentA) != truthA &&
				solution.get(agentB) != truthB)
				output.append(agentAWithValue + " or " + agentBWithValue + " is not satisfied.\n");
		}
		return output.toString();
	}
	
	String allAgentsOk(Map<String, Boolean> solution, List<String> constraints) {
		Set<String> agents = new HashSet<String>();
		
		for (String agentWithValue : constraints)
			agents.add(Agents.name(agentWithValue));
		
		for (String agent : solution.keySet())
			if (!agents.contains(agent))
					return agent + " is not an agent;\n";
		
		for (String agent : agents)
			if (solution.get(agent) == null)
					return agent + " is missing from solution;\n";
					
		return constraintsMet(solution, constraints);
	}
	
	public void testEmpty() {
		String[] constraints = {};
		List<String> list = Arrays.asList(constraints);
		assertEquals("empty", "", allAgentsOk(sf.solve(list), list));
	}
	
	public void testOneOf() {
		String[] constraints = { "Bob", "Jim" };
		List<String> list = Arrays.asList(constraints);
		assertEquals("one of", "", allAgentsOk(sf.solve(list), list));
	}
	
	public void testOneAway() {
		String[] constraints = { "!Bob", "!Jim" };
		List<String> list = Arrays.asList(constraints);
		assertEquals("one away", "", allAgentsOk(sf.solve(list), list));
	}
	
	public void testImplication1() {
		String[] constraints = { "!Bob", "Jim" };
		List<String> list = Arrays.asList(constraints);
		assertEquals("implication1", "", allAgentsOk(sf.solve(list), list));
	}
	
	public void testImplication2() {
		String[] constraints = { "Bob", "!Jim" };
		List<String> list = Arrays.asList(constraints);
		assertEquals("implication2", "", allAgentsOk(sf.solve(list), list));
	}
	
	public void testTautology() {
		String[] constraints = { "Bob", "!Bob" };
		List<String> list = Arrays.asList(constraints);
		assertEquals("tautology", "", allAgentsOk(sf.solve(list), list));
	}
	
	public void testForcedTruth() {
		String[] constraints = { "Bob", "Bob" };
		List<String> list = Arrays.asList(constraints);
		assertEquals("forced truth", "", allAgentsOk(sf.solve(list), list));
	}
	
	public void testForcedFalse() {
		String[] constraints = { "!Bob", "!Bob" };
		List<String> list = Arrays.asList(constraints);
		assertEquals("forced false", "", allAgentsOk(sf.solve(list), list));
	}
	
	public void testIndirectForcing() {
		String[] constraints = { "Bob", "Jim", "!Bob", "Jim" };
		List<String> list = Arrays.asList(constraints);
		assertEquals("indirect forcing", "", allAgentsOk(sf.solve(list), list));
	}
	
	public void testIndirectForceChain() {
		String[] constraints = { "Bob", "Jim", "!Bob", "Jim", "!Jim", "Bob" };
		List<String> list = Arrays.asList(constraints);
		assertEquals("indirect force chain", "", allAgentsOk(sf.solve(list), list));
	}
	
	public void testImpossible1() {
		String[] constraints = { "Bob", "Bob", "!Bob", "!Bob" };
		List<String> list = Arrays.asList(constraints);
		assertNull("impossible1", sf.solve(list));
	}
	
	public void testImpossible2() {
		String[] constraints = { "Bob", "Jim", "Bob", "!Jim", "!Bob", "Jim", "!Bob", "!Jim" };
		List<String> list = Arrays.asList(constraints);
		assertNull("impossible2", sf.solve(list));
	}
	
	public void testRandom() {
		Random r = new MTRandom2(42);
		int impossible = 0;
		for (int i = 0; i < 10000; ++i) {
			List<String> constraints = new ArrayList<String>();
			for (int j = 0; j < 26; ++j) {
				constraints.add((r.nextBoolean() ? "!" : "") + Agents.get(r.nextInt(26)));
				constraints.add((r.nextBoolean() ? "!" : "") + Agents.get(r.nextInt(26)));
			}
			Map<String, Boolean> solution = sf.solve(constraints);
/*
			for (String c : constraints)
				System.out.print(" " + c);
			System.out.print("\n");
*/
			if (solution != null) {
				// debugSolution(solution);
				assertEquals("random solution is wrong", "", allAgentsOk(solution, constraints));
			} else {
				++impossible;
			}
		}
		assertEquals("too many random constraints rejected", 502, impossible);
	}
	
	public void testBig() {
		Random r = new MTRandom2(42);
		int impossible = 0;
		for (int i = 0; i < 50; ++i) {
			List<String> constraints = new ArrayList<String>();
			for (int j = 0; j < 10000; ++j) {
				constraints.add((r.nextBoolean() ? "!" : "") + Integer.toString(r.nextInt(10000)));
				constraints.add((r.nextBoolean() ? "!" : "") + Integer.toString(r.nextInt(10000)));
			}
			Map<String, Boolean> solution = sf.solve(constraints);
/*
			for (String c : constraints)
				System.out.print(" " + c);
			System.out.print("\n");
*/
			if (solution != null) {
				// debugSolution(solution);
				assertEquals("big random solution is wrong", "", allAgentsOk(solution, constraints));
			} else {
				++impossible;
			}
		}
		assertEquals("too many big random constraints rejected", 8, impossible);
	}
}
