/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST
 * Place all of your implementation into CounterIntelligence.java.
 */
import junit.framework.TestCase;
import java.util.*;

public class Step3Test_4 extends TestCase {
	CounterIntelligence c;
	
	public void setUp() {
		c = new CounterIntelligence();
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
	
	public void testOne() {
		CounterIntelligence.Tree tree = c.new Tree(new Interval(4, 5), "bob");
		checkBiggestEnd(tree);
	}
	
	public void testLeftBigger() {
		CounterIntelligence.Tree tree = c.new Tree(new Interval(4, 5), "bob");
		tree = c.add(tree, new Interval(3, 6), "bob");
		checkBiggestEnd(tree);
	}
	
	public void testLeftSmaller() {
		CounterIntelligence.Tree tree = c.new Tree(new Interval(4, 5), "bob");
		tree = c.add(tree, new Interval(3, 4), "bob");
		checkBiggestEnd(tree);
	}
	
	public void testRightBigger() {
		CounterIntelligence.Tree tree = c.new Tree(new Interval(4, 6), "bob");
		tree = c.add(tree, new Interval(5, 7), "bob");
		checkBiggestEnd(tree);
	}
	
	public void testRightSmaller() {
		CounterIntelligence.Tree tree = c.new Tree(new Interval(4, 6), "bob");
		tree = c.add(tree, new Interval(5, 5), "bob");
		checkBiggestEnd(tree);
	}
	
	public void testTwoChildrenLeftBig() {
		CounterIntelligence.Tree tree = c.new Tree(new Interval(4, 6), "bob");
		tree = c.add(tree, new Interval(3, 8), "bob");
		tree = c.add(tree, new Interval(5, 7), "bob");
		checkBiggestEnd(tree);
	}
	
	public void testTwoChildrenRightBig() {
		CounterIntelligence.Tree tree = c.new Tree(new Interval(4, 6), "bob");
		tree = c.add(tree, new Interval(3, 7), "bob");
		tree = c.add(tree, new Interval(5, 8), "bob");
		checkBiggestEnd(tree);
	}
	
	public void testRandom() {
		MTRandom4 rand = new MTRandom4(42);
		for (int i = 1; i <= 500; ++i) {
			CounterIntelligence.Tree tree = null;
			List<Interval> ivs = new ArrayList<Interval>();
			for (int j = 0; j < 5000; ++j) {
				int x = rand.nextInt(100000), y = rand.nextInt(100000);
				if (x > y) ivs.add(new Interval(y, x)); else ivs.add(new Interval(x, y));
			}
			for (Interval x : ivs) tree = c.add(tree, x, "bob");
			checkBiggestEnd(tree);
		}
	}
}
