/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST
 * Place all of your implementation into Layoffs.java.
 */
public class Coordinate {
	public double x, y;
	public String agent;
	
	Coordinate(double x, double y, String agent) {
		this.x = x;
		this.y = y;
		this.agent = agent;
	}
	
	double length() {
		return Math.sqrt(x*x + y*y);
	}
	
	double distance(Coordinate o) {
		double dx = x - o.x;
		double dy = y - o.y;
		return Math.sqrt(dx*dx + dy*dy);
	}
}
