package org.ioopm.calculator.ast;

public abstract class SymbolicExpression {
    public SymbolicExpression () {
	
    }

    public boolean isConstant(){
	return false;
    }
    public String getName(){
	throw new RuntimeException("getName() called on expression with no operator");
    }

    public int getPriority () {
	return 0;
    }

    public Double getValue(){
	throw new RuntimeException("getValue() called on non constant expression");
    }

    public boolean equals(Object other){
        if(other instanceof SymbolicExpression) return true;
	else return false;     
	
	    
    }

	/*public SymbolicExpression eval(){
	return null;
    }*/
}
