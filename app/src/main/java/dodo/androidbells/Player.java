package dodo.androidbells;

/**
 * Created by Anna on 4/15/2018.
 */

public class Player extends Collideable{

    private double targetX;
    private double targetY;

    private final int JUMP_HEIGHT = -12;


    public Player(){
        super(Collideable.PLAYER_ID);
        this.setLocation(500,1500);
        this.setTarget(500,1500);
        this.setVelocity(40, -20);
        this.setSize(100,100);
    }

    public void setTarget(double a, double b) {
        this.targetX = a;
        this.targetY = b;
    }

    public double getTargetX()    {
        return this.targetX;
    }

    public double getTargetY()  {
        return this.targetY;
    }

    @Override
    public void update(double delta)    {
        //I've disgusted myself

        this.setLocation(this.getX() + calcXLocation() * delta, calcGravity(delta));
    }

    public void updateFirstJump(double delta)    {
        this.setLocation(this.getX() + calcXLocation() * delta, 1500);

    }

    public double calcXLocation()  {
        double Vx;

        Vx = Math.abs(this.getTargetX() - this.getX()) < this.getWidth()/2 ? 0 : this.getXVelocity();
        Vx = (Math.abs(this.getTargetX() - this.getX()) < this.getXVelocity()) ? Math.abs(this.getTargetX() - this.getX()) : Vx;
        Vx = (this.getTargetX() - this.getX() < 0) ? -Vx: Vx;
        return Vx;
    }

    private double calcGravity(double delta)    {
        this.setVelocity(this.getXVelocity(), this.getYVelocity() + this.GLOBAL_ACCEL * delta);
        return this.getY() + this.getYVelocity() * delta;
    }

    public void jump()  {
        this.setVelocity(this.getXVelocity(), JUMP_HEIGHT);
    }




}
