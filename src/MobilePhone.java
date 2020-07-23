public class MobilePhone{
	int id;
	String status;
	MobilePhone(int number){
		id = number;
		Status = null;
	}
	public int number(){
		return id;
	}
	public Boolean status(){
		if (status.equals("ON")){
			return true;
		}
		else return false;
	}
	public void switchOn(){
		status = "ON";
	}
	public void switchOff(){
		status = "OFF";
	}
	public Exchange location(){

	}
}