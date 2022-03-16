public class Point {
    public int type = 0;
    public Point next;
    public boolean moved;
    public int speed = 0;

    public void setMoved(boolean moved) {
        this.moved = moved;
    }
    public int getType() {
        return type;
    }
    public void setNext(Point next) {
        this.next = next;
    }

    public void move() {
        if (type == 1 && !moved ) {
            Point curr = this;
            for (int i=0; i<speed; i++){
                curr = curr.next;
            }

            type = 0;
            curr.type = 1;
            moved = true;
            curr.moved = true;
            curr.speed = this.speed;
        }
    }

    public void clicked() {
        type = 1;
        speed = 1;
    }

    public void clear() {
        type = 0;
        speed = 0;
    }

    public void slow_down_if_needed(){
        Point neighbour = this;
        for (int i=0; i<speed; i++){
            if (neighbour.next.type == 1){
                speed = i;
                break;
            }
            neighbour = neighbour.next;
        }
    }

    public void speed_up(){
        if (speed < 5){
            speed += 1;
        }
    }

    public void slow_down(){
        if (speed > 0){
            speed -= 1;
        }
    }
}

