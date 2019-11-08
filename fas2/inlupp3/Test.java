import org.ioopm.calculator.ast.*;

public class Test {
    static void testPrinting(String expected, SymbolicExpression e) {
	if (expected.equals("" + e)) {
	    System.out.println("Passed: " + e);
	} else {
	    System.out.println("Error: expected '" + expected + "' but got '" + e + "'");
	}
    }
    static void testEvaluating(SymbolicExpression expected, SymbolicExpression e) {
	SymbolicExpression r = e.eval();
	if (r.equals(expected)) {
	    System.out.println("Passed: " + e);
	} else {
	    System.out.println("Error: expected '" + expected + "' but got '" + e + "'");
	}
    }

	
    public static void main(String args[]){
	Constant c1 = new Constant(5);
	Constant c2 = new Constant(2);
	Constant c3 = new Constant(7);
	Constant c4 = new Constant(10);
	Variable v = new Variable("x");
	Addition a1 = new Addition(c1, v);
	Addition a2 = new Addition(c3, v);
	Multiplication m1 = new Multiplication(a1, c2);
	Multiplication m2 = new Multiplication(a2, c2);
	Multiplication m3 = new Multiplication(a1, c4);
	Cos c = new Cos(m3);
    
	testPrinting("(5.0 + x) * 2.0", m1);
	testPrinting("(7.0 + x) * 2.0", m2);
	testPrinting("cos((5.0 + x) * 10.0)", c);

	Addition a3 = new Addition(c1, c2);
	testEvaluating(7.0, a3);
    }

}
