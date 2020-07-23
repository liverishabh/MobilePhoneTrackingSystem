class LinkedList{
	Node head = null;
	Node temp2 = head;
	int size = 0;

	static class Node{
		Object data;
		Node next;
		Node(Object o){
			data = o;
			next = null;
		}
		public Object getData(){
		    return data;
	    }
	    public Node getNext(){
	    	return next;
	    }
	}
	public int size(){
		return size;
	}
	
	public Boolean IsEmpty(){
		if (head == null){
			return true;
		}
		else return false;
	}
	public Boolean IsMember(Object o){
		Node temp = head;
		while (temp != null ){
			if (o == temp.data){
				return true;
			}
			temp = temp.next;
		}
		return false;
	}
	public void InsertFirst(Object o){
		Node new_node = new Node(o);
		new_node.next = head;
		head = new_node;
		size ++;
	}
	public void Delete(Object o){
		try{
			Node temp = head;
			Node temp1 = head;
			while (temp != null){
				if(o == head.data){           //if the data to be deleted is in the head node. 
					head = head.next;
					break;
				}
				else if(o == temp.data){
					temp1.next = temp.next;
					break;
				}
				temp1 = temp;
				temp = temp.next;
				size --;
			}
			throw new Exception("demo");
		}
		catch (Exception e){
			System.out.println("Element to be deleted is not in the set");
		}
	}
}

public class Myset {
	LinkedList l = new LinkedList();
	public Boolean IsEmpty(){
		if (l.IsEmpty()){
			return true;
		}
		else return false;
	}
	public Boolean IsMember(Object o){
		if (l.IsMember(o)){
			return true;
		}
		else return false;	
	}
	public void Insert(Object o){
		l.InsertFirst(o);
	}
	public void Delete(Object o){
		l.Delete(o);
	}
	public Myset Union(Myset a){
		while (l.temp2 != null){
			if (a.IsMember(l.temp2.getData())){
				a.Delete(l.temp2.getData());
			}
			l.temp2 = l.temp2.next;
		}
		l.temp2 = l.head;
		while (l.temp2 != null){
			a.Insert(l.temp2.data);
			l.temp2 = l.temp2.next;
		}
		return a;
	}
	public Myset Intersection(Myset a){
		while(l.temp2 != null){
			if (!a.IsMember(l.temp2.data)){
				a.Delete(l.temp2.data);
			}
			l.temp2 = l.temp2.next;
			
		}
		return a;
	}
}







