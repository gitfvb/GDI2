/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST.
 * Place all of your implementation into FundingNetwork.java.
 */
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests3 {
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for Problem 3");
		suite.addTestSuite(BFSTest.class);
		suite.addTestSuite(AugmentingPathTest.class);
		suite.addTestSuite(EdmondsKarpTest.class);
		suite.addTestSuite(MinCutTest.class);
		return suite;
	}
}
