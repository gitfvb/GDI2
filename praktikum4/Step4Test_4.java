/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST
 * Place all of your implementation into CounterIntelligence.java.
 */
import junit.framework.TestCase;
import java.util.*;

public class Step4Test_4 extends TestCase {
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
	
	int checkHeight(CounterIntelligence.Tree node) {
		if (node == null) return 0;
		int heightLeft  = checkHeight(node.left);
		int heightRight = checkHeight(node.right);
		int height = (heightLeft>heightRight?heightLeft:heightRight)+1;
		assertTrue("height is correct", node.height == height);
		return height;
	}
	
	void checkBalanced(CounterIntelligence.Tree node) {
		if (node == null) return;
		checkBalanced(node.left);
		int heightLeft = (node.left == null) ? 0 : node.left.height;
		int heightRight = (node.right == null) ? 0 : node.right.height;
		int balance = heightLeft - heightRight;
		assertTrue("balance_factor is -1, 0, or 1", balance <= 1 && balance >= -1);
		checkBalanced(node.right);
	}
	
	int checkBiggestEnd(CounterIntelligence.Tree node) {
		if (node == null) return Integer.MIN_VALUE;
		int biggest  = node.interval.end; 
		int bigLeft  = checkBiggestEnd(node.left);
		int bigRight = checkBiggestEnd(node.right);
		if (bigLeft  > biggest) biggest = bigLeft;
		if (bigRight > biggest) biggest = bigRight;
		assertTrue("biggest is correct", node.biggest_end == biggest);
		return biggest;
	}
	
	void check(CounterIntelligence.Tree node) {
		checkSortOrder(null, node);
		checkHeight(node);
		checkBiggestEnd(node);
		checkBalanced(node);
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
	
	public void testOne() {
		CounterIntelligence.Tree tree = null;
		tree = c.add(tree, new Interval(3, 6), "bob");
		tree = c.remove(tree, new Interval(3, 6), "bob");
		assertEquals("empty tree", null, tree);
	}
	
	public void testRemoveLeft() {
		CounterIntelligence.Tree tree = null;
		tree = c.add(tree, new Interval(3, 6), "bob");
		tree = c.add(tree, new Interval(4, 6), "bob");
		tree = c.add(tree, new Interval(5, 6), "bob");
		tree = c.remove(tree, new Interval(3, 6), "bob");
		check(tree);
		assertEquals("two left", 2, size(tree));
	}
	
	public void testRemoveRight() {
		CounterIntelligence.Tree tree = null;
		tree = c.add(tree, new Interval(3, 6), "bob");
		tree = c.add(tree, new Interval(4, 6), "bob");
		tree = c.add(tree, new Interval(5, 6), "bob");
		tree = c.remove(tree, new Interval(5, 6), "bob");
		check(tree);
		assertEquals("two left", 2, size(tree));
	}
	
	public void testRemoveRoot() {
		CounterIntelligence.Tree tree = null;
		tree = c.add(tree, new Interval(3, 6), "bob");
		tree = c.add(tree, new Interval(4, 6), "bob");
		tree = c.add(tree, new Interval(5, 6), "bob");
		tree = c.remove(tree, new Interval(4, 6), "bob");
		check(tree);
		assertEquals("two left", 2, size(tree));
	}
	
	public void testForceRebalanceLeftLeft() {
		CounterIntelligence.Tree tree = null;
		tree = c.add(tree, new Interval(4, 6), "bob");
		tree = c.add(tree, new Interval(3, 6), "bob");
		tree = c.add(tree, new Interval(5, 6), "bob");
		tree = c.add(tree, new Interval(2, 6), "bob");
		tree = c.remove(tree, new Interval(5, 6), "bob");
		check(tree);
		assertEquals("three left", 3, size(tree));
	}
	
	public void testForceRebalanceLeftRight() {
		CounterIntelligence.Tree tree = null;
		tree = c.add(tree, new Interval(4, 6), "bob");
		tree = c.add(tree, new Interval(2, 6), "bob");
		tree = c.add(tree, new Interval(5, 6), "bob");
		tree = c.add(tree, new Interval(3, 6), "bob");
		tree = c.remove(tree, new Interval(5, 6), "bob");
		check(tree);
		assertEquals("three left", 3, size(tree));
	}
	
	public void testForceRebalanceRightRight() {
		CounterIntelligence.Tree tree = null;
		tree = c.add(tree, new Interval(4, 6), "bob");
		tree = c.add(tree, new Interval(2, 6), "bob");
		tree = c.add(tree, new Interval(5, 6), "bob");
		tree = c.add(tree, new Interval(6, 6), "bob");
		tree = c.remove(tree, new Interval(2, 6), "bob");
		check(tree);
		assertEquals("three left", 3, size(tree));
	}
	
	public void testForceRebalanceRightLeft() {
		CounterIntelligence.Tree tree = null;
		tree = c.add(tree, new Interval(4, 6), "bob");
		tree = c.add(tree, new Interval(2, 6), "bob");
		tree = c.add(tree, new Interval(6, 6), "bob");
		tree = c.add(tree, new Interval(5, 6), "bob");
		tree = c.remove(tree, new Interval(2, 6), "bob");
		check(tree);
		assertEquals("three left", 3, size(tree));
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
			for (int j = 0; j < 5000; ++j)
				tree = c.add(tree, ivs.get(j), Integer.toString(j));
			for (int j = 0; j < 1000; ++j) 
				tree = c.remove(tree, ivs.get(j), Integer.toString(j));
			assertEquals("still 4000 elements", 4000, size(tree));
			check(tree);
			for (int j = 1000; j < 5000; ++j)
				assertTrue("Interval is present", contains(tree, ivs.get(j), Integer.toString(j)));
		}
	}
}
