package org.ioopm.calculator.ast;

/**
 * @file Log.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class Log
 * @brief Class for unary operator Log
 */
public class Log extends Unary {

    public Log (SymbolicExpression expr){
	super (expr);
    }
    
// **************************************************
// Public methods
// **************************************************   

    /**
     * @brief Retrieve operator name ("log")
     * @return Operator name 
     */
    @Override
    public String getName (){
	return "log";
    }

    /**
     * @brief Determines precedence order of where to use parenthesis
     * @return The priority of the class
     */
    @Override
    public int getPriority () {
	return 50;
    }

    /**
     * @brief Determines equality between undetermied object and object of Class Log
     * @param other undetermined object
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object other){
	if (other instanceof Log) {
	    return this.equals((Unary) other);
	} else {
	    return false;
	}
    }


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
