package org.ioopm.calculator.ast;

public class Variable extends Atom {
    private String variableName;
    
    public Variable (String name){
	this.variableName = name;
    }

    public int getPriority(){
	return 200;
    }
    
    public String getName (){
	return variableName;
    }
    
    public String toString(){
	return variableName;
    }
}
