package org.ioopm.calculator.ast;

public abstract class Unary extends SymbolicExpression {
    private SymbolicExpression expr = null;
    
    public Unary (SymbolicExpression expr){
	this.expr = expr;
    }

    public int getPriority () {
	return 150;
    }

    public String toString(){
	return this.getName() + "(" + expr + ")";
    }

    public boolean equals(Unary other){
	return this.expr.equals(other.expr);
    }

    protected SymbolicExpression getExpr(){
	return expr;
    }
}
