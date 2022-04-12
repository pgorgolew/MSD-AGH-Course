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
		int smallest = staticField;
		for (Point neighbour: neighbors){
			smallest = Math.min(smallest, neighbour.staticField);
		}

		if (smallest+1 < staticField){
			staticField = smallest+1;
			return true;
		}
		return false;
	}
	
	public void move(){
		if (isPedestrian && !blocked){
			int smallest_non_occuppied = staticField;
			Point new_position = this;
			for (Point neighbour: neighbors){
				if ((neighbour.type == 0 || neighbour.type == 2) && !neighbour.blocked) {
					if (smallest_non_occuppied > neighbour.staticField) {
						new_position = neighbour;
						smallest_non_occuppied = neighbour.staticField;
					}
				}
			}

			if (new_position != this){
				if (new_position.type != 2){
					new_position.type = this.type;
					new_position.isPedestrian = true;
				}
				this.type = 0;
				this.isPedestrian = false;
			}
			new_position.blocked = true;
		}
	}

	public void addNeighbor(Point nei) {
		neighbors.add(nei);
	}
}