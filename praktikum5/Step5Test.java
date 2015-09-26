/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST
 * Place all of your implementation into Layoffs.java.
 */

import junit.framework.TestCase;
import java.util.*;

public class Step5Test extends TestCase {
	Layoffs l;
	
	public void setUp() {
		l = new Layoffs();
	}

	Coordinate make(Random r, int i) {
		return new Coordinate(r.nextDouble(), r.nextDouble(), Integer.toString(i));
	}
	
	public void testEmpty() {
		List<Coordinate> agents = new ArrayList<Coordinate>();
		assertNull("No agent pairs", l.findClosest(agents));
	}
	
	public void testOne() {
		List<Coordinate> agents = new ArrayList<Coordinate>();
		agents.add(new Coordinate(0.5, 0.5, "bob"));
		assertNull("No agent pairs", l.findClosest(agents));
	}
	
	public void testTwo() {
		List<Coordinate> agents = new ArrayList<Coordinate>();
		agents.add(new Coordinate(0.5, 0.5, "bob"));
		agents.add(new Coordinate(0.1, 0.1, "joe"));
		Layoffs.Result result = l.findClosest(agents);
		assertEquals("matching pair", agents.get(0).distance(agents.get(1)), result.distance());
	}
	
	public void testNotXSorting() {
        List<Coordinate> agents = new ArrayList<Coordinate>();
        for (int i = 0; i < 100; ++i)
            agents.add(new Coordinate(i / 1000.0, i, "bob"));
        agents.add(new Coordinate(0.5, 0, "bob"));
        Layoffs.Result result = l.findClosest(agents);
        assertEquals("matching pair", agents.get(0).distance(agents.get(100)), result.distance());
    }
   
    public void testNotYSorting() {
        List<Coordinate> agents = new ArrayList<Coordinate>();
        for (int i = 0; i < 100; ++i)
            agents.add(new Coordinate(i, i / 1000.0,"bob"));
        agents.add(new Coordinate(0, 0.5, "bob"));
        Layoffs.Result result = l.findClosest(agents);
        assertEquals("matching pair", agents.get(0).distance(agents.get(100)), result.distance());
    }
   
    public void testNotXYSorting() {
        List<Coordinate> agents = new ArrayList<Coordinate>();
        agents.add(new Coordinate(0, 0, "bob"));
        for (int i = 0; i < 100; ++i)
            agents.add(new Coordinate(3*(i+1), 0.1, "bob"));
        for (int i = 0; i < 100; ++i)
            agents.add(new Coordinate(0.1, 3*(i+1), "bob"));
        agents.add(new Coordinate(1, 1, "bob"));
        Layoffs.Result result = l.findClosest(agents);
        assertEquals("matching pair", agents.get(0).distance(agents.get(201)), result.distance());
    }
    
	public void testSmallRandom() {
		Random r = new MTRandom(42);
		
		for (int i = 0; i < 10000; ++i) {
			List<Coordinate> agents = new ArrayList<Coordinate>();
			for (int j = 0; j < 120; ++j) agents.add(make(r, j));
			
			Layoffs.Result best = null;
			for (Coordinate a : agents)
				for (Coordinate b : agents)
					if (a != b)
						best = l.min(best, l.new Result (a, b));
			
			Layoffs.Result answer = l.findClosest(agents);
			
			assertEquals("Closest agents found", best.distance(), answer.distance());
			assertTrue("Agent a exists", agents.get(Integer.parseInt(answer.a.agent)) == answer.a);
			assertTrue("Agent b exists", agents.get(Integer.parseInt(answer.b.agent)) == answer.b);
		}
	}
	
	public void testBigRandom() {
		Random r = new MTRandom(42);
		
		double answers[] = { 2.4009828064149608E-5, 9.967907020180125E-5, 7.037373021503618E-5, 4.973796302433349E-5, 2.0350417279941547E-5, 2.638960498062588E-5, 1.2489975295730202E-4, 8.85512049152114E-5, 7.664641521773662E-5, 8.551785377352437E-5, 1.176886648724328E-4, 4.320335132173907E-5, 7.056903132233033E-5, 7.687510051825466E-5, 1.2403317253843068E-4, 1.4485278932482967E-4, 4.8355286354141076E-5, 7.374138184405907E-5, 4.077985020444501E-5, 4.683945297027062E-5, 2.960563365477405E-5, 5.0933775158579674E-5, 2.6663133840917687E-5, 3.1163410775550435E-5, 1.1046457004792384E-4, 9.180622374273597E-6, 1.243963546133035E-4, 1.0842001778379188E-4, 4.0352680919878285E-5, 5.551577108022462E-5, 4.709777301410871E-5, 5.6598578596469716E-5, 5.0234080699190074E-5, 8.092768810556269E-5, 1.4554698143100044E-4, 4.4815227781513E-5, 6.84083538789753E-5, 3.975280996735118E-5, 5.097335776809491E-5, 1.19362304133951E-4, 3.413297728870159E-5, 9.759811901373043E-5, 5.7767102147321435E-5, 1.1743492509353578E-4, 8.640840395042369E-5, 1.001000427649079E-4, 1.4073018516084698E-5, 6.847115742122275E-5, 4.052738823475772E-5, 6.879679298651744E-5, 3.382951636636418E-5, 5.3441515389224474E-5, 4.2072344811871E-5, 4.609267646133294E-5, 7.956138038674338E-5, 1.0497173185345373E-4, 4.5814400803143704E-5, 6.613234806168571E-5, 1.1174556239289734E-4, 7.135466448517783E-5, 2.497678495422055E-5, 4.5560317693808615E-5, 7.242870863373487E-5, 6.969744178253344E-5, 1.380632138401361E-4, 8.0538250158634E-5, 4.358929868218859E-5, 1.0537732501734857E-4, 6.956690664703169E-5, 1.1467942147482133E-4, 8.658329998905987E-5, 1.1522052277963303E-4, 3.345233792705273E-5, 8.66070869804096E-5, 1.038055696523891E-4, 6.189715438323906E-5, 1.0916456133847062E-4, 6.807628462046797E-5, 1.1542314993816628E-5, 6.953930382717971E-5, 3.212045165557059E-5, 2.3291303800085787E-5, 3.03780217677411E-5, 1.9776274530104123E-5, 4.4468587468502455E-5, 1.3316417240675975E-4, 1.122065563263409E-4, 9.246186617128587E-5, 1.1683965844758995E-4, 5.753584104855066E-6, 8.124603796008753E-5, 3.133268754194216E-5, 1.838397460977578E-5, 6.564038175634361E-5, 7.599002542173633E-5, 6.495199587357774E-5, 8.734487303750767E-5, 1.183952342954005E-4, 7.806030016464758E-5, 4.2396846898869805E-5 };
		
		for (int i = 0; i < 100; ++i) {
			List<Coordinate> agents = new ArrayList<Coordinate>();
			for (int j = 0; j < 10000; ++j) agents.add(make(r, j));
			
			Layoffs.Result answer = l.findClosest(agents);
			
			assertEquals("Closest agents found", answers[i], answer.distance());
			assertTrue("Agent a exists", agents.get(Integer.parseInt(answer.a.agent)) == answer.a);
			assertTrue("Agent b exists", agents.get(Integer.parseInt(answer.b.agent)) == answer.b);
		}
	}
}
