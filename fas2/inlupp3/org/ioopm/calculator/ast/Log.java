package org.ioopm.calculator.ast;

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
     * @brief Determines equality between undetermied object and object of clas Log
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
     * @param vars Hashmap containing SymbolicExpressions to be evaluated
     * @return A new Constant if arguemnet is a constant. Else new Log
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
