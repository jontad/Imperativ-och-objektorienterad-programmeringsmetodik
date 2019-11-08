package org.ioopm.calculator.ast;

public class Sin extends Unary {
    
    public Sin (SymbolicExpression expr){
	super (expr);
    }

    public String getName (){
	return "sin";
    }
}
