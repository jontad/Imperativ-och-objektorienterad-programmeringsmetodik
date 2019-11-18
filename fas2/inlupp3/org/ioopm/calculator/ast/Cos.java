package org.ioopm.calculator.ast;

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
     * @brief Determines equality between undetermied object and object of clas Cos
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
     * @param vars Hashmap containing SymbolicExpressions to be evaluated
     * @return A new Constant if arguemnet is a constant. Else new Cos
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
