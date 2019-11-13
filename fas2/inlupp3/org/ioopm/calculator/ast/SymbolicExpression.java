package org.ioopm.calculator.ast;
import java.util.HashMap;

public abstract class SymbolicExpression {
    public SymbolicExpression () {	
    }

    public boolean isConstant(){
	return false;
    }
    public boolean isVariable(){
	return false;
    }
    public boolean isCommand(){
	return false;
    }
    
    public String getName(){
	throw new RuntimeException("getName() called on expression with no operator");
    }

    public int getPriority() {
	return 0;
    }

    public Double getValue(){
	throw new RuntimeException("getValue() called on non constant expression");
    }

    public abstract SymbolicExpression eval(Environment vars);
}
