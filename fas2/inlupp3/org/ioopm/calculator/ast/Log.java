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
    
}
