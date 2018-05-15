package dodo.androidbells;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.*;
import java.lang.*;

public class Game extends Thread{

    private boolean gameRunning = true;

    private User user;
    private ArrayList<Platform> platforms;
    private ArrayList<Enemy> enemies;
    private ArrayList<Bullet> bullets;
    private Player player;

    private final double PLATFORM_SPAWNRATE = 1;

    private Display display;
    private SurfaceHolder surfaceHolder;
    final int WIDTH = 1000;
    final int HEIGHT = 2000;

    private final int BASE_LINE = 1500;

    boolean isScroll = false;
    boolean onFirstJump = true;


    public Game(Display gameSurface, SurfaceHolder surfaceHolder) {
        this.display = gameSurface;
        this.surfaceHolder = surfaceHolder;

        player = new Player();
        platforms = new ArrayList<>();
        bullets = new ArrayList<>();

    }

    @Override
    public void run()   {
        runLoop();
    }

    private void runLoop()   {
        long lastLoopTime = System.nanoTime();
        final int FPS = 60;
        final long TARGET = 1000000000 / FPS;
        long lastFpsTime = 0;
        int fps = 0;
        double platformTimer = 0;
        double scrollTimer = 0;

        while(gameRunning)  {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double)TARGET);
            lastFpsTime += updateLength;
            fps ++;
            Canvas canvas = null;
            if (lastFpsTime >= 1000000000)  {
                lastFpsTime = 0;
                fps = 0;
            }

            //TODO Update
            //some messed up timing code
            platformTimer += delta;
            if (platformTimer > Platform.SPAWN_RATE * 60) {
                spawnPlatform(platformTimer);
                platformTimer %= (int) (Platform.SPAWN_RATE* 60);
            }

            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (canvas){

                    if (isScroll) {
                        scrollTimer += delta;

                        player.scroll(BASE_LINE - player.getX(), .5);
                        for (Platform p: platforms) {
                            p.scroll(BASE_LINE - p.getX(), .5);
                        }
                        if (scrollTimer > 10) {
                            isScroll = false;
                            scrollTimer = 0;
                        }
                    }
                    else {
                        if (!onFirstJump) {
                            player.update(delta);
                            updateCollideable(delta, platforms, 0);
                            checkJump(delta);
                        } else {
                            player.updateFirstJump(delta);
                            updateCollideable(delta, platforms, 900);
                        }
                    }

                    if(checkEndGame(player))    {
                        gameRunning = false;
                        display.endGame();
                    }
                    display.draw(canvas);

                    //TODO Render
                }
            }catch(Exception e)    {
                Log.e("Game Loop","Failed to lock Canvas:" + e.getMessage());
            }
            finally {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
            try {Thread.sleep( (lastLoopTime-System.nanoTime() + TARGET/1000000));}
            catch(Exception e) {
            }
        }
    }

    private void spawnPlatform(double t)    {
        int platform_type = (int)(Math.random() * 100);
        int plat_location = (int)((Math.random() * WIDTH * .95) + 25);
        platforms.add(new Platform(plat_location, 1));
    }

    private void updateCollideable(double delta, ArrayList<? extends Collideable> list, int offset)   {
        Iterator<? extends Collideable> i = list.iterator();
        while(i.hasNext())  {
            Collideable ent = i.next();
            ent.update(delta);
            if (!Collideable.checkBounds(ent, 0, -400, WIDTH, HEIGHT - offset))   {
                i.remove();
            }
        }
    }

    private boolean checkJump(double delta)    {
        for (Platform plat : platforms)  {
            if(plat.checkCollision(player))  {
                double distance = calcScrollDistance();
                if (distance > 150) {
                    for (Platform p : platforms) {
                        p.setScrollVelocity(distance, 10 * delta);
                    }
                    player.setScrollVelocity(distance, 10 * delta);
                    isScroll = true;
                }
                player.jump();
                return true;
            }
        }
        return false;
    }

    private double calcScrollDistance()   {
        return BASE_LINE - player.getY();
    }

    private boolean checkEndGame(Player p)    {
        return p.getY() > HEIGHT;
    }


    //This is called bad design
    public Player getPlayer() {
        return player;
    }
    public ArrayList<Platform> getPlatforms()   {return platforms;}
}