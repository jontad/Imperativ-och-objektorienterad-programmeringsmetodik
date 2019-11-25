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
    
    public String getName (){
	return "log";
    }

    /**
     * @brief Determines precedence order of where to use parenthesis
     * @return The priority of the class
     */
    public int getPriority () {
	return 50;
    }

    /**
     * @brief Determines equality between undetermied object and object of Class Log
     * @param other undetermined object
     * @return true if equal, else false
     */
    public boolean equals(Object other){
	if (other instanceof Log) {
	    return this.equals((Unary) other);
	} else {
	    return false;
	}
    }

    /**
     * @brief Evaluates SymbolicExpressions
     * @param vars Environment in which to evaluate expression
     * @return A new Constant if argument is a constant. Else new Log
     */
    public SymbolicExpression eval(Environment vars){
	SymbolicExpression arg = this.getExpr().eval(vars);
	if (arg.isConstant()){
	    return new Constant(Math.log(arg.getValue()));
	} else {
	    return new Log(arg);
	}
    }
}
