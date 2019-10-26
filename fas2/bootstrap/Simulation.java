import java.util.Random;

public class Simulation{

    private Store store;
    private int time;
    private int intesity;
    private int maxGroceries;
    private int thresholdForNewRegister;
    private Random random;
    private int maxWaitTime;
    private int servedCustomers;
    private float averageWaitTime;
    private float totalWaitTime;
    private boolean open;
    private int groceries;
    
    
    public Simulation(int amountOfRegisters, int probability, int max, int threshold) {
	this.store = new Store(amountOfRegisters);
	this.time = 0;
	this.intesity = probability;
	this.maxGroceries = max - 1;
	this.thresholdForNewRegister = threshold;
	this.random = new Random();
	this.maxWaitTime = 0;
	this.servedCustomers = 0;
	this.averageWaitTime = 0;
	this.totalWaitTime = 0;
	this.open = false;
	this.groceries = 0;
    }

    public String toString(){
	
	
	return store.toString() + "\nNumbers of customers served: " + servedCustomers + "\nMax wait-time: " + maxWaitTime + "\nAverage wait-time: " + averageWaitTime;
    }

    
    public void step(){       
	time++;
	if(random.nextInt(99) + 1 < intesity){
	    this.groceries = random.nextInt(maxGroceries) + 1;
	    Customer customer = new Customer(time, groceries);
	    store.newCustomer(customer);
	}

	int averageLength = store.getAverageQueueLength();
	if(thresholdForNewRegister < averageLength){
	    store.openNewRegister();
	}
	store.step();


	
        Customer doneCustomers[] = store.getDoneCustomers();
	for (int i = 0; i < doneCustomers.length; i++) {	
	    if(doneCustomers[i] != null){
				
		this.servedCustomers++;

	    
		int timeBuying = time - doneCustomers[i].time();
		this.totalWaitTime = totalWaitTime + timeBuying;

		if(maxWaitTime < timeBuying){
		    this.maxWaitTime = timeBuying;
		}
	    }
	}
	this.averageWaitTime = totalWaitTime/servedCustomers;

	
		
    }	
}
