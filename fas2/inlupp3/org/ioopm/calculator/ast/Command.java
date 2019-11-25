package org.ioopm.calculator.ast;

/**
 * @file Command.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class Command
 * @brief Common superclass of all Commands (Quit, Vars, Clear)  
 */

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
    
    /**
     * @brief Commands are not evaluated. Attempting to evaluate a command will result in a RuntimeException
     * @param vars Environment in which to evaluate expression
     * @return no return
     */
    public SymbolicExpression eval(Environment vars){
        throw new RuntimeException("Attempted to evaluate a Command");
    }
}
