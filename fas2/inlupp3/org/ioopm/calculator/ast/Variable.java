package org.ioopm.calculator.ast;

public class Variable extends Atom {
    private String variableName;
    
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
    public boolean isVariable(){
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
     * @brief Retrieve variable name
     * @return Name of current variable object
     */
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
     * @param other Other variable to compare with current variable
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
     * @brief Evaluates SymbolicExpressions
     * @param vars Hashmap containing SymbolicExpressions to be evaluated
     * @return A new variable if expression is not null. Else correspondening expression to current variable in vars
     */
    public SymbolicExpression eval(Environment vars){
	SymbolicExpression other = new Variable(variableName);
	SymbolicExpression expr = vars.get(this);
	if (expr != null){
	    return expr;
	} else {
	    return new Variable(variableName);
	}
    }
}
