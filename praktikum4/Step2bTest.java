/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST
 * Place all of your implementation into CounterIntelligence.java.
 */
import junit.framework.TestCase;
import java.util.*;

public class Step2bTest extends TestCase {
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
	
	int fixupHeight(CounterIntelligence.Tree node) {
		if (node == null) return 0;
		int heightLeft  = fixupHeight(node.left);
		int heightRight = fixupHeight(node.right);
		node.height = (heightLeft>heightRight?heightLeft:heightRight)+1;
		return node.height;
	}
	
	CounterIntelligence.Tree addNode(CounterIntelligence.Tree node, Random r) {
		if (node == null) return c.new Tree(new Interval(0,0), "bob");
		if (r.nextInt(2) == 0) 
			node.left = addNode(node.left, r);
		else
			node.right = addNode(node.right, r);
		return node;
	}
	
	CounterIntelligence.Tree deepShit() {
		Interval x = new Interval(0, 0);
		CounterIntelligence.Tree node = c.new Tree(x, "bob"); 
		node.right = c.new Tree(x, "bob");
		node.right.right = c.new Tree(x, "bob");
		node.right.right.right = c.new Tree(x, "bob");
		return node;
	}
	
	public void testLeftTwo() {
		Interval x = new Interval(0, 0);
		CounterIntelligence.Tree tree = c.new Tree(x, "bob");
		tree.right = c.new Tree(x, "tod");
		fixupHeight(tree);
		tree = c.rotateLeft(tree);
		assertEquals("rotation happened", "tod", tree.agent);
		checkHeight(tree);
	}
	
	public void testLeftDeepRight() {
		Interval x = new Interval(0, 0);
		CounterIntelligence.Tree tree = c.new Tree(x, "bob");
		tree.right = c.new Tree(x, "tod");
		tree.right.right = c.new Tree(x, "bob");
		fixupHeight(tree);
		tree = c.rotateLeft(tree);
		assertEquals("rotation happened", "tod", tree.agent);
		checkHeight(tree);
	}
	
	public void testLeftDeepMid() {
		Interval x = new Interval(0, 0);
		CounterIntelligence.Tree tree = c.new Tree(x, "bob");
		tree.right = c.new Tree(x, "tod");
		tree.right.left = c.new Tree(x, "bob");
		fixupHeight(tree);
		tree = c.rotateLeft(tree);
		assertEquals("rotation happened", "tod", tree.agent);
		checkHeight(tree);
	}
	
	public void testLeftDeepLeft() {
		Interval x = new Interval(0, 0);
		CounterIntelligence.Tree tree = c.new Tree(x, "bob");
		tree.right = c.new Tree(x, "tod");
		tree.left = c.new Tree(x, "bob");
		fixupHeight(tree);
		tree = c.rotateLeft(tree);
		assertEquals("rotation happened", "tod", tree.agent);
		checkHeight(tree);
	}
	
	public void testLeftDeepMidLeft() {
		Interval x = new Interval(0, 0);
		CounterIntelligence.Tree tree = c.new Tree(x, "bob");
		tree.right = c.new Tree(x, "tod");
		tree.right.left = c.new Tree(x, "bob");
		tree.left = c.new Tree(x, "bob");
		fixupHeight(tree);
		tree = c.rotateLeft(tree);
		assertEquals("rotation happened", "tod", tree.agent);
		checkHeight(tree);
	}
	
	public void testLeftDeepMidRight() {
		Interval x = new Interval(0, 0);
		CounterIntelligence.Tree tree = c.new Tree(x, "bob");
		tree.right = c.new Tree(x, "tod");
		tree.right.left = c.new Tree(x, "bob");
		tree.right.right = c.new Tree(x, "bob");
		fixupHeight(tree);
		tree = c.rotateLeft(tree);
		assertEquals("rotation happened", "tod", tree.agent);
		checkHeight(tree);
	}
	
