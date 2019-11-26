import org.junit.Test;
import junit.framework.TestCase;
import static org.junit.Assert.assertEquals;
import java.io.IOException;

import org.ioopm.calculator.parser.*;
import org.ioopm.calculator.ast.*;
import org.ioopm.calculator.*;

public class TestSuite extends TestCase {

    //getValue()
    @Test
    public void testGetValue() {
        SymbolicExpression constant = new Constant(10);
	assertEquals(10.0, constant.getValue());

	try{
	    SymbolicExpression sin = new Sin(new Constant(10));
	    double s = sin.getValue();
	    assertTrue(false);
	} catch(RuntimeException e) {
	    assertTrue(true);
	}
	
    }

    //isConstant()
    @Test
    public void testIsConstant() {
	SymbolicExpression c = new Constant(10);
	assertTrue(c.isConstant());

	SymbolicExpression var = new Variable("var");
	assertFalse(var.isConstant());

	SymbolicExpression namedConstant = new NamedConstant("pi");
	assertTrue(namedConstant.isConstant());

	SymbolicExpression sin = new Sin(c);
	assertFalse(sin.isConstant());

        SymbolicExpression cos = new Cos(c);
	assertFalse(cos.isConstant());

	SymbolicExpression exp = new Exp(c);
	assertFalse(exp.isConstant());

	SymbolicExpression log = new Log(c);
	assertFalse(log.isConstant());

	SymbolicExpression neg = new Negation(c);
	assertFalse(neg.isConstant());

	SymbolicExpression add = new Addition(c, c);
	assertFalse(add.isConstant());
	
	SymbolicExpression sub = new Subtraction(c, c);
	assertFalse(sub.isConstant());

	SymbolicExpression mult = new Multiplication(c, c);
	assertFalse(mult.isConstant());

	SymbolicExpression div = new Division(c, c);
	assertFalse(div.isConstant());

	SymbolicExpression ass = new Assignment(c, c);
	assertFalse(ass.isConstant());

	assertFalse(Quit.instance().isConstant());

	assertFalse(Vars.instance().isConstant());

	assertFalse(Clear.instance().isConstant());
	
    }
    
    //getName()
    @Test
    public void testgetName() {
	SymbolicExpression c = new Constant(10);
	assertEquals("10.0", c.getName());
	
	SymbolicExpression var = new Variable("var");
	assertEquals("var", var.getName());
		
	SymbolicExpression namedConstant = new NamedConstant("pi");
	assertEquals("pi", namedConstant.getName());
		
	SymbolicExpression sin = new Sin(c);
	assertEquals("sin", sin.getName());
	
        SymbolicExpression cos = new Cos(c);
	assertEquals("cos", cos.getName());
	
	SymbolicExpression exp = new Exp(c);
	assertEquals("exp", exp.getName());
	
	SymbolicExpression log = new Log(c);
	assertEquals("log", log.getName());
	
	SymbolicExpression neg = new Negation(c);
	assertEquals("-", neg.getName());
	
	SymbolicExpression add = new Addition(c, c);
	assertEquals("+", add.getName());
	
	SymbolicExpression sub = new Subtraction(c, c);
	assertEquals("-", sub.getName());
	
	SymbolicExpression mult = new Multiplication(c, c);
	assertEquals("*", mult.getName());
	
	SymbolicExpression div = new Division(c, c);
	assertEquals("/", div.getName());
	
	SymbolicExpression ass = new Assignment(c, c);
	assertEquals("=", ass.getName());
	
	assertEquals("Quit", Quit.instance().getName());
	
	assertEquals("Vars",Vars.instance().getName());
	
        assertEquals("Clear", Clear.instance().getName());	
    }

    //isCommand()
    @Test
    public void testIsCommand() {
	SymbolicExpression c = new Constant(10);
	assertFalse(c.isCommand());

	SymbolicExpression var = new Variable("var");
	assertFalse(var.isCommand());

	SymbolicExpression namedConstant = new NamedConstant("pi");
	assertFalse(namedConstant.isCommand());

	SymbolicExpression sin = new Sin(c);
	assertFalse(sin.isCommand());

        SymbolicExpression cos = new Cos(c);
	assertFalse(cos.isCommand());

	SymbolicExpression exp = new Exp(c);
	assertFalse(exp.isCommand());

	SymbolicExpression log = new Log(c);
	assertFalse(log.isCommand());

	SymbolicExpression neg = new Negation(c);
	assertFalse(neg.isCommand());

	SymbolicExpression add = new Addition(c, c);
	assertFalse(add.isCommand());
	
	SymbolicExpression sub = new Subtraction(c, c);
	assertFalse(sub.isCommand());

	SymbolicExpression mult = new Multiplication(c, c);
	assertFalse(mult.isCommand());

	SymbolicExpression div = new Division(c, c);
	assertFalse(div.isCommand());

	SymbolicExpression ass = new Assignment(c, c);
	assertFalse(ass.isCommand());

	assertTrue(Quit.instance().isCommand());

	assertTrue(Vars.instance().isCommand());

	assertTrue(Clear.instance().isCommand());	
    }

