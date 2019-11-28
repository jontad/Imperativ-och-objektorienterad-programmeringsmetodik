package org.ioopm.calculator.ast;

/**
 * @file Unary.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class Unary
 * @brief Common superclass of all unary operations (Sin, Cos, Log, Exp, Negation)  
 */

public abstract class Unary extends SymbolicExpression {
    private SymbolicExpression expr = null;
    
    public Unary (SymbolicExpression expr){
	this.expr = expr;
    }

// **************************************************
// Public methods
// **************************************************   

    /**
     * @brief Determines precedence order of where to use parenthesis
     * @return The priority of the class
     */
    @Override
    public int getPriority () {
	return 150;
    }

    
    /**
     * @brief Convert unary to string
     * @return Unary as string
     */
    public String toString(){
	return this.getName() + "(" + expr + ")";
    }
    
    /**
     * @brief Determines equality to other Object
     * @param other Other Object to compare with this Unary
     * @return true if other is a Unary and they are equal, else false
     */
    public boolean equals(Object other){
	if (other instanceof Unary) {
	    return this.equals((Unary) other);
	} else {
	    return false;
	}
    }
    
    /**
     * @brief Determines equality between objects of class Unary
     * @param other Other unary to compare with this unary  
     * @return true if equal, else false
     */
    public boolean equals(Unary other){
	return this.expr.equals(other.expr);
    }
    
    /**
     * @brief Get method to retrieve expression field of this Unary
     * @return expression of Unary
     */
    protected SymbolicExpression getExpr(){
	return expr;
    }
}
