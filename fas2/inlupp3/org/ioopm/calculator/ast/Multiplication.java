package org.ioopm.calculator.ast;

public class Multiplication extends Binary {

    public Multiplication (SymbolicExpression lhs, SymbolicExpression rhs){
	super (lhs, rhs);
    }

    
    public String getName (){
	return "*";
    }

    public int getPriority () {
	return 100;
    }

}
