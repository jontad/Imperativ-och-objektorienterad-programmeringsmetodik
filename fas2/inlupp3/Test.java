import org.ioopm.calculator.ast.*;
import java.util.HashMap;

public class Test {
    static void testPrinting(String expected, SymbolicExpression e) {
	if (expected.equals("" + e)) {
	    System.out.println("Passed: " + e);
	} else {
	    System.out.println("Error: expected '" + expected + "' but got '" + e + "'");
	}
    }
    
    static void testEvaluating(SymbolicExpression expected, SymbolicExpression e, Environment vars) {
	SymbolicExpression r = e.eval(vars);
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
	Constant c5 = new Constant(-2);
	Variable v = new Variable("x");
	Variable v1 = new Variable("x");
	Addition a1 = new Addition(c1, v);
	Subtraction s1 = new Subtraction(c1, v);
	Addition a2 = new Addition(c3, v);
	Multiplication m1 = new Multiplication(a1, c2);
	Multiplication m2 = new Multiplication(a2, c2);
	Multiplication m3 = new Multiplication(a1, c4);
	Cos c = new Cos(m3);
    
	testPrinting("(5.0 + x) * 2.0", m1);
	testPrinting("(7.0 + x) * 2.0", m2);
	testPrinting("cos((5.0 + x) * 10.0)", c);

        Environment vars = new Environment();
	
	Addition a3 = new Addition(c1, c2);
	testEvaluating(c3, a3, vars);
	vars = new Environment();

	Negation n = new Negation(c2);
	testEvaluating(c5, n, vars);
	vars = new Environment();

	Assignment a = new Assignment(c4, v);
	testEvaluating(c4, a, vars);
	testEvaluating(c4, v1, vars);
    }

}
