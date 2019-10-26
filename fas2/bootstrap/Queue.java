
public class Queue{
    
    private class Node{
	private Customer element;
	private Node next;
    }

    Node first;
    Node last;
    int length;

    public Queue(){
	this.first = null;
	this.last = null;
	this.length = 0;
    }

    public int length(){
	return this.length;
    }

    public void enqueue(Customer customer){
	Node temp = last;
        this.last = new Node();
	this.last.element = customer;
	this.last.next = null;

	if(first == null){
	    this.first = last;
	}
	else {
	    temp.next = last;
	}
	length++;
    }

    public Customer dequeue(){
	if(first == null){
	    throw new EmptyQueueException(); 
	}
	Customer customer = first.element;
	this.first = first.next;

	length--;

	return customer;
    }

    public Customer first(){
	return first.element;
    }
    public String toString()
    {
	return "C " + first();
    }
    
    /*    public static void main(String[] args){
	Queue q = new Queue();

	Customer cust1 = new Customer(0,9);
	Customer cust2 = new Customer(0,7);
	Customer cust3 = new Customer(0,2);

	q.enqueue(cust1);
	q.enqueue(cust2);
	q.enqueue(cust3);

	System.out.println("Length: " + q.length()); 
	
	System.out.println(q.first());
	q.dequeue();
	System.out.println(q.first());
	q.dequeue();	
	System.out.println(q.first());
    

	
	}*/
}
