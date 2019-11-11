package org.ioopm.calculator.ast;

public class Cos extends Unary {

    public Cos (SymbolicExpression expr){
	super (expr);
    }
    
    public String getName (){
	return "cos";
    }

    public int getPriority () {
	return 50;
    }
    
    public boolean equals(Object other){
	if (other instanceof Cos) {
	    return this.equals((Unary) other);
	} else {
	    return false;
	}
    }

    public SymbolicExpression eval(Environment vars){
	SymbolicExpression arg = this.getExpr().eval(vars);
	if (arg.isConstant()){
	    return new Constant(Math.cos(arg.getValue()));
	} else {
	    return new Cos(arg);
	}
    }
}
