package org.ioopm.calculator;
import org.ioopm.calculator.ast.*;
import org.ioopm.calculator.parser.*;

import java.util.Scanner;

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
        final Environment env = new Environment();

	
	Scanner scan = new Scanner(System.in);

	int entered = 0, evaluated = 0, fullyEvaluated = 0;

	System.out.println("Welcome to the parser!");
	while (true){
	    String input = scan.nextLine(); 
	    entered++;
	    try {
		SymbolicExpression topLevel = parser.parse(input);
    
		if (topLevel.equals(Quit.instance())){
		    System.out.println("Goodbye!\nYou entered " + entered + " Expressions"
				       +"\n" + evaluated + " of them were evaluated successfully"
				       +"\n" + fullyEvaluated + " of those were evaluated fully.");
		    break;
		} else if (topLevel.equals(Vars.instance())){
		    System.out.println(env);

		} else if (topLevel.equals(Clear.instance())){
		    env.clear();

		} else {
		    try {
			final NamedConstantChecker checker = new NamedConstantChecker();
			final ReassignmentChecker reassCheck =  new ReassignmentChecker();

			if(checker.check(topLevel) && reassCheck.check(topLevel)) {
			    final EvaluationVisitor evaluator = new EvaluationVisitor();
			    final SymbolicExpression result = evaluator.evaluate(topLevel, env);
			    System.out.println(result);

			    env.put(new Variable("ans"), result);
			    evaluated++;
			    if (result.isConstant()){
				fullyEvaluated++;
			    }
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