    //getPriority()
    @Test
    public void testGetPriority() {
	SymbolicExpression c = new Constant(10);
	assertEquals(200, c.getPriority());

	SymbolicExpression var = new Variable("var");
	assertEquals(200, var.getPriority());

	SymbolicExpression namedConstant = new NamedConstant("pi");
	assertEquals(200, namedConstant.getPriority());

	SymbolicExpression sin = new Sin(c);
	assertEquals(50, sin.getPriority());

        SymbolicExpression cos = new Cos(c);
	assertEquals(50, cos.getPriority());

	SymbolicExpression exp = new Exp(c);
	assertEquals(50, exp.getPriority());

	SymbolicExpression log = new Log(c);
	assertEquals(50, log.getPriority());

	SymbolicExpression neg = new Negation(c);
	assertEquals(50, neg.getPriority());

	SymbolicExpression add = new Addition(c, c);
	assertEquals(50, add.getPriority());
	
	SymbolicExpression sub = new Subtraction(c, c);
	assertEquals(50, sub.getPriority());

	SymbolicExpression mult = new Multiplication(c, c);
	assertEquals(100, mult.getPriority());

	SymbolicExpression div = new Division(c, c);
	assertEquals(100, div.getPriority());

	SymbolicExpression ass = new Assignment(c, c);
	assertEquals(200, ass.getPriority());

	assertEquals(0, Quit.instance().getPriority());

	assertEquals(0, Vars.instance().getPriority());

	assertEquals(0, Clear.instance().getPriority());	
    }

    //toString()
    @Test
    public void testToString() {
	Constant con1 = new Constant(5);
	Constant con2 = new Constant(2);
	Constant con3 = new Constant(7);
	Constant con4 = new Constant(10);
	Constant con5 = new Constant(0);

	Variable var = new Variable("x");

	Subtraction sub = new Subtraction(con3, con2);
	Addition add = new Addition(con1, var);
	Multiplication mult = new Multiplication(add, con2);
	Division div = new Division(mult, con1);
	Assignment ass = new Assignment(con4, var);
	
	Cos cos = new Cos(con5);
	Sin sin = new Sin(con5);
	Exp exp = new Exp(con4);
	Log log = new Log(exp);
	Negation neg = new Negation(con2);

	//Atom
	assertEquals("10.0", con4.toString());
	assertEquals("x", var.toString());
	
	//Binary
	assertEquals("5.0 + x", add.toString());
	assertEquals("10.0 = x", ass.toString());
	assertEquals("7.0 - 2.0", sub.toString());
	assertEquals("(5.0 + x) * 2.0", mult.toString());
	assertEquals("(5.0 + x) * 2.0 / 5.0", div.toString());

	//Unary
	assertEquals("exp(10.0)", exp.toString());
	assertEquals("log(exp(10.0))", log.toString());
	assertEquals("cos(0.0)", cos.toString());
	assertEquals("sin(0.0)", sin.toString());
	assertEquals("-(2.0)", neg.toString());
    }

    //equals()
    @Test
    public void testEquals(){
	SymbolicExpression c = new Constant(10);
	SymbolicExpression con1 = new Constant(100);
	SymbolicExpression con2 = new Constant(10);

	SymbolicExpression var1 = new Variable("var");
	SymbolicExpression var2 = new Variable("var");

	SymbolicExpression sin = new Sin(c);

        SymbolicExpression cos = new Cos(c);

	SymbolicExpression exp = new Exp(c);

	SymbolicExpression log1 = new Log(c);
	SymbolicExpression log2 = new Log(con1);

	SymbolicExpression neg1 = new Negation(c);
	SymbolicExpression neg2 = new Negation(c);

	SymbolicExpression add = new Addition(c, c);
	
	SymbolicExpression sub1 = new Subtraction(c, c);
	SymbolicExpression sub2 = new Subtraction(c, c);

	SymbolicExpression mult = new Multiplication(c, c);

	SymbolicExpression div = new Division(c, c);

	//Constant
	assertFalse(con1.equals(var1));
	assertTrue(c.equals(con2));

	//Variable
	assertTrue(var2.equals(var1));
	assertTrue(var1.equals(var2));

	//Unary
	assertFalse(sin.equals(cos));
	assertTrue(sin.equals(sin));

	assertFalse(log1.equals(log2));
	assertFalse(exp.equals(sin));
	assertTrue(neg1.equals(neg2));

	//Binary
	assertFalse(add.equals(sub1));
	assertTrue(sub1.equals(sub2));
	assertFalse(div.equals(con1));

    }

