package dodo.androidbells;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by DoDo on 4/16/18.
 */

public class Display extends SurfaceView implements SurfaceHolder.Callback {


    private Game gameThread;
    private Player player;
    private ArrayList<Platform> platforms;

    final private int WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    final private int HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;


    private int[] colors = {Color.BLACK, Color.RED, Color.GREEN, Color.CYAN};
    private double WIDTH_SCALE;
    private double  HEIGHT_SCALE;


    private final Context Game_Activity;

    public Display(Context context)    {
        super(context);
        this.setFocusable(true);
        this.getHolder().addCallback(this);
        Game_Activity = context;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        drawList(platforms, canvas);
        drawPlayer(player, canvas);

    }

    public void drawPlayer(Player p, Canvas canvas)   {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawRect((int)((p.getX() - p.getWidth()/2) * WIDTH_SCALE),(int)((p.getY() - p.getWidth()/2) * HEIGHT_SCALE),
                (int)((p.getX() + p.getWidth()/2)*WIDTH_SCALE),(int)((p.getY() + p.getHeight()/2)*HEIGHT_SCALE), paint);
    }

    public void drawList(ArrayList<? extends Collideable> list, Canvas canvas)    {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        for (Collideable p : list)    {
            canvas.drawRect((int)((p.getX() - p.getWidth()/2) * WIDTH_SCALE),(int)((p.getY() - p.getWidth()/2) * HEIGHT_SCALE),
                    (int)((p.getX() + p.getWidth()/2)*WIDTH_SCALE),(int)((p.getY() + p.getHeight()/2)*HEIGHT_SCALE), paint);
        }
    }

    public void endGame()   {
        Intent intent = new Intent(Game_Activity, EndScreenActivity.class);
        Game_Activity.startActivity(intent);
    }


    @Override
    public void surfaceCreated(SurfaceHolder h) {
        this.gameThread = new Game(this, h);
        this.gameThread.start();
        
        WIDTH_SCALE = (double)WIDTH / (double)gameThread.WIDTH;
        HEIGHT_SCALE = (double)HEIGHT / (double)gameThread.HEIGHT;
        
        player = gameThread.getPlayer();
        platforms = gameThread.getPlatforms();
    }

    @Override
    public void surfaceChanged(SurfaceHolder h, int f, int width, int height)   {}

    @Override
    public void surfaceDestroyed(SurfaceHolder h)   {}

    @Override
    public boolean onTouchEvent(MotionEvent e)  {

        switch (e.getAction())  {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int xpos = (int) (e.getX() * (1/WIDTH_SCALE) + player.getXVelocity());
                int ypos = (int) (e.getY() * (1/HEIGHT_SCALE) + player.getYVelocity());
                Log.d(""+WIDTH_SCALE,""+HEIGHT_SCALE);
                player.setTarget(xpos - player.getWidth() / 2, 1500);
                break;
            case MotionEvent.ACTION_UP:
                if (gameThread.onFirstJump) {
                    player.setVelocity(player.getXVelocity(), -15);
                    gameThread.onFirstJump = false;
                }

        }
        return true;
    }


}
