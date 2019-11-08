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
}
