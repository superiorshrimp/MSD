import java.util.ArrayList;

public class Point {
	private ArrayList<Point> neighbors;
	private int currentState;
	private int nextState;
	private int numStates = 6;
	
	public Point() {
		currentState = 0;
		nextState = 0;
		neighbors = new ArrayList<Point>();
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
		if(this.currentState > 0) {
			this.nextState = this.currentState - 1;
			if (this.currentState == 6 && this.neighbors.size() > 0) {
				this.neighbors.get(0).nextState = this.currentState;
			}
		}
		else{
			this.nextState = 0;
		}
	}

	public void changeState() {
		currentState = nextState;
	}
	
	public void addNeighbor(Point nei) {
		neighbors.add(nei);
	}

	//TODO: write method counting all active neighbors of THIS point
	public int getNeighborsCount(){ //useless in this exercise
		int counter = 0;
		for(Point nei : this.neighbors){
			if(nei.currentState == 1){
				counter++;
			}
		}
		return counter;
	}

	void drop(){
		if(Math.random() * 100 <= 5){
			this.nextState = 6;
		}
	}

}
