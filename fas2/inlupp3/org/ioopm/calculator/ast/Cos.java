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
}
