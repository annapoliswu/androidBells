package dodo.androidbells;

/**
 * Created by Anna on 4/15/2018.
 */

public class Platform extends Collideable {

    private boolean jumpedOn;
    private final double FALL_VELOCITY = 9;
    private int jumpLimit;
    private double platformPerSecond = .8;
    public static final double SPAWN_RATE = 1;

    public Platform(double a, double b)   {
        super(Collideable.PLATFORM_ID);
        setLocation(a, b);
        setSize((int)(Math.random()*30 + 20), 20);
        setVelocity(0,FALL_VELOCITY);
        jumpedOn = false;
        jumpLimit = 2;
    }

    public Platform(double a, double b, int width, int height) {
        super(2);
        setLocation(a,b);
        setSize(width, height);
        setVelocity(0,FALL_VELOCITY);
        jumpedOn = false;
        jumpLimit = 2;
    }

    @Override
    public void update(double delta)    {
        this.setLocation(this.getX() + this.getXVelocity() * delta,
                this.getY() + this.getYVelocity() * delta);
    }

    public boolean getJumpedOn(){
        return jumpedOn;
    }

    public void setJumpedOn(boolean a){
        jumpedOn = a;
    }
    public int getJumpLimit(){
        return jumpLimit;
    }

    public void setJumpLimit(int limit){
        jumpLimit = limit;
    }

}
