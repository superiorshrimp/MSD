import java.util.ArrayList;

public class Point {
	private ArrayList<Point> neighbors;
	private int currentState;
	private int nextState;
	private int numStates = 6;
	//atd - alive to dead conditions
	//dta - dead to alive conditions
	//private static final int[] atd = {2,3,4,5}; //cities
	//private static final int[] dta = {4,5,6,7,8}; //cities

	private static final int[] atd = {4,5,6,7,8}; //coral
	private static final int[] dta = {3}; //coral

	public Point() {
		currentState = 0;
		nextState = 0;
		neighbors = new ArrayList<Point>();
	}

	public boolean atd_contains(int val){
		for(int el : this.atd){
			if(val == el){
				return true;
			}
		}
		return false;
	}

	public boolean dta_contains(int val){
		for(int el : this.dta){
			if(val == el){
				return true;
			}
		}
		return false;
	}

	public void clicked() {
		currentState=(++currentState)%numStates;	
	}
	
	public int getState() {
		return currentState;
	}

	public void setState(int s) {
		currentState = s;
	}

	public void calculateNewState() {
		//TODO: insert logic which updates according to currentState and 
		//number of active neighbors
		if(this.currentState == 0){
			if(dta_contains(this.getNeighborsCount())){
				this.nextState = 1;
			}
			else{
				this.nextState = 0;
			}
		}
		else{
			if(!atd_contains(this.getNeighborsCount())){
				this.nextState = 0;
			}
			else{
				this.nextState = 1;
			}
		}
	}

	public void changeState() {
		currentState = nextState;
	}
	
	public void addNeighbor(Point nei) {
		neighbors.add(nei);
	}

	//TODO: write method counting all active neighbors of THIS point
	public int getNeighborsCount(){
		int counter = 0;
		for(Point nei : this.neighbors){
			if(nei.currentState == 1){
				counter++;
			}
		}
		return counter;
	}
}
