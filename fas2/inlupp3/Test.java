import org.ioopm.calculator.ast.*;
import org.ioopm.calculator.parser.*;
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

    static void testParsing(SymbolicExpression expected, String e, Environment vars){
	CalculatorParser p = new CalculatorParser();
	SymbolicExpression r = p.parse(e).eval(vars);
	if (r.equals(expected)){
	    System.out.println("Passed: " + e);
	} else {
	    System.out.println("Error: expected '" + expected + "' but got '" + r + "'");
	}
    }
	
    public static void main(String args[]){
	Constant c1 = new Constant(5);
	Constant c2 = new Constant(2);
	Constant c3 = new Constant(7);
	Constant c4 = new Constant(10);
	Constant c5 = new Constant(-2);
	Constant c6 = new Constant(0);
	Constant c7 = new Constant(1);

	Variable v = new Variable("x");
	Variable v1 = new Variable("x");

	Subtraction s1 = new Subtraction(c1, v);
	Subtraction s2 = new Subtraction(c3, c2);

	Addition a1 = new Addition(c1, v);
	Addition a2 = new Addition(c3, v);
	Addition a3 = new Addition(c1, c2);

	Multiplication m1 = new Multiplication(a1, c2);
	Multiplication m2 = new Multiplication(a2, c2);
	Multiplication m3 = new Multiplication(c1, c2);

	Division d = new Division(c4, c1);

	Assignment a = new Assignment(c4, v);
	
	Cos c = new Cos(c6);

	Sin s = new Sin(c6);

	Exp e = new Exp(c4);

	Log l = new Log(e);

	Negation n = new Negation(c2);

	
	testPrinting("(5.0 + x) * 2.0", m1);
	testPrinting("(7.0 + x) * 2.0", m2);

        Environment vars = new Environment();

	//Constant
	testEvaluating(c1, c1, vars);

	//Assignment
	testEvaluating(c4, a, vars);

	//Variable
	testEvaluating(c4, v1, vars);

	//Subtraction
	testEvaluating(c1, s2, vars);
	
	//Addition
	testEvaluating(c3, a3, vars);

	//Multiplication
	testEvaluating(c4, m3, vars);

	//Division
	testEvaluating(c2, d, vars);

	//Cos
	testEvaluating(c7, c, vars);

	//Sin
	testEvaluating(c6, s, vars);

	//Exp & Log
	testEvaluating(c4, l, vars);

	//Negation
	testEvaluating(c5, n, vars);

	System.out.println("=====PARSING=====");
	vars = new Environment();
	
	//Constant
	testParsing(c1, "5", vars);

	//Assignment
	testParsing(c4, "10 = x", vars);

	//Variable
	testParsing(c4, "x", vars);

	//Subtraction
	testParsing(c1, "7 -     2", vars);
	
	//Addition
	testParsing(c3, "5 + 2", vars);

	//Multiplication
	testParsing(c4, "5*2", vars);

	//Division
	testParsing(c2, "10/5", vars);

	//Cos
	testParsing(c7, "cos (0)", vars);

	//Sin
	testParsing(c6, "sin 0", vars);

	//Exp & Log
	testParsing(c4, "lOg(eXp(10))", vars);

	//Negation
	testParsing(c5, "-2", vars);

	//Multiple assignment
	testParsing(c4, "10 = a = b = cd", vars);
	testParsing(c2, "(a+b-cd)/5", vars);

	//Named Constant
	testParsing(c7, "-cos(pi)", vars);

	//Invalid assignment
	try {
	    testParsing(c4, "10 = L", vars);
	} catch (IllegalExpressionException exception){
	    System.out.println("Caught invalid expression '10 = L': " + exception);
	}
    }

}
