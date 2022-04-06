import java.util.ArrayList;

public class Point {

	public ArrayList<Point> neighbors;
	public static Integer []types ={0,1,2,3};
	public int type;
	public int staticField;
	public boolean isPedestrian;
	public boolean blocked = false;

	public Point() {
		type=0;
		staticField = 100000;
		neighbors= new ArrayList<Point>();
	}
	
	public void clear() {
		staticField = 100000;
	}

	public boolean calcStaticField() {
		int min = 100000;
		int walls = 0;
		for(Point nei : this.neighbors){
			if(nei.type != 1){
				if(nei.staticField < min){
					min = nei.staticField;
				}
			}
			else{
				walls = 1;
			}
		}
		if(min + 1 + walls < this.staticField){
			this.staticField = min + 1 + walls;
			return true;
		}
		return false;
	}
	
	public void move(){
		if(this.isPedestrian && !this.blocked){
			int min = this.staticField;
			Point rem = null;
			//int count = 0;
			//for(Point nei : this.neighbors){if(nei.isPedestrian && nei.staticField <= this.staticField){count++;}}
			for(Point nei : this.neighbors){
				//int cnt = -count;
				//for(Point neig : nei.neighbors){if(neig.isPedestrian && neig.staticField <= nei.staticField){cnt++;}}
				if(!nei.isPedestrian && !nei.blocked && min >= nei.staticField){// - cnt){
					min = nei.staticField;// - cnt;
					rem = nei;
				}
			}
			if(rem != null){
				this.isPedestrian = false;
				this.blocked = true;
				if(rem.type != 2){
					rem.isPedestrian = true;
					rem.blocked = true;
				}
			}
		}
	}

	public void addNeighbor(Point nei) {
		neighbors.add(nei);
	}
}