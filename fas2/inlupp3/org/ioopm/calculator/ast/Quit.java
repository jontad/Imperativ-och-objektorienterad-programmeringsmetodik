package org.ioopm.calculator.ast;

public class Quit extends Command {
    private boolean runOnce = false;
    private static final Quit theInstance = new Quit();
    
    private Quit (){
	assert !runOnce : "Singleton has been run more than once";
	runOnce = true;
    }

    public static Quit instance(){
	return theInstance;
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
