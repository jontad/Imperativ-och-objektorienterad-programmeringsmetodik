package org.ioopm.calculator.ast;

/**
 * @file Sin.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class Sin
 * @brief Class for unary operator Sin
 */

public class Sin extends Unary {
    
    public Sin (SymbolicExpression expr){
	super (expr);
    }

// **************************************************
// Public methods
// **************************************************   

/**
 * @brief Retrieve operator name
 * @return Operator name 
 */
    public String getName (){
	return "sin";
    }
        
    /**
     * @brief Determines precedence order of where to use parenthesis
     * @return The priority of the class
     */
    public int getPriority(){
	return 50;
    }

    /**
     * @brief Determines equality between undetermied object and object of Class Sin
     * @param other undetermined object
     * @return true if equal, else false
     */
    public boolean equals(Object other){
	if (other instanceof Sin) {
	    return this.equals((Unary) other);
	} else {
	    return false;
	}
    }

    /**
     * @brief Evaluates SymbolicExpressions
     * @param vars Environment in which to evaluate expression
     * @return A new Constant if argument is a constant. Else new Sin
     */
    public SymbolicExpression eval(Environment vars){
	SymbolicExpression arg = this.getExpr().eval(vars);
	if (arg.isConstant()){
	    return new Constant(Math.sin(arg.getValue()));
	} else {
	    return new Sin(arg);
	}
    }
}
