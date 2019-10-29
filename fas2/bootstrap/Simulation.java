import java.util.Random;

public class Simulation{

    private Store store;
    private int time;
    private int intensity;
    private int maxGroceries;
    private int thresholdForNewRegister;
    private int maxWaitTime;
    private int servedCustomers;
    private float averageWaitTime;
    private float totalWaitTime;
    
    public Simulation(int amountOfRegisters, int probability, int max, int threshold) {
	this.store = new Store(amountOfRegisters);
	this.time = 0;
	this.intensity = probability;
	this.maxGroceries = max - 1;
	this.thresholdForNewRegister = threshold;
	this.maxWaitTime = 0;
	this.servedCustomers = 0;
	this.averageWaitTime = 0;
	this.totalWaitTime = 0;
    }

    public String toString(){	
	return store.toString()
	    + "\nNumbers of customers served: " + servedCustomers
	    + "\nMax wait-time: " + maxWaitTime
	    + "\nAverage wait-time: " + averageWaitTime;
    }

    
    public void step(){
	
	Random random = new Random();
	int rand = random.nextInt(99) + 1;
	
	if(rand < intensity){
	    int groceries = random.nextInt(maxGroceries) + 1;
	    Customer customer = new Customer(time, groceries);

	    this.store.newCustomer(customer);
	}
	
	this.averageWaitTime = this.store.getAverageQueueLength();
	System.out.println("Average wait-time: " + averageWaitTime);

	if(this.thresholdForNewRegister < this.averageWaitTime){
	    this.store.openNewRegister();
	}
	
        Customer doneCustomers[] = this.store.getDoneCustomers();
	this.servedCustomers += doneCustomers.length;
	
	for (int i = 0; i < doneCustomers.length; i++) {	
	    if(doneCustomers[i] != null){
				
		this.servedCustomers++;
	    
		int timeBuying = time - doneCustomers[i].time();
		this.totalWaitTime = totalWaitTime + timeBuying;

		if(this.maxWaitTime < timeBuying){
		    this.maxWaitTime = timeBuying;
		}
	    }
	}
	this.averageWaitTime = totalWaitTime/servedCustomers;
	this.time++;
	this.store.step();   
	
    }	
}
