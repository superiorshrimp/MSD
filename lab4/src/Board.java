import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;

public class Board extends JComponent implements MouseInputListener, ComponentListener {
    private static final long serialVersionUID = 1L;
    private Point[][] points;
    private int size = 25;
    public int editType = 0;

    public Board(int length, int height) {
        addMouseListener(this);
        addComponentListener(this);
        addMouseMotionListener(this);
        setBackground(Color.WHITE);
        setOpaque(true);
    }

    private void initialize(int length, int height) {
        points = new Point[length][height];
        int len;
        if(points != null){
            len = points.length;
        }
        else{
            len = 0;
        }
        for (int x = 0; x < len; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y] = new Point();
                if(y != 2 && y != 3){
                    points[x][y].type = 5;
                }
                else{
                    points[x][y].type = 0;
                }
            }
        }
        for (int x = 0; x < len; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                if(x + 1 < points.length){
                    points[x][y].next = points[x + 1][y];
                }
                else{
                    points[x][y].next = points[0][y];
                }
                if(x - 1 >= 0){
                    points[x][y].prev = points[x - 1][y];
                }
                else{
                    points[x][y].prev = points[points.length-1][y];
                }
                if(y - 1 >= 0){
                    points[x][y].upper = points[x][y-1];
                }
                else{
                    points[x][y].upper = points[x][points[x].length-1];
                }
                if(y + 1 < points[x].length){
                    points[x][y].lower = points[x][y+1];
                }
                else{
                    points[x][y].lower = points[x][0];
                }
            }
        }
    }

    public void iteration() {
        int len;
        if(points != null){
            len = points.length;
        }
        else{
            len = 0;
        }

        for (int x = 0; x < len; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].moved = false;
            }
        }

        for (int x = 0; x < len; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                if(!points[x][y].moved){
                    if(y == 2){
                        int counter = 0;
                        Point car = points[x][y].prev;
                        while(counter < 7){
                            if(car.type != 0 && car.type != 5){
                                if(1 + 2*car.type > points[x][y].speed){
                                    points[x][y].returning();
                                }
                                break;
                            }
                            counter++;
                            car = car.prev;
                        }

                    }
                }
                if(!points[x][y].moved){
                    if(y == 3){
                        points[x][y].overtake();
                    }
                }
                if(!points[x][y].moved){
                    points[x][y].move();
                }
            }
        }
        this.repaint();
    }

    public void clear() {
        int len;
        if(points!=null){
            len = points.length;
        }
        else{
            len = 0;
        }
        for (int x = 0; x < len; ++x)
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].clear();
            }
        this.repaint();
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
        int len;
        if(points!=null){
            len = points.length;
        }
        else{
            len = 0;
        }
        for (x = 0; x < len; ++x) {
            for (y = 0; y < points[x].length; ++y) {
                if(points[x][y].type == 0){
                    g.setColor(Color.WHITE);
                }
                else if(points[x][y].type == 1){
                    g.setColor(Color.YELLOW);
                }
                else if(points[x][y].type == 2){
                    g.setColor(Color.BLUE);
                }
                else if(points[x][y].type == 3){
                    g.setColor(Color.RED);
                }
                else if(points[x][y].type == 5){
                    g.setColor(Color.GREEN);
                }

                g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));
            }
        }

    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
            if(editType==0){
                points[x][y].clicked();
            }
            else {
                points[x][y].type = editType;
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
            if(editType==0){
                points[x][y].clicked();
            }
            else {
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
