import java.util.*;

public class Store{
    private Register registers[];
    private String printRegisters;

    
    public Store(int amountOfRegisters){
	this.registers = new Register[amountOfRegisters];
	this.printRegisters = new String();
	
	for (int i = 0; i < registers.length; i++) {
	    this.registers[i] = new Register();
	}
    }

    public int getAverageQueueLength(){
	int length = 0;
	
	for (int i = 0; i < registers.length; i++) {
	    int regLength = registers[i].getQueueLength();
	    if(registers[i].isOpen()){
		length = regLength + length;
	    }
	}
	return length/(registers.length);
    }

    
    public void newCustomer(Customer c){
	int shortestQueue = registers[0].getQueueLength();
	Register reg  = registers[0];
	for (int i = 1; i < registers.length; i++) {

	    if(registers[i].getQueueLength() < shortestQueue){
		shortestQueue = registers[i].getQueueLength();
	        reg = registers[i];
	    }	
	}
	reg.addToQueue(c);
    }

    public void step(){
	for (int i = 0; i < registers.length; i++) {
	    registers[i].step();
	}
    }

    public void openNewRegister(){
	for (int i = 0; i < registers.length; i++) {
	    if (registers[i].isOpen()){
		registers[i].open();
		break;
	    }
	}
    }

    public Customer[] getDoneCustomers(){
	
        int counter = 0;
	for (int i = 0; i < registers.length; i++) {

	    if(registers[i].currentCustomerIsDone()){
		counter++;
	    }
	}
	Customer[] doneCustomers = new Customer[counter];
	
	for (int i = 0; i < counter; i++) {

	    if(registers[i].currentCustomerIsDone()){
		doneCustomers[i] = registers[i].removeCurrentCustomer(); 
	    }
	}	
	return doneCustomers;
    }

    public void registersToString(){
	for (int i = 0; i < registers.length; i++) {
	    this.printRegisters += registers[i].toString();
	}
    }
    
    public String toString(){
	registersToString();
	return printRegisters;
    }
    
}




