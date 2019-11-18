package org.ioopm.calculator.ast;

public abstract class Command extends SymbolicExpression {

    public Command (){
    }

// **************************************************
// Public methods
// **************************************************   
    
    /**
     * @brief Determines if object is command
     * @return true if object is command, else false
     */
    public boolean isCommand(){
	return true;
    }   
}
