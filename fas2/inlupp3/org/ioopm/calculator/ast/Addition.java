package org.ioopm.calculator.ast;

public class Addition extends Binary {

    public Addition (SymbolicExpression lhs, SymbolicExpression rhs) {
	super (lhs, rhs);
    }

    public String getName (){
	return "+";
    }
    
    public int getPriority () {
	return 50;
    }

    public boolean equals(Object other){
	if (other instanceof Addition) {
	    return this.equals((Binary) other);
	} else {
	    return false;
	}
    }

    public SymbolicExpression eval(Environment vars){
	SymbolicExpression lhs = this.getLhs().eval(vars);
	SymbolicExpression rhs = this.getRhs().eval(vars);
	if (lhs.isConstant() && rhs.isConstant()){
	    return new Constant(lhs.getValue() + rhs.getValue());
	} else {
	    return new Addition(lhs, rhs);
	}
    }
}
