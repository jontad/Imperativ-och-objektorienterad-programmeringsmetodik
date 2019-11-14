package org.ioopm.calculator;
import org.ioopm.calculator.ast.*;
import org.ioopm.calculator.parser.*;

public class Calculator {
    
    public static void main(String args[]){
	final CalculatorParser parser = new CalculatorParser();
        final Environment vars = new Environment();

	while (true){
	    String input = System.console().readLine("? ");
	    try {
		SymbolicExpression expr = parser.parse(input);

		if (expr.equals(Quit.instance())){
		    break;
		} else if (expr.equals(Vars.instance())){
		    System.out.println(vars);
		} else if (expr.equals(Clear.instance())){
		    vars.clear();
		} else {
		    try {
			SymbolicExpression result = expr.eval(vars);
			System.out.println(result);
			vars.put(new Variable("ans"), result);
		    } catch (IllegalExpressionException e){
		        System.out.println("*** Illegal Expression: " + e.getMessage());
		    }
		}
	    } catch (SyntaxErrorException e){
		System.out.println("*** Syntax Error: " + e.getMessage());
	    }
	}
    }
}
