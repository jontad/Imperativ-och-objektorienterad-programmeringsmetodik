package org.ioopm.calculator.ast;

/**
 * @file Constant.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class Constant
 * @brief Class for representing constant values.
 */

public class Constant extends Atom {

// **************************************************
// Attributes
// **************************************************
    /**
     * @param value Value of constant 
     */
    private double value;

// **************************************************
// Constructors
// **************************************************

     /**
     * @brief Creates new Constant with a given value
     * @param value Value of constant 
     */
    public Constant (double value){
	this.value = value;
    }

// **************************************************
// Public methods
// **************************************************

    /**
     * @brief Determines if object is constant
     * @return true if object is constant, else false
     */
    public boolean isConstant (){
	return true;
    }

    /**
     * @brief Determines precedence order of where to use parenthesis
     * @return The priority of the class
     */
    public int getPriority(){
	return 200;
    }

    /**
     * @brief Retrieve string of value
     * @return String of value, in current constant
     */
    public String getName(){
	return Double.toString(value);
    }

    /**
     * @brief Retrieve value of constant
     * @return The value of object Constant
     */
    public Double getValue(){
	return value;
    }

    /**
     * @brief Retrieve string of value
     * @return String of value, in current constant
     */
    public String toString(){
	return String.valueOf(this.value);
    }

     /**
     * @brief Determines equality between Constant and other Object
     * @param other Other Object to compare with this constant
     * @return true if other is a constant and they are equal, else false
     */
    public boolean equals(Object other){
	if (other instanceof Constant) {
	    return this.equals((Constant) other);
	} else {
	    return false;
	}	
    }

    /**
     * @brief Determines equality between Constants
     * @param other Other Constant to compare with this Constant  
     * @return true if equal, else false
     */
    public boolean equals(Constant other){
	return this.value == other.value;
    }

    /**
     * @brief Evaluates SymbolicExpressions
     * @param vars Environment in which to evaluate expression
     * @return A new constant 
     */
    public SymbolicExpression eval(Environment vars){
	return new Constant(value);
    }
}
 
