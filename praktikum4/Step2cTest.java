/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST
 * Place all of your implementation into CounterIntelligence.java.
 */
import junit.framework.TestCase;
import java.util.*;

public class Step2cTest extends TestCase {
	CounterIntelligence c;
	
	public void setUp() {
		c = new CounterIntelligence();
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
	
	public void testRightTooDeep() {
		CounterIntelligence.Tree tree = null;
		tree = c.add(tree, new Interval(5, 6), "bob");
		tree = c.add(tree, new Interval(6, 7), "tod");
		tree = c.add(tree, new Interval(7, 8), "bob");
		assertEquals("tod is top", "tod", tree.agent);
		checkHeight(tree);
		checkBalanced(tree);
	}
	
	public void testRightMidDeep() {
		CounterIntelligence.Tree tree = null;
		tree = c.add(tree, new Interval(5, 6), "bob");
		tree = c.add(tree, new Interval(7, 8), "bob");
		tree = c.add(tree, new Interval(6, 7), "tod");
		assertEquals("tod is top", "tod", tree.agent);
		checkHeight(tree);
		checkBalanced(tree);
	}
	
	public void testLeftMidDeep() {
		CounterIntelligence.Tree tree = null;
		tree = c.add(tree, new Interval(7, 8), "bob");
		tree = c.add(tree, new Interval(5, 6), "bob");
		tree = c.add(tree, new Interval(6, 7), "tod");
		assertEquals("tod is top", "tod", tree.agent);
		checkHeight(tree);
		checkBalanced(tree);
	}
	
	public void testLeftTooDeep() {
		CounterIntelligence.Tree tree = null;
		tree = c.add(tree, new Interval(7, 8), "bob");
		tree = c.add(tree, new Interval(6, 7), "tod");
		tree = c.add(tree, new Interval(5, 6), "bob");
		assertEquals("tod is top", "tod", tree.agent);
		checkHeight(tree);
		checkBalanced(tree);
	}
	
	public void testRandom() {
		MTRandom4 rand = new MTRandom4(42);
		for (int i = 1; i <= 300; ++i) {
			CounterIntelligence.Tree tree = null;
			List<Interval> ivs = new ArrayList<Interval>();
			for (int j = 0; j < 5000; ++j) {
				int x = rand.nextInt(100000), y = rand.nextInt(100000);
				if (x > y) ivs.add(new Interval(y, x)); else ivs.add(new Interval(x, y));
			}
			for (Interval x : ivs) tree = c.add(tree, x, "bob");
			checkHeight(tree);
			checkBalanced(tree);
		}
	}
}
