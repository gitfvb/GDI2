/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST.
 * Place all of your implementation into StrikeForce.java.
 */
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests2 {
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for Problem 2");
		suite.addTestSuite(TransposeTest.class);
		suite.addTestSuite(TopologicalSortTest.class);
		suite.addTestSuite(ComponentsTest.class);
		suite.addTestSuite(ContraintsToGraphTest.class);
		suite.addTestSuite(StrikeForceTest.class);
		return suite;
	}
}
