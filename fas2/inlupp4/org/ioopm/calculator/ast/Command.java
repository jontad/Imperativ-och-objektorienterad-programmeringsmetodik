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
    @Override
    public boolean isCommand(){
	return true;
    }
    
    /**
     * @brief Determines which Symbolicexpression to return
     * @param v Carries the visitor 
     */
    @Override
    public SymbolicExpression accept(Visitor v){
        throw new RuntimeException("Attempted to accept a Command");
    }

}
