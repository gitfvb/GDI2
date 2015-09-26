/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST
 * Place all of your implementation into Surveillance.java.
 */
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests1 {
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for Problem 1");
		suite.addTestSuite(SatEventsTest.class);
		suite.addTestSuite(SatCoverageTest.class);
		suite.addTestSuite(FewestSatellitesTest.class);
		return suite;
	}
}
