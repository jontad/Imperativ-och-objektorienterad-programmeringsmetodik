
public class Register{
    private boolean open;
    private Queue queue;
    private String customersPrint;

    public Register(){
	this.open = false;
	this.queue = new Queue();
	this.customersPrint = new String();
    }

    public boolean open(){
	return this.open = true;
    }

    public boolean close(){
	return this.open = false;
    }

    public boolean isOpen(){
	if(open) return true;
	else return false;
    }
    
    public void step(){
	if(queue.length != 0){
	 queue.first().serve();
	}
    }
 
    public boolean hasCustomer(){
	if(queue.length() == 0) return false;
	else return true;
    }

    public boolean currentCustomerIsDone(){
	if(queue.length != 0) return queue.first().isDone();
	else return false;
    }

    public void addToQueue(Customer c){
	queue.enqueue(c);
    }

    public Customer removeCurrentCustomer(){
	return queue.dequeue();
    }

    public int getQueueLength(){
	return queue.length();
    }

    public void amountOfCustomers(){
	for (int i = 0; i < queue.length() - 1; i++){
	    this.customersPrint += "@";
	}
    }

    //dont forget groceries ([n])
    public String toString(){
	if(!open){
	    amountOfCustomers();
	    //String groceries = queue.first().groceriesToString();
	   return "[ ]" + customersPrint;
	}
	else return "x [ ]";
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
