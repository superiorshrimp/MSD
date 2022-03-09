public class Point {

	public Point nNeighbor;
	public Point wNeighbor;
	public Point eNeighbor;
	public Point sNeighbor;
	public float nVel;
	public float eVel;
	public float wVel;
	public float sVel;
	public float pressure;
	public static Integer[] types = {0,1,2};
	public int type;
	public int sinInput;

	public Point() {
		type = 0;
		clear();
	}

	public void clicked() {
		pressure = 1;
	}
	
	public void clear() {
		pressure = 0;
		nVel = 0;
		sVel = 0;
		wVel = 0;
		eVel = 0;
		type = 0;
		sinInput = 0;
	}

	public void updateVelocity() {
		if(nNeighbor!=null){
			nVel += pressure - nNeighbor.getPressure();
		}
		if(sNeighbor!=null){
			sVel += pressure - sNeighbor.getPressure();
		}
		if(wNeighbor!=null){
			wVel += pressure - wNeighbor.getPressure();
		}
		if(eNeighbor!=null){
			eVel += pressure - eNeighbor.getPressure();
		}
	}

	public void updatePresure() {
		if(type == 0){
			float s = 0;
			if(nNeighbor!=null){
				s += nVel;
			}
			if(sNeighbor!=null){
				s += sVel;
			}
			if(wNeighbor!=null){
				s += wVel;
			}
			if(eNeighbor!=null){
				s += eVel;
			}
			pressure -= 0.5f * s;
		}
		else{
			sinInput = (int) (Math.random() * 1000);
			double radians = Math.toRadians(sinInput);
			pressure = (float) (Math.sin(radians));
		}

	}

	public float getPressure() {
		return pressure;
	}
}