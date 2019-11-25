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
    public String getName (){
	return "-";
    }

    /**
     * @brief Determines precedence order of where to use parenthesis
     * @return The priority of the class
     */
    public int getPriority () {
	return 50;
    }

    /**
     * @brief Determines equality between undetermined object and object of Class Negation
     * @param other undetermined object
     * @return true if equal, else false
     */
    public boolean equals(Object other){
	if (other instanceof Negation) {
	    return this.equals((Unary) other);
	} else {
	    return false;
	}
    }

    /**
     * @brief Evaluates SymbolicExpressions
     * @param vars Environment in which to evaluate expression
     * @return A new Constant if argument is a constant. Else new Negation
     */
    public SymbolicExpression eval(Environment vars){
	SymbolicExpression arg = this.getExpr().eval(vars);
	if (arg.isConstant()){
	    return new Constant(-arg.getValue());
	} else {
	    return new Negation(arg);
	}
    }
}
