
public class Customer {

    private int bornTime;
    private int groceries;

    public Customer(int time, int amount) {
	this.bornTime = time;
	this.groceries = amount;
    }
    
    public void serve() {
	    this.groceries--;
    }

    public boolean isDone() {
	return this.groceries == 0; 
    }

    public int time() {
	 return this.bornTime;
    }

    public String toString(){
	return "[" + this.groceries + "]";
    }
    
    /*public static void main(String[] args){
	Customer groceries = new Customer(0,9);
	for (; !groceries.isDone(); groceries.serve()) {
	    System.out.println(groceries);   
	}
	}*/	    
}

