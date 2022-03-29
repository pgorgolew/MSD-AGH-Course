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
            Point curr = this;
            if (isAbleToGoRight()){
                curr = getPointAfterSideChange(this.right);
            }
            else if (isAbleToGoLeft()){
                curr = getPointAfterSideChange(this.left);
            }
            else{
                speed_up();
                curr.speed = slowDownIfNeeded(curr);
            }

            if (curr.speed == 0){
                curr.moved = true;
                return;
            }

            Point next_pos = curr;
            for (int i=0; i<curr.speed; i++)
                next_pos = next_pos.next;

            next_pos.type = curr.getType();
            next_pos.moved = true;
            next_pos.speed = curr.speed;
            curr.type = 0;
            curr.speed = 0;
            curr.moved = true;
        }
    }

    private Point getPointAfterSideChange(Point side) {
        side.type = this.type;
        side.speed = this.speed;
        this.moved = true;
        clear();
        return side;
    }

    public boolean isAbleToGoLeft(){
        return speed != get_max_speed() && left != null && isDistanceBehind(this, 7) &&
                isDistanceBehind(left, 7) && isDistanceAhead(left);
    }

    public boolean isAbleToGoRight(){
       return right != null && isDistanceBehind(right, 7) && isDistanceAhead(right) &&
               !isDistanceBehind(this, 7);
    }

    public boolean isDistanceBehind(Point starting, int max_distance){
        Point curr = starting;
        int distance = 0;
        while (distance <= max_distance){
            if (carTypes.contains(curr.type) && curr != this)
                return curr.get_max_speed() < distance;

            curr = curr.prev;
            distance++;
        }

        return true;
    }

    public boolean isDistanceAhead(Point starting){
        Point curr = starting;
        int distance = 0;
        while (distance <= curr.speed && curr != this){
            if (carTypes.contains(curr.type))
                return false;

            curr = curr.next;
            distance++;
        }

        return true;
    }
    public void clicked() {
        this.type = 0;
        this.speed = 0;
    }

    public void clear() {
        type = 0;
        speed = 0;
    }

    public int slowDownIfNeeded(Point p){
        int maxLaneSpeed = speed;
        Point neighbour = p;
        for (int i=0; i<p.speed; i++){
            if (carTypes.contains(neighbour.next.type)){
                maxLaneSpeed = i;
                break;
            }
            neighbour = neighbour.next;
        }

        return maxLaneSpeed;
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

