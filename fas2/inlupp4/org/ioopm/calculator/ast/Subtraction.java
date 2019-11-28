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
    @Override
    public String getName (){
	return "-";
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
     * @brief Determines equality between undetermined object and object of class Subtraction
     * @param other undetermined object
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object other){
	if (other instanceof Subtraction) {
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
