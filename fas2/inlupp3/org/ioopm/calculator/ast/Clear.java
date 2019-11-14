package org.ioopm.calculator.ast;

public class Clear extends Command {
    private boolean runOnce = false;
    private static final Clear theInstance = new Clear();
    
    private Clear (){
	assert !runOnce : "Singleton has been run more than once";
	runOnce = true;
    }
    

    public static Clear instance() {
        return theInstance;
    }
    
    public String getName (){
	return "Clear";
    }

    public boolean equals(Object other){
	if (other instanceof Clear) {
	    return true;
	} else {
	    return false;
	}	
    }

    public SymbolicExpression eval(Environment vars){
	return null;
    }
}
