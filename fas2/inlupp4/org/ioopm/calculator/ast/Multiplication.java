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
    @Override
    public String getName (){
	return "*";
    }

    
    /**
     * @brief Determines precedence order of where to use parenthesis
     * @return The priority of the class
     */
    @Override
    public int getPriority () {
	return 100;
    }

   /**
     * @brief Determines equality between undetermined object and object of class Multiplication
     * @param other undetermined object   
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object other){
	if (other instanceof Multiplication) {
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
