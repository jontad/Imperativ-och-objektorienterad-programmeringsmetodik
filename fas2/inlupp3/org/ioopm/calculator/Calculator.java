package org.ioopm.calculator;
import org.ioopm.calculator.ast.*;
import org.ioopm.calculator.parser.*;

/**
 * @file Calculator.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class Calculator
 * @brief Class for utilizing the Calculator
 */
public class Calculator {
        
    public static void main(String args[]){
	final CalculatorParser parser = new CalculatorParser();
        final Environment vars = new Environment();
	int entered = 0, evaluated = 0, fullyEvaluated = 0;

	while (true){
	    String input = System.console().readLine("? ");
	    entered++;
	    try {
		SymbolicExpression expr = parser.parse(input);
    
		if (expr.equals(Quit.instance())){
		    System.out.println("Goodbye!\nYou entered " + entered + " Expressions"
				       +"\n" + evaluated + " of them were evaluated successfully"
				       +"\n" + fullyEvaluated + " of those were evaluated fully.");
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
			evaluated++;
			if (result.isConstant()){
			    fullyEvaluated++;
			}
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
