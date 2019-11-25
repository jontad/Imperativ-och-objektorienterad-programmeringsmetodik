package org.ioopm.calculator.ast;

/**
 * @file Quit.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class Quit
 * @brief Singleton Class for command Quit
 */

public class Quit extends Command {
    private boolean runOnce = false;
    private static final Quit theInstance = new Quit();
    
    private Quit (){
	assert !runOnce : "Singleton has been run more than once";
	runOnce = true;
    }

// **************************************************
// Public methods
// **************************************************   
       
    /**
     * @brief Get Quit singleton
     * @return The instance of Quit
     */ 
    public static Quit instance(){
	return theInstance;
    }
    
    /**
     * @brief Retrieve name of object
     * @return Name of object
     */
    public String getName (){
	return "Quit";
    }

    /**
     * @brief Determines equality between object of type Quit and other undetermined object
     * @param other Other Object to compare with this SymbolicExpression
     * @return true if equal, else false
     */
    public boolean equals(Object other){
	if (other instanceof Quit) {
	    return true;
	} else {
	    return false;
	}	
    }

}
