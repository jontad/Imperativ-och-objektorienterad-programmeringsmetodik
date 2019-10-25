import java.util.*;

public class Store{
    Register registers[];

    
    public Store(int amountOfRegisters){
	this.registers = new Register[amountOfRegisters];
	for (int i = 0; i < registers.length; i++) {
	    this.registers[i] = new Register();
	}
    }

    public int getAverageQueueLength(){
	int length = 0;
	
	for (int i = 0; i < registers.length; i++) {
	    int regLength = registers[i].getQueueLength(); 
	    length = regLength + length;
	}
	return length/(registers.length);
    }

    //?
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


    //tiden går ett steg i varuhuset
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

    public List getDoneCustomers(){
	List doneCustomers = new LinkedList();
	for (int i = 0; i < registers.length; i++) {

	    if(registers[i].currentCustomerIsDone()){
		Customer doneCustomer = registers[i].removeCurrentCustomer();
		doneCustomers.add(doneCustomer);
	    }
	}
	
	return doneCustomers;
    }

    
}




