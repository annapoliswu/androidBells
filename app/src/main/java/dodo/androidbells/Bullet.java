package dodo.androidbells;

/**
 * Created by DoDo on 4/22/18.
 */

public class Bullet extends Collideable {

    public Bullet(double a, double b) {
        super(0);
        setLocation(a,b);
        setVelocity(0,-15);
        setSize(4,4);
    }

}
