package org.ioopm.calculator.ast;

public class Sin extends Unary {
    
    public Sin (SymbolicExpression expr){
	super (expr);
    }

    public String getName (){
	return "sin";
    }

    public int getPriority(){
	return 50;
    }
    
    public boolean equals(Object other){
	if (other instanceof Sin) {
	    return this.equals((Unary) other);
	} else {
	    return false;
	}
    }

    public SymbolicExpression eval(Environment vars){
	SymbolicExpression arg = this.getExpr().eval(vars);
	if (arg.isConstant()){
	    return new Constant(Math.sin(arg.getValue()));
	} else {
	    return new Sin(arg);
	}
    }
}
