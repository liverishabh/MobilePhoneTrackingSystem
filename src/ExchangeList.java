package assignment_1;

public class ExchangeList{
	Node head = null;
	Node temp2 = head;
	int size = 0;

	static class Node{
		Exchange data;
		Node next;
		Node(Exchange o){
			data = o;
			next = null;
		}
		public Exchange getExchange(){
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
		if (size == 0){
			return true;
		}
		else return false;
	}
	public Boolean IsMember(Exchange o){
		Node temp = head;
		while (temp != null ){
			if (o.id == temp.data.id){
				return true;
			}
			temp = temp.next;
		}
		return false;
	}
	public void InsertFirst(Exchange o){
		Node new_node = new Node(o);
		new_node.next = head;
		head = new_node;
		size ++;
	}
	public void Delete(Exchange o){
		try{
			Node temp = head;
			Node temp1 = head;
			while (temp != null){
				if(o.id == head.data.id){           //if the Exchange to be deleted is in the head node. 
					head = head.next;
					size --;
					break;
				}
				else if(o.id == temp.data.id){
					temp1.next = temp.next;
					size --;
					break;
				}
				temp1 = temp;
				temp = temp.next;
			}
			throw new Exception("demo");
		}
		catch (Exception e){
			System.out.println("Element to be deleted is not in the list");
		}
	}
}
