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
		if(this.currentState == 0){
			if(this.getNeighborsCount() == 3){
				this.nextState = 1;
			}
			else{
				this.nextState = 0;
			}
		}
		else{
			if(this.getNeighborsCount() != 2 && this.getNeighborsCount() != 3){
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
