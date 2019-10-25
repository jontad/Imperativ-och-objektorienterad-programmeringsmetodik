import java.util.Scanner;

public class MyDieTest{

    public static void main(String [] args){
	Scanner result = new Scanner(System.in); 
	System.out.print("Number of sides?: ");

	int sides = result.nextInt();
	Die die = new Die(sides);

	int total = 0;
	for(int i = 0; i < 10; i++){

	    total = total + die.roll();
	}
	System.out.println("Alea iacta est: " + total);	    

    }
}
