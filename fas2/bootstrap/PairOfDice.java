import java.util.Scanner;


public class PairOfDice {

    private Die die1;
    private Die die2;


    public PairOfDice(int sides){
        this.die1 = new Die(sides); 
	this.die2 = new Die(sides);
    }

    public void rollPair(){
	this.die1.roll();
	this.die2.roll();	
    }
   
    public int getDie1(){

	return die1.get();
    }
    
    public int getDie2(){

	return die2.get();
    }

    
    public String toString(){

	return die1.toString() + ", " + die2.toString();
    }
    
    public static void main(String [] args) {
	Scanner sc = new Scanner(System.in);
	System.out.print("Number of sides: ");
	int sides = sc.nextInt();

	PairOfDice dice = new PairOfDice(sides);
	dice.rollPair();
	System.out.println(dice);
    }

}
