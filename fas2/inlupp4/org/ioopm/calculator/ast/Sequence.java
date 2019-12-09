package org.ioopm.calculator.ast;
import java.util.LinkedList;

/**
 * @file Sequence.java 
 * @author Elias Insulander, Jonathan Tadese 
 * @date 29-11-2019
 * @class Sequence
 * @brief Class that contains body and parameters of function. 
 * Works in tandem with class FunctionDeclaration and FunctionCall.
 */

public class Sequence extends SymbolicExpression{
    private LinkedList<String> argFunc;
    private LinkedList<SymbolicExpression> body;
        
    
    public Sequence(LinkedList<String> argFunc, LinkedList<SymbolicExpression> body){
	this.argFunc = argFunc;
 	this.body = body;
    }

       
// **************************************************
// Public methods
// **************************************************   

    /**
     * @brief Get-method used to retrieve list of arguments  
     * @return List of arguments for function
     */
     public LinkedList<String> getArgFunc() {return this.argFunc;}

    /**
     * @brief Get-method used to retrieve linked list containing an entire function 
     * @return Linked list with entire function 
     */
    public LinkedList<SymbolicExpression> getBody() {return this.body;}
    
    
    /**
     * @brief Determines amount of arguments in linked list
     * @return Size of list
     */
    public int amountOfArg() {return argFunc.size();}

    
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
