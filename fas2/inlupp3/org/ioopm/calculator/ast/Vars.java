package org.ioopm.calculator.ast;


/**
 * @file Vars.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class Vars
 * @brief Singleton Class for command Vars
 */

public class Vars extends Command {
    private boolean runOnce = false;
    private static final Vars theInstance = new Vars();
    
    private Vars (){
	assert !runOnce : "Singleton has been run more than once";
	runOnce = true;
    }
    
       
    /**
     * @brief Get Vars singleton
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
     * @param other Other Object to compare with this SymbolicExpression
     * @return true if equal, else false
     */
    public boolean equals(Object other){
	if (other instanceof Vars) {
	    return true;
	} else {
	    return false;
	}	
    }
}
