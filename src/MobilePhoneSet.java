public class RoutingMapTree {
	Exchange root;
	RoutingMapTree(){
		root = new Exchange(0,null);		
	}
	public Boolean containsNode(int num) {
		
		if (this.root.id == num) {
			return true;
		}
		int i = 0;
		while (i < this.root.numChildren()) {
			RoutingMapTree temp = this.root.subtree(i);
			if (temp.containsNode(num)) {
				return true;
			}
			i++;
		}
		return false;
	}
	public Exchange getNode(int num) {
		if (!this.containsNode(num)) {
			return null;
		}
		if (this.root.id == num) {
			return this.root;
		}
		int i = 0;
		while (i < this.root.numChildren()) {
			RoutingMapTree temp = this.root.subtree(i);
			
				Exchange temp1= temp.getNode(num);
				if (temp1 != null) {
					return temp1;
				}
			
			i++;
		}
		return null;
		
	}

	public MobilePhone findMobile(int num) {
		MobilePhoneSet temp = root.getMobilePhoneSet();
		return temp.getMobile(num);		
	}
	public void updatetree(MobilePhone a,Exchange b) {
		while (b.parent != null) {
			b.parent.residentSet().Insert(a);
			b = b.parent;
		}
		
	}
	public void updatetree(MobilePhone a) {
		Exchange b = a.location();
		while (b.parent != null) {
			b.parent.residentSet().Delete(a);
			b = b.parent;
		}
	}
	public void switchOn(MobilePhone a,Exchange b) {
		
		if (!a.status()) {
			a.switchOn();
		}
		b.allmobile.Insert(a);
		this.updatetree(a,b);
		
		
	}
	public void switchOff(MobilePhone a) throws Exception {

			if (a == null ) {
				throw new Exception();
			}
			if (a.status()) {
				
				a.switchOff();
			}
			Exchange b = a.location();
			b.residentSet().Delete(a);
			this.updatetree(a);
			
	}
	public Exchange findPhone(MobilePhone m) {
		if (m == null)
				return null;
			
		else return m.location();
		
		
	}
	public Exchange lowestRouter(Exchange a, Exchange b) {
		LinkedList l1 = new LinkedList();
		LinkedList l2 = new LinkedList();
		Exchange temp1 = a;
		Exchange temp2 = b;
		int result = 0;
		while(temp1!= null) {
			l1.InsertLast(temp1.id);
			temp1=temp1.parent;
		}
		while(temp2!= null) {
			l2.InsertLast(temp2.id);
			temp2=temp2.parent;
		}
		l1.temp2 = l1.head;
		while(l1.temp2!= null) {
			if (l2.IsMember(l1.temp2.data)) {
				result = (Integer)l1.temp2.data;			
				break;}
			l1.temp2 = l1.temp2.next;			
		}
		
		return this.getNode(result);
		
	}
	public ExchangeList routeCall(MobilePhone a, MobilePhone b) {
		Exchange a_location = a.location();
		Exchange b_location = b.location();
		Exchange low = this.lowestRouter(a_location, b_location);
		ExchangeList temp = new ExchangeList();
		ExchangeList temp1 = new ExchangeList();
		while(a_location.id != low.id) {
			temp.InsertLast(a_location);
			a_location = a_location.parent;
		}
		temp.InsertLast(low);
		while(b_location.id != low.id) {
			temp1.InsertFirst(b_location);
			b_location = b_location.parent;
		}
		while(temp1.head != null) {
			temp.InsertLast(temp1.head.data);
			temp1.head = temp1.head.next;
		}
		return temp;		
	}
	public void movePhone(MobilePhone a, Exchange b) throws Exception {
		this.switchOff(a);
		this.switchOn(a, b);
		a.station = b;
	}
	public String performAction(String actionMessage) {
		String[] temp = actionMessage.split("\\s");
		if (temp[0].equals("addExchange")) {
			try {
				Exchange a = this.getNode(Integer.parseInt(temp[1]));
				Exchange b = new Exchange(Integer.parseInt(temp[2]),a);				
				a.children.InsertLast(b);
				return "";
				
			}
			
			catch (Exception e) {
				return actionMessage +": Error - No exchange with identifier "+temp[1]+" found in the network";
			}
		}
		
	
		if (temp[0].equals("switchOffMobile")) {
			try {
				MobilePhone a = this.findMobile(Integer.parseInt(temp[1]));
				
				this.switchOff(a);			
				return "";
			}
			catch(Exception e) {
				return actionMessage +": Error- No mobile phone with identifier "+ temp[1]+" found in the network";
			}
			
		}
		if (temp[0].equals("switchOnMobile")){
			try {
				Exchange b = this.getNode(Integer.parseInt(temp[2]));
				if ( b== null) {
					throw new Exception(actionMessage+ ": Error - No exchange with identifier "+temp[1]+" found in the network");
				}
				MobilePhone o =  this.findMobile(Integer.parseInt(temp[1]));
				if (o!= null) {
					throw new Exception("switchonMobile : Error - mobile phone with identifier "+ temp[1]+ " is already present in the network");
				}
				MobilePhone a = new MobilePhone(Integer.parseInt(temp[1])); 
				a.station = b;
				switchOn(a,b);
				return "";
				
			}
			catch(Exception e) {
				return e.getMessage();
				
			}
		}
		if (temp[0].equals("queryNthChild")) {
			Exchange a = this.getNode(Integer.parseInt(temp[1]));
		    int id = -1;
		    try {
		    	if (a.child(Integer.parseInt(temp[2]))!= null) {
			    	id = a.child(Integer.parseInt(temp[2])).id;
			    	String result = String.valueOf(id);
			    	result = "queryNthChild "+temp[1]+" "+temp[2]+": "+result;
			    	return result;
			    }
		    	else {
		    		
		    	    throw new Exception();
		     	}
		    	
		    }
		    catch (Exception e) {
		    	return actionMessage +": Error - No "+ temp[2]+ "  child of Exchange "+ temp[1]+" found in the network";
		    }
		    	    
		}
		if (temp[0].equals("queryMobilePhoneSet")) {
			try {
				Exchange a = this.getNode(Integer.parseInt(temp[1]));
				MobilePhoneSet temp1 = a.residentSet();
				String result = temp1.getMobileId(Integer.parseInt(temp[1]));
				return result;
			}
			catch(Exception e) {
				return actionMessage +": Error - No exchange with identifier "+temp[1]+" found in the network" ;
			}
			
		}
		if (temp[0].equals("findPhone")) {
			try {
				MobilePhone m = this.findMobile(Integer.parseInt(temp[1]));
				Exchange e = this.findPhone(m);
				if (e == null) {
					throw new Exception("queryFindPhone "+temp[1]+": Error - No mobile phone with identifier "+ temp[1]+" found in the network");
				}
				return "queryFindPhone "+temp[1]+": "+String.valueOf(e.id);
			}
			catch(Exception e) {
				return e.getMessage();
			}
			
		}
		if (temp[0].equals("lowestRouter")){                          
			try {
				Exchange a = this.getNode(Integer.parseInt(temp[1]));
				Exchange b = this.getNode(Integer.parseInt(temp[2]));
				if (a == null && b == null) {
					throw new Exception("queryLowestRouter "+temp[1]+" " +temp[2]+": "+ "Error - No exchanges with identifiers "+temp[1] +" and "+temp[2]+" found in the network");
				}
				if (a == null) {
					throw new Exception("queryLowestRouter "+temp[1]+" " +temp[2]+": "+ "Error - No exchange with identifier "+temp[1]+" found in the network");
				}
				if (b == null) {
					throw new Exception("queryLowestRouter "+temp[1]+" " +temp[2]+": "+ "Error - No exchange with identifier "+temp[2]+" found in the network");
				}
				Exchange e = this.lowestRouter(a, b);
					
				String result = "queryLowestRouter "+temp[1]+" " +temp[2]+": "+ String.valueOf(e.id);
				return result ;
			}
			catch(Exception e) {
				return e.getMessage();
			}
			
		}
		if (temp[0].equals("findCallPath")) {
			try {
			    MobilePhone a = this.findMobile(Integer.parseInt(temp[1]));
				MobilePhone b = this.findMobile(Integer.parseInt(temp[2]));
				if (a == null) {
					throw new Exception("queryFindCallPath "+temp[1]+" " +temp[2]+": "+ "Error - Mobile phone with identifier "+temp[1]+" is currently switched off");
				}
				if (b == null) {
					throw new Exception("queryFindCallPath "+temp[1]+" " +temp[2]+": "+ "Error - Mobile phone with identifier "+temp[2]+" is currently switched off");
				}
				ExchangeList result = this.routeCall(a, b);
				return result.getExchangeId(Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
			}
			catch(Exception e) {
				return e.getMessage();
			}
			
		}
        if (temp[0].equals("movePhone")) {
        	try {
        		MobilePhone a = this.findMobile(Integer.parseInt(temp[1]));
        		if (a == null) {
					throw new Exception("movePhone "+temp[1]+" " +temp[2]+": Error - No mobile phone with identifier "+temp[1]+" found in the network");
				}
            	Exchange b = this.getNode(Integer.parseInt(temp[2]));
            	if ( b== null) {
					throw new Exception("movePhone "+temp[1]+" " +temp[2]+ ": Error - No exchange with identifier "+temp[1]+" found in the network");
				}
            	this.movePhone(a, b);
        	}
        	catch(Exception e) {
        		return e.getMessage();
        	}
		}
		return "";
		
	}
}

