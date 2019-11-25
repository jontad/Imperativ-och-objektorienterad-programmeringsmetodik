package org.ioopm.calculator.ast;

/**
 * @file Multiplication.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class Multiplication
 * @brief Class for binary operation multiplication
 */

public class Multiplication extends Binary {

// **************************************************
// Constructor
// **************************************************
 
    public Multiplication (SymbolicExpression lhs, SymbolicExpression rhs){
	super (lhs, rhs);
    }
    
// **************************************************
// Public methods
// **************************************************

    /**
     * @brief Retrieve operator name for multiplication
     * @return Operator name of class
     */
    public String getName (){
	return "*";
    }

    
    /**
     * @brief Determines precedence order of where to use parenthesis
     * @return The priority of the class
     */
    public int getPriority () {
	return 100;
    }

   /**
     * @brief Determines equality between undetermined object and object of class Multiplication
     * @param other undetermined object   
     * @return true if equal, else false
     */
    public boolean equals(Object other){
	if (other instanceof Multiplication) {
	    return this.equals((Binary) other);
	} else {
	    return false;
	}
    }
    
    /**
     * @brief Evaluates SymbolicExpressions
     * @param vars Environment in which to evaluate expression
     * @return A new Constant if lhs and rhs are constants. Else new Multiplication
     */
    public SymbolicExpression eval(Environment vars){
	SymbolicExpression lhs = this.getLhs().eval(vars);
	SymbolicExpression rhs = this.getRhs().eval(vars);
	if (lhs.isConstant() && rhs.isConstant()){
	    return new Constant(lhs.getValue() * rhs.getValue());
	} else {
	    return new Multiplication(lhs, rhs);
	}
    }
}
