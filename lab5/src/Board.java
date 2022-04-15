import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;

public class Board extends JComponent implements MouseInputListener, ComponentListener {
	private static final long serialVersionUID = 1L;
	private Point[][] points;
	private int size = 10;
	public int editType=0;
	public ArrayList<Point> toCheck = new ArrayList<Point>();

	public Board(int length, int height) {
		addMouseListener(this);
		addComponentListener(this);
		addMouseMotionListener(this);
		setBackground(Color.WHITE);
		setOpaque(true);
	}

	public void iteration() {
		for (int x = 1; x < points.length - 1; ++x){
			for (int y = 1; y < points[x].length - 1; ++y) {
				if(points[x][y].type != 1){
					points[x][y].blocked = false;
				}
			}
		}

		ArrayList<Pair> shuff = new ArrayList<Pair>();
		for (int x = 1; x < points.length - 1; ++x)
			for (int y = 1; y < points[x].length - 1; ++y)
				shuff.add(new Pair(x,y));
		Collections.shuffle(shuff); //for random order

		while(!shuff.isEmpty()){
			Pair el =  shuff.get(0);
			points[el.left][el.right].move();
			shuff.remove(0);
		}
		/*
		for (int x = 1; x < points.length - 1; ++x)
			for (int y = 1; y < points[x].length - 1; ++y)
				points[x][y].move();
		*/

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

		for (int x = 0; x < points.length; ++x){
			for (int y = 0; y < points[x].length; ++y){
				points[x][y] = new Point();
			}
		}

		for (int x = 1; x < points.length-1; ++x) {
			for (int y = 1; y < points[x].length-1; ++y) {

				for(int i = -1; i<=1; i++){
					for(int j = -1; j<=1; j++){
						if((i != 0 || j != 0) && x+i >0 && x+i < points.length-1 && y+j >0 && y+j < points[x].length-1){
							points[x][y].addNeighbor(points[x+i][y+j]); //von Neumann
						}
					}
				}
				/* Moore // 2 ściany na ukos wystarcza aby nie dalo sie przejsc !!!!!
				if(x-1>0){
					points[x][y].addNeighbor(points[x-1][y]);
				}
				if(x+1 < points.length-1){
					points[x][y].addNeighbor(points[x+1][y]);
				}
				if(y-1>0){
					points[x][y].addNeighbor(points[x][y-1]);
				}
				if(y+1 < points[x].length-1){
					points[x][y].addNeighbor(points[x][y+1]);
				}
				*/
			}
		}	
	}
	
	private void calculateField(){
		while(!toCheck.isEmpty()){
			Point p = toCheck.get(0);
			if(p.type == 2){
				p.staticField = 0;
				for(Point nei : p.neighbors){
					if(nei.type != 2 && nei.type != 1){
						toCheck.add(nei);
					}
				}
			}
			if(p.calcStaticField()){
				toCheck.addAll(p.neighbors);
			}
			toCheck.remove(0);
		}
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

		int len = 0;
		if(points != null){
			len = points.length-1;
		}

		for (x = 1; x < len; ++x) {
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
					points[x][y].blocked = true;
					g.setColor(new Color(1.0f, 0.0f, 0.0f, 0.7f));
				}
				else if (points[x][y].type==2){
					toCheck.add(points[x][y]);
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
