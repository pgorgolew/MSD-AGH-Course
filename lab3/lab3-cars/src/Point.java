public class Point {
    private int type = 0;
    private Point next;
    private boolean moved;

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
        if (type == 1 && next.type == 0 && !moved) {
            type = 0;
            next.type = 1;
            moved = true;
            next.moved = true;
        }
    }

    public void clicked() {
        type = 1;
    }

    public void clear() {
        type = 0;
    }
}

