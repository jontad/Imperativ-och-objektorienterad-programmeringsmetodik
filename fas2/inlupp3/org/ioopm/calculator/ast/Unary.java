package org.ioopm.calculator.ast;

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
     * @brief Determines equality between objects of class Unary
     * @param other Other unary to compare with current unary  
     * @return true if equal, else false
     */
    public boolean equals(Unary other){
	return this.expr.equals(other.expr);
    }
    
    /**
     * @brief Get method to retrieve expression of object
     * @return expression of object
     */
    protected SymbolicExpression getExpr(){
	return expr;
    }
}
