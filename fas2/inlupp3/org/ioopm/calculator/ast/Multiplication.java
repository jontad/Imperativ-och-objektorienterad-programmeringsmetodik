package org.ioopm.calculator.ast;

public class Multiplication extends Binary {

    public Multiplication (SymbolicExpression lhs, SymbolicExpression rhs){
	super (lhs, rhs);
    }

    
    public String getName (){
	return "*";
    }

    public int getPriority () {
	return 100;
    }

    public boolean equals(Object other){
	if (other instanceof Multiplication) {
	    return this.equals((Binary) other);
	} else {
	    return false;
	}
    }

    public SymbolicExpression eval(Environment vars){
	SymbolicExpression lhs = this.getLhs().eval(vars);
	SymbolicExpression rhs = this.getRhs().eval(vars);
	if (lhs.isConstant() && rhs.isConstant()){
	    return new Constant(lhs.getValue() * rhs.getValue());
	} else {
	    return new Multiplication(lhs, rhs);
	}
    }
}
