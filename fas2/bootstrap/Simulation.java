import java.util.Random;

public class Simulation{

    Store store;
    int time;
    int intesity;
    int maxGroceries;
    int thresholdForNewRegister;
    Random random;

    public Simulation(int amountOfRegisters, int probability, int max, int threshold){
	this.store = new Store(amountOfRegisters);
	this.time = 0;
	this.intesity = probability;
	this.maxGroceries = max;
	this.thresholdForNewRegister = threshold;
	this.random = new Random();
    }

    public void simulationOfStore(){

	int averageLength = store.getAverageQueueLength();

	if(random.nextInt(1,100) < intesity){
	    Random groceries = random.nextInt(1, maxGroceries);
	    Customer customer = new Customer(time, groceries);

	    store.newCustomer(customer);
	}
	
	if(thresholdForNewRegister < averageLength){
	    store.openNewRegister();
	}
	store.getDoneCustomers();

    }
}
