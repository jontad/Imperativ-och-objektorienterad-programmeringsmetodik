package org.ioopm.calculator.ast;

public class Vars extends Command {
    private boolean runOnce = false;
    private static final Vars theInstance = new Vars();
    
    private Vars (){
	assert !runOnce : "Singleton has been run more than once";
	runOnce = true;
    }
    
       
    /**
     * @brief Get the instance of the object
     * @return The instance of Vars
     */ 
    public static Vars instance() {
        return theInstance;
    }
    
       
    /**
     * @brief Retrieve name of object
     * @return Name of object
     */
    public String getName (){
	return "Vars";
    }

    /**
     * @brief Determines equality between object of type Vars and other undetermined object
     * @param other Other variable to compare with current SymbolicExpression
     * @return true if equal, else false
     */
    public boolean equals(Object other){
	if (other instanceof Vars) {
	    return true;
	} else {
	    return false;
	}	
    }

    
    /**
     * @brief Evaluates class
     * @return ????????????????
     */
    public SymbolicExpression eval(Environment vars){
	return null;
    }
}
