
public class Customer {

    private int bornTime;
    private int groceries;

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

    public int time() {
	return bornTime;
    }

    public String groceriesToString(){
	return Integer.toString(groceries);
    }

    public static void main(String[] args){
	Customer groceries = new Customer(0,9);
	for (; !groceries.isDone(); groceries.serve()) {
	    System.out.println(groceries);   
	}
    }	    
}

