/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST
 * Place all of your implementation into Layoffs.java.
 */

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for Problem 5");
		suite.addTestSuite(Step1Test.class);
		suite.addTestSuite(Step2Test.class);
		suite.addTestSuite(Step3Test.class);
		suite.addTestSuite(Step4Test.class);
		suite.addTestSuite(Step5Test.class);
		return suite;
	}
}
