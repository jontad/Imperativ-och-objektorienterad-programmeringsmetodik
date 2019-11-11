package org.ioopm.calculator.ast;

public class Quit extends Command {

    public Quit (){

    }
    
    public String getName (){
	return "Quit";
    }

    public boolean equals(Object other){
	if (other instanceof Quit) {
	    return true;
	} else {
	    return false;
	}	
    }

    public SymbolicExpression eval(Environment vars){
	return null;
    }
}
