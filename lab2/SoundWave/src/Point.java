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

	public Point() {
		clear();
	}

	public void clicked() {
		pressure = 1;
	}
	
	public void clear() {
		nVel = 0;
		eVel = 0;
		wVel = 0;
		sVel = 0;
		pressure = 0;
	}

	public void updateVelocity() {
		// TODO: velocity update
	}

	public void updatePresure() {
		// TODO: pressure update
	}

	public float getPressure() {
		return pressure;
	}
}