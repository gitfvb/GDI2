/* Make your changes only to this file. Once complete, upload
 * it to https://www.dvs.tu-darmstadt.de/teaching/inf2/2009-prak/
 */
import java.util.*;

public class CounterIntelligence_en {
	/* An AVL Tree implementation which stores intervals. */
	public class Tree {
		/* A tree has left and right child nodes (which may be null!) */
		public Tree left, right;
		
		/* The interval stored in this node of the tree.
		 * The 'start' field is the primary sort key of the tree.
		 * The 'end' field is more-or-less just extra information.
		 */
		public Interval interval;
		
		/* Which agent was busy during this time interval 
		 * This is the secondary sort key for the tree. 
		 */
		public String agent;
				
		/* The AVL tree height (step 2).
		 * An empty tree (null) has height 0.
		 * A tree with one element has height 1.
		 */
		public int height;
		
		/* The largest 'end' value for the subtree (step 3).
		 * This information can be used to quickly determine if an interval overlaps.  
		 */
		public int biggest_end;
		
		public Tree(Interval x, String y) {
			interval = x;
			agent = y;
			height = 1;
			biggest_end = x.end;
		}
	}
	
	/* Add a new node to the tree with the specified start and end.
	 * Input: the root of the tree and the interval+agent to store in it. 
	 * Output: the new root of the tree.
	 * In step 1, the function should ensure that the tree-order is ('start', 'agent').
	 * In step 2, the function should update height and maintain balance.
	 * In step 3, the function should maintain biggest_end.
	 */
	public Tree add(Tree node, Interval x, String agent) {
		return node; // unimplemented
	}
	
	/* A helper function to get the height of a (possibly empty) subtree. */
	int height(Tree node) {
		return (node == null) ? 0 : node.height;
	}

	/* A helper function to update the height of a node.
	 * Sets height to max(left, right)+1.
	 */
	void updateHeight(Tree node) {
		// unimplemented
	}
	
	/* Rotate the tree left, updating links and height.
	 * 
	 *       A                     B
	 *      / \                   / \
	 *     x   B        =>       A   z
	 *        / \               / \
	 *       y   z             x   y
	 */
	public Tree rotateLeft(Tree node) {
		return node; // unimplemented
	}
	
	/* Rotate the tree right, updating links and height.
	 * 
	 *       B                     A
	 *      / \                   / \
	 *     A   z        =>       x   B
	 *    / \                       / \
	 *   x   y                     y   z
	 */
	public Tree rotateRight(Tree node) {
		return node; // unimplemented
	}
	
	/* A helper function to calculate the balance of a node.
	 * Returns the height of the right subtree - the left subtree.
	 */
	int balance(Tree node) {
		return 0; // unimplemented
	}
	
	/* A helper function to rebalance a node.
	 * Input: A subtree which might be unbalanced.
	 * Output: A subtree where balance(node) is -1, 0, or 1. 
	 */
	Tree rebalance(Tree node) {
		return null; // unimplemented
	}
	
	/* A helper function to maintain the biggest_end value.
	 * Input: A tree node with up-to-date children.
	 * Output: Updated node with a correct biggest_end value.
	 */
	void updateBiggest(Tree node) {
		// unimplemented
	}
	
	/* Java cannot return two values. Hence this class. */ 
	class RemoveResult {
		public Tree removed;
		public Tree node;
		
		public RemoveResult(Tree a, Tree b) {
			removed = a;
			node = b;
		}
	}
	
	/* Remove the smallest node from the specified subtree.
	 * Input: A non-null node.
	 * Output: out.removed was the smallest child in the subtree.
	 *         out.node is the new subtree root after out.removed is removed.
	 */
	RemoveResult removeSmallest(Tree node) {
		return null; // unimplemented
	}

	/* Delete an agent's interval of innocence..
	 * Input: A subtree guaranteed to have exactly one node where
	 *        node.interval.start == x.start and node.agent == agent
	 * Output: A tree with the designated agent removed.
	 */
	public Tree remove(Tree node, Interval x, String agent) {
		return null; // unimplemented
	}
	
	/* Determine which agents could not have participated in the breach.
	 * Input: The root node of the database and the time the breach occured.
	 * Output: The names of agents with a time interval which overlaps the breach.
	 */
	public void innocentAgents(Tree node, Interval breach, Set<String> output) {
		// unimplemented
	}
}
