package org.ioopm.calculator.ast;

public class Vars extends Command {

    public Vars (){

    }
    
    public String getName (){
	return "Vars";
    }

    public boolean equals(Object other){
	if (other instanceof Vars) {
	    return true;
	} else {
	    return false;
	}	
    }

    public SymbolicExpression eval(Environment vars){
	return null;
    }
}
