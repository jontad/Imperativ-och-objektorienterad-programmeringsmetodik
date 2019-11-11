package org.ioopm.calculator.ast;

public class Assignment extends Binary {
    
    public Assignment (SymbolicExpression lhs, SymbolicExpression rhs) {
	super (lhs, rhs);
    }
    
    public String getName (){
	return "=";
    }

    public int getPriority () {
	return 200; //?
    }

    public boolean equals(Object other){
	if (other instanceof Assignment) {
	    return this.equals((Binary) other);
	} else {
	    return false;
	}
    }

    public SymbolicExpression eval(Environment vars){
	SymbolicExpression lhs = this.getLhs().eval(vars);
	SymbolicExpression rhs = this.getRhs();
	if (rhs.isVariable()){
	    vars.put((Variable)rhs, lhs);
	} else {
	    throw new IllegalArgumentException("Right hand side expression must be a variable name"); //TODO is this the best type of exception
	}
	return lhs;
    }
}
