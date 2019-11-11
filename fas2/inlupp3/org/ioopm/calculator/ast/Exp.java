package org.ioopm.calculator.ast;

public class Exp extends Unary {

    public Exp (SymbolicExpression expr){
	super (expr);
    }
    
    public String getName (){
	return "exp";
    }

    public int getPriority () {
	return 50;
    }
    
    public boolean equals(Object other){
	if (other instanceof Exp) {
	    return this.equals((Unary) other);
	} else {
	    return false;
	}
    }

    public SymbolicExpression eval(Environment vars){
	SymbolicExpression arg = this.getExpr().eval(vars);
	if (arg.isConstant()){
	    return new Constant(Math.exp(arg.getValue()));
	} else {
	    return new Exp(arg);
	}
    }
}
