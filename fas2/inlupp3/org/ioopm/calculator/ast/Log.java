package org.ioopm.calculator.ast;

public class Log extends Unary {

    public Log (SymbolicExpression expr){
	super (expr);
    }
    
    public String getName (){
	return "log";
    }

    public int getPriority () {
	return 50;
    }
    
    public boolean equals(Object other){
	if (other instanceof Log) {
	    return this.equals((Unary) other);
	} else {
	    return false;
	}
    }

    public SymbolicExpression eval(Environment vars){
	SymbolicExpression arg = this.getExpr().eval(vars);
	if (arg.isConstant()){
	    return new Constant(Math.log(arg.getValue()));
	} else {
	    return new Log(arg);
	}
    }
}
