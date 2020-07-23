package assignment_1;

public class Exchange{
	int id;
	Exchange parent;
	ExchangeList children;
	Exchange (int number, Exchange parentNode){
		id = number;
		parent = parentNode;
		children.head = null;
	}
	public Exchange parent(){
		return parent;
	}
	public int numChildren(){
		return children.size();
	}
	public ExchangeList.Node child(int i){
		int a = 1;
		while(a < i){
			children.temp2 = children.temp2.next;
			a++;
		}
		return children.temp2;
	}
	public Boolean isRoot(){
		if (parent == null){
			return true;
		}
		else return false;
	}

}
