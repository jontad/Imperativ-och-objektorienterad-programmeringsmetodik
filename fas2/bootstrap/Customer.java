
public class Customer {

    int bornTime;
    int groceries;

    public Customer(int time, int amount) {
	this.bornTime = time;
	this.groceries = amount;
    }
    
    public int serve() {
	 this.groceries = groceries - 1;
	 return this.groceries;
    }

    public boolean isDone() {
	if (this.groceries == 0) return true;
	else return false;
    }

    public String toString() {
	return "Groceries processed: " + groceries;
    }

    public static void main(String[] args){
	Customer groceries = new Customer(0,9);
	for (; !groceries.isDone(); groceries.serve()) {
	    System.out.println(groceries);
    
	}
    	
    }	    
	
}

