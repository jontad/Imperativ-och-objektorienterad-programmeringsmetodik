package org.ioopm.calculator.ast;

public abstract class Command extends SymbolicExpression {

    public Command (){
    }

    public boolean isCommand(){
	return true;
    }
    
}
