import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;

public class Board extends JComponent implements MouseInputListener, ComponentListener {
	private static final long serialVersionUID = 1L;
	private Point[][] points;
	ArrayList<Integer> xOrder;
	ArrayList<Integer> yOrder;
	private int size = 10;
	public int editType=0;
	public boolean moore = false;

	public Board(int length, int height) {
		addMouseListener(this);
		addComponentListener(this);
		addMouseMotionListener(this);
		setBackground(Color.WHITE);
		setOpaque(true);
	}

	public void iteration() {

		for (int x = 1; x < points.length - 1; ++x)
			for (int y = 1; y < points[x].length - 1; ++y)
				points[x][y].blocked = false;

		Collections.shuffle(xOrder);
		Collections.shuffle(yOrder);
		for (Integer x: xOrder)
			for (Integer y: yOrder)
				points[x][y].move();
		this.repaint();
	}

	public void clear() {
		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y) {
				points[x][y].clear();
			}
		calculateField();
		this.repaint();
	}

	private void initialize(int length, int height) {
		points = new Point[length][height];

		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y)
				points[x][y] = new Point();

		for (int x = 1; x < points.length-1; ++x) {
			for (int y = 1; y < points[x].length-1; ++y) {
				if (moore)
					addMoore(x,y);
				else
					addVonNeuman(x,y);
			}
		}

		xOrder = IntStream.range(0, points.length).boxed().collect(Collectors.toCollection(ArrayList::new));
		yOrder = IntStream.range(0, points[0].length).boxed().collect(Collectors.toCollection(ArrayList::new));
	}

	private void addVonNeuman(int x, int y){
		points[x][y].addNeighbor(points[x][y-1]);
		points[x][y].addNeighbor(points[x+1][y]);
		points[x][y].addNeighbor(points[x][y+1]);
		points[x][y].addNeighbor(points[x-1][y]);
	}

	private void addMoore(int x, int y){
		points[x][y].addNeighbor(points[x][y-1]);
		points[x][y].addNeighbor(points[x+1][y-1]);
		points[x][y].addNeighbor(points[x+1][y]);
		points[x][y].addNeighbor(points[x+1][y+1]);
		points[x][y].addNeighbor(points[x][y+1]);
		points[x][y].addNeighbor(points[x-1][y+1]);
		points[x][y].addNeighbor(points[x-1][y]);
		points[x][y].addNeighbor(points[x-1][y-1]);
	}

	private void calculateField(){
		ArrayList<Point> toCheck = new ArrayList<Point>();
		ArrayList<Point> walls = new ArrayList<>();
		for (int x = 0; x < points.length; ++x) {
			for (int y = 0; y < points[x].length; ++y) {
				if (points[x][y].type == 2) {
					points[x][y].staticField = 0;
					points[x][y].isStaticCalculated = true;
					toCheck.addAll(points[x][y].neighbors);
				}
				else if (points[x][y].type == 1)
					walls.add(points[x][y]);
			}
		}

		while (!toCheck.isEmpty()){
			Point point = toCheck.remove(0);
			if (point.calcStaticField())
				toCheck.addAll(point.neighbors.stream()
								.filter(p->!p.isStaticCalculated)
								.collect(Collectors.toCollection(ArrayList::new)));
		}

		Set<Point> nearWalls = new HashSet<>();
		while (!walls.isEmpty())
			nearWalls.addAll(walls.remove(0).neighbors);

		for (Point point: nearWalls)
			point.staticField += 1;
	}

	protected void paintComponent(Graphics g) {
		if (isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		g.setColor(Color.GRAY);
		drawNetting(g, size);
	}

	private void drawNetting(Graphics g, int gridSpace) {
		Insets insets = getInsets();
		int firstX = insets.left;
		int firstY = insets.top;
		int lastX = this.getWidth() - insets.right;
		int lastY = this.getHeight() - insets.bottom;

		int x = firstX;
		while (x < lastX) {
			g.drawLine(x, firstY, x, lastY);
			x += gridSpace;
		}

		int y = firstY;
		while (y < lastY) {
			g.drawLine(firstX, y, lastX, y);
			y += gridSpace;
		}

		for (x = 1; x < points.length-1; ++x) {
			for (y = 1; y < points[x].length-1; ++y) {
				if(points[x][y].type==0){
					float staticField = points[x][y].staticField;
					float intensity = staticField/100;
					if (intensity > 1.0) {
						intensity = 1.0f;
					}
					g.setColor(new Color(intensity, intensity,intensity ));
				}
				else if (points[x][y].type==1){
					g.setColor(new Color(1.0f, 0.0f, 0.0f, 0.7f));
				}
				else if (points[x][y].type==2){
					g.setColor(new Color(0.0f, 1.0f, 0.0f, 0.7f));
				}
				if (points[x][y].isPedestrian){
					g.setColor(new Color(0.0f, 0.0f, 1.0f, 0.7f));
				}
				g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));
			}
		}

	}

	public void mouseClicked(MouseEvent e) {
		int x = e.getX() / size;
		int y = e.getY() / size;
		if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
			if(editType==3){
				points[x][y].isPedestrian=true;
			}
			else{
				points[x][y].type= editType;
			}
			this.repaint();
		}
	}

	public void componentResized(ComponentEvent e) {
		int dlugosc = (this.getWidth() / size) + 1;
		int wysokosc = (this.getHeight() / size) + 1;
		initialize(dlugosc, wysokosc);
	}

	public void mouseDragged(MouseEvent e) {
		int x = e.getX() / size;
		int y = e.getY() / size;
		if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
			if(editType==3){
				points[x][y].isPedestrian=true;
			}
			else{
				points[x][y].type= editType;
			}
			this.repaint();
		}
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void componentHidden(ComponentEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

}
