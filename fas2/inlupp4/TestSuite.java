import org.junit.Test;
import junit.framework.TestCase;
import static org.junit.Assert.assertEquals;

public class TestSuite extends TestCase {
    @Test
    public void testOk() {
	String str = "Junit is working fine";
	assertEquals("Junit is working fine", str);
    }
    @Test
    public void testNotOk() {
	String str = "Junit is working fine";
	assertEquals("Junit is not working fine", str);
    }
}