	public void testLeftDeepLeftRight() {
		Interval x = new Interval(0, 0);
		CounterIntelligence.Tree tree = c.new Tree(x, "bob");
		tree.right = c.new Tree(x, "tod");
		tree.right.left = c.new Tree(x, "bob");
		tree.left = c.new Tree(x, "bob");
		fixupHeight(tree);
		tree = c.rotateLeft(tree);
		assertEquals("rotation happened", "tod", tree.agent);
		checkHeight(tree);
	}
	
	public void testLeftVeryDeepRight() {
		Interval x = new Interval(0, 0);
		CounterIntelligence.Tree tree = c.new Tree(x, "bob");
		tree.right = c.new Tree(x, "tod");
		tree.right.right = deepShit();
		fixupHeight(tree);
		tree = c.rotateLeft(tree);
		assertEquals("rotation happened", "tod", tree.agent);
		checkHeight(tree);
	}
	
	public void testLeftVeryDeepMid() {
		Interval x = new Interval(0, 0);
		CounterIntelligence.Tree tree = c.new Tree(x, "bob");
		tree.right = c.new Tree(x, "tod");
		tree.right.left = deepShit();
		fixupHeight(tree);
		tree = c.rotateLeft(tree);
		assertEquals("rotation happened", "tod", tree.agent);
		checkHeight(tree);
	}
	
	public void testLeftVeryDeepLeft() {
		Interval x = new Interval(0, 0);
		CounterIntelligence.Tree tree = c.new Tree(x, "bob");
		tree.right = c.new Tree(x, "tod");
		tree.left = deepShit();
		fixupHeight(tree);
		tree = c.rotateLeft(tree);
		assertEquals("rotation happened", "tod", tree.agent);
		checkHeight(tree);
	}
	
	public void testLeftVeryDeepRightDeepLeft() {
		Interval x = new Interval(0, 0);
		CounterIntelligence.Tree tree = c.new Tree(x, "bob");
		tree.left = c.new Tree(x, "bob");
		tree.right = c.new Tree(x, "tod");
		tree.right.right = deepShit();
		fixupHeight(tree);
		tree = c.rotateLeft(tree);
		assertEquals("rotation happened", "tod", tree.agent);
		checkHeight(tree);
	}
	
	public void testLeftVeryDeepRightDeepMid() {
		Interval x = new Interval(0, 0);
		CounterIntelligence.Tree tree = c.new Tree(x, "bob");
		tree.right = c.new Tree(x, "tod");
		tree.right.left = c.new Tree(x, "bob");
		tree.right.right = deepShit();
		fixupHeight(tree);
		tree = c.rotateLeft(tree);
		assertEquals("rotation happened", "tod", tree.agent);
		checkHeight(tree);
	}
	
	public void testLeftVeryDeepMidDeepLeft() {
		Interval x = new Interval(0, 0);
		CounterIntelligence.Tree tree = c.new Tree(x, "bob");
		tree.right = c.new Tree(x, "tod");
		tree.right.left = deepShit();
		tree.left = c.new Tree(x, "bob");
		fixupHeight(tree);
		tree = c.rotateLeft(tree);
		assertEquals("rotation happened", "tod", tree.agent);
		checkHeight(tree);
	}
	
	public void testLeftVeryDeepMidDeepRight() {
		Interval x = new Interval(0, 0);
		CounterIntelligence.Tree tree = c.new Tree(x, "bob");
		tree.right = c.new Tree(x, "tod");
		tree.right.left = deepShit();
		tree.right.right = c.new Tree(x, "bob");
		fixupHeight(tree);
		tree = c.rotateLeft(tree);
		assertEquals("rotation happened", "tod", tree.agent);
		checkHeight(tree);
	}
	
	public void testLeftVeryDeepLeftDeepMid() {
		Interval x = new Interval(0, 0);
		CounterIntelligence.Tree tree = c.new Tree(x, "bob");
		tree.right = c.new Tree(x, "tod");
		tree.right.left = c.new Tree(x, "bob");
		tree.left = deepShit();
		fixupHeight(tree);
		tree = c.rotateLeft(tree);
		assertEquals("rotation happened", "tod", tree.agent);
		checkHeight(tree);
	}
	
