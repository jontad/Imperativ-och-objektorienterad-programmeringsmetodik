package org.ioopm.calculator.ast;

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
     * @brief Used to quit the program
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
     * @param other Other variable to compare with current SymbolicExpression
     * @return true if equal, else false
     */
    public boolean equals(Object other){
	if (other instanceof Quit) {
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
