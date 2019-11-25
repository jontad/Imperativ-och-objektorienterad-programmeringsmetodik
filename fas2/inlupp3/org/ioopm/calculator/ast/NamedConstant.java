package org.ioopm.calculator.ast;
import org.ioopm.calculator.parser.Constants;

/**
 * @file NamedConstant.java 
 * @author Patrik Johansson, Jonathan Tadese 
 * @date 15-11-2019
 * @class NamedConstant
 * @brief Class for representing a named constant, named constants must be present in org.ioopm.calculator.parser.Constants.
 */

public class NamedConstant extends Constant{
    private String constantName;

    
     /**
     * @brief Constructor for NamedConstant
     * @param name of NamedConstant
     */
    public NamedConstant(String name){
        super(Constants.namedConstants.get(name));
	if (!Constants.namedConstants.containsKey(name)){
	    throw new IllegalArgumentException("Invalid name for named constant");
	}
	this.constantName = name;
    }

    /**
     * @brief Getter for field constantName
     * @return constantName
     */
    public String getName(){
	return constantName;
    }
    
    /**
     * @brief Convert NamedConstant to string
     * @return String representation of NamedConstant
     */
    public String toString(){
	return constantName;
    }
}
