package org.ioopm.calculator.ast;

/**
 * @file FunctionDeclaration.java 
 * @author Elias Insulander, Jonathan Tadese 
 * @date 29-11-2019
 * @class FunctionDeclaration
 * @brief Class that keeps track of name of function. 
 * Works in tandem with class Sequence and FunctionCall.
 */
 
public class FunctionDeclaration extends SymbolicExpression{
    String nameOfFunction; 
    Sequence body;
    
    public FunctionDeclaration(String nameOfFunction, Sequence body) {
	this.nameOfFunction = nameOfFunction;
	this.body = body;
    }

    /**
     * @brief Get-method used to retrieve name of function  
     * @return Name of function
     */
     public String getNameOfFunc() {return this.nameOfFunction;}

    /**
     * @brief Get-method used to retrieve Sequence 
     * @return Sequence containing code of function 
     */
    public Sequence getSequence() {return this.body;}

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
