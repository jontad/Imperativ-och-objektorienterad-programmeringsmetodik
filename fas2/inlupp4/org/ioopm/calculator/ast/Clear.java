package org.ioopm.calculator.ast;

/**
 * @file Clear.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class Clear
 * @brief Singleton Class for command Clear
 */

public class Clear extends Command {
    private boolean runOnce = false;
    private static final Clear theInstance = new Clear();
    
    private Clear (){
	assert !runOnce : "Singleton has been run more than once";
	runOnce = true;
    }
    
// **************************************************
// Public methods
// **************************************************   
       
    /**
     * @brief Get Clear singleton
     * @return The instance of Clear
     */ 

    public static Clear instance() {
        return theInstance;
    }

    /**
     * @brief Retrieve name of object
     * @return Name of object
     */
    @Override
    public String getName (){
	return "Clear";
    }
    
    /**
     * @brief Determines equality between object of type Clear and other undetermined object
     * @param other Other Object to compare with this SymbolicExpression
     * @return true if equal, else false
     */
    public boolean equals(Object other){
	if (other instanceof Clear) {
	    return true;
	} else {
	    return false;
	}	
    }
}
