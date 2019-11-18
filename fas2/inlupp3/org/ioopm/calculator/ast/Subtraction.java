package org.ioopm.calculator.ast;

/**
 * @file Subtraction.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class Subtraction
 * @brief Class for binary operation subtraction
 */


public class Subtraction extends Binary {

// **************************************************
// Constructor
// **************************************************
 
    public Subtraction (SymbolicExpression lhs, SymbolicExpression rhs) {
	super (lhs, rhs);
    }


// **************************************************
// Public methods
// **************************************************
   
    /**
     * @brief Retrieve operator name for subtraction 
     * @return Operator name of class
     */
    public String getName (){
	return "-";
    }

    /**
     * @brief Determines precedence order of where to use parenthesis
     * @return The priority of the class
     */
    public int getPriority () {
	return 50;
    }

    /**
     * @brief Determines equality between undetermined object and object of class Subtraction
     * @param other undetermined object
     * @return true if equal, else false
     */
    public boolean equals(Object other){
	if (other instanceof Subtraction) {
	    return this.equals((Binary) other);
	} else {
	    return false;
	}
    }
    
    /**
     * @brief Evaluates SymbolicExpressions
     * @param vars Hashmap containing SymbolicExpressions to be evaluated
     * @return A new Constant if lhs and rhs are constants. Else new Subtraction
     */
    public SymbolicExpression eval(Environment vars){
	SymbolicExpression lhs = this.getLhs().eval(vars);
	SymbolicExpression rhs = this.getRhs().eval(vars);
	if (lhs.isConstant() && rhs.isConstant()){
	    return new Constant(lhs.getValue() - rhs.getValue());
	} else {
	    return new Subtraction(lhs, rhs);
	}
    }
}
