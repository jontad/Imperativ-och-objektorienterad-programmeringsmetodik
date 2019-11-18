package org.ioopm.calculator.ast;

/**
 * @file Division.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class Division
 * @brief Class for binary operation Division
 */

public class Division extends Binary {

// **************************************************
// Constructor
// **************************************************
     
    public Division (SymbolicExpression lhs, SymbolicExpression rhs) {
	super (lhs, rhs);
    }
    
// **************************************************
// Public methods
// **************************************************

    /**
     * @brief Retrieve operator name for division
     * @return Operator name of class
     */
     public String getName (){
	return "/";
    }

    
   
    /**
     * @brief Determines precedence order of where to use parenthesis
     * @return The priority of the class
     */
    public int getPriority () {
	return 100;
    }

    /**
     * @brief Determines equality between undetermined object and object of class Division
     * @param other undetermined object
     * @return true if equal, else false
     */
    public boolean equals(Object other){
	if (other instanceof Division) {
	    return this.equals((Binary) other);
	} else {
	    return false;
	}
    }

      /**
     * @brief Evaluates SymbolicExpressions
     * @param vars Hashmap containing SymbolicExpressions to be evaluated
     * @return A new Constant if lhs and rhs are constants. Else new Division
     */
    public SymbolicExpression eval(Environment vars){
	SymbolicExpression lhs = this.getLhs().eval(vars);
	SymbolicExpression rhs = this.getRhs().eval(vars);
	if (lhs.isConstant() && rhs.isConstant()){
	    return new Constant(lhs.getValue() / rhs.getValue());
	} else {
	    return new Division(lhs, rhs);
	}
    }
}
