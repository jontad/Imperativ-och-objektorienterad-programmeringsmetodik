package org.ioopm.calculator.ast;
import java.util.LinkedList;

/**
 * @file FunctionCall.java 
 * @author Elias Insulander, Jonathan Tadese 
 * @date 29-11-2019
 * @class FunctionCall
 * @brief Class that calls function with function (i.e. factorial(2))
 */

public class FunctionCall extends SymbolicExpression{
    LinkedList<SymbolicExpression> argFuncCall = null;
    String identifier; 

    public FunctionCall(LinkedList<SymbolicExpression> argFuncCall, String identifier){
	this.argFuncCall = argFuncCall;
	this.identifier = identifier;
    }


    
// **************************************************
// Public methods
// **************************************************   

    /**
     * @brief Get-method used to retrieve identifier for Function  
     * @return An identifier 
     */
    public String getIdentifier() {return this.identifier;}

    /**
     * @brief Get-method used to retrieve arguments during function call  
     * @return Arguments
     */
    public LinkedList<SymbolicExpression> getArgFuncCall() {return this.argFuncCall;}
    
     /**
     * @brief Determines amount of arguments in linked list
     * @return Size of list
     */
    public int amountOfArg() {return argFuncCall.size();}

    /**
     * @brief Determines which Symbolicexpression to return
     * @param v Carries the visitor 
     * @return Returns determined Symbolicexpression
     */
    @Override
    public SymbolicExpression accept(Visitor v) {
	return v.visit(this);
    }

}
