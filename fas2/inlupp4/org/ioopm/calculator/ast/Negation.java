package org.ioopm.calculator.ast;

/**
 * @file Negation.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class Negation
 * @brief Class for unary operator Negation
 */
public class Negation extends Unary {

    public Negation (SymbolicExpression expr){
	super (expr);
    }

// **************************************************
// Public methods
// **************************************************   

   /**
    * @brief Retrieve operator name ("-")
    * @return Operator name 
    */
    @Override
    public String getName (){
	return "-";
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
     * @brief Determines equality between undetermined object and object of Class Negation
     * @param other undetermined object
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object other){
	if (other instanceof Negation) {
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
