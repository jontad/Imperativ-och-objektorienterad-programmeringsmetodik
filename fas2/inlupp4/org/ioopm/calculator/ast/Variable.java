package org.ioopm.calculator.ast;

/**
 * @file Variable.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class Variable
 * @brief Class for representing a Variable.
 */

public class Variable extends Atom implements Comparable<Variable> { ///TODO: Helt osäker på detta. Kolla Ticket 2 step 3 !!!!!!!!!!
    private String variableName;
   
     /**
     * @brief Constructor for Variable
     * @param name of variable
     */
    public Variable (String name){
	this.variableName = name;
    }
    
// **************************************************
// Public methods
// **************************************************   

    
    /**
     * @brief Determines if object is variable
     * @return true if object is variable, else false
     */
    @Override
    public boolean isVariable(){
	return true;
    }

    /**
     * @brief Determines precedence order of where to use parenthesis
     * @return The priority of the class
     */
    @Override
    public int getPriority(){
	return 200;
    }

    /**
     * @brief Retrieve variable name
     * @return Name of current variable object
     */
    @Override
    public String getName (){
	return variableName;
    }

    /**
     * @brief Convert variable name to string
     * @return Name of current object
     */
    public String toString(){
	return variableName;
    }

    /**
     * @brief Determines equality between undetermied object and object of class Variable
     * @param other undetermined object
     * @return true if equal, else false
     */
    public boolean equals(Object other){
	if (other instanceof Variable) {
	    return this.equals((Variable) other);
	} else {
	    return false;
	}	
    }

    /**
     * @brief Determines equality between objects of type Variable
     * @param other Other Object to compare with this variable
     * @return true if equal, else false
     */
    public boolean equals(Variable other){
	return this.variableName.equals(other.variableName);
    }

    /**
     * @brief Determines hashcode of current variable
     * @return Hashcode of variable name of current variable
     */
    public int hashCode(){
	return variableName.hashCode();
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

    public int compareTo(Variable other){
	String otherVarName = other.getName();
	return variableName.compareTo(otherVarName);
    }
}
