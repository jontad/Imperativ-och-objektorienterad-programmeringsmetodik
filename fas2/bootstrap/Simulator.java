
public class Simulator{

    public static void main(String[] args) throws InterruptedException{
        int steps = 100;
        Simulation s = new Simulation(4, 40, 10, 3);
	//(noOfRegisters, probOfNewCustomer, maxGroceries, lengthOfQueueForNewReg)

        for(int i = 0; i < steps; i++){
            System.out.print("\033[2J\033[;H");
            s.step();
            System.out.println(s);
            Thread.sleep(500);
        }
        System.out.println("");
        System.out.println("Simulation finished!");
    }
}

