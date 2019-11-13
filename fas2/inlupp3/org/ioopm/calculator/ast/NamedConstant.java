package org.ioopm.calculator.ast;
import org.ioopm.calculator.parser.Constants;

public class NamedConstant extends Constant{
    private String constantName;

    public NamedConstant(String name){
        super(Constants.namedConstants.get(name));
	if (!Constants.namedConstants.containsKey(name)){
	    throw new IllegalArgumentException("Invalid name for named constant");
	}
	this.constantName = name;
    }

    public String getName(){
	return constantName;
    }

    public String toString(){
	return constantName;
    }
}
