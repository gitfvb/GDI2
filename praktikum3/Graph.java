/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST.
 * Place all of your implementation into FundingNetwork.java.
 */
import java.util.*;

public interface Graph extends Map<String, Map<String, Double>> {
	void addVertex(String i);
	void addEdge(String i, String j, double capacity);
	
	String toString();
	Graph clone();
}
