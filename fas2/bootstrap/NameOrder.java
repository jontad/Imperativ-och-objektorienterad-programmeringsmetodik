import java.util.Scanner;

public class NameOrder {
   
    public static void main(String[] args){

	Scanner fstName = new Scanner(System.in);
	System.out.print("First name: ");
	String name1 = fstName.next();
	
	Scanner sndName = new Scanner(System.in);
	System.out.print("Second name: ");
	String name2 = sndName.next();

	int result = name1.compareTo(name2);

	if(result < 0){
	    System.out.println(name1);
	    System.out.println(name2);   
	}
	else{
	    System.out.println(name2);
	    System.out.println(name1);   
	}
    }
}
