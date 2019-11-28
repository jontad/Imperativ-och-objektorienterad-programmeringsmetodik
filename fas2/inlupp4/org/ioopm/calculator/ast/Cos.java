package org.ioopm.calculator.ast;

/**
 * @file Cos.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class Cos
 * @brief Class for unary operator Cos
 */
public class Cos extends Unary {

    public Cos (SymbolicExpression expr){
	super (expr);
    }

// **************************************************
// Public methods
// **************************************************   

    /**
     * @brief Retrieve operator name ("cos")
     * @return Operator name 
     */
    @Override
    public String getName (){
	return "cos";
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
     * @brief Determines equality between undetermied object and object of Class Cos
     * @param other undetermined object
     * @return true if equal, else false
     */
    @Override
    public boolean equals(Object other){
	if (other instanceof Cos) {
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
