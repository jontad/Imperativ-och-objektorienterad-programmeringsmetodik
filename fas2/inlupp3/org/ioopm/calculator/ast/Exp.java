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
    public String getName (){
	return "exp";
    }

    /**
     * @brief Determines precedence order of where to use parenthesis
     * @return The priority of the class
     */
    public int getPriority () {
	return 50;
    }

    /**
     * @brief Determines equality between undetermied object and object of Class Exp
     * @param other undetermined object
     * @return true if equal, else false
     */
    public boolean equals(Object other){
	if (other instanceof Exp) {
	    return this.equals((Unary) other);
	} else {
	    return false;
	}
    }

    /**
     * @brief Evaluates SymbolicExpressions
     * @param vars Environment in which to evaluate expression
     * @return A new Constant if argument is a constant. Else new Exp
     */
    public SymbolicExpression eval(Environment vars){
	SymbolicExpression arg = this.getExpr().eval(vars);
	if (arg.isConstant()){
	    return new Constant(Math.exp(arg.getValue()));
	} else {
	    return new Exp(arg);
	}
    }
}