	public void testLeftVeryDeepLeftDeepRight() {
		Interval x = new Interval(0, 0);
		CounterIntelligence.Tree tree = c.new Tree(x, "bob");
		tree.right = c.new Tree(x, "tod");
		tree.right.right = c.new Tree(x, "bob");
		tree.left = deepShit();
		fixupHeight(tree);
		tree = c.rotateLeft(tree);
		assertEquals("rotation happened", "tod", tree.agent);
		checkHeight(tree);
	}
	
	public void testRightTwo() {
		Interval x = new Interval(0, 0);
		CounterIntelligence.Tree tree = c.new Tree(x, "bob");
		tree.left = c.new Tree(x, "tod");
		fixupHeight(tree);
		tree = c.rotateRight(tree);
		assertEquals("rotation happened", "tod", tree.agent);
		checkHeight(tree);
	}
	
	public void testRightDeepLeft() {
		Interval x = new Interval(0, 0);
		CounterIntelligence.Tree tree = c.new Tree(x, "bob");
		tree.left = c.new Tree(x, "tod");
		tree.left.left = c.new Tree(x, "bob");
		fixupHeight(tree);
		tree = c.rotateRight(tree);
		assertEquals("rotation happened", "tod", tree.agent);
		checkHeight(tree);
	}
	
	public void testRightDeepMid() {
		Interval x = new Interval(0, 0);
		CounterIntelligence.Tree tree = c.new Tree(x, "bob");
		tree.left = c.new Tree(x, "tod");
		tree.left.right = c.new Tree(x, "bob");
		fixupHeight(tree);
		tree = c.rotateRight(tree);
		assertEquals("rotation happened", "tod", tree.agent);
		checkHeight(tree);
	}
	
	public void testRightDeepRight() {
		Interval x = new Interval(0, 0);
		CounterIntelligence.Tree tree = c.new Tree(x, "bob");
		tree.left = c.new Tree(x, "tod");
		tree.right = c.new Tree(x, "bob");
		fixupHeight(tree);
		tree = c.rotateRight(tree);
		assertEquals("rotation happened", "tod", tree.agent);
		checkHeight(tree);
	}
	
	public void testRightVeryDeepLeft() {
		Interval x = new Interval(0, 0);
		CounterIntelligence.Tree tree = c.new Tree(x, "bob");
		tree.left = c.new Tree(x, "tod");
		tree.left.left = deepShit();
		fixupHeight(tree);
		tree = c.rotateRight(tree);
		assertEquals("rotation happened", "tod", tree.agent);
		checkHeight(tree);
	}
	
	public void testRightVeryDeepMid() {
		Interval x = new Interval(0, 0);
		CounterIntelligence.Tree tree = c.new Tree(x, "bob");
		tree.left = c.new Tree(x, "tod");
		tree.left.right = deepShit();
		fixupHeight(tree);
		tree = c.rotateRight(tree);
		assertEquals("rotation happened", "tod", tree.agent);
		checkHeight(tree);
	}
	
	public void testRightVeryDeepRight() {
		Interval x = new Interval(0, 0);
		CounterIntelligence.Tree tree = c.new Tree(x, "bob");
		tree.left = c.new Tree(x, "tod");
		tree.right = deepShit();
		fixupHeight(tree);
		tree = c.rotateRight(tree);
		assertEquals("rotation happened", "tod", tree.agent);
		checkHeight(tree);
	}
	
	public void testRandom() {
		MTRandom4 rand = new MTRandom4(42);
		for (int i = 1; i <= 500; ++i) {
			CounterIntelligence.Tree tree = null;
			
			for (int j = 1; j <= 100; ++j)
				tree = addNode(tree, rand);
			fixupHeight(tree);

			if (rand.nextInt(2) == 0 && tree.left != null) {
				tree = c.rotateRight(tree);
				checkHeight(tree);
			} else if (tree.right != null){
				tree = c.rotateLeft(tree);
				checkHeight(tree);
			}
		}
	}
}
