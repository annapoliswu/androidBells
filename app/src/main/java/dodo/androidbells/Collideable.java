package dodo.androidbells;


import android.util.Log;

/**
 * Created by Anna on 4/15/2018.
 */

public abstract class Collideable {

    final public static int PLAYER_ID = 1;
    final public static int PLATFORM_ID = 2;

    private double xVelocity;
    private double yVelocity;
    private double x;
    private double y;
    private int width;
    private int height;
    private int id;

    private double yScrollVelocity;

    protected final double GLOBAL_ACCEL = .2;

    public Collideable(int a)    {
        this.id = a;
    }

    public int getID() {
        return this.id;
    }
    public void setSize(int a,int b)    {
        this.width = a;
        this.height = b;
    }

    public int getWidth()   {
        return this.width;
    }

    public int getHeight()  {
        return this.height;
    }

    public void setLocation(double a, double b) {
        this.x = a;
        this.y = b;
    }

    public double getX() {
        return this.x;
    }

    public double getY()   {
        return this.y;
    }

    public void setVelocity(double a, double b) {
        this.xVelocity = a;
        this.yVelocity = b;
    }

    public void setScrollVelocity(double distance, double time)    {
        this.yScrollVelocity = distance / time;
    }

    public double getScrollVelocity()   {
        return this.yScrollVelocity;
    }

    public void scroll(double distance, double time)    {
        this.setLocation(this.getX(), this.getY() + this.getScrollVelocity());

    }

    public double getXVelocity(){
        return xVelocity;
    }
    public double getYVelocity(){
        return yVelocity;
    }

    public void update(double delta)    {
        Log.d("d","I didn't declare this abstract out of laziness");
    }

    public boolean checkCollision(Collideable e){
        double x = this.getX() - this.getWidth()/2 - e.getWidth()/2;
        double y = this.getY() - this.getHeight()/2 - e.getHeight()/2;
        double x2 = this.getX() + this.getWidth()/2 + e.getWidth()/2;
        double y2 = this.getY() + this.getHeight()/2 + e.getHeight()/2;

        if (e.getX() > x && e.getX() < x2)  {
            if (e.getY() > y && e.getY() < y2)  {
                return true;
            }
        }
        return false;
    }

    public static boolean checkBounds(Collideable e, int left, int upper, int right, int lower)   {
        if (e.getX() < left || e.getX() > right)   {
            return false;
        }
        if (e.getY() < upper || e.getY() > lower)  {
            return false;
        }
        return true;
    }
}
