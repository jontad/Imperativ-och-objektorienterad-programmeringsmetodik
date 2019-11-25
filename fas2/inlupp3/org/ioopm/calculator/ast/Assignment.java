package org.ioopm.calculator.ast;


/**
 * @file Assignment.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class Assignment
 * @brief Class for binary operation Assignment. Used to assign value to Variables.
 */


public class Assignment extends Binary 
{

// **************************************************
// Constructor
// **************************************************
     
    public Assignment (SymbolicExpression lhs, SymbolicExpression rhs) {
	super (lhs, rhs);
    }
    
// **************************************************
// Public methods
// **************************************************

    /**
     * @brief Retrieve operator name for assignment
     * @return Operator name of class
     */
    public String getName (){
	return "=";
    }
    
    /**
     * @brief Determines precedence order of where to use parenthesis
     * @return The priority of the class
     */
    public int getPriority () {
	return 200;
    }


    /**
     * @brief Determines equality between undetermined object and object of class Assignment
     * @param other undetermined object
     * @return true if equal, else false
     */
    public boolean equals(Object other){
	if (other instanceof Assignment) {
	    return this.equals((Binary) other);
	} else {
	    return false;
	}
    }

    /**
     * @brief Evaluates SymbolicExpressions
     * @param vars Environment in which to evaluate expression
     * @return Evaluation of lhs
     */
    public SymbolicExpression eval(Environment vars){
	SymbolicExpression lhs = this.getLhs().eval(vars);
	SymbolicExpression rhs = this.getRhs();
	if (rhs.isVariable()){
	    vars.put((Variable)rhs, lhs);
	} else {
	    throw new IllegalExpressionException("Right hand side expression may not be a named constant");
	}
	return lhs;
    }
}
