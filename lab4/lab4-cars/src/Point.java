import java.util.ArrayList;
import java.util.Arrays;

public class Point {
    public int type;
    public Point next;
    public Point prev;
    public Point left = null;
    public Point right = null;
    public boolean moved;
    public int speed;
    public static Integer[] types = {0, 1, 2, 3, 5};
    public static ArrayList<Integer> carTypes = new ArrayList<>(Arrays.asList(1, 2, 3));

    public void setMoved(boolean moved) {
        this.moved = moved;
    }
    public int getType() {
        return type;
    }

    public Point(int type) {
        this.type = type;
        this.speed = get_max_speed(type);
    }

    public void move() {
        if (carTypes.contains(this.type) && !moved) {
            if (speed == 0){
                moved = true;
                return;
            }

            Point curr = this;
            for (int i=0; i<speed; i++){
                curr = curr.next;
            }

            curr.type = getType();
            curr.moved = true;
            curr.speed = this.speed;
            clear();
            moved = true;
        }
    }

    public void clicked() {
        this.type = 0;
        this.speed = 0;
    }

    public void clear() {
        type = 0;
        speed = 0;
    }

    public void slow_down_if_needed(){
        Point neighbour = this;
        for (int i=0; i<speed; i++){
            if (carTypes.contains(neighbour.next.type)){
                speed = i;
                break;
            }
            neighbour = neighbour.next;
        }
    }

    public void speed_up(){
        if (speed < get_max_speed()){
            speed += 1;
        }
    }

    public void slow_down(){
        if (speed > 0){
            speed -= 1;
        }
    }

    static public int get_max_speed(int type){
        return switch (type) {
            case 1 -> 3;
            case 2 -> 5;
            case 3 -> 7;
            default -> 0;
        };
    }

    public int get_max_speed(){
        return get_max_speed(this.type);
    }
}

