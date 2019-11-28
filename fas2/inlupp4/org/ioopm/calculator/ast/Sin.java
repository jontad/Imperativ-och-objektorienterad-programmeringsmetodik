package org.ioopm.calculator.ast;

/**
 * @file Sin.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class Sin
 * @brief Class for unary operator Sin
 */

public class Sin extends Unary {
    
    public Sin (SymbolicExpression expr){
	super (expr);
    }

// **************************************************
// Public methods
// **************************************************   

    /**
     * @brief Retrieve operator name
     * @return Operator name 
     */
    @Override
    public String getName (){
	return "sin";
    }
        
    /**
     * @brief Determines precedence order of where to use parenthesis
     * @return The priority of the class
     */
    @Override
    public int getPriority(){
	return 50;
    }

    /**
     * @brief Determines equality between undetermied object and object of Class Sin
     * @param other undetermined object
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object other){
	if (other instanceof Sin) {
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
