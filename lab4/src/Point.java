
public class Point {
    public int type;
    public Point next;
    public Point prev;
    public Point upper;
    public Point lower;
    public boolean moved;
    public int speed;
    public static Integer[] types = {0, 1, 2, 3, 5};

    private int distance(){
        int counter = 0;
        int maxSpeed = 1 + 2*this.type;
        Point car = this.next;
        while(counter < maxSpeed && counter < this.speed + 1 && !car.moved){
            if(car.type != 0){
                return counter;
            }
            counter++;
            car.moved = true;
            car = car.next;
        }
        return counter;
    }

    public void move() {
        if(this.type == 0 || this.type == 5){return;}
        this.moved = true;
        int dist = this.distance();
        Point car = this.next;
        if(dist == 0){
            return;
        }
        for(int i = 0; i<dist - 1; i++){
            car = car.next;
        }
        car.type = this.type;
        car.speed = dist;
        this.type = 0;
        this.speed = 0;
    }

    public boolean overtake(){ //assuming its the right lane
        if(this.type == 0 || this.type == 5){return false;}
        int maxSpeed = 1 + 2*this.type;
        if(this.speed >= maxSpeed){return false;}
        int counter = 1;
        Point car = this.prev;
        while(counter < 7){
            if(car.type != 0 && car.type != 5){
                if(1 + 2*car.type > counter){return false;}
                break;
            }
            counter++;
            car = car.prev;
        }
        counter = 1;
        car = this.upper.prev;
        while(counter < 7){
            if(car.type != 0 && car.type != 5){
                if(1 + 2*car.type > counter){return false;}
                break;
            }
            counter++;
            car = car.prev;
        }
        counter = 1;
        car = this.upper.next;
        while(counter < 7){
            if(car.type != 0 && car.type != 5){
                if(this.speed > counter){return false;}
                break;
            }
            counter++;
            car = car.next;
        }
        this.moved = true;
        this.upper.moved = true;
        this.upper.speed = this.speed + 1;
        this.upper.type = this.type;
        this.speed = 0;
        this.type = 0;
        return true;
    }

    public boolean returning(){ //assuming its the left lane
        if(this.type == 0 || this.type == 5){return false;}
        int counter = 1;
        Point car = this.lower.prev;
        while(counter < 7){
            if(car.type != 0 && car.type != 5){
                if(1 + 2*car.type > counter){return false;}
                break;
            }
            counter++;
            car = car.prev;
        }
        counter = 1;
        car = this.prev;
        while(counter < 7){
            if(car.type != 0 && car.type != 5){
                if(1 + 2*car.type > counter){return false;}
                break;
            }
            counter++;
            car = car.prev;
        }
        counter = 1;
        car = car.lower.next;
        while(counter < 7){
            if(car.type != 0 && car.type != 5){
                if(this.speed > counter){return false;}
                break;
            }
            counter++;
            car = car.next;
        }
        this.moved = true;
        this.lower.moved = true;
        this.lower.speed = this.speed;
        this.lower.type = this.type;
        this.speed = 0;
        this.type = 0;
        return true;
    }


    public void clicked() {
        if(this.type != 5){
            this.type = (this.type + 1)%4;
            this.speed = 1 + 2*this.type;
        }
    }
    /*
    public void clicked() {
        this.type = 0;
    }*/
    public void clear() {
        this.type = 0;
        this.speed = 0;
    }
}

