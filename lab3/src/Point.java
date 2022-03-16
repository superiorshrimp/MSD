
public class Point {
    public static double p = 0.2;
    public int type;
    public Point next;
    public boolean moved;
    public int speed = 0;

    private int distance(){
        int counter = 0;
        Point car = this.next;
        while(counter < 5 && counter < this.speed + 1 && !car.moved){
            if(car.type == 1){
                return counter;
            }
            counter++;
            car.moved = true;
            car = car.next;
        }
        return counter;
    }

    public void move() {
        if(this.type == 0){return;}
        if(this.speed > 0 && Math.random() < p){
            this.speed -= 1;
        }
        this.moved = true;
        int dist = this.distance();
        Point car = this.next;
        if(dist == 0){
            return;
        }
        for(int i = 0; i<dist - 1; i++){
            car = car.next;
        }
        car.type = 1;
        car.speed = dist;
        this.type = 0;
        this.speed = 0;
    }

    public void clicked() {
        this.type = 1;
    }

    public void clear() {
        this.type = 0;
    }
}

