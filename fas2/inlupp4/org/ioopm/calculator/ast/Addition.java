package org.ioopm.calculator.ast;

/**
 * @file Addition.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class Addition
 * @brief Class for binary operation addition
 */

public class Addition extends Binary {

// **************************************************
// Constructor
// **************************************************
    
    public Addition (SymbolicExpression lhs, SymbolicExpression rhs) {
	super (lhs, rhs);
    }
    
// **************************************************
// Public methods
// **************************************************

    /**
     * @brief Retrieve operator name for addition 
     * @return Operator name of class
     */
    @Override
    public String getName (){
	return "+";
    }
    
    /**
     * @brief Determines precedence order of where to use parenthesis
     * @return The priority of the class
     */
    @Override
    public int getPriority () {
	return 50;
    }

     /**
     * @brief Determines equality between undetermied object and object of class Addition
     * @param other undetermined object
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object other){
	if (other instanceof Addition) {
	    return this.equals((Binary) other);
	} else {
	    return false;
	}
    }
    
    /**
     * @brief Determines which Symbolicexpression to return
     * @param v Carries the visitor 
     * @return Returns determined Symbolicexpression
     */
    @Override
    public SymbolicExpression accept(Visitor v) {
	return v.visit(this);
    }
}
