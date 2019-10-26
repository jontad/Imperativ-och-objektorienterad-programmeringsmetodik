import java.util.Scanner;

public class Die { //klass
  private int numberOfSides; 
  private int value;

  public Die() {
      this.numberOfSides = 6; //konstruktor (skapar objekt)
  }

  public Die(int sides) {

      if(sides <= 0){
	  throw new IllegalArgumentException(sides + " is an illegal number of sides for a die");
      }
      this.numberOfSides = sides;
  }

  public int roll() {
    this.value = (int) (Math.random() * numberOfSides) + 1; //gör något med ett objekt (metod)

    return this.get();
  }

  public int get() {
      if(value == 0){
	roll();
      }
      return this.value;
  }

  public String toString(){
      return "Die(" + this.value + ")";
  }

    public boolean equals(Die otherDie){

	if(otherDie.numberOfSides == numberOfSides && otherDie.value == value) return true;
	else return false;
    }
    
  public static void main(String [] args) {
      Scanner sc = new Scanner(System.in);
      System.out.print("Number of sides: ");
      int sides = sc.nextInt();
      Die die = new Die(sides);
      die.roll();
      System.out.println(die);
  }
}


//klass (beskrivning av ett objekt)
//objekt (sak med tillstånd och beteende)

//överlagring (samma namn kan användas på flera olika metoder, så länge de har olika signatur)
//signatur (argument)

//new (allokerar minne på heapen för klassen i fråga)

//this (variabel som refererar till nuvarande object.)
