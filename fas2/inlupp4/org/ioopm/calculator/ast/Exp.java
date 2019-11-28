package org.ioopm.calculator.ast;

/**
 * @file Exp.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class Exp
 * @brief Class for unary operator Exp
 */
public class Exp extends Unary {

    public Exp (SymbolicExpression expr){
	super (expr);
    }

// **************************************************
// Public methods
// **************************************************   

    /**
     * @brief Retrieve operator name ("exp")
     * @return Operator name 
     */
    @Override
    public String getName (){
	return "exp";
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
     * @brief Determines equality between undetermied object and object of Class Exp
     * @param other undetermined object
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object other){
	if (other instanceof Exp) {
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
