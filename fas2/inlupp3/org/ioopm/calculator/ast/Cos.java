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
    public String getName (){
	return "cos";
    }
    
    /**
     * @brief Determines precedence order of where to use parenthesis
     * @return The priority of the class
     */
    public int getPriority () {
	return 50;
    }

    /**
     * @brief Determines equality between undetermied object and object of Class Cos
     * @param other undetermined object
     * @return true if equal, else false
     */
    public boolean equals(Object other){
	if (other instanceof Cos) {
	    return this.equals((Unary) other);
	} else {
	    return false;
	}
    }

    /**
     * @brief Evaluates SymbolicExpressions
     * @param vars Environment in which to evaluate expression
     * @return A new Constant if argument is a constant. Else new Cos
     */
    public SymbolicExpression eval(Environment vars){
	SymbolicExpression arg = this.getExpr().eval(vars);
	if (arg.isConstant()){
	    return new Constant(Math.cos(arg.getValue()));
	} else {
	    return new Cos(arg);
	}
    }
}
