package org.ioopm.calculator.ast;

public class Vars extends Command {
    private boolean runOnce = false;
    private static final Vars theInstance = new Vars();
    
    private Vars (){
	assert !runOnce : "Singleton has been run more than once";
	runOnce = true;
    }
    

    public static Vars instance() {
        return theInstance;
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
