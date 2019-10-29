
public class Register{
    private boolean open;
    private Queue<Customer> queue;

    public Register(){
	this.open = false;
	this.queue = new Queue<Customer>();
    }

    public void open(){
	this.open = true;
    }

    public void close(){
	this.open = false;
    }

    public boolean isOpen(){
	return this.open;
    }
    
    public void step(){
	Customer customer = this.queue.first(); 
	customer.serve();
	 
    }
 
    public boolean hasCustomer(){
	return this.queue.length() > 0;
        
    }

    public boolean currentCustomerIsDone(){
	if(queue.length() != 0){
	    Customer customer = this.queue.first(); 
	    return customer.isDone();
	}
	else return false;
    }

    public void addToQueue(Customer c){
	this.queue.enqueue(c);
    }

    public Customer removeCurrentCustomer(){
	return this.queue.dequeue();
    }

    public int getQueueLength(){
	return this.queue.length();
    }

    public Customer getFirstCustomer(){
	return this.queue.first();
    }
    
    private String amountOfCustomers(){
	String customersToPrint = "  " + this.queue.first() + "";
	for (int i = 0; i < queue.length() - 1; i++){
	    customersToPrint += "@";
	}
	return customersToPrint + "\n";
    }

    public String toString(){
	if(this.open && 0 < queue.length()){
	    String customers = amountOfCustomers();
	    return customers;
	}
	else if(this.open) return "  [ ] \n";
	else return "x [ ]\n";
    }

	    
    
    
    /*    public static void main(String[] args){
	Register reg = new Register();
	
	Customer cust1 = new Customer(0,9);
	Customer cust2 = new Customer(0,7);
	Customer cust3 = new Customer(0,2);

	reg.addToQueue(cust1);
	reg.addToQueue(cust2);
	reg.addToQueue(cust3);
	
	reg.open();

	for (; reg.hasCustomer();) {

	    System.out.println(reg.step() + 1);
	    
	    if(reg.currentCustomerIsDone())
		{
		    reg.removeCurrentCustomer();
		}
	}
	
	
	}*/
    
}
