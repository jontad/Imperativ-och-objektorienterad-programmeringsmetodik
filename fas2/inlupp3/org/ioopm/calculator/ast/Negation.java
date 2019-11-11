package org.ioopm.calculator.ast;

public class Negation extends Unary {

    public Negation (SymbolicExpression expr){
	super (expr);
    }
    
    public String getName (){
	return "-";
    }

    public int getPriority () {
	return 50;
    }
    
    public boolean equals(Object other){
	if (other instanceof Negation) {
	    return this.equals((Unary) other);
	} else {
	    return false;
	}
    }

    public SymbolicExpression eval(Environment vars){
	SymbolicExpression arg = this.getExpr().eval(vars);
	if (arg.isConstant()){
	    return new Constant(-arg.getValue());
	} else {
	    return new Negation(arg);
	}
    }
}
