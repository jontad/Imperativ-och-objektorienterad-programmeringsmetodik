package org.ioopm.calculator.ast;


/**
 * @file Binary.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class Binary
 * @brief Common superclass of all binary operations (Addition, Subtraction, Multiplication, Division and Assignment)  
 */

public abstract class Binary extends SymbolicExpression {
// **************************************************
// Attributes
// **************************************************

    private SymbolicExpression lhs = null;
    private SymbolicExpression rhs = null;

// **************************************************
// Constructor
// **************************************************

     /**
     * @brief Determines the Binary class
     * @param lhs Left hand side that will be used in binary operation. Is a subtree       
     * @param rhs Right hand side that will be used in binary operation. Is a subtree
     */
    public Binary (SymbolicExpression lhs, SymbolicExpression rhs){
	this.lhs = lhs;
	this.rhs = rhs;
    }

// **************************************************
// Protected methods
// **************************************************


    /**
     * @brief Get-method used to retrieve lhs 
     * @return A SymbolicExpression
     */
    protected SymbolicExpression getLhs(){
	return lhs;
    }

    
     
    /**
     * @brief Get-method used to retrieve rhs
     * @return A SymbolicExpression
     */
    protected SymbolicExpression getRhs(){
	return rhs;
    }

// **************************************************
// Public methods
// **************************************************
    
    /**
     * @brief Determines equality between Binary and SymbolicExpression
     * @param other Other Object to compare with current binary
     * @return true if other is a constant and they are equal, else false
     */
    public boolean equals(Object other){
	if (other instanceof Binary) {
	    return this.equals((Binary) other);
	} else {
	    return false;
	}
    }
    
    /**
     * @brief Determines equality between objects of class Binary
     * @param other Other Binary to compare with current binary  
     * @return true if equal, else false
     */
    public boolean equals(Binary other){
 	return this.lhs.equals(other.lhs) && this.rhs.equals(other.rhs);
    }
    
    /**
     * @brief Prints full binary expression
     * @return Full binary expression
     */
     public String toString (){
	String leftString = lhs.toString();
	String rightString = rhs.toString();
	if (lhs.getPriority() < this.getPriority()){
	    leftString = "(" + leftString + ")";
	}
	if (rhs.getPriority() < this.getPriority()){
	    rightString = "(" + rightString + ")";
	}
	return leftString + " " + this.getName() + " " + rightString;
    }
}
