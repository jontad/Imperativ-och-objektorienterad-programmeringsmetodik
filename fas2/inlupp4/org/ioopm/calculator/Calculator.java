package org.ioopm.calculator;
import org.ioopm.calculator.ast.*;
import org.ioopm.calculator.parser.*;

import java.util.LinkedList;

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
	final EvaluationVisitor evaluator = new EvaluationVisitor(); 
	
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
			final NamedConstantChecker checker = new NamedConstantChecker(evaluator.getFuncEnv());
			final ReassignmentChecker reassCheck =  new ReassignmentChecker(evaluator.getFuncEnv());

			if(checker.check(topLevel) && reassCheck.check(topLevel)) {
			    if (topLevel instanceof FunctionDeclaration) {
				FunctionDeclaration funcDec = (FunctionDeclaration) topLevel;
				LinkedList<SymbolicExpression> body = funcDec.getSequence().getBody();
				String line = "notEnd";
				while (!line.equals("end")) {
				    System.out.print(">");
				    line = scan.nextLine();
				    SymbolicExpression lineInFunction = parser.parse(line);
				    if(!line.equals("end")){
					body.add(lineInFunction);
				    }
				}
			    }
			    
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
