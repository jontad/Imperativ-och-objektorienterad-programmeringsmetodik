package org.ioopm.calculator.ast;

public abstract class Binary extends SymbolicExpression {
    private SymbolicExpression lhs = null;
    private SymbolicExpression rhs = null;
    
    public Binary (SymbolicExpression lhs, SymbolicExpression rhs){
	this.lhs = lhs;
	this.rhs = rhs;
    }

    protected SymbolicExpression getLhs(){
	return lhs;
    }
    
    protected SymbolicExpression getRhs(){
	return rhs;
    }

    public boolean equals(Object other){
	if (other instanceof Binary) {
	    return this.equals((Binary) other);
	} else {
	    return false;
	}
    }

    public boolean equals(Binary other){
	return this.lhs.equals(other.lhs) && this.rhs.equals(other.rhs);
    }
    
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
