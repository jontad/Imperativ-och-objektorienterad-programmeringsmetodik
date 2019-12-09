package org.ioopm.calculator.ast;

/**
 * @file Scope.java 
 * @author Elias Insulander, Jonathan Tadese 
 * @date 29-11-2019
 * @class Scope
 * @brief Class for binding variables only in a scope
 */
 
public class Scope extends SymbolicExpression{
    private SymbolicExpression expr = null;

    
    public Scope (SymbolicExpression expr) {
	this.expr = expr;
    }
    

// **************************************************
// Public methods
// **************************************************   

    /**
     * @brief Convert Scope to string
     * @return Scope as string
     */
    public String toString(){
	return "{" + expr + "}";
    }
    

    public SymbolicExpression getExpr(){
	return this.expr;
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