    //eval()
    @Test
    public void testEval() {
	Constant con1 = new Constant(5);
	Constant con2 = new Constant(2);
	Constant con3 = new Constant(7);
	Constant con4 = new Constant(10);
	Constant con5 = new Constant(-2);
	Constant con6 = new Constant(0);
	Constant con7 = new Constant(1);

	Variable var = new Variable("x");
	Variable var1 = new Variable("x");

	Subtraction sub1 = new Subtraction(con1, var);
	Subtraction sub2 = new Subtraction(con3, con2);

	Addition add1 = new Addition(con1, var);
	Addition add2 = new Addition(con3, var);
	Addition add3 = new Addition(con1, con2);

	Multiplication mult1 = new Multiplication(add1, con2);
	Multiplication mult2 = new Multiplication(add2, con2);
	Multiplication mult3 = new Multiplication(con1, con2);

	Division div = new Division(con4, con1);

	Assignment ass = new Assignment(con4, var);
	
	Cos cos = new Cos(con6);

	Sin sin = new Sin(con6);

	Exp exp = new Exp(con4);

	Log log = new Log(exp);

	Negation neg = new Negation(con2);

	
	Environment vars = new Environment();

	//Constant
	assertEquals(con1, con1.eval(vars));	

	//Assignment
	assertEquals(con4, ass.eval(vars));

	//Variable
	assertEquals(con4, var1.eval(vars));

	//Subtraction
	assertEquals(con1, sub2.eval(vars));
	
	//Addition
	assertEquals(con3, add3.eval(vars));

	//Multiplication
	assertEquals(con4, mult3.eval(vars));

	//Division
	assertEquals(con2, div.eval(vars));

	//Cos
	assertEquals(con7, cos.eval(vars));

	//Sin
	assertEquals(con6, sin.eval(vars));

	//Exp & Log
	assertEquals(con4, log.eval(vars));

	//Negation
	assertEquals(con5, neg.eval(vars));
	
    }
    
    @Test
    public void testParser() {
	SymbolicExpression c = new Constant(10);
	SymbolicExpression con = new Constant(10);
	String conS = con.toString();
	
	SymbolicExpression var = new Variable("var");
	String varS = var.toString();
	
	SymbolicExpression sin = new Sin(c);
	String sinS = sin.toString();

	SymbolicExpression add = new Addition(c, c);
	String addS = add.toString();
	
	SymbolicExpression sub = new Subtraction(c, c);
	String subS = sub.toString();
	
	SymbolicExpression mult = new Multiplication(c, c);
	String multS = mult.toString();

	SymbolicExpression a = new Addition(var, c);
	SymbolicExpression b = new Multiplication(a, a);
	SymbolicExpression negation = new Negation(new Negation(new Negation(b)));
	String negationS = negation.toString();

	
	CalculatorParser parser = new CalculatorParser();


	SymbolicExpression symbCon = parser.parse(conS);
	assertEquals(symbCon, con);

	SymbolicExpression symbVar = parser.parse(varS);
	assertEquals(symbVar, var);

	SymbolicExpression symbSin = parser.parse(sinS);
	assertEquals(symbSin, sin);

	SymbolicExpression symbAdd = parser.parse(addS);
	assertEquals(symbAdd, add);

	SymbolicExpression symbSub = parser.parse(subS);
	assertEquals(symbSub, sub);

	SymbolicExpression symbMult = parser.parse(multS);
	assertEquals(symbMult, mult);

	SymbolicExpression symbNegation = parser.parse(negationS);
	assertEquals(symbNegation, negation);
	assertTrue(symbNegation.equals(negation));
	/*
	try{
	    SymbolicExpression expectingFailure = parser.parse(" ");
	    assertFalse(false);
	} catch(IOException e) {
	    assertTrue(true);
	}
	
	try{
	    SymbolicExpression expectingFailure = parser.parse("(5 + ");
	    assertFalse(false);
	} catch(IOException e) {
	    assertTrue(true);
	}
	*/
    }
	
	
    
}
