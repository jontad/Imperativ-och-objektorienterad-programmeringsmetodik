package org.ioopm.calculator.ast;
import java.util.HashMap;

/**
 * @file SymbolicExpression.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class SymbolicExpression
 * @brief Root class of hierarchy. Defines common expressions for all operations of the calculator.
*/

public abstract class SymbolicExpression {

// **************************************************
// Constructor
// **************************************************


    /**
     * @brief Default constructor 
     */
    public SymbolicExpression () {	
    }

// **************************************************
// Public methods
// **************************************************

    /**
     * @brief Determines if object is constant
     * @return true if object is constant, else false
     */
    public boolean isConstant(){
	return false;
    }

    /**
     * @brief Determines if object is variable
     * @return true if object is variable, else false
     */
    public boolean isVariable(){
	return false;
    }

    /**
     * @brief Determines if object is command
     * @return true if object is command, else false
     */
    public boolean isCommand(){
	return false;
    }

    
    /**
     * @brief Retrieve operator name, i.e. "sin", "cos", or "+"
     * @return Operator name if subclass contains overridden method, else throws a RuntimeException
     */
    public String getName(){
	throw new RuntimeException("getName() called on expression with no operator");
    }

    /**
     * @brief Determines precedence order of where to use parenthesis
     * @return The priority of the class
     */
    public int getPriority() {
	return 0;
    }

    /**
     * @brief Get a constant value
     * @return Constant value if subclass contains overriden method, else throws RuntimeException
     */
    public Double getValue(){
	throw new RuntimeException("getValue() called on non constant expression");
    }

    /**
     * @brief Determines which Symbolicexpression to return
     * @param v Carries the visitor 
     * @return Returns determined Symbolicexpression
     */
    public abstract SymbolicExpression accept(Visitor v);
    
}
