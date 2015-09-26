/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST
 * Place all of your implementation into CounterIntelligence.java.
 */
import junit.framework.TestCase;
import java.util.*;

public class Step1Test_4 extends TestCase {
	CounterIntelligence c;
	
	public void setUp() {
		c = new CounterIntelligence();
	}
	
	CounterIntelligence.Tree checkSortOrder(CounterIntelligence.Tree low, CounterIntelligence.Tree node) {
		if (node == null) return low;
		CounterIntelligence.Tree largest = checkSortOrder(low, node.left);
		assertTrue("Tree is in sorted order", 
				largest == null ||
				largest.interval.start < node.interval.start ||
				(largest.interval.start == node.interval.start &&
				 largest.agent.compareTo(node.agent) <= 0));
		return checkSortOrder(node, node.right);
	}
	
	void sortOrder(CounterIntelligence.Tree node) {
		checkSortOrder(null, node);
	}
	
	int size(CounterIntelligence.Tree node) {
		if (node == null) return 0;
		return 1 + size(node.left) + size(node.right);
	}
	
	boolean contains(CounterIntelligence.Tree node, Interval x, String agent) {
		if (node == null) return false;
		if (x.start < node.interval.start) 
			return contains(node.left, x, agent);
		else if (x.start > node.interval.start)
			return contains(node.right, x, agent);
		else if (agent.compareTo(node.agent) < 0)
			return contains(node.left, x, agent);
		else if (agent.compareTo(node.agent) > 0)
			return contains(node.right, x, agent);
		else
			return true;
	}	
	
	public void testTwo() {
		CounterIntelligence.Tree node;
		node = null;
		node = c.add(node, new Interval(4, 5), "bob");
		node = c.add(node, new Interval(5, 6), "bob");
		assertEquals("two nodes", 2, size(node));
		sortOrder(node);
		node = null;
		node = c.add(node, new Interval(5, 6), "bob");
		node = c.add(node, new Interval(4, 5), "bob");
		assertEquals("two nodes", 2, size(node));
		sortOrder(node);
	}
	
	public void testStartMattersMost() {
		CounterIntelligence.Tree node;
		node = null;
		node = c.add(node, new Interval(4, 7), "karl");
		node = c.add(node, new Interval(5, 6), "bob");
		assertEquals("two nodes", 2, size(node));
		sortOrder(node);
	}
	
	public void testAgentMattersToo() {
		CounterIntelligence.Tree node;
		node = null;
		node = c.add(node, new Interval(5, 7), "karl");
		node = c.add(node, new Interval(5, 6), "bob");
		assertEquals("two nodes", 2, size(node));
		sortOrder(node);
		node = null;
		node = c.add(node, new Interval(5, 6), "bob");
		node = c.add(node, new Interval(5, 7), "karl");
		assertEquals("two nodes", 2, size(node));
		sortOrder(node);
	}
	
	public void testThreeDeepLeft() {
		CounterIntelligence.Tree node;
		node = null;
		node = c.add(node, new Interval(6, 7), "bob");
		node = c.add(node, new Interval(5, 6), "bob");
		node = c.add(node, new Interval(4, 5), "bob");
		assertEquals("three nodes", 3, size(node));
		sortOrder(node);
	}
	
	public void testThreeBalanced() {
		CounterIntelligence.Tree node;
		node = null;
		node = c.add(node, new Interval(5, 6), "bob");
		node = c.add(node, new Interval(4, 5), "bob");
		node = c.add(node, new Interval(6, 7), "bob");
		assertEquals("three nodes", 3, size(node));
		sortOrder(node);
	}
	
	public void testZigZag() {
		CounterIntelligence.Tree node;
		node = null;
		node = c.add(node, new Interval(4, 5), "bob");
		node = c.add(node, new Interval(6, 7), "bob");
		node = c.add(node, new Interval(5, 6), "bob");
		assertEquals("three nodes", 3, size(node));
		sortOrder(node);
	}
	
	public void testRandom() {
		MTRandom4 rand = new MTRandom4(42);
		for (int i = 1; i <= 300; ++i) {
			List<Interval> ivs = new ArrayList<Interval>();
			for (int j = 0; j < 5000; ++j) {
				int x = rand.nextInt(100000), y = rand.nextInt(100000);
				if (x > y) ivs.add(new Interval(y, x)); else ivs.add(new Interval(x, y));
			}
			CounterIntelligence.Tree tree = null;
			for (Interval x : ivs) tree = c.add(tree, x, "bob");
			sortOrder(tree);
			for (Interval x : ivs) assertTrue("Interval is present", contains(tree, x, "bob"));
		}
	}
}
