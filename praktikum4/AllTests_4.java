/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST.
 * Place all of your implementation into CounterIntelligence.java.
 */
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests_4 {
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for Problem 4");
		suite.addTestSuite(Step1Test_4.class);
		suite.addTestSuite(Step2aTest.class);
		suite.addTestSuite(Step2bTest.class);
		suite.addTestSuite(Step2cTest.class);
		suite.addTestSuite(Step3Test_4.class);
		suite.addTestSuite(Step4Test_4.class);
		suite.addTestSuite(Step5Test_4.class);
		return suite;
	}
}
