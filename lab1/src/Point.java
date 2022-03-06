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
		currentState=(++currentState)%(numStates+1);
	}

	public int getState() {
		return currentState;
	}

	public void setState(int s) {
		currentState = s;
	}

	public void calculateNewState(Rules rule) {
		int aliveNeighbours = calculateActiveNeighbours();
		if (currentState == 1 && rule.neighboursForAliveCell().contains(aliveNeighbours))
			nextState = 1;
		else if (currentState == 0 && rule.neighboursForDeadCell().contains(aliveNeighbours))
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