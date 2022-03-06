import java.util.ArrayList;
import java.util.Random;

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
		currentState=(++currentState)%(numStates+1);
	}

	public int getState() {
		return currentState;
	}

	public void setState(int s) {
		currentState = s;
	}

	public void calculateNewState() {
		int aliveNeighbours = calculateActiveNeighbours();
		if (aliveNeighbours == 3 || (aliveNeighbours == 2 && currentState == 1))
			nextState = 1;
		else
			nextState = 0;
	}

	public void changeState() {
		currentState = nextState;
	}

	public void addNeighbor(Point nei) {
		neighbors.add(nei);
	}

	public int calculateActiveNeighbours(){
		return (int) neighbors.stream().filter(point -> point.currentState == 1).count();
	}
}