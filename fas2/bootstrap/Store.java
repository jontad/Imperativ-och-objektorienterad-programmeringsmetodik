import java.util.*;

public class Store{
    private Register registers[];
    private Customer[] doneCustomers;
    
    public Store(int amountOfRegisters){
	if(amountOfRegisters < 1){
	    throw new IllegalArgumentException(amountOfRegisters + " is an illegal amount of registers");
	}
	this.registers = new Register[amountOfRegisters];	
	for (int i = 0; i < this.registers.length; i++) {
	    registers[i] = new Register();
	}
	registers[0].open();
    }

    public int getAverageQueueLength(){
	int length = 0;
	int openRegisters = 0;
	
	for (int i = 0; i < registers.length; i++){
	    if(registers[i].isOpen()){
		openRegisters++;
		length += registers[i].getQueueLength();
	    }
	}
	if(openRegisters == 0) return 0;
	return length/openRegisters;
    }

    
    public void newCustomer(Customer c){
	int shortestQueue = this.registers[0].getQueueLength();
	Register reg  = this.registers[0];
	for (int i = 1; i < registers.length; i++) {

	    if(registers[i].isOpen() && registers[i].getQueueLength() < shortestQueue){
		shortestQueue = registers[i].getQueueLength();
	        reg = registers[i];
	    }	
	}
	reg.addToQueue(c);
    }

    public void step(){
	for (int i = 0; i < registers.length; i++) {
	    if(registers[i].isOpen() && registers[i].hasCustomer()){
		registers[i].step();
	    }
	}
    }

    public void openNewRegister(){
	for (int i = 0; i < registers.length; i++) {

	    if (!registers[i].isOpen()){
		registers[i].open();
		break;
	    }
	}
    }

    public Customer[] getDoneCustomers(){

	int counter = 0;
	
	Customer[] doneCustomers = new Customer[registers.length];
	for(Register reg : registers){
	    if(reg.currentCustomerIsDone()){
		doneCustomers[counter] = reg.removeCurrentCustomer();	    
		counter++;
	    }
	}
	return doneCustomers;
    }
    
    private String registersToString(){
	String regToString = new String();
	for (int i = 0; i < registers.length; i++) {
	    regToString += registers[i].toString();
	}
	return regToString;
    }
    
    public String toString(){
	String result = registersToString();
	return result;
    }
}




