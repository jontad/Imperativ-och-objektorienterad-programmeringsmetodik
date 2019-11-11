package org.ioopm.calculator.ast;

public class Variable extends Atom {
    private String variableName;
    
    public Variable (String name){
	this.variableName = name;
    }

    public boolean isVariable(){
	return true;
    }

    public int getPriority(){
	return 200;
    }
    
    public String getName (){
	return variableName;
    }
    
    public String toString(){
	return variableName;
    }
    
    public boolean equals(Object other){
	if (other instanceof Variable) {
	    return this.equals((Variable) other);
	} else {
	    return false;
	}	
    }

    public boolean equals(Variable other){
	return this.variableName.equals(other.variableName);
    }

    public int hashCode(){
	return variableName.hashCode();
    }

    public SymbolicExpression eval(Environment vars){
	SymbolicExpression other = new Variable(variableName);
	SymbolicExpression expr = vars.get(this);
	if (expr != null){
	    return expr;
	} else {
	    return new Variable(variableName);
	}
    }
}
