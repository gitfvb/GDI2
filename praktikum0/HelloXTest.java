/* DO NOT MODIFY THIS FILE. ANY CHANGES MADE WILL BE LOST.
 * Place all of your implementation into HelloX.java.
 */
import junit.framework.TestCase;

public class HelloXTest extends TestCase {
	HelloX hellox;
	
	public void setUp() {
		hellox = new HelloX();
	}
	
	public void testHello1() {
		assertEquals("empty hello", " world", hellox.hello("", "world"));
		assertEquals("empty world", "Hello ", hellox.hello("Hello", ""));
	}
	
	public void testHello2() {
		assertEquals("classic hello world", "Hello world", hellox.hello("Hello", "world"));
	}
	
	public void testHello3() {
		assertEquals("German hello", "Hallo äppler", hellox.hello("Hallo", "äppler"));
	}
	
	public void testHello4() {
		assertEquals("Russian hello", "Привет мир", hellox.hello("Привет", "мир"));
	}
	
	public void testHello5() {
		assertEquals("Japanese hello", "ハロー ワールド", hellox.hello("ハロー", "ワールド"));
	}
}
