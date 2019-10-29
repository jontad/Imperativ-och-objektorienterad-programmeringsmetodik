
public class Queue<T>{
    
    private class Node {
	private T element;
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

    public void enqueue(T elem){
	Node temp = this.last;
        this.last = new Node();
	this.last.element = elem;
	this.last.next = null;

	if(first == null){
	    this.first = last;
	}
	else {
	    temp.next = this.last;
	}
	this.length++;
    }

    public T dequeue(){
	if(this.first == null){
	    throw new EmptyQueueException(); 
	}
	T elem = this.first.element;
	this.first = this.first.next;

	this.length--;

	if(first == null) last = null;
	return elem;
    }

    public T first(){
	if(first == null) throw new EmptyQueueException();
	return this.first.element;
    }

    /*public String toString()
    {
	return "C " + first();
    }
    
       public static void main(String[] args){
	Queue q = new Queue();

	T cust1 = new T(0,9);
	T cust2 = new T(0,7);
	T cust3 = new T(0,2);

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
