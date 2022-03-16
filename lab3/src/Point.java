
public class Point {
    public static double p = 0.8;
    public int type;
    public Point next;
    public boolean moved;
    public int speed = 0;
    public void move() {
        if(this.type == 1){
            if( this.speed > 0 && (int) (Math.random() * 100) < p*100){
                this.speed -= 1;
            }
            this.moved = true;
            int j = 0;
            Point c = this.next;
            while(c.type == 0 && j<5 && j<this.speed + 1 && !c.moved){
                j++;
                c = c.next;
            }
            int rem_speed = this.speed;
            if(j > 0){
                this.speed = 0;
                this.type = 0;
            }
            else{return;}
            c = this.next;
            c.moved = true;
            int rem = j;
            while(j>0){
                c = c.next;
                c.moved = true;
                j--;
            }
            c.type = 1;
            if(rem > rem_speed){
                c.speed = rem_speed + 1;
            }
            else{
                c.speed = rem;
            }
        }
    }

    public void clicked() {
        this.type = 1;
    }

    public void clear() {
        this.type = 0;
    }
}

