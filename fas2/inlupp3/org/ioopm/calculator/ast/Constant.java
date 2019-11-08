package org.ioopm.calculator.ast;

public class Constant extends Atom {
    private double value;
    
    public Constant (double value){
	/// Does it need to call super?
	this.value = value;
    }

    public boolean isConstant (){
	return true;
    }

    public int getPriority(){
	return 200;
    }

    public String getName(){
	return Double.toString(value);
    }

    public Double getValue(){
	return value;
    }
    
    public String toString(){
	return String.valueOf(this.value);
    }
}
 
