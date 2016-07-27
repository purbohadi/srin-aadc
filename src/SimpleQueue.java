import java.io.ObjectInputStream.GetField;


public class SimpleQueue {
	static SimpleQueue myQueue;
	
	static SimpleLinkedList ll =  new SimpleLinkedList();
	int itemCount = 0;
	
	public static void main(String[] args) {
		
		myQueue = new SimpleQueue();
		myQueue.add(1);
		myQueue.add(2);
		myQueue.add(3);
		
		printQueue();
		
		System.out.println("Remove 1 item from queue ");
		myQueue.remove();
		printQueue();
		
		System.out.println("Poll means remove and print item from queue ");
		System.out.println("Item remove : "+myQueue.poll());
		printQueue();
	}
	
	public static void printQueue(){
		for (int i = 0; i < myQueue.itemCount; i++) {
			System.out.print(get(i)+" ");
		}
	}
	
	public void add(int item){
		ll.insertLast(item);
		itemCount++;
	}
	
	public Node remove(){
		itemCount--;
		return ll.deleteFirst();
	}
	
	public int poll(){//remove and dequeue
		Node node = remove();
		return node.data;
	}
	
	public static int get(int index){
		return ll.get(index); 
	}
	
	public boolean isEmpty(){
		if(itemCount>0)
			return false;
		else
			return true;
	}
}

class SimpleNode{
	
	public int data;
	public Node next;
	
	public SimpleNode(int data) {
		// TODO Auto-generated constructor stub
		this.data = data;
	}
	
}

class SimpleLinkedList{
	
	private Node head;
	
	public SimpleLinkedList() {
		// TODO Auto-generated constructor stub
		head=null;
	}
	
	public void insertLast(int data){
		Node newNode = new Node(data);
		
		if (head==null) {
			head=newNode;
			return;
		}
		
		Node tempNode = head;
		while (tempNode.next!=null) {
			tempNode=tempNode.next;
		}
		
		tempNode.next=newNode;
	}
	
	public Node deleteFirst(){
		if(head==null)
			System.out.println("is Empty");
		
		Node tempNode = head;
		
		head = head.next;
		return tempNode;
	}
	
	public int get(int index){
		
		if (index==0) {
			return head.data;
		}
		
		Node current = head.next;
		for (int i = 1; i < index; i++) {
			if (current.next == null) {
				return 0;
			}
			
			current = current.next;
		}
		
		return current.data;
		
	}
	
}
