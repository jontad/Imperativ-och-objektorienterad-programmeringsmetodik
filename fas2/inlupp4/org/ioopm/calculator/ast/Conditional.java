package org.ioopm.calculator.ast;

/**
 * @file Conditional.java 
 * @author Elias Insulander, Jonathan Tadese 
 * @date 29-11-2019
 * @class Conditional
 * @brief Class for expressing conditionals
 */

public class Conditional extends SymbolicExpression{
// **************************************************
// Attributes
// **************************************************

    private SymbolicExpression left;
    private String op;
    private SymbolicExpression right;
    private Scope leftScope;
    private Scope rightScope;

// **************************************************
// Constructor
// **************************************************

    public Conditional(SymbolicExpression left, String op, SymbolicExpression right, Scope leftScope, Scope rightScope){
	this.left = left;
	this.op = op;
	this.right = right;
	this.leftScope = leftScope;
	this.rightScope = rightScope;
    }

    // **************************************************
    // Public methods
    // **************************************************


   
    /**
     * @brief Get-method used to retrieve Scope 
     * @return Scope used in if-else statement
     */
    public Scope getLeftScope(){
	return this.leftScope;
    }
    
    /**
     * @brief Get-method used to retrieve Scope 
     * @return Scope used in if-else statement
     */
    public Scope getRightScope(){
	return this.rightScope;
    }

    /**
     * @brief Get-method used to retrieve SymbolicExpression 
     * @return SymbolicExpression used in if-else statement
     */
    public SymbolicExpression getLeft(){
	return this.left;
    }

    /**
     * @brief Get-method used to retrieve SymbolicExpression 
     * @return SymbolicExpression used in if-else statement
     */
    public SymbolicExpression getRight(){
	return this.right;
    }
    
  

     public boolean compareConstants(double leftCon, double rightCon){
	switch(this.op){
	case "<":
	    return leftCon < rightCon;
	case ">":
	    return leftCon > rightCon;
	case "<=":
	    return leftCon <= rightCon;
	case ">=":
	    return leftCon >= rightCon;
	case "==":
	    return leftCon == rightCon;
	default:
	    return false;
	}
     }

        
    /**
     * @brief Creates String representation of if-else statement
     * @return String version of if-else statement
     */

    public String toString() {
	return "if " + left + " " + op + " " + right + " " + leftScope + " else " + rightScope;
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
